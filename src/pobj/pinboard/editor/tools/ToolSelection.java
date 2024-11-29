package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool{
	private double oriX, oriY, deltaX, deltaY;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		oriX = e.getX();
		oriY = e.getY();
		
		//initialisaition avec des memes valeurs que ori

		deltaX = 0.0;
		deltaY = 0.0;
		
		if(e.isShiftDown()) { //ToogleSelect
			i.getSelection().toogleSelect(i.getBoard(), oriX, oriY);
		}else {
			i.getSelection().select(i.getBoard(), oriX, oriY);
		}
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		deltaX = e.getX() - oriX;
		deltaY = e.getY() - oriY;
		
		for(Clip clip : i.getSelection().getContents()) {
			clip.move(deltaX, deltaY);
		}
		oriX = e.getX();
		oriY = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {		
		i.getSelection().drawFeedback(i, gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return null;
	}

}
