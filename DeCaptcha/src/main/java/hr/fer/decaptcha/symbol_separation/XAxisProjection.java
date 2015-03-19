package hr.fer.decaptcha.symbol_separation;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.List;

public class XAxisProjection implements ISymbolSeparator {

	public List<BufferedImage> separateSymbols(BufferedImage image) {
		
		int[] histogramColumn = generateHistogram(image);
		
		for(int i = 0; i < histogramColumn.length; i++) {
			System.out.println("Index[" + i + "]: " + histogramColumn[i]);
		}
		
		return null;
	}

	/**
	 * Method which generates histogram values from given gray scale image.
	 * @param image reference to GRAY SCALE image.
	 * @return 	array of int where index of array represents column in image, while
	 * 			value of array represents number of black pixels in corresponding column.
	 */
	private int[] generateHistogram(BufferedImage image) {
		
		Raster raster = image.getData();
		
		int imageWidth = raster.getWidth();
		int imageHeight = raster.getHeight();
		
		int[] histogram = new int[imageWidth];
		
		for(int i = 0; i < imageWidth; i++) {
			int count = 0;
			for(int j = 0; j < imageHeight; j++) {
				if(raster.getSample(i, j, 0) == 1) count++;
			}
			
			histogram[i] = count;
		}
		
		return histogram;
	}

}
