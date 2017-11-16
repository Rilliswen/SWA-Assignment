import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject {

	protected Image img;
	protected double x, y;
	protected GraphicsContext gc;
	public GameObject(double x, double y, GraphicsContext gc) {
		super();
		this.x = x;
		this.y = y;
		this.gc = gc;
	}
	
	public void update() {
		if (img!=null){
			gc.drawImage(img, x, y, 30, 30);
		}
	}
}
