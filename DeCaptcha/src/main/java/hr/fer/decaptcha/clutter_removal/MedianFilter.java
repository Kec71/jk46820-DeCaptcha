package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * <p>Class which represents clutter removal technique called median filter.</p>
 * <p>This technique uses mask of particular size to determine value of new pixel of image at index x, y.
 * Pixel at index x,y is placed at center of mask. Mask spans over several other pixels.
 * All the pixels which masks spans over are used to determine new pixel value at index x, y.
 * New pixel value is median of all pixel values which mask spawns over.</p>
 * @author Janko
 *
 */
public class MedianFilter implements IClutterRemoval {
	
	static Logger log = Logger.getLogger(MedianFilter.class);

	public BufferedImage removeClutter(BufferedImage inputImage) {
		log.info("Applying median filter to image.");
		
		/* Represents mask size which will be used to sample surrounding pixel values */
		final int maskSize = 3;
		Raster inputRaster = inputImage.getData();
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		/* Create blank buffered image which represents image without clutter and set its size same as input image */
		BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		for(int x = 0; x < imageWidth; x++) {
			for(int y = 0; y < imageHeight; y++) {
				
				int pixelValue = calculatePixelValue(inputRaster, maskSize, imageWidth, imageHeight, x, y);
				
				/* Patch to exclude all gray values */
				if(pixelValue != Constant.PIXEL_WHITE && pixelValue != Constant.PIXEL_BLACK) {
					outputRaster.setPixel(x, y, new int[]{Constant.PIXEL_WHITE});
				} else {
					outputRaster.setPixel(x, y, new int[]{pixelValue}); 
				}
			}
		} 
		
		outputImage.setData(outputRaster);
		return outputImage;
	}

	/**
	 * Method which calculates pixel value using median filter.
	 * @param inputRaster reference to raster which contains pixels which will determine pixel value.
	 * @param maskSize size of mask which represents number of pixels which will be used to sample surrounding pixel values.
	 * @param imageWidth width of image.
	 * @param imageHeight height of image.
	 * @param x x-coordinate of target pixel.
	 * @param y y-coordinate of target pixel.
	 * @return value of pixel at x,y coordinates using median filter technique.
	 */
	private int calculatePixelValue(Raster inputRaster, int maskSize, int imageWidth, int imageHeight, int x, int y) {
		
		int offset = maskSize/2;
		// Zamjeniti sa poljem
		List<Integer> sample = new ArrayList<Integer>();
		
		for(int i = x - offset; i <= x + offset; i++) {
			for(int j = y - offset; j <= y + offset; j++) {
				
				/* If pixel is on edge of raster then skip it */
				if(i < 0 || i >= imageWidth || j < 0 || j >= imageHeight) continue;

				int[] pixelData = new int[1];
				inputRaster.getPixel(i, j, pixelData);
				
				sample.add(pixelData[0]);
			}
		}
		
		Collections.sort(sample);
		return sample.get(sample.size()/2);
	}
}
