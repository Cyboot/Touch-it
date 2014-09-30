package de.timweb.touchit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import de.timweb.touchit.util.ImageLoader;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas implements Runnable {
	private static final long	TARGET_DELTA	= 10;

	private Game				game;

	public GameCanvas() {
		setBackground(Color.black);
		addMouseListener(InputHandler.getInstance());
		addMouseMotionListener(InputHandler.getInstance());

		setFocusable(true);
		ImageLoader.init();
	}

	@Override
	public void run() {
		long delta = 0;
		game = new Game();
		game.start();

		while (true) {
			long start = System.currentTimeMillis();

			game.update((int) delta);

			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				continue;
			}
			render(bs.getDrawGraphics());
			if (bs != null)
				bs.show();

			long timepassed = System.currentTimeMillis() - start;
			if (timepassed < TARGET_DELTA) {
				try {
					Thread.sleep(TARGET_DELTA - timepassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			delta = System.currentTimeMillis() - start;
		}
	}

	private void render(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			try {
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} catch (Exception e) {
				System.err.println("Antialias failed for displaying the Font");
			}
		}
		game.render(g);

		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}

	public void start() {
		new Thread(this).start();
	}
}
