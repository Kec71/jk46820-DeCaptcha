package hr.fer.decaptcha.clutter_removal;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Erode implements IClutterRemoval {

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
		Raster inputRaster = inputImage.getData();
		
		BufferedImage outputImage = new BufferedImage(inputRaster.getWidth(), inputRaster.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		outputRaster = erode(inputRaster, outputRaster);
		outputImage.setData(outputRaster);
		
		return outputImage;
	}

	private WritableRaster erode(Raster inputRaster, WritableRaster outputRaster) {
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		for(int i = 0; i < imageWidth; i++) {
			for(int j = 0; j < imageHeight; j++) {
				
				if(inputRaster.getSample(i, j, 0) == 1) {
					
					if(i <= 0 || i >= imageWidth-1 || j <= 0 || j >= imageHeight-1) continue;
						
					/* Check surrounding pixels - if they are all black (1) then set pixel to black (1) */
					if(	inputRaster.getSample(i, j+1, 0) == 1 &&
						inputRaster.getSample(i, j-1, 0) == 1 &&
						inputRaster.getSample(i+1, j, 0) == 1 &&
						inputRaster.getSample(i-1, j, 0) == 1 ) {

						outputRaster.setSample(i, j, 0, 1);
					}
					
				}
				
			}
		}
		
		return outputRaster;
	}
}
