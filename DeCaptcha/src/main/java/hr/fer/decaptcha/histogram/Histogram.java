package hr.fer.decaptcha.histogram;

import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.constants.Boundary;
import hr.fer.decaptcha.constants.Constant;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

public class Histogram {
	
	static Logger log = Logger.getLogger(Histogram.class);

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
		log.info("Generating histogram of image for " + (axis.equals(Axis.X_AXIS) ? "X axis" : "Y axis") + ".");
		
		if(image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
			throw new IllegalArgumentException("Invalid image type while trying to generate histogram.\n"
					+ "Expected:  " + BufferedImage.TYPE_BYTE_GRAY + ", but got: " + image.getType() + ".");
		}
		
		int projectionApscisa = axis == Axis.X_AXIS ? image.getWidth() : image.getHeight();
		int projectionOrdinate = axis == Axis.X_AXIS ? image.getHeight() : image.getWidth();
		
		int[] histogram = new int[projectionApscisa];
		
		/* Implementirati if-else slucaj za X os i Y os*/
		Raster raster = image.getData();
		for(int x = 0; x < projectionApscisa; x++) {
			int pixelCount = 0;

			for(int y = 0; y < projectionOrdinate; y++) {
				if(axis == Axis.X_AXIS && raster.getPixel(x, y, new int[1])[0] == Constant.PIXEL_BLACK) pixelCount++;
				if(axis == Axis.Y_AXIS && raster.getPixel(y, x, new int[1])[0] == Constant.PIXEL_BLACK) pixelCount++;
			}
			
			histogram[x] = pixelCount;
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
	 * @return index in histogram which represents boundry or 0 if index cannot be found.
	 * 
	 * @see Boundary
	 */
	public static int findBoundy(final int[] histogram, Boundary boundry, int threshold, int offset) {
		
		log.info("Searching for " + (boundry.equals(Boundary.UPPER) ? "upper" : "bottom") + " boundry "
				+ "[treshold = " +  threshold + ", offset = " + offset + "].");
		
		int index = 0;
		
		/* Reverse histogram in order to treat it the same way as if bottom boundry is being searched. */
		if(boundry == Boundary.UPPER) {
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
		if(boundry == Boundary.UPPER) {
			ArrayUtils.reverse(histogram);
			index = histogram.length - index - 1;
		}
		
		return index;
	}
	
	/**
	 * <p>Method which determines next area above threshold starting from starting index in given histogram.</p>
	 * @param histogram reference to histogram.
	 * @param startingIndex starting index of search.
	 * @param threshold value which will determine area. All histogram values equal to or above threshold will be included.
	 * @return reference to pair object with attributes {@link Pair#getLeft()} as index of areas left boundary <br>
	 * 			and	{@link Pair#getRight()} as index of areas right boundary.<br>
	 *          If there is no area above threshold then this method will return Pair with {@link Pair#getLeft()} value<br>
	 *          equal or bigger than {@link Pair#getRight()}.
	 */
	public static Pair<Integer, Integer> findAreaAboveTreshold(final int[] histogram, int startingIndex, int threshold) {
		log.info("Searching for area above treshold in histogram [size = " + histogram.length + "] for starting index = " + startingIndex + ".");
		
		if(startingIndex > histogram.length) throw new IllegalArgumentException("Starting index is bigger than histogram lenght.");
		/* Eliminate all histogram values before starting index. */
		int[] subHistogram = Arrays.copyOfRange(histogram, startingIndex, histogram.length-1);
		
		int start = -1;
		int end = -1;
		
		for(int i = 0; i < subHistogram.length; i++) {
			
			if((subHistogram[i] >= threshold) && start == -1) {
				start = i;
				continue;
			} 
			
			if ((subHistogram[i] < threshold) && start != -1) {
				/* Position yourself to previous index since we just exited the treshold area. */
				end = i - 1;
				break;
			}
		}
		
		Pair<Integer, Integer> pair = new MutablePair<>(startingIndex + start, startingIndex + end);
		return pair;
	}
	
}
