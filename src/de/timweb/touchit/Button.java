package de.timweb.touchit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button {
	private int				x, y;
	private int				width, height;
	private boolean			active	= true;
	private boolean			isPressed;
	private String			text;
	private BufferedImage	img;

	public Button(int x, int y, int width, int height, String text) {
		this(x, y, width, height, text, null);
	}

	public Button(int x, int y, int width, int height, BufferedImage img) {
		this(x, y, width, height, null, img);
	}

	private Button(int x, int y, int width, int height, String text, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.img = img;
	}

	public void update(long delta) {
		if (active) {
			InputHandler input = InputHandler.getInstance();
			if (input.isPressed() && checkMouse(input.getClickPoint())) {
				isPressed = true;
			}
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean checkPressed() {
		if (isPressed) {
			isPressed = false;
			return true;
		}
		return false;
	}

	private boolean checkMouse(Point point) {
		Rectangle rect = new Rectangle(x, y, width, height);

		if (rect.contains(point)) {
			return true;
		}
		return false;
	}

	public void render(Graphics g) {
		if (active) {
			if (img != null) {
				boolean hover = checkMouse(InputHandler.getInstance().getMouseHoverPoint());

				if (hover)
					g.drawImage(img, x, y, width, height, Color.darkGray, null);
				else
					g.drawImage(img, x, y, width, height, null);

			}
			if (text != null) {
				g.setColor(Color.white);
				g.drawRect(x, y, width, height);
				g.drawString(text, x + 10, y + 15);
			}
		}
	}
}
