package hr.fer.decaptcha.image.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import org.apache.log4j.Logger;

public class ImageUtil {
	
	static Logger log = Logger.getLogger(ImageUtil.class);
	
	/**
	 * Method which converts given image to given type.
	 * @param type type of image for given image to be converted to.
	 * @param oldImage reference to image to be converted.
	 * @return reference to new, converted image.
	 */
	public static BufferedImage convertImage(int type, BufferedImage oldImage) {
		log.info("Converting image to type: " + type + ".");
		BufferedImage convertedImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), type);
		
		ColorConvertOp op = new ColorConvertOp(
				oldImage.getColorModel().getColorSpace(),
				convertedImage.getColorModel().getColorSpace(),
				null);
		
		op.filter(oldImage, convertedImage);
		
		return convertedImage;
	}
	
	/**
	 * <p>Method which copies image.</p>
	 * <p>Reason why this method is used instead of {@link BufferedImage#getSubimage(int, int, int, int)} is because
	 * this method creates new instance of buffered image which doesn't share data array with original image.</p>
	 * @param source reference to source image which will be cropped.
	 * @param x see {@link BufferedImage#getSubimage(int, int, int, int)} 
	 * @param y see {@link BufferedImage#getSubimage(int, int, int, int)} 
	 * @param width see {@link BufferedImage#getSubimage(int, int, int, int)} 
	 * @param height see {@link BufferedImage#getSubimage(int, int, int, int)}
	 * @return reference to new cropped buffered image.
	 */
	public static BufferedImage copyImage(BufferedImage source, int x, int y, int width, int height) {
		log.info("Copying image with new dimensions: x = " + x + ", y = " + y + ", width = " + width + ", height = " + height + ".");
		BufferedImage subImage = source.getSubimage(x, y, width, height); 
		BufferedImage copyOfImage = new BufferedImage(subImage.getWidth(), subImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(subImage, 0, 0, null);
		return copyOfImage;
	}
	
	
	/**
	 * <p>Method which resizes image to given dimensions.</p>
	 * @param source reference to image to be resized.
	 * @param width wanted width of new image.
	 * @param height wanted height of new image.
	 * @return reference to new, resized image.
	 */
	public static BufferedImage resizeImage(BufferedImage source, int width, int height) {
		log.info("Resizing image to width = " + width + ", height = " + height + ".");
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(source, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		return resizedImage;
	}
}
