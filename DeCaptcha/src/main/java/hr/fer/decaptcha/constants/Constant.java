package hr.fer.decaptcha.constants;

public class Constant {
	
	public static int PIXEL_WHITE = 255;
	public static int PIXEL_BLACK = 0;
	
	/** Threshold which is used to determine how many projected black pixels should be interpretated
	 * as symbol in image histogram */
	public static int PIXEL_THRESHOLD = 3;
	
	/** Offset which is used to determine how consequent histogram values must be above threshold for
	 * histogram index to represent boundary */
	public static int PIXEL_OFFSET = 3;
	
	/** Represents output symbol image width */
	public static int IMAGE_WIDTH = 30;
	/** Represents output symbol image height */
	public static int IMAGE_HEIGHT = 30;
}
