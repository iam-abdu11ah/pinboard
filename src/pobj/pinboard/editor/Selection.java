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
		clear();
		Clip topClip = null;
		for(int i=board.getContents().size()-1; i>=0; i--) {
			Clip clip = board.getContents().get(i);
			if(clip.isSelected(x, y)) {
				clipSelectionne.add(clip);
				topClip = clip;
				break; //car on selecte que le premier élément graphique de la planche
			}
		}
		if(topClip != null) {
			//board.getContents().set(board.getContents().size()-1, topClip);
			board.getContents().remove(topClip);
			board.getContents().add(topClip);
		}
	}
	
	public void toogleSelect(Board board, double x, double y) {
		
		for(int i=board.getContents().size()-1; i>=0; i--) {
			Clip clip = board.getContents().get(i);
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
			gc.strokeRect(clip.getLeft(), clip.getTop(), clip.getRight()-clip.getLeft(), clip.getBottom() - clip.getTop());
		}	
	}
}
