package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
	private List<Clip> clipSelectionne ;
	public Selection() {
		clipSelectionne = new ArrayList<>();
	}
	
	public void select(Board board, double x, double y) {
		for(Clip clip : board.getContents()) {
			if(clip.isSelected(x, y)) {
				clipSelectionne.add(clip);
				break; //car on selecte le premier élément graphique de la planche
			}
		}
	}
	
	public void toggleSelect(Board board, double x, double y) {
		for(Clip clip : board.getContents()) {
			if(clip.isSelected(x, y)) {
				if(clipSelectionne.contains(clip)) {
					clipSelectionne.remove(clip); //si deuxieme press sur le meme élément on le déselect
				}
				else {
					clipSelectionne.add(clip);
					break; //toujours le premier element de la planche
				}				
			}
		}
	}
	
	public void clear() {
		clipSelectionne.clear();
	}
	
	public List<Clip> getContents(){
		return clipSelectionne;
	}
	
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(1.0);
		for(Clip clip : clipSelectionne) {
			//gc.strokeRect(Math.min(oriX, endX), Math.min(oriY, endY), Math.abs(endX-oriX), Math.abs(endY-oriY));
			gc.strokeRect(clip.getLeft(), clip.getTop(), clip.getRight(), clip.getBottom());
		}	
	}
}
