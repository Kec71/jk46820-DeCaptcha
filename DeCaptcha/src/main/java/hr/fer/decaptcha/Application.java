package hr.fer.decaptcha;

import hr.fer.decaptcha.clutter_removal.AxisTrimmer;
import hr.fer.decaptcha.clutter_removal.Dilate;
import hr.fer.decaptcha.clutter_removal.Erode;
import hr.fer.decaptcha.clutter_removal.IClutterRemoval;
import hr.fer.decaptcha.clutter_removal.MedianFilter;
import hr.fer.decaptcha.constants.Axis;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
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
			
			image = convertImage(BufferedImage.TYPE_BYTE_GRAY, ImageIO.read(inputFile));
			
			clutterRemoval = new MedianFilter();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Erode();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new Dilate();
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new AxisTrimmer(Axis.X_AXIS);
			image = clutterRemoval.removeClutter(image);
			
			clutterRemoval = new AxisTrimmer(Axis.Y_AXIS);
			image = clutterRemoval.removeClutter(image);
			
//			ISymbolSeparator separator = new XAxisProjection();
//			separator.separateSymbols(image);
			
			ImageIO.write(image, "png", outputFile);
			
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
	
	public static void printImage(BufferedImage image) {
		for(int i=0; i<image.getWidth(); i++) {
			for(int j=0; j<image.getHeight(); j++) {
				int[] buff = new int[3];
				image.getRaster().getPixel(i, j, buff);
				System.out.print(buff[0]);
				
				if(buff[1] != 0) System.out.println("Dodatna komponenta");
			}
			
			System.out.println();
		}
	}
	
	/**
	 * Method which converts given image to given type.
	 * @param type type of image for given image to be converted to.
	 * @param oldImage reference to image to be converted.
	 * @return reference to new, converted image.
	 */
	public static BufferedImage convertImage(int type, BufferedImage oldImage) {
		BufferedImage convertedImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), type);
		
		ColorConvertOp op = new ColorConvertOp(
				oldImage.getColorModel().getColorSpace(),
				convertedImage.getColorModel().getColorSpace(),
				null);
		
		op.filter(oldImage, convertedImage);
		
		return convertedImage;
	}

}
