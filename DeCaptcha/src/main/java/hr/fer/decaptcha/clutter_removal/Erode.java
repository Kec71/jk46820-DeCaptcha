package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * <p>Erosion is one of the two basic operators in the area of mathematical morphology, the other being {@link Dilate}.</p>
 * <p>It is typically applied to binary images, but there are versions that work on grayscale images. 
 * The basic effect of the operator on a binary image is to erode away the boundaries of regions of foreground pixels (i.e. white pixels, typically).
 * Thus areas of foreground pixels shrink in size, and holes within those areas become larger.</p>
 * 
 * <p>This method is often used with technique {@link Dilate}.</p>
 * @author Janko
 *
 */
public class Erode implements IClutterRemoval {

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
		Raster inputRaster = inputImage.getData();
		
		/* Create blank buffered image which represents image without clutter and set its size same as input image */
		BufferedImage outputImage = new BufferedImage(inputRaster.getWidth(), inputRaster.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		outputRaster = erode(inputRaster, outputRaster);
		outputImage.setData(outputRaster);
		
		return outputImage;
	}

	/**
	 * Erodes the image which is written in raster.
	 * @param inputRaster reference to raster which contains image.
	 * @param outputRaster reference to blank output raster where dilated image will be written in.
	 * @return reference to modified output raster.
	 */
	private WritableRaster erode(Raster inputRaster, WritableRaster outputRaster) {
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		int[] pixelData = new int[1];
		
		for(int i = 0; i < imageWidth; i++) {
			for(int j = 0; j < imageHeight; j++) {
					
				/* Check surrounding pixels - if one of them is white then set pixel to white (erode it) */
				if(	inputRaster.getPixel(i, j, pixelData)[0] == Constant.PIXEL_BLACK && 
				   (inputRaster.getPixel(i+1, j+1, pixelData)[0] == Constant.PIXEL_WHITE 	||
					inputRaster.getPixel(i+1, j, pixelData)[0] == Constant.PIXEL_WHITE 		||
					inputRaster.getPixel(i+1, j-1, pixelData)[0] == Constant.PIXEL_WHITE 	||
					inputRaster.getPixel(i, j+1, pixelData)[0] == Constant.PIXEL_WHITE 		||
					inputRaster.getPixel(i, j-1, pixelData)[0] == Constant.PIXEL_WHITE 		||
					inputRaster.getPixel(i-1, j+1, pixelData)[0] == Constant.PIXEL_WHITE 	||
					inputRaster.getPixel(i-1, j, pixelData)[0] == Constant.PIXEL_WHITE 		||
					inputRaster.getPixel(i-1, j-1, pixelData)[0] == Constant.PIXEL_WHITE) ) {
						outputRaster.setPixel(i, j, new int[] {Constant.PIXEL_WHITE});
				} 
				/* Otherwise set pixel to current pixel value */
				else {
					outputRaster.setPixel(i, j, inputRaster.getPixel(i, j, pixelData));
				}
			}
		}
		
		return outputRaster;
	}
}
