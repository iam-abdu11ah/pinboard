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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pobj.pinboard.document.*;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener{
	private Board board;
	private Canvas canvas;
	private VBox vbox;
	private MenuBar menuBar;
	private ToolBar toolBar;
	private ToolBar colorBar;
	private Label statusBar;
	
	private Tool currentTool;
	private Color currentColor = Color.BLACK; //couleur par défaut
	
	private Selection selected;
	
	
	public EditorWindow(Stage stage) {
		//initialisation 
		this.board = new Board();
		this.canvas = new Canvas(1000, 800);
		this.selected = new Selection();
		Clipboard.getInstance().addListener(this); //ajout de cet objet dans la liste de Clipboard Listener
		
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
		closeFile.setOnAction(e->{
			stage.close();
			Clipboard.getInstance().removeListener(this); //retrait de cet objet de la liste de Clipboard Listener
		});
		
		//Edit
		Menu editMenu = new Menu("Edit");
		//drop-down (menu itmes du Edit)
		MenuItem copy = new MenuItem("Copy");
		MenuItem paste = new MenuItem("Paste");
		MenuItem delete = new MenuItem("Delete");
		editMenu.getItems().addAll(copy, paste, delete);
		
		//actions pour les Menu items de Edit
		copy.setOnAction(e -> Clipboard.getInstance().copyToClipboard(selected.getContents()));
		paste.setOnAction(e -> board.addClip(Clipboard.getInstance().copyFromClipboard()));
		delete.setOnAction(e -> board.removeClip(selected.getContents()));
		
		//Tools
		Menu toolsMenu = new Menu("Tools");
		
		
		//ajout de File, Edit, Tools à la barre de Menu
		menuBar.getMenus().addAll(fileMenu, editMenu, toolsMenu);	
		
		//ToolBar
		toolBar = new ToolBar();
		Button rectButton = new Button("Rectangle");
		Button ellipseButton = new Button("Ellipse");
		Button imgButton = new Button("Image");
		Button selectButton = new Button("Select");
		toolBar.getItems().addAll(rectButton, ellipseButton, imgButton, selectButton);
		
		
		//action des boutons
		rectButton.setOnAction(e->currentTool = new ToolRect(currentColor));
		ellipseButton.setOnAction(e->currentTool = new ToolEllipse(currentColor));
		imgButton.setOnAction(e->currentTool = new ToolImage(stage));
		selectButton.setOnAction(e->currentTool = new ToolSelection());
		
		
		//barre/palette de couleur
		ToolBar colorBar = new ToolBar();
		Button redButton = createColorButton(Color.RED);
		redButton.setOnAction(e->{
			currentColor = Color.RED;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.RED);
			}
		});
		Button blueButton = createColorButton(Color.BLUE);
		blueButton.setOnAction(e->{
			currentColor = Color.BLUE;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.BLUE);
			}
		});
		Button whiteButton = createColorButton(Color.WHITE);
		whiteButton.setOnAction(e->{
			currentColor = Color.WHITE;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.WHITE);
			}
		});

		Button greenButton = createColorButton(Color.GREEN);
		greenButton.setOnAction(e->{
			currentColor = Color.GREEN;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.GREEN);
			}
		});

		Button roseButton = createColorButton(Color.MISTYROSE);
		roseButton.setOnAction(e->{
			currentColor = Color.MISTYROSE;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.MISTYROSE);
			}
		});

		Button purpleButton = createColorButton(Color.PURPLE);
		purpleButton.setOnAction(e->{
			currentColor = Color.PURPLE;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.PURPLE);
			}
		});

		Button blackButton = createColorButton(Color.BLACK);
		blackButton.setOnAction(e->{
			currentColor = Color.BLACK;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.BLACK);
			}
		});

		Button yellowButton = createColorButton(Color.YELLOW);
		yellowButton.setOnAction(e->{
			currentColor = Color.YELLOW;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.YELLOW);
			}
		});

		Button limeButton = createColorButton(Color.LIMEGREEN);
		limeButton.setOnAction(e->{
			currentColor = Color.LIMEGREEN;
			if(currentTool != null && currentTool instanceof ToolRect toolRect) {
				toolRect.setColor(Color.LIMEGREEN);
			}
		});

		colorBar.getItems().addAll(redButton, blueButton, whiteButton, greenButton, roseButton, purpleButton, blackButton, yellowButton, limeButton);
		
		
		
		//Separateur
		Separator sepa = new Separator();
		
		//statusBar / Label
		statusBar = new Label("Bienvenu au PinBoard");
		
		vbox.getChildren().addAll(menuBar, toolBar, colorBar, canvas, sepa, statusBar);
		
		
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
	
	private Button createColorButton(Color c) {
		Button button = new Button();
		//CSS
		button.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-border: 1;");
		
		Rectangle colRect = new Rectangle(40, 40);
		colRect.setArcHeight(10);
		colRect.setArcWidth(10);
		colRect.setFill(c);
		colRect.setStroke(Color.BLACK);
		
		button.setGraphic(colRect);
		
		return button;
		
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
		return this.selected;
	}


	@Override
	public CommandStack getUndoStack() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void clipboardChanged() {
		Menu edit = menuBar.getMenus().get(1); 		//edit est en 2e position
		MenuItem paste = edit.getItems().get(1);	//paste est en 2e postition
		
		//si aucun element est copié, on grise l'option "paste", sinon il reste telquel

		boolean isEmpty = Clipboard.getInstance().isEmpty();
		paste.setDisable(isEmpty);
	}


}
