package hr.fer.decaptcha.symbol_separation;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ISymbolSeparator {

	/**
	 * Method which separates symbols in image.
	 * @param image reference to image which contains symbols to be separated.
	 * @return reference to collection of separated images.
	 */
	List<BufferedImage> separateSymbols(final BufferedImage image);
}
