package hr.fer.decaptcha.test;

import hr.fer.decaptcha.constants.Boundary;
import hr.fer.decaptcha.histogram.Histogram;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class HistogramTest {

	@Test
	public void upperBoundryTest() {
		int[] histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 4, 0, 0};
		Assert.assertEquals(10, histogram[Histogram.findBoundy(histogram, Boundary.UPPER, 5, 1)]);
		histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 4, 0, 0};
		Assert.assertEquals(4, histogram[Histogram.findBoundy(histogram, Boundary.UPPER, 2, 1)]);
	}
	
	@Test
	public void bottomBoundryTest() {
		int[] histogram = new int[] {0, 0, 0, 3, 5, 10, 15, 10, 1, 0, 0};
		Assert.assertEquals(4, Histogram.findBoundy(histogram, Boundary.BOTTOM, 5, 1));
		Assert.assertEquals(3, Histogram.findBoundy(histogram, Boundary.BOTTOM, 2, 1));
	}
	
	@Test
	public void areaTest() {
		
		int[] histogram = new int[] {0, 2, 4, 3, 0, 10, 15, 10, 1, 0, 5, 0, 0, 0};
		
		Pair<Integer,Integer> pair = Histogram.findAreaAboveTreshold(histogram, 0, 2);
		
		Assert.assertEquals(2, histogram[pair.getLeft()]);
		Assert.assertEquals(3, histogram[pair.getRight()]);
		
		pair = Histogram.findAreaAboveTreshold(histogram, pair.getRight() + 1, 2);
		
		Assert.assertEquals(10, histogram[pair.getLeft()]);
		Assert.assertEquals(10, histogram[pair.getRight()]);
		
		pair = Histogram.findAreaAboveTreshold(histogram, 11, 2);
	}

}
