package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.constants.Boundry;
import hr.fer.decaptcha.constants.Constant;
import hr.fer.decaptcha.histogram.Histogram;

import java.awt.image.BufferedImage;

/**
 * Class which is used to trim unnecessary parts of image which are not symbols.
 * @author Janko
 *
 */
public class AxisTrimmer implements IClutterRemoval {
	private final Axis axis;

	/**
	 * Constructs new axis trimmer.
	 * @param axis image will be trimmet with respect to this axis. 
	 */
	public AxisTrimmer(final Axis axis) {
		this.axis = axis;
	}

	public BufferedImage removeClutter(BufferedImage image) {

		int[] histogram = Histogram.generateHistogram(image, this.axis);

		int upper = Histogram.findBoundy(histogram, Boundry.UPPER, Constant.PIXEL_THRESHOLD, Constant.PIXEL_OFFSET);
		int bottom = Histogram.findBoundy(histogram, Boundry.BOTTOM, Constant.PIXEL_THRESHOLD, Constant.PIXEL_OFFSET);

		/* Note: Subimage of image shares the same data array as the original image. */
		return image.getSubimage(bottom, 0, upper-bottom, image.getHeight());
	}
}
