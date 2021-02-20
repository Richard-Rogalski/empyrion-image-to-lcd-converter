import java.io.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 *@author Aslantis
 *@version 0.2
 *Takes an image and converts it to whatever an LCD takes to have that image put in Empyrion. Proudly written in Vim.
 *Coded for Java 8. Compiled with OpenJDK
 */
public class LCDImage{
	public static void main(String[] args){
		String imageName;//name of the input image. don't think i need to spell that one out for ya
		BufferedImage img;//The input image
		File file;//file of input image
		String LCD;//The output for use in an LCD
		
		//getting image name
		try{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);//i know theres a cleaner way to do this, but it works ig
			System.out.println("Please enter the image you would like to convert (i.e. flower.png)");
			imageName = br.readLine();
		}
		catch(IOException ioe){
			System.out.println("There was an I/O exception. Please make sure you entered the image name with extension and of type .png or .jpg/.jpeg and run again");
			imageName = "";
		}

		//getting image
		try{
			file = new File(imageName);
			img = ImageIO.read(file);
			System.out.println("Successfully found & loaded image " + imageName);
		}
		catch(IOException ioe){
			System.out.println("I/O error reading image, make sure image name and extension are spelled correctly or try using a different image and re-run the program");
			img = null;
		}

		int w = img.getWidth(null);//width
		int h = img.getHeight(null);//height
		int color = img.getRGB(0, 0);//color in RGB (alpha not yet supported)

		if(color == 0) LCD = "<line-height=80%>";
		else LCD = "<line-height=80%><color=#" + getLCDColor(img,0,0) + ">"; //starting statements
		//this is the actual part that converts an image into LCD "code" that is pasteable into a projector etc
		//█ <-- the "pixel" unicode char
		//Also please note i have 0 idea what I'm doing, please feel free to DM me a better approach
		for(int y = 0; y < h; y++){ 
			for(int x = 0; x < w; x++){
				String paste = "";
				//System.out.println(Integer.toHexString(img.getRGB(x, y)).substring(2)); //just use for debugging
				if(img.getRGB(x, y) == 0)
					paste += "░";
				else if(color == img.getRGB(x, y)){
					paste += "█"; //If color is the same as the last pixel, just insert another without changing the color
					//LCD += String.valueOf(Character.toChars(2588)); this code is kinda buggy and isn't needed if saving to a file. may fix later
				}
				else{
					paste += "<color=#" + getLCDColor(img,x,y) + ">█"; //If color is different insert new color changing statement and then the pixel
				}
				
				LCD += paste;
				color = img.getRGB(x, y);
			}
			LCD += System.lineSeparator(); //once we reach a new line of pixels in our image, we create a new line for our LCD text to mimic that
		}
		System.out.println("\n" + LCD);
		System.out.print("Would you like to save the output to a .txt file? (y/n)");
		String saveToFile;
		//getting if typed a y
		try{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);//yes, its still messy
			saveToFile = br.readLine();
		}
		catch(IOException ioe){
			System.out.println("There was an I/O exception. This will only save it to a file if you enter y");
			saveToFile = "n";
		}
		//if they did, then this code will write the output to a file
		if(saveToFile.toLowerCase().equals("y")){
			System.out.println("Saving file...");
			File textFile;
			try{
				textFile = new File(imageName + ".txt");
				textFile.createNewFile();
				FileWriter writer = new FileWriter(imageName + ".txt");
				writer.write(LCD);
				writer.close();
				System.out.println("Successfully wrote to file!");
			}
			catch(IOException ioe){
				System.out.println("someting broke and it probably didnt write to file. idk why anymore than you do");
			}
		}
	}
	
	public static String getLCDColor(BufferedImage img,int x,int y){
		String temp = Integer.toHexString(img.getRGB(x, y)).substring(2);
		if(temp.length() == 5)
			temp = temp.substring(0, 4) + "0" + temp.substring(4);
		if(temp.substring(1,2).equals("0") && temp.substring(3,4).equals("0") && temp.substring(5).equals("0"))
			temp = (temp.substring(0,1) + temp.substring(2,3) + temp.substring(4,5));
		return temp;
	}
}
