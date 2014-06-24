package de.timweb.touchit;

import java.awt.Color;
import java.awt.Graphics;

public class Game {
	double	x	= 0;

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, (int) x, 10);
	}

	public void update(long delta) {
		x += delta * 0.5;
		if (x > 500)
			x = 0;
	}

}
