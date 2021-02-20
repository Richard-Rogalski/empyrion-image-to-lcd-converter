import kotlin.jvm.JvmStatic
import java.awt.image.BufferedImage
import java.io.*
import java.lang.StringBuilder
import javax.imageio.ImageIO
import kotlin.system.exitProcess

/**
 * @author Aslantis, ClevorTrevor
 * @version 1.0
 * Takes an image and converts it to whatever an LCD takes to have that image put in Empyrion. Proudly written in Vim.
 * OG Coded for Java 8 & Compiled with OpenJDK
 */

object LCDImage {
    private const val bufferMaxLength = 1993
    private const val panelWidth = 10
    private const val panelHeight = 10
    private const val panelHeader = "<line-height=148%><size=194%>"
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            println("Please enter the image you would like to convert as a command-line argument")
            return
        }
        val img: BufferedImage? //The input image
        val baseName: String
        val imageFileName = args[0]

        //getting image
        try {
            val file = File(imageFileName)
            baseName = file.nameWithoutExtension
            img = ImageIO.read(file)
          }
        catch (ioe: IOException) {
            println("I/O error reading image, make sure image name and extension are spelled correctly or try using a different image and re-run the program")
            exitProcess(1)
        }
        val w = img.width
        val h = img.height
        println("Successfully found & loaded image $imageFileName width=$w height=$h")
        val alphaApplied = img.isAlphaPremultiplied
        val panelRowCount = (h + panelHeight - 1) / panelHeight
        val panelColumnCount = (w + panelWidth - 1) / panelWidth
        for (panelRow in 0 until panelRowCount) {
            for (panelColumn in 0 until panelColumnCount) {
                val startX = panelColumn * panelWidth
                val startY = panelRow * panelHeight
                var subImageWidth = w - (panelColumn * panelWidth)
                if (subImageWidth > panelWidth) subImageWidth = panelWidth
                var subImageHeight = h - (panelRow * panelHeight)
                if (subImageHeight > panelHeight) subImageHeight = panelHeight
                val rgbArray = img.getRGB(startX, startY, subImageWidth, subImageHeight, null, 0 , subImageWidth)
                val panelText = createPanelText(rgbArray, subImageWidth, alphaApplied)

                println("Text for panel($panelRow, $panelColumn) width=1 height=1 fontSize=16")
                println(panelText)
                println("Size for panel($panelRow, $panelColumn) is ${panelText.length}")
                if (baseName.isNotBlank()) {
                    try {
                        val textFileName: String
                        if (panelRowCount == 1 && panelColumnCount == 1) {
                            textFileName = "$baseName.txt"
                        }
                        else  {
                            textFileName = "$baseName-$panelRow$panelColumn.txt"
                        }

                        println("Saving file $textFileName...")
                        val textFile = File(textFileName)
                        textFile.createNewFile()
                        textFile.writeText(panelText)
                        println("Successfully wrote to file!")
                    } catch (ioe: IOException) {
                        println("something broke and it probably did not write to file. idk why anymore than you do")
                    }
                }
            }
        }
        exitProcess(0)
    }

    // scanSize is the length of a row in the array
    private fun createPanelText(rgbArray: IntArray, scanSize: Int, alphaApplied: Boolean): String {
        val panelText = StringBuilder(bufferMaxLength)
        panelText.append(panelHeader)
        panelText.append(System.lineSeparator())
        var currentColor = rgbArray[0]
        panelText.append(getHexColorString(currentColor, alphaApplied))
        for ((index, color) in rgbArray.withIndex()) {
            if (currentColor != color) {
                panelText.append(getHexColorString(color, alphaApplied))
                currentColor = color
            }
            panelText.append("█")
            val scanX = (index + 1) % scanSize
            if (scanX == 0) {
                panelText.append(System.lineSeparator())
            }
        }
        return panelText.toString()
    }

    private fun getHexColorString(color: Int, alphaApplied: Boolean): String {
        var hexString = String.format("%06X", color and 0xffffff)
        // extract alpha from top 2 hex digits
        if (alphaApplied) {
            val alpha = color and -0x1000000 ushr 24
            val alphaString = String.format("%02X", alpha)
            if (alpha != 255) {
                hexString += alphaString
            }
        }

        return "<color=#$hexString>"
    }
}
