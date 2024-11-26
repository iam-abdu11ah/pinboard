package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool{
	
	private double oriX, oriY, endX, endY;

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
		i.getBoard().addClip(new ClipRect(Math.min(oriX, endX), Math.min(oriY, endY), Math.max(oriX, endX), Math.max(oriY, endY), Color.BLUE));
		
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(1.0);
		
		gc.strokeRect(Math.min(oriX, endX), Math.min(oriY, endY), Math.abs(endX-oriX), Math.abs(endY-oriY));
		
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Rectangl Tool";
	}

}
