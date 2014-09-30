package de.timweb.touchit;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainWindow extends Window {
	private static MainWindow	instance;

	private MainWindow(Component gamecanvas) {
		super(new JFrame());

		add(gamecanvas);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);
		// setBounds(0, 0, screenSize.width, screenSize.height - 55);
		// setBounds(-1600, 0, 1280, 720);

		JFrame owner = (JFrame) getOwner();
		owner.setState(JFrame.MAXIMIZED_BOTH);
	}

	public JFrame getFrame() {
		return (JFrame) getOwner();
	}

	public static MainWindow getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		GameCanvas gamecanvas = new GameCanvas();

		instance = new MainWindow(gamecanvas);
		instance.setVisible(true);

		gamecanvas.start();
	}
}