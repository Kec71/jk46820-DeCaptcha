package hr.fer.decaptcha.clutter_removal;

import java.awt.image.BufferedImage;

public interface IClutterRemoval {
	
	/**
	 * Method which removes unnecessary clutter from picture.
	 * @param image reference to image which will have unnecessary clutter removed.
	 * @return reference to new image with clutter removed.
	 */
	public BufferedImage removeClutter(BufferedImage image);

}
