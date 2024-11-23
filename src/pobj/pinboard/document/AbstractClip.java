package pobj.pinboard.document;

import javafx.scene.paint.Color;

public abstract class AbstractClip {
	private double left, top, right, bottom;
	private Color color;
	public AbstractClip(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
		this.color = color;
	}
	
	public double getTop() {
		return this.top;
	}

	public double getLeft() {
		return this.left;
	}

	public double getBottom() {
		return this.bottom;
	}

	public double getRight() {
		return this.right;
	}

	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public void move(double x, double y) {
		this.left += x;
		this.right += x;
		this.top += y;
		this.bottom += y;
	}
	
	public void setColor(Color c) {
		this.color = c;
		
	}

	public Color getColor() {
		return this.color;
	}
	
	public boolean isSelected(double x, double y) {
		return this.left<=x && x <= this.right && this.top <= y && y <= this.bottom;
	}
}
