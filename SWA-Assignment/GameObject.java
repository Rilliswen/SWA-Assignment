import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameObject {

	public Image img;
	protected double x, y;
	protected GraphicsContext gc;

	public GameObject(GraphicsContext gc, double x, double y)
	{
		this.gc=gc;
		this.x=x;
		this.y=y;
	}


	public void update() {
		if (img!=null){
			gc.drawImage(img, x, y, 30, 30);
		}
	}	
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void up()
	{
		y-=30;
	}
	public void down()
	{
		y+=30;
	}
	public void left()
	{
		x-=30;
	}
	public void right()
	{
		x+=30;
	}
}


//*** FOOD CLASS ***//

class Food extends GameObject{

	public Food(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/seed.png").toExternalForm());
		update();
	}
}

//*** ENEMY CLASS ***//

class Enemy extends GameObject{
	double dx=1, dy=1;
	public Enemy(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/Meteor.png").toExternalForm());
		update();
	}
	public void update() {
		x+=dx;
		if(x>800)
			dx=-1;
		if(x<0)
			dx=1;
		y+=dy;
		if(y>600)
			dy=-1;
		if(y<0)
			dy=1;
		super.update();
	}

}

//*** DELEGATION PATTERN ***//


interface Evolution {
	public void update(); 
	void up(); 
	void down(); 
	void left(); 
	void right();
	double getX();
	double getY();};

class FirstCell extends GameObject implements Evolution{

	Evolution delegate;
	protected int age=0;

	public FirstCell(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/cell.png").toExternalForm());	
		delegate = this;		
		update();
	}


	public void eat(){
		age++;
	}

	public void evolve(String type){
		switch (type) {

		case "Become a vertebrate" :	delegate = new Vertebrate(gc,getX(),getY());	
				break;
		case "Become an invertebrate": 	delegate = new Invertebrate(gc,getX(),getY());
				break;


		case "I want a bony skeleton":	break;
		case "Become a "
		+ "Cartilaginous fish":			delegate = new CartilaginousFish(gc,getX(),getY());
				break;
		case "Become an Arthropod" : 	
				break;
		case "Become a Cnidarian" : 	delegate = new Cnidarian(gc,getX(),getY());
				break;


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
		default: 						System.out.print("Ya fked up");

		}

		delegate.update();

		//*** XXX   DIAGNOSTICS    ***//
		//System.out.println(delegate.getClass().toGenericString());				
		//System.out.println("Evolve IMG: " + this.img.impl_getUrl()); //img remains the cell image even though constructor is supposed to change it.

	}
	public int getAge() {
		return age;
	}
	
	public void afterMove()
	{
		delegate.update();
	}
	
	public void up()
	{
		if(delegate instanceof FirstCell)
			super.up();
		else
			delegate.up();
	}
	public void down()
	{
		if(delegate instanceof FirstCell)
			super.down();
		else
			delegate.down();
	}
	public void left()
	{
		if(delegate instanceof FirstCell)
			super.left();
		else
			delegate.left();
	}
	public void right()
	{
			if(delegate instanceof FirstCell)
				super.right();
			else
				delegate.right();
	}
	
	public double getX(){
		if(delegate instanceof FirstCell)
			return super.getX();
		else
			return delegate.getX();
	}
	public double getY(){
		if(delegate instanceof FirstCell)
			return super.getY();
		else
			return delegate.getY();
	}

}

class Vertebrate extends GameObject implements Evolution{

	public Vertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		update();
	}

}

class Invertebrate extends GameObject implements Evolution{

	public Invertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/trilobite (first invertebrate).png").toExternalForm());
		update();
		//**    DIAGNOSTICS    **//
//		gc.setFill(Color.RED);
//		gc.fillText("Player class: " +  this.getClass().toGenericString(), 100, 500);
//		gc.fillText("Player Image: " + this.img.impl_getUrl(), 100, 450);
//		System.out.println("Constructor exit");				
	}

}

class Cnidarian extends GameObject implements Evolution{

	public Cnidarian(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/jellyfish.png").toExternalForm());
		update();
	}

}

class CartilaginousFish extends GameObject implements Evolution{

	public CartilaginousFish(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/Shark.png").toExternalForm());
		update();
	}

}

//
//class RayfinnedFish extends GameObject implements Evolution{
//
//	public RayfinnedFish(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/fish.png").toExternalForm());
//		update();
//	}
//	
//}
//
//class Amphibian extends GameObject implements Evolution{
//
//	public Amphibian(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/Frog.png").toExternalForm());
//		update();
//	}
//}
//
//class Insect extends GameObject implements Evolution{
//
//	public Insect(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/ladybug.png").toExternalForm());
//		update();
//	}
//}
//
//class Shrimp extends GameObject implements Evolution{
//
//	public Shrimp(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/shrimp.png").toExternalForm());
//		update();
//	}
//}
//
//class SeaSpider extends GameObject implements Evolution{
//
//	public SeaSpider(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/Sea_Spider.png").toExternalForm());
//		update();
//	}
//}
//
//class Mammal extends GameObject implements Evolution{
//
//	public Mammal(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/black_cat.png").toExternalForm());
//		update();
//	}
//}
//
//class Spider extends GameObject implements Evolution{
//
//	public Spider(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/spider.png").toExternalForm());
//		update();
//	}
//}
//
//class Scorpion extends GameObject implements Evolution{
//
//	public Scorpion(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/Scorpion.png").toExternalForm());
//		update();
//	}
//}
//
//class Bird extends GameObject implements Evolution{
//
//	public Bird(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/bird.png").toExternalForm());
//		update();
//	}
//}
//
//class Snake extends GameObject implements Evolution{
//
//	public Snake(double x, double y, GraphicsContext gc) {
//
//		super(gc, x, y);
//		img = new Image(GameObject.class.getResource("res/snake.png").toExternalForm());
//		update();
//	}
//}