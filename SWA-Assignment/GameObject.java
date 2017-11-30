import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameObject {

	public Image img;
	protected double x, y;
	protected GraphicsContext gc;
	double maxHp;
	double currentHP;

	public GameObject(GraphicsContext gc, double x, double y)
	{
		this.gc=gc;
		this.x=x;
		this.y=y;
		currentHP = maxHp;
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
	{ if (y>30)
		y-=30;
	}
	public void down()
	{if (y<600-30)
		y+=30;
	}
	public void left()
	{if (x> 30)
		x-=30;
	}
	public void right()
	{if (x< 800-30)
		x+=30;
	}
	
	public ProgressBar hbar(){
		ProgressBar hbar = new ProgressBar((currentHP-0)/(maxHp-0));
		System.out.println("Current HP" + currentHP);
		System.out.println("max HP" + maxHp);
		hbar.setLayoutX(400);
		hbar.setLayoutY(50);
		hbar.setMaxSize(200, 5);
		return hbar;
	}
	public void decrementHP(){
		currentHP--;
	}
	public double curHP(){
		return currentHP;
	}
}




class NullObj extends GameObject{
	private static NullObj instance = null;

	private NullObj() {
		super(null, -1, -1);
	}

	public static NullObj getInstance(){
		if (instance == null)
			instance = new NullObj();
		return instance;
	}

	public void update(){}	
	public void up(){}
	public void down(){}
	public void left(){}
	public void right(){}	
}


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
	double getY();
	ProgressBar hbar();
	public void decrementHP();
	double curHP();}

class FirstCell extends GameObject implements Evolution{

	Evolution delegate;
	protected int age=0;

	public FirstCell(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp = 20;
		currentHP = maxHp;
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


		case "I want a bony skeleton":	break; //XXX class needed
		case "Become a "
		+ "Cartilaginous fish":			delegate = new CartilaginousFish(gc,getX(),getY());
										break;
		case "Become an Arthropod" : 	break; //XXX class needed
		case "Become a Cnidarian" : 	delegate = new Cnidarian(gc,getX(),getY());
										break;


		case "Become a "
		+ "Ray-Finned fish": 			delegate = new RayfinnedFish(gc,getX(),getY());
										break;
		case "I want four limbs":		break; //XXX class needed
		case "I want to have jaws":		break; //XXX class needed
		case "I want to have "
		+ "fangs and pincers": 			break; //XXX class needed


		case "I want to have "
		+ "an Amniotic egg": 			break; //XXX class needed
		case "Become an Amphibian":		delegate = new Amphibian(gc,getX(),getY());
										break;
		case "Become an Insect":		delegate = new Insect(gc,getX(),getY());
										break;
		case "Become a Shrimp" :		delegate = new Shrimp(gc,getX(),getY());
										break;
		case "Become an Arachnida":		break; //XXX class needed
		case "Become a Sea spider": 	delegate = new SeaSpider(gc,getX(),getY());
										break;


		case "Become a reptile":		break; //XXX class needed
		case "Become a mammal": 		delegate = new Mammal(gc,getX(),getY());break;
		case "Become a Spider":			delegate = new Spider(gc,getX(),getY());break;
		case "Become a scorpion":		delegate = new Scorpion(gc,getX(),getY());break;


		case "Become a bird":			delegate = new Bird(gc,getX(),getY());break;
		case "Become a snake":			delegate = new Snake(gc,getX(),getY());break;
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
		//delegate.hbar();
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
	public ProgressBar hbar(){
		if(delegate instanceof FirstCell)
			return super.hbar();
		else
			return delegate.hbar();
	}
	public void decrementHP(){
		if(delegate instanceof FirstCell)
			super.decrementHP();
		else
			delegate.decrementHP();
	}
	
	public double curHP(){
		if(delegate instanceof FirstCell)
			return super.curHP();
		else
			return delegate.curHP();
	}
	
}

class Vertebrate extends GameObject implements Evolution{

	public Vertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp =30;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		update();
	}
}

class Invertebrate extends GameObject implements Evolution{

	public Invertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp =30;
		currentHP = maxHp;
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
		maxHp = 40;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/jellyfish.png").toExternalForm());
		update();
	}
}

class CartilaginousFish extends GameObject implements Evolution{

	public CartilaginousFish(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 40;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Shark.png").toExternalForm());
		update();
	}
}


class RayfinnedFish extends GameObject implements Evolution{

	public RayfinnedFish(GraphicsContext gc, double x, double y) {		
		super(gc, x, y);	
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/fish.png").toExternalForm());
		update();
	}
	
}

class Amphibian extends GameObject implements Evolution{

	public Amphibian(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Frog.png").toExternalForm());
		update();
	}
}

class Insect extends GameObject implements Evolution{

	public Insect(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 50;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/ladybug.png").toExternalForm());
		update();
	}
}

class Shrimp extends GameObject implements Evolution{

	public Shrimp(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 50;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/shrimp.png").toExternalForm());
		update();
	}
}

class SeaSpider extends GameObject implements Evolution{

	public SeaSpider(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 20;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Sea_Spider.png").toExternalForm());
		update();
	}
}

class Mammal extends GameObject implements Evolution{

	public Mammal(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 100;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/black_cat.png").toExternalForm());
		update();
	}
}

class Spider extends GameObject implements Evolution{

	public Spider(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/spider.png").toExternalForm());
		update();
	}
}

class Scorpion extends GameObject implements Evolution{

	public Scorpion(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 80;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Scorpion.png").toExternalForm());
		update();
	}
}

class Bird extends GameObject implements Evolution{

	public Bird(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/bird.png").toExternalForm());
		update();
	}
}

class Snake extends GameObject implements Evolution{

	public Snake(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/snake.png").toExternalForm());
		update();
	}
}