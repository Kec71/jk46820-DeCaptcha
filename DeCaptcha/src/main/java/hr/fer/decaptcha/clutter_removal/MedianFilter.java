package hr.fer.decaptcha.clutter_removal;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class MedianFilter implements IClutterRemoval {

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
		/* Represents mask size which will be used to sample surrounding pixel values */
		final int maskSize = 3;
		Raster inputRaster = inputImage.getData();
		
		/* Create blank buffered image which represents image without clutter and set its size same as input image */
		BufferedImage outputImage = new BufferedImage(inputRaster.getWidth(), inputRaster.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		for(int x = 0; x < inputRaster.getWidth(); x++) {
			for(int y = 0; y < inputRaster.getHeight(); y++) {
				
				int pixelValue = calculatePixelValue(inputRaster, maskSize, x, y);
				/* Zero represents gray-scale value of sample */
				outputRaster.setSample(x, y, 0, pixelValue);
			}
		}
		
		return outputImage;
	}

	/**
	 * Method which calculates pixel value using median filter.
	 * @param inputRaster reference to raster which contains pixels which will determine pixel value.
	 * @param maskSize size of mask which represents number of pixels which will be used to sample surrounding pixel values.
	 * @param x x-coordinate of target pixel.
	 * @param y y-coordinate of target pixel.
	 * @return value of pixel at x,y coordinates using median filter technique.
	 */
	private int calculatePixelValue(Raster inputRaster, int maskSize, int x, int y) {
		
		int maxHeight = inputRaster.getHeight();
		int maxWidth = inputRaster.getWidth();
		
		int offset = maskSize/2;
		int[] sample = new int[maskSize*maskSize];
		
		for(int i = x - offset; i <= x + offset; i++) {
			for(int j = y - offset; j <= y + offset; j++) {
				
				/* If pixel is on edge of raster then skip it */
				if(i < 0 || i > maxWidth || j < 0 || j > maxHeight) continue;
				
				try {
					sample[i*offset + j] = inputRaster.getSample(i, j, 0);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("AIOBE na x: " + i + ", y: " + j);
				}
			}
		}
		
		Arrays.sort(sample);
		int median = sample[sample.length/2];
		
		return median;
	}
}
