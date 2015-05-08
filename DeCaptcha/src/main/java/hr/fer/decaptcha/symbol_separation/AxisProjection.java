package hr.fer.decaptcha.symbol_separation;

import hr.fer.decaptcha.constants.Axis;
import hr.fer.decaptcha.constants.Constant;
import hr.fer.decaptcha.histogram.Histogram;
import hr.fer.decaptcha.image.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

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
		
		List<BufferedImage> symbolImageList = new ArrayList<>(); 
		int[] histogram = Histogram.generateHistogram(image, Axis.X_AXIS);
		
		int startIndex = 0;
		while(true) {
			Pair<Integer, Integer> boundaries = Histogram.findAreaAboveTreshold(histogram, startIndex, Constant.PIXEL_THRESHOLD);

			/* If we reached end of histogram then store remaining part of image. */
			if(boundaries.getLeft() >= boundaries.getRight()) {
				BufferedImage im = ImageUtil.copyImage(image, boundaries.getLeft(), 0, image.getWidth()-boundaries.getLeft(), image.getHeight());
				symbolImageList.add(ImageUtil.resizeImage(im, Constant.IMAGE_WIDTH, Constant.IMAGE_HEIGHT));
				break;
			} 
			/* Otherwise store subimage and set new starting index to search next symbol. */
			else {
				BufferedImage im = ImageUtil.copyImage(image, boundaries.getLeft(), 0, boundaries.getRight()-boundaries.getLeft(), image.getHeight());
				symbolImageList.add(ImageUtil.resizeImage(im, Constant.IMAGE_WIDTH, Constant.IMAGE_HEIGHT));
				startIndex = boundaries.getRight() + 1;
			}
		}
		
		return symbolImageList;
	}

}
