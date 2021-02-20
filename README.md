# empyrion-image-to-lcd-converter
Converts an image to text that LCD's can display in Empyrion Galactic Survival. Huge amount of thanks to ClevorTrevor for his huge amount of help & work on this program.
Note: Since Empyrion has an arbitrary limit of 2K characters per LCD, this program will only 100% take only 1 projector/LCD block if the image is around the size of 11x11. If it takes more than 2K characters, this program will automatically give you what you would need to put in per each projector/LCD. Will fit a lot more on one screen if images have consecutive pixels with the *exact* same hex color value. Mainly designed as a tool to add pixel art to Empyrion blueprints; although technically possible to add full HD pictures (if you're willing to use a *lot* of projectors).

# Install (easier than it looks I promise)
Requires java to run. Can install here if not already installed https://www.oracle.com/java/technologies/javase-jre8-downloads.html (most likely you've installed it already for something in the past)

If on windows, there is usually the extra step of setting java's path. Don't worry, this step shouldn't take more than a minute or two https://explainjava.com/set-java-path-and-java-home-windows/ (follow for both java home and path)

Compile this program or *download the ready to go release right here* https://github.com/Richard-Rogalski/empyrion-image-to-lcd-converter/releases

# Use
Put the LCDImage.jar you just downloaded and any images you want to make into LCD's into the same folder. I'd recommend making a folder for this, but you could just keep everything in your downloads if you prefer. Open a terminal in that folder (if on windows you could use the command prompt shortcut) and type

java -jar LCDImage.jar *nameofimage.png*

Works with .jpg and other image types too. Last step? Enjoy!
