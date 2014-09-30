package de.timweb.touchit.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.timweb.touchit.MainWindow;

public class ImageLoader {
	public static BufferedImage	restart;
	public static BufferedImage	close;
	public static BufferedImage	circle_3;
	public static BufferedImage	circle_4;
	public static BufferedImage	circle_5;

	public static void init() {
		try {
			restart = readImage("restart2.png");
			close = readImage("close.png");
			circle_3 = readImage("3.png");
			circle_4 = readImage("4.png");
			circle_5 = readImage("5.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage readImage(final String res) throws IOException {
		return ImageIO.read(MainWindow.class.getResource("/" + res));
	}
}