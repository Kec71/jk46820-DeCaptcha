package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.apache.log4j.Logger;

/**
 * <p>Dilation is one of the two basic operators in the area of mathematical morphology, the other being {@link Erode}.</p> 
 * <p>It is typically applied to binary images, but there are versions that work on grayscale images. 
 * The basic effect of the operator on a binary image is to gradually enlarge the boundaries of regions of foreground pixels (i.e. white pixels, typically).
 * Thus areas of foreground pixels grow in size while holes within those regions become smaller.</p>
 * 
 * <p>This method is often used with technique {@link Erode}.</p>
 * @author Janko
 *
 */
public class Dilate implements IClutterRemoval {
	
	static Logger log = Logger.getLogger(Dilate.class); 

	public BufferedImage removeClutter(BufferedImage inputImage) {
		log.info("Applying dilitation on image.");
		
		Raster inputRaster = inputImage.getData();
		
		/* Create blank buffered image which represents image without clutter and set its size same as input image */
		BufferedImage outputImage = new BufferedImage(inputRaster.getWidth(), inputRaster.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		outputRaster = dilate(inputRaster, outputRaster);
		outputImage.setData(outputRaster);
		
		return outputImage;
	}

	/**
	 * Dilates the image which is written in raster.
	 * @param inputRaster reference to raster which contains image.
	 * @param outputRaster reference to blank output raster where dilated image will be written in.
	 * @return reference to modified output raster.
	 */
	private WritableRaster dilate(Raster inputRaster, WritableRaster outputRaster) {
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		int[] pixelData = new int[1];
		
		for(int x = 0; x < imageWidth; x++) {
			for(int y = 0; y < imageHeight; y++) {
				
				inputRaster.getPixel(x, y, pixelData);
				outputRaster.setPixel(x, y, pixelData);
				
				if(pixelData[0] == Constant.PIXEL_BLACK) {
					
					if(x > 0) outputRaster.setPixel(x-1, y, new int[] {Constant.PIXEL_BLACK});
					if(y > 0) outputRaster.setPixel(x, y-1, new int[] {Constant.PIXEL_BLACK});
					if(x+1 < imageWidth) outputRaster.setPixel(x+1, y, new int[] {Constant.PIXEL_BLACK});
					if(y+1 < imageHeight) outputRaster.setPixel(x, y+1, new int[] {Constant.PIXEL_BLACK});
				} 
			}
		}
		
		return outputRaster;
	}
}
