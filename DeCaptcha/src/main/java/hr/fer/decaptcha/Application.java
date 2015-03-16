package hr.fer.decaptcha;

import hr.fer.decaptcha.clutter_removal.IClutterRemoval;
import hr.fer.decaptcha.clutter_removal.MedianFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Application {

	public static void main(String[] args) {
		
		BufferedImage image = null;
		
		File inputFile = new File("demo_images/captcha_demo_1.png");
		File outputFile = new File("demo_output/output_image.png");
		
		IClutterRemoval clutterRemoval = new MedianFilter();
		
		try {
			
			image = ImageIO.read(inputFile);
			image = clutterRemoval.removeClutter(image);
			ImageIO.write(image, "png", outputFile);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error occured while trying to read or write file.");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
