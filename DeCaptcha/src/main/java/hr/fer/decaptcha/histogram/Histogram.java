package hr.fer.decaptcha.histogram;

import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.constants.Boundry;
import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import org.apache.commons.lang3.ArrayUtils;

public class Histogram {

	/**
	 * <p>Method which generates histogram from image. </p>
	 * @param image reference to {@link BufferedImage.TYPE_BYTE_GRAY} image which will be used to generate histogram.
	 * @param axis represent axis on which will {@link Constant#PIXEL_BLACK} pixels be projected.
	 * @return 	reference to int array which represents histogram of image;<br>
	 * 			index of array represents index of image (with respect to given axis)<br>
	 * 			while value of array repesents number of {@link Constant#PIXEL_BLACK} pixels. 
	 * @throws IllegalArgumentException if type of buffered image is not {@link BufferedImage#TYPE_BYTE_GRAY}
	 */
	public static int[] generateHistogram(BufferedImage image, Axis axis) throws IllegalArgumentException {
		
		if(image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
			throw new IllegalArgumentException("Invalid image type while trying to generate histogram.\n"
					+ "Expected:  " + BufferedImage.TYPE_BYTE_GRAY + ", but got: " + image.getType() + ".");
		}
		
		int projectionAbscisa = axis.equals(Axis.X_AXIS) ? image.getWidth() : image.getHeight();
		int projectionOrdinate = axis.equals(Axis.X_AXIS) ? image.getHeight() : image.getWidth();
		
		int[] histogram = new int[projectionAbscisa];
		
		Raster raster = image.getData();
		for(int i = 0; i < projectionAbscisa; i++) {
			int pixelCount = 0;
			for(int j = 0; j < projectionOrdinate; j++) {
				if(raster.getPixel(i, j, new int[1])[0] == Constant.PIXEL_BLACK) pixelCount++;
			}
			histogram[i] = pixelCount;
		}
		
		return histogram;
	}
	
	/**
	 * <p>Method which determines upper or lower boundry for given histogram.</p>
	 * <p>Boundry is defined as index of histogram after which there is a sequence of histogram values above given treshold.</p>
	 * @param histogram reference to histogram which contains values.
	 * @param boundry determines whether upper or lower boundry is searched.
	 * @param threshold represents treshold of histogram.
	 * @param offset represents how many indexes of histogram must be above or equal to threshold in order for this to be boundry.
	 * @return index in histogram which represents boundry.
	 * 
	 * @see Boundry
	 */
	public static int findBoundy(final int[] histogram, Boundry boundry, int threshold, int offset) {
		
		int index = 0;
		
		/* Reverse histogram in order to treat it the same way as if bottom boundry is being searched. */
		if(boundry == Boundry.UPPER) {
			ArrayUtils.reverse(histogram);
		}
		
		int aboveTresholdCount = 0;
		for(int i = 0; i < histogram.length; i++) {
			
			if(histogram[i] >= threshold) {
				aboveTresholdCount++;
			} else {
				aboveTresholdCount = 0;
			}
			
			if(aboveTresholdCount == offset) {
				/* Several consequent values above treshold have been found.
				 * Boundry had been found, return its index. */
				index = i - offset + 1;
				break;
			}
			
		}
		
		/* Neutralize reversion of histogram */
		if(boundry == Boundry.UPPER) {
			ArrayUtils.reverse(histogram);
			index = histogram.length - index - 1;
		}
		
		return index;
	}
	
}
