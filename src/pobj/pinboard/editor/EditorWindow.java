package pobj.pinboard.editor;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pobj.pinboard.document.*;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;

public class EditorWindow implements EditorInterface{
	private Board board;
	private Canvas canvas;
	private VBox vbox;
	private MenuBar menuBar;
	private ToolBar toolBar;
	private Label statusBar;
	
	private Tool currentTool;
	
	
	public EditorWindow(Stage stage) {
		//initialisation 
		this.board = new Board();
		this.canvas = new Canvas(800, 600);
		
		
		//cofig du layout principale
		this.vbox = new VBox();
		
		
		//barre de menu
		this.menuBar = new MenuBar();
		
		//File
		Menu fileMenu = new Menu("File");
		
		//drop-down menu items du File
		MenuItem newFile = new MenuItem("New");
		MenuItem closeFile = new MenuItem("Close");
		fileMenu.getItems().addAll(newFile, closeFile);
		
		//actions pour newFile et closeFile
		newFile.setOnAction(e->new EditorWindow(new Stage()));
		closeFile.setOnAction(e->stage.close());
		
		//Edit
		Menu editMenu = new Menu("Edit");
		
		//Tools
		Menu toolsMenu = new Menu("Tools");
		
		//ajout de File, Edit, Tools à la barre de Menu
		menuBar.getMenus().addAll(fileMenu, editMenu, toolsMenu);	
		
		//ToolBar
		toolBar = new ToolBar();
		Button rectButton = new Button("Rectangle");
		Button ellipseButton = new Button("Ellipse");
		Button imgButton = new Button("Image");
		toolBar.getItems().addAll(rectButton, ellipseButton, imgButton);
		
		//action des boutons
		rectButton.setOnAction(e->currentTool = new ToolRect());
		ellipseButton.setOnAction(e->currentTool = new ToolEllipse());
		imgButton.setOnAction(e->currentTool = new ToolImage(stage));
		
		//Separateur
		Separator sepa = new Separator();
		
		//statusBar / Label
		statusBar = new Label("Bienvenu au PinBoard");
		
		vbox.getChildren().addAll(menuBar, toolBar, canvas, sepa, statusBar);
		
		
		//ajout d'éléments dans le canvas
		
		//board.addClip(new ClipRect(100, 100, 300, 200, Color.BLUE));
		//board.addClip(new ClipEllipse(200, 150, 400, 250, Color.RED));
		//board.draw(canvas.getGraphicsContext2D());
		
		
		//gestion des events de la souris
		canvas.setOnMousePressed(e->{
			if(currentTool != null) {
				currentTool.press(this, e);
				draw();
			}
		});
		
		canvas.setOnMouseDragged(e->{
			if(currentTool != null) {
				currentTool.drag(this, e);
				draw();
			}
		});
		
		canvas.setOnMouseReleased(e->{
			if(currentTool != null) {
				currentTool.release(this, e);
				draw();
			}
		});
		
		
		Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("PinBoard");
        stage.show();

	}
	

	private void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		board.draw(gc); //dessine les elements existants
		
		if(currentTool != null) currentTool.drawFeedback(this, gc);
	}

	@SuppressWarnings("exports")
	@Override
	public Board getBoard() {
		return this.board;
	}


	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CommandStack getUndoStack() {
		// TODO Auto-generated method stub
		return null;
	}


}
