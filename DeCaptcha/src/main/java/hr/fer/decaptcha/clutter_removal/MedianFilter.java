package hr.fer.decaptcha.clutter_removal;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;

public class MedianFilter implements IClutterRemoval {

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
		/* Represents mask size which will be used to sample surrounding pixel values */
		final int maskSize = 3;
		Raster inputRaster = inputImage.getData();
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		/* Create blank buffered image which represents image without clutter and set its size same as input image */
		BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		for(int x = 0; x < imageWidth; x++) {
			for(int y = 0; y < imageHeight; y++) {
				
				int pixelValue = calculatePixelValue(inputRaster, maskSize, imageWidth, imageHeight, x, y);
				
				/* Zero represents gray-scale value of sample */
				outputRaster.setSample(x, y, 0, pixelValue);
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
		ArrayList<Integer> sample = new ArrayList<Integer>();
		
		for(int i = x - offset; i <= x + offset; i++) {
			for(int j = y - offset; j <= y + offset; j++) {
				
				/* If pixel is on edge of raster then skip it */
				if(i < 0 || i >= imageWidth || j < 0 || j >= imageHeight) continue;
				
				sample.add(inputRaster.getSample(i, j, 0));
			}
		}
		
		Collections.sort(sample);
		return sample.get(sample.size()/2);
	}
}
