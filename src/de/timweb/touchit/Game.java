package de.timweb.touchit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import de.timweb.touchit.util.ImageLoader;

public class Game {
	public static final int	MODUS_3x3	= 0;
	public static final int	MODUS_4x3	= 1;
	public static final int	MODUS_5x4	= 2;
	public static final int	HIT_DEFAULT	= 20;
	NumberFormat			formatter	= new DecimalFormat("#0.00");

	private List<HitCircle>	circles		= new ArrayList<>();
	private int				hitCount;
	private int				maxHit;
	private int				time;
	private Button			button_restart;
	private Button			button_close;
	private Button			button_3x3;
	private Button			button_4x3;
	private Button			button_5x4;
	private Font			font;
	private int				currentModus;
	private int				width;
	private int				height;
	private Font			fontBig;


	public void start() {
		hitCount = 0;
		time = 0;
		initField();
		initButton();
	}

	public void restart(int maxHit) {
		this.maxHit = maxHit;

		initField();
		chooseNewCircle(null);
		hitCount = 0;
		time = 0;

		initButton();
	}

	private void initField() {
		width = MainWindow.getInstance().getWidth();
		height = MainWindow.getInstance().getHeight();
		int maxX = 0;
		int maxY = 0;

		switch (currentModus) {
		case MODUS_3x3:
			maxX = 3;
			maxY = 3;
			break;
		case MODUS_4x3:
			maxX = 4;
			maxY = 3;
			break;
		case MODUS_5x4:
			maxX = 5;
			maxY = 4;
			break;
		}

		int maxRadius_x = width / (maxX + 2) / 2;
		int maxRadius_y = height / (maxY + 1) / 2;

		int radius = Math.min(maxRadius_x, maxRadius_y);

		circles.clear();
		for (int dx = 0; dx < maxX; dx++) {
			for (int dy = 0; dy < maxY; dy++) {
				double x = (dx * width + 0.5 * width) / maxX;
				double y = (dy * height + 0.5 * height) / maxY;

				circles.add(new HitCircle((int) x, (int) y, radius));
			}
		}
	}

	private void initButton() {
		final int BUTTON_SIZE = 48;

		button_3x3 = new Button(0, 120, BUTTON_SIZE, BUTTON_SIZE, ImageLoader.circle_3);
		button_4x3 = new Button(0, 120, BUTTON_SIZE, BUTTON_SIZE, ImageLoader.circle_4);
		button_5x4 = new Button(0, 120, BUTTON_SIZE, BUTTON_SIZE, ImageLoader.circle_5);
		button_3x3.setActive(false);
		button_5x4.setActive(false);
		button_4x3.setActive(false);

		button_restart = new Button(0, 60, BUTTON_SIZE, BUTTON_SIZE, ImageLoader.restart);
		button_restart.setActive(false);

		button_close = new Button(width - BUTTON_SIZE, 0, BUTTON_SIZE, BUTTON_SIZE,
				ImageLoader.close);
		button_close.setActive(false);

		font = new JLabel().getFont().deriveFont(Font.BOLD, 18);
		fontBig = new JLabel().getFont().deriveFont(Font.BOLD, 36);
	}

	public void render(Graphics g) {
		for (HitCircle c : circles) {
			c.render(g);
		}
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("Hit: " + hitCount + " / " + maxHit, 5, 20);

		g.drawString(formatter.format(time / 1000.) + " s", 5, 45);
		button_restart.render(g);

		if (hitCount >= maxHit) {
			g.setFont(fontBig);
			g.drawString(formatter.format(time / 1000.) + " s", width / 2 - 50, height / 2);
			button_restart.render(g);
		}

		button_close.render(g);
		button_3x3.render(g);
		button_4x3.render(g);
		button_5x4.render(g);
	}

	public void update(int delta) {

		if (hitCount >= maxHit) {
			for (HitCircle c : circles) {
				c.reset();
			}

			button_3x3.update(delta);
			button_4x3.update(delta);
			button_5x4.update(delta);

			switch (currentModus) {
			case MODUS_3x3:
				button_3x3.setActive(false);
				button_4x3.setActive(true);
				button_5x4.setActive(false);
				break;
			case MODUS_4x3:
				button_3x3.setActive(false);
				button_4x3.setActive(false);
				button_5x4.setActive(true);
				break;
			case MODUS_5x4:
				button_3x3.setActive(true);
				button_4x3.setActive(false);
				button_5x4.setActive(false);
				break;
			}
			if (button_4x3.checkPressed()) {
				currentModus = MODUS_4x3;
				initField();
			}
			if (button_5x4.checkPressed()) {
				currentModus = MODUS_5x4;
				initField();
			}
			if (button_3x3.checkPressed()) {
				currentModus = MODUS_3x3;
				initField();
			}


			button_restart.setActive(true);
			button_close.setActive(true);

			button_close.update(delta);

			if (button_close.checkPressed()) {
				System.exit(0);
			}

			button_restart.update(delta);

			if (button_restart.checkPressed()) {
				restart(HIT_DEFAULT);
			}

			InputHandler.getInstance().update(delta);
			return;
		}

		if (hitCount > 0)
			time += delta;


		HitCircle hit = null;

		for (HitCircle c : circles) {
			c.update(delta);
			if (c.wasHit()) {
				hit = c;
			}
		}
		if (hit != null) {
			hitCount++;
			if (hitCount != maxHit) {
				chooseNewCircle(hit);
			}
		}

		InputHandler.getInstance().update(delta);
	}

	private void chooseNewCircle(HitCircle hit) {
		int random;
		HitCircle nextCircle;

		do {
			random = RandomUtil.nextInt(circles.size());
			nextCircle = circles.get(random);
		} while (hit == nextCircle);

		nextCircle.activate();
	}
}
