package de.timweb.touchit;

import java.util.Random;

public class RandomUtil {
	private static Random	random	= new Random(123);

	public static int nextInt(int max) {
		return random.nextInt(max);
	}
}
