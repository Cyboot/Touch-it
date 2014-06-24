package de.timweb.touchit;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameEngineCanvas extends Canvas implements Runnable {
	private static final long	TARGET_DELTA	= 10;

	private Game				game			= new Game();

	public static void main(String[] args) {
		GameEngineCanvas canvas = new GameEngineCanvas();

		JFrame frame = new JFrame("Touch it!");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(canvas);
		frame.setContentPane(panel);
		frame.setUndecorated(true);
		frame.pack();
		// frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		new Thread(canvas).start();
	}

	public GameEngineCanvas() {
		Dimension dim = new Dimension(640, 480);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
	}

	@Override
	public void run() {
		long delta = 0;

		while (true) {
			long start = System.currentTimeMillis();

			game.update(delta);

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

		g.setColor(Color.blue);
		g.drawString("Test", 50, 50);

		game.render(g);

		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
}
