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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void update() {
		if (img!=null){
			gc.drawImage(img, x, y, 30, 30);
		}
	}

	public Image getImg() {
		return img;
	}

	public double getWidth(){
		if (img!=null)
			return img.getWidth();
		return 30;
	}

	public double getHeight(){
		if (img!=null)
			return img.getHeight();
		return 30;
	}

	public void eat(){

	}

}



class Food{
	protected Image img;
	protected double x, y;
	protected GraphicsContext gc;

	public Food(double x, double y, GraphicsContext gc) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		img = new Image("res/seed.png");
		update();
	}
	public void update() {
		if (img!=null){
			gc.drawImage(img, x, y, 30, 30);
		}
	}
}

interface Evolution { void update();};

class FirstCell extends GameObject implements Evolution{

	Evolution delegate; 
	protected int age;

	public FirstCell(double x, double y, GraphicsContext gc) {
		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/cell.png").toExternalForm());
		update();
		delegate = this;
	}

	public void eat(){
		age++;
		if (age==2){
			delegate = new Invertebrate(x,y,gc);
			img = new Image(GameObject.class.getResource("res/trilobite (first invertebrate).png").toExternalForm());
		}
		if (age==4){
			delegate = new Vertebrate(x,y,gc);			
			img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		}
		delegate.update();
	}

	public int getAge() {
		return age;
	}

}

class Vertebrate extends GameObject implements Evolution{

	public Vertebrate(double x, double y, GraphicsContext gc) {
		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		update();
	}	
}

class Invertebrate extends GameObject implements Evolution{

	public Invertebrate(double x, double y, GraphicsContext gc) {
		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/trilobite (first invertebrate).png").toExternalForm());
		update();
	}	
}