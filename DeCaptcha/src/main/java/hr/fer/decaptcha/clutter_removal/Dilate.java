package hr.fer.decaptcha.clutter_removal;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Dilate implements IClutterRemoval {

	public BufferedImage removeClutter(BufferedImage inputImage) {
		
		Raster inputRaster = inputImage.getData();
		
		BufferedImage outputImage = new BufferedImage(inputRaster.getWidth(), inputRaster.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster outputRaster = (WritableRaster) outputImage.getData();
		
		outputRaster = dilate(inputRaster, outputRaster);
		outputImage.setData(outputRaster);
		
		return outputImage;
	}

	private WritableRaster dilate(Raster inputRaster, WritableRaster outputRaster) {
		
		int imageWidth = inputRaster.getWidth();
		int imageHeight = inputRaster.getHeight();
		
		for(int i = 0; i < imageWidth; i++) {
			for(int j = 0; j < imageHeight; j++) {
				
				if(inputRaster.getSample(i, j, 0) == 1) {
					outputRaster.setSample(i, j, 0, 1);
					
					if(i > 0) outputRaster.setSample(i-1, j, 0, 1);
					if(j > 0) outputRaster.setSample(i, j-1, 0, 1);
					if(i+1 < imageWidth) outputRaster.setSample(i+1, j, 0, 1);
					if(j+1 < imageHeight) outputRaster.setSample(i, j+1, 0, 1);
				}
				
			}
		}
		
		return outputRaster;
	}
}
