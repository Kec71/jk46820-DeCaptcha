package hr.fer.decaptcha;

import hr.fer.decaptcha.clutter_removal.Dilate;
import hr.fer.decaptcha.clutter_removal.Erode;
import hr.fer.decaptcha.clutter_removal.GaussianBlur;
import hr.fer.decaptcha.clutter_removal.IClutterRemoval;
import hr.fer.decaptcha.clutter_removal.MedianFilter;
import hr.fer.decaptcha.symbol_separation.XAxisProjection;
import hr.fer.decaptcha.symbol_separation.ISymbolSeparator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Application {

	public static void main(String[] args) {
		
		BufferedImage image = null;
		IClutterRemoval clutterRemoval = null;
				
		File inputFile = new File("demo_images/captcha_demo_1.png");
		File outputFile = new File("demo_output/output_image.png");
		
		try {
			
			image = ImageIO.read(inputFile);
			
			clutterRemoval = new MedianFilter();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new GaussianBlur();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Erode();
			//image = clutterRemoval.removeClutter(image);
			
			
			clutterRemoval = new Dilate();
			//image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Dilate();
			//image = clutterRemoval.removeClutter(image);
			
			ISymbolSeparator separator = new XAxisProjection();
			//separator.separateSymbols(image);
			
			ImageIO.write(image, "png", outputFile);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error occured while trying to read or write file.");
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println("Error occured while trying to access non-existing index.");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
