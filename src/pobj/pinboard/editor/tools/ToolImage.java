package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool{
	private Stage stage;
	private File selectedFile;
	private double oriX, oriY, endX, endY;
	public ToolImage(Stage stage) {
		this.stage = stage;
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("sélectionnez un fichier img");
		 fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		 
		 selectedFile = fileChooser.showOpenDialog(stage);
		 
        if (selectedFile == null) {
            System.out.println("aucune img séléctionnée");
        	
        }
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		oriX = e.getX();
		oriY = e.getY();
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		try {
			ClipImage img = new ClipImage(oriX, oriY, selectedFile);
			i.getBoard().addClip(img);

		}catch(Exception ex) {
			System.out.println("ClipImage n'est pas instanciée");
		}
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {

	}

	@Override
	public String getName(EditorInterface editor) {
		return editor.toString();
	}

}
