package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

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

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
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
		
		for(int i = 0; i < imageWidth; i++) {
			for(int j = 0; j < imageHeight; j++) {
				
				if(inputRaster.getPixel(i, j, pixelData)[0] == Constant.PIXEL_BLACK) {
					
					outputRaster.setPixel(i, j, new int[] {Constant.PIXEL_BLACK});
					
					if(i > 0) outputRaster.setPixel(i-1, j, new int[] {Constant.PIXEL_BLACK});
					if(j > 0) outputRaster.setPixel(i, j-1, new int[] {Constant.PIXEL_BLACK});
					if(i+1 < imageWidth) outputRaster.setPixel(i+1, j, new int[] {Constant.PIXEL_BLACK});
					if(j+1 < imageHeight) outputRaster.setPixel(i, j+1, new int[] {Constant.PIXEL_BLACK});
				} else {
					outputRaster.setPixel(i, j, inputRaster.getPixel(i, j, pixelData));
				}
				
			}
		}
		
		return outputRaster;
	}
}
