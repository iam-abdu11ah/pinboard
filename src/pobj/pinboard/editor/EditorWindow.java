package pobj.pinboard.editor;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pobj.pinboard.document.*;

public class EditorWindow {
	private Board board;
	private Canvas canvas;
	private VBox vbox;
	private MenuBar menuBar;
	private ToolBar toolBar;
	private Label statusBar;
	
	
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
		MenuItem newItem = new MenuItem("New");
		MenuItem closeItem = new MenuItem("Close");
		fileMenu.getItems().addAll(newItem, closeItem);
		//actions pour new et close
		newItem.setOnAction(e->new EditorWindow(new Stage()));
		closeItem.setOnAction(e->stage.close());
		
		//Edit
		Menu editMenu = new Menu("Edit");
		
		//Tools
		Menu toolsMenu = new Menu("Tools");
		
		//ajout de File, Edit, Tools à la barre de Menu
		menuBar.getMenus().addAll(fileMenu, editMenu, toolsMenu);	
		
		//ToolBar
		toolBar = new ToolBar(new Button("Box"), new Button("Ellipse"), new Button("Img")); 
		
		//Separateur
		Separator sepa = new Separator();
		
		//statusBar / Label
		statusBar = new Label("Bienvenu au PinBoard");
		
		vbox.getChildren().addAll(menuBar, toolBar, canvas, sepa, statusBar);
		
		
		//ajout d'éléments dans le canvas
		
		board.addClip(new ClipRect(100, 100, 300, 200, Color.BLUE));
		board.addClip(new ClipEllipse(200, 150, 400, 250, Color.RED));
		board.draw(canvas.getGraphicsContext2D());
		
		
		Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("PinBoard");
        stage.show();

	}


}
