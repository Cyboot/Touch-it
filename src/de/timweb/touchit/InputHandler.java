package de.timweb.touchit;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {
	private static InputHandler	instance;

	private Point				click_point	= new Point();
	private Point				mouse_point	= new Point();

	private InputHandler() {
	}

	public synchronized Point getClickPoint() {
		return click_point;
	}

	public Point getMouseHoverPoint() {
		return mouse_point;
	}

	public synchronized boolean isPressed() {
		return click_point != null;
	}

	public synchronized void update(int delta) {
		click_point = null;
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		click_point = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public static InputHandler getInstance() {
		if (null == instance) {
			instance = new InputHandler();
		}
		return instance;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse_point.setLocation(e.getPoint());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}
}
