# empyrion-image-to-lcd-converter
Converts an image to text that LCD's can display in Empyrion Galactic Survival. Huge amount of thanks to ClevorTrevor for his huge amount of help & work on this program.
Note: Since Empyrion has an arbitrary limit of ~2K characters per LCD, this program will only 100% work on images around the size of 11x11. Will work on larger images if many consecutive pixels have the *exact* same hex value. Mainly designed as a tool for pixel art for Empyrion blueprints.

# Use
Requires kotlin. Please install kotlin from https://github.com/JetBrains/kotlin/releases/tag/v1.4.30 
After compiling or grabbing the release, open a terminal (command prompt on windows). Then type in the following:

kotlin LCDImage thenameofyourimage.png

Works with .png .jpg etc. Make sure both LCDImage.class & the image you want to use are in the same folder, and make sure that your terminal was opened in that folder too
