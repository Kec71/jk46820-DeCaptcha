package hr.fer.decaptcha.clutter_removal;

import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.constants.Boundary;
import hr.fer.decaptcha.constants.Constant;
import hr.fer.decaptcha.histogram.Histogram;
import hr.fer.decaptcha.image.util.ImageUtil;

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

		int upper = Histogram.findBoundy(histogram, Boundary.UPPER, Constant.PIXEL_THRESHOLD, Constant.PIXEL_OFFSET);
		int bottom = Histogram.findBoundy(histogram, Boundary.BOTTOM, Constant.PIXEL_THRESHOLD, Constant.PIXEL_OFFSET);

		BufferedImage outputImage = null;
		
		if(axis.equals(Axis.X_AXIS)) {
			outputImage = ImageUtil.copyImage(image, bottom, 0, upper-bottom, image.getHeight());
		} else if(axis.equals(Axis.Y_AXIS)){
			outputImage = ImageUtil.copyImage(image, 0, bottom, image.getWidth(), upper-bottom);
		}
		
		return outputImage;
	}
}
