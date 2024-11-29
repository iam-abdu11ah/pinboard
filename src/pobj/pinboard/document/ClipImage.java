package pobj.pinboard.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip implements Clip{
	private Image img ;
	private File filename;
	
	public ClipImage(double left, double top, File filename) throws FileNotFoundException {
		super(left, top, 0, 0, Color.TRANSPARENT);
        this.img = new Image(new FileInputStream(filename.getAbsolutePath()));
        super.setGeometry(left, top, left+img.getWidth(), top+img.getHeight());
        this.filename = filename;
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(img, getLeft(), getTop());
	}

	@Override
	public Clip copy() {
		try {
			return new ClipImage(super.getLeft(), super.getRight(), filename);
		}catch(FileNotFoundException e) {
			throw new RuntimeException("copie échouée");
		}
		
	}
}
