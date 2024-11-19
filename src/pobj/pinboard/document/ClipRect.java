package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect implements Clip{

	//coordonnées du rectangle(window) englobant ce rectangle. 
		private double left, top, right, bottom;
		private Color color;
		
		
		public ClipRect(double left, double top, double right, double bottom, Color color) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			
			this.color = color;
		}

		@Override
		public void draw(GraphicsContext ctx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public double getTop() {
			return this.top;
		}

		@Override
		public double getLeft() {
			return this.left;
		}

		@Override
		public double getBottom() {
			return this.bottom;
		}

		@Override
		public double getRight() {
			return this.right;
		}

		@Override
		public void setGeometry(double left, double top, double right, double bottom) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			
		}

		@Override
		public void move(double x, double y) {
			this.left += x;
			this.right += x;
			this.top += y;
			this.bottom += y;
			
		}

		@Override
		public boolean isSelected(double x, double y) {
			return false;
		}

		@Override
		public void setColor(Color c) {
			this.color = c;
			
		}

		@Override
		public Color getColor() {
			return this.color;
		}

		@Override
		public Clip copy() {
			
			return new ClipRect(this.left, this.top, this.right, this.bottom, this.color);
		}

}
