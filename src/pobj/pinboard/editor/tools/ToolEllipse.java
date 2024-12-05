package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;

public class ToolEllipse implements Tool{

	private double oriX, oriY, endX, endY;
	private Color color;
	public ToolEllipse(Color color) {
		this.color = color;
	}
	

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		oriX = e.getX();
		oriY = e.getY();
		
		endX = e.getX();
		endY = e.getX();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		endX = e.getX();
		endY = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		endX = e.getX();
		endY = e.getY();
		
		//ajout de rectangle final à la planche
		i.getBoard().addClip(new ClipEllipse(Math.min(oriX, endX), Math.min(oriY, endY), Math.max(oriX, endX), Math.max(oriY, endY), this.color));
		
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		if(this.color == Color.BLACK) {
			gc.setStroke(Color.RED);
		}else {
			gc.setStroke(Color.BLACK);
		}
		gc.setLineWidth(1.0);
		
		gc.strokeOval(Math.min(oriX, endX), Math.min(oriY, endY), Math.abs(endX-oriX), Math.abs(endY-oriY));
		
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Ellipse Tool";
	}

}
