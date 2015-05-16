package hr.fer.decaptcha;

import hr.fer.decaptcha.clutter_removal.AxisTrimmer;
import hr.fer.decaptcha.clutter_removal.Dilate;
import hr.fer.decaptcha.clutter_removal.Erode;
import hr.fer.decaptcha.clutter_removal.IClutterRemoval;
import hr.fer.decaptcha.clutter_removal.MedianFilter;
import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.image.util.ImageUtil;
import hr.fer.decaptcha.symbol_separation.AxisProjection;
import hr.fer.decaptcha.symbol_separation.ISymbolSeparator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class Application {
	
	static Logger log = Logger.getLogger(Application.class); 

	public static void main(String[] args) {
		
		BufferedImage image = null;
		IClutterRemoval clutterRemoval = null;
		ISymbolSeparator separator = null;
				
		File inputFile = new File("demo_images/image2.png");
		File outputFile = new File("demo_output/image_" + System.currentTimeMillis() % 1000 + ".png");
		
		try {
			
			log.info("Loading image from: " + inputFile);
			image = ImageUtil.convertImage(BufferedImage.TYPE_BYTE_GRAY, ImageIO.read(inputFile));
			
			clutterRemoval = new MedianFilter();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Erode();
			image = clutterRemoval.removeClutter(image);
			
			ImageIO.write(image, "png", outputFile);
			
			/* Reorganizirati. Metoda koja vraca int sa granicama. Ideja je eliminirati meduslike. */
			clutterRemoval = new AxisTrimmer(Axis.X_AXIS);
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new AxisTrimmer(Axis.Y_AXIS);
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Dilate();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new MedianFilter();
			image = clutterRemoval.removeClutter(image);
			
			separator = new AxisProjection();
			List<BufferedImage> symbols = separator.separateSymbols(image);
			
			for(BufferedImage symbol : symbols) {
				Thread.sleep(10);
				File symbolFile = new File("demo_output/symbol_" + System.currentTimeMillis() % 1000 + ".png");
				log.info("Saving symbol to: " + symbolFile);
				ImageIO.write(symbol, "png", symbolFile);
			}
			
			ImageIO.write(image, "png", new File("demo_output/image_test.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error occured while trying to read or write in file.");
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println("Error occured while trying to access non-existing index.");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
