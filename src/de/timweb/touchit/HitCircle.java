package de.timweb.touchit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class HitCircle {
	private static final int	HIT_ANIMATION_TIME	= 500;
	private int					x, y;
	private int					radius;
	private int					hitTimeLeft;

	private boolean				active				= false;
	private boolean				wasHit				= false;


	public HitCircle(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public void activate() {
		active = true;
	}

	public boolean wasHit() {
		if (wasHit) {
			wasHit = false;
			return true;
		}
		return false;
	}

	public void update(long delta) {
		hitTimeLeft -= delta;

		if (active) {
			InputHandler input = InputHandler.getInstance();
			if (input.isPressed()) {
				checkMouse(input.getClickPoint());
			}
		}
	}

	public void reset() {
		hitTimeLeft = 0;
		active = false;
	}


	public void render(Graphics g) {
		if (hitTimeLeft > 0) {
			g.setColor(Color.green);
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		} else if (active) {
			g.setColor(Color.red);
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		} else {
			g.setColor(Color.yellow);
			g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
		}
	}

	public void checkMouse(Point point) {
		double distX = x - point.x;
		double distY = y - point.y;

		double dist = Math.sqrt(distX * distX + distY * distY);

		// Circle is hit
		if (dist < radius) {
			hitTimeLeft = HIT_ANIMATION_TIME;
			active = false;
			wasHit = true;
		}
	}
}
