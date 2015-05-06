package hr.fer.decaptcha.symbol_separation;

import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.List;

/**
 * <p>Class which is used as symbol separation technique.</p>
 * <p>This class separates gray scale image by projecting pixels which represent symbols (usually black pixels) to X axis.
 * Histogram is created based on those projections and threshold is defined.
 * If histogram value at index x is below threshold then this represents start or end of symbol.</p> 
 * 
 * <p>It is assumed that image is preprocessed and that clutter is removed from image.</p>
 *  
 * @author Janko
 *
 */
public class AxisProjection implements ISymbolSeparator {

	public List<BufferedImage> separateSymbols(final BufferedImage image) {
		
		int[] histogramColumn = generateHistogram(image);
		
		for(int i = 0; i < histogramColumn.length; i++) {
			System.out.println("Index[" + i + "]: " + histogramColumn[i]);
		}
		
		return null;
	}

	/**
	 * Method which generates histogram values from given gray scale image.
	 * @param image reference to GRAY SCALE image.
	 * @return 	array of int where index of array represents column of image, while
	 * 			value of array represents number of black pixels in corresponding column.
	 */
	private int[] generateHistogram(BufferedImage image) {
		
		Raster raster = image.getData();
		
		int imageWidth = raster.getWidth();
		int imageHeight = raster.getHeight();
		
		int[] histogram = new int[imageWidth];
		int[] pixelData = new int[1];
		
		for(int i = 0; i < imageWidth; i++) {
			int count = 0;
			for(int j = 0; j < imageHeight; j++) {
				if(raster.getPixel(i, j, pixelData)[0] == Constant.PIXEL_BLACK) count++;
			}
			
			histogram[i] = count;
		}
		
		return histogram;
	}

}
