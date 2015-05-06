package hr.fer.decaptcha.test;

import hr.fer.decaptcha.constants.Boundry;
import hr.fer.decaptcha.histogram.Histogram;

import org.junit.Assert;
import org.junit.Test;

public class HistogramTest {

	@Test
	public void upperBoundryTest() {
		int[] histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 4, 0, 0};
		Assert.assertEquals(7, Histogram.findBoundy(histogram, Boundry.UPPER, 5, 1));
		histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 4, 0, 0};
		Assert.assertEquals(8, Histogram.findBoundy(histogram, Boundry.UPPER, 2, 1));
	}
	
	@Test
	public void bottomBoundryTest() {
		int[] histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 1, 0, 0};
		Assert.assertEquals(4, Histogram.findBoundy(histogram, Boundry.BOTTOM, 5, 1));
		Assert.assertEquals(3, Histogram.findBoundy(histogram, Boundry.BOTTOM, 2, 1));
	}

}
