package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool{
	private Stage stage;
	private File selectedFile;
	private double oriX, oriY;
	public ToolImage(Stage stage) {
		this.stage = stage;
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("sélectionnez un fichier img");
		 fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		 
		 selectedFile = fileChooser.showOpenDialog(stage);
		 
        if (selectedFile != null) {
            System.out.println("aucune img séléctionnée");
        	
        }
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		/**
		if(selectedFile != null) {
			oriX = e.getX();
			oriY = e.getY();
		}
		*/
		
		try {
			ClipImage img = new ClipImage(e.getX(), e.getY(), selectedFile);
		}catch(Exception ex) {
			System.out.println("ClipImage n'est pas instanciée");
		}
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if (selectedFile != null) {
            try {
                i.getBoard().addClip(new ClipImage(oriX, oriY, selectedFile));
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return null;
	}

}
