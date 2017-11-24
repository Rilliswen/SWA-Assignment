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

	public Evolution delegate;
	protected int age;

	public FirstCell(double x, double y, GraphicsContext gc) {
		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/cell.png").toExternalForm());
		update();
		delegate = this;
	}

	public void eat(){
		age++;

	}

	public void evolve(String type){
		switch (type) {
		case "Become a vertebrate" :	delegate = new Vertebrate(x,y,gc);
		img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		break;
		case "Become an invertebrate": 	delegate = new Invertebrate(x,y,gc);
		img = new Image(GameObject.class.getResource("res/trilobite (first invertebrate).png").toExternalForm());
		break;


		case "I want a bony skeleton":	break;
		case "Become a "
		+ "Cartilaginous fish":			delegate = new Invertebrate(x,y,gc);
										img = new Image(GameObject.class.getResource("res/shark.png").toExternalForm());
										break;
		case "Become an Arthropod" : 	break;
		case "Become a Cnidarian" : 	break;


		case "Become a "
		+ "Ray-Finned fish": 			break;
		case "I want four limbs":		break;
		case "I want to have jaws":		break;
		case "I want to have "
		+ "fangs and pincers": 			break;


		case "I want to have "
		+ "an Amniotic egg": 			break;
		case "Become an Amphibian":		break;
		case "Become an Insect":		break;
		case "Become a Shrimp" :		break;
		case "Become an Arachnida":		break;
		case "Become a Sea spider": 	break;


		case "Become a reptile":		break;
		case "Become a mammal": 		break;
		case "Become a Spider":			break;
		case "Become a scorpion":		break;


		case "Become a bird":			break;
		case "Become a snake":			break;
		default : 						System.out.print("Ya fked up");
		}
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
		System.out.println("hi im an invertebrate");
	}
}

class Cnidarian extends GameObject implements Evolution{

	public Cnidarian(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/jellyfish.png").toExternalForm());
		update();
	}
}

class CartilaginousFish extends GameObject implements Evolution{

	public CartilaginousFish(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/Shark.png").toExternalForm());
		update();
	}
}

class RayfinnedFish extends GameObject implements Evolution{

	public RayfinnedFish(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/fish.png").toExternalForm());
		update();
	}
}

class Amphibian extends GameObject implements Evolution{

	public Amphibian(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/Frog.png").toExternalForm());
		update();
	}
}

class Insect extends GameObject implements Evolution{

	public Insect(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/ladybug.png").toExternalForm());
		update();
	}
}

class Shrimp extends GameObject implements Evolution{

	public Shrimp(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/shrimp.png").toExternalForm());
		update();
	}
}

class SeaSpider extends GameObject implements Evolution{

	public SeaSpider(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/Sea_Spider.png").toExternalForm());
		update();
	}
}

class Mammal extends GameObject implements Evolution{

	public Mammal(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/black_cat.png").toExternalForm());
		update();
	}
}

class Spider extends GameObject implements Evolution{

	public Spider(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/spider.png").toExternalForm());
		update();
	}
}

class Scorpion extends GameObject implements Evolution{

	public Scorpion(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/Scorpion.png").toExternalForm());
		update();
	}
}

class Bird extends GameObject implements Evolution{

	public Bird(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/bird.png").toExternalForm());
		update();
	}
}

class Snake extends GameObject implements Evolution{

	public Snake(double x, double y, GraphicsContext gc) {

		super(x, y, gc);
		img = new Image(GameObject.class.getResource("res/snake.png").toExternalForm());
		update();
	}
}