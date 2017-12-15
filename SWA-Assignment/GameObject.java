import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;


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
			gc.drawImage(img, x, y, 32, 32);
		}
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
	//	double dx=1, dy=1;
	String dir;
	int movecounter=0;
	public Enemy(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		img = new Image(GameObject.class.getResource("res/Meteor.png").toExternalForm());
		changedir();
		update();
	}
	public void update() {
		movecounter++;
		if (AppClass.rnd.nextInt(100) < 30) {
			changedir();
		}
		if (movecounter> 8) {
			movecounter = 0;
			switch(dir) {
			case "S" : 	
				if (y<640-32) 
					if (CustomMap.BOUNDS[calcpos(this.x, this.y+32)]==-1) 
						y+=32; 
					else 
						changedir();
				else 
					changedir();
				break;
			case "N" :
				if (y>0) 
					if (CustomMap.BOUNDS[calcpos(this.x, this.y-32)]==-1) 
						y-=32; 
					else 
						changedir();
				else 
					changedir();
				break;
			case "E" :
				if (x< 800-32)
					if (CustomMap.BOUNDS[calcpos(this.x+32, this.y)]==-1) 
						x+=32;
					else 
						changedir();
				else 
					changedir();
				break;
			case "W" :
				if (x> 0)
					if (CustomMap.BOUNDS[calcpos(this.x-32, this.y)]==-1) 
						x-=32;
					else 
						changedir();
				else 
					changedir();
				break;
			}
		}
		super.update();
	}
	private void changedir() {
		int direction = AppClass.rnd.nextInt(4);
		switch(direction) {
		case 0: dir = "S"; break;
		case 1: dir = "W"; break;
		case 2: dir = "E"; break;
		case 3: dir = "N"; break;
		}
	}

	public int calcpos(double x, double y) {
		int pos = (((int)(y/32)*25)+(int)(x/32));
		return pos;
	}
}

//*** DELEGATION PATTERN ***//

class Player extends GameObject {
	int maxHp;
	int currentHP;

	public Player(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		currentHP = maxHp;
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void up()
	{ if (y>0) 
		if (CustomMap.BOUNDS[calcpos(this.x, this.y-32)]==-1) {
			y-=32;
			if(CustomMap.DEADLY[calcpos(this.x, this.y)]!=-1)
				decrementHP(currentHP);
		}

	}
	public void down()
	{if (y<640-32) 
		if (CustomMap.BOUNDS[calcpos(this.x, this.y+32)]==-1) {
			y+=32;
			if(CustomMap.DEADLY[calcpos(this.x, this.y)]!=-1)
				decrementHP(currentHP);
		}
	}
	public void left()
	{if (x> 0) 
		if (CustomMap.BOUNDS[calcpos(this.x-32, this.y)]==-1) {
			x-=32;
			if(CustomMap.DEADLY[calcpos(this.x, this.y)]!=-1)
				decrementHP(currentHP);
		}
	}
	public void right()
	{if (x< 800-32) 
		if (CustomMap.BOUNDS[calcpos(this.x+32, this.y)]==-1) {
			x+=32;
			if(CustomMap.DEADLY[calcpos(this.x, this.y)]!=-1)
				decrementHP(currentHP);
		}
	}

	public int calcpos(double x, double y) {
		int pos = (((int)(y/32)*25)+(int)(x/32));
		System.out.println("Current position: " + pos);
		return pos;
	}

	public ProgressBar setHbar() {
		ProgressBar hbar = new ProgressBar((double)(currentHP-0)/(maxHp-0));
		hbar.setLayoutX(272);
		hbar.setLayoutY(32);
		hbar.setMinWidth(256);
		hbar.setMinHeight(16);
		hbar.setStyle("-fx-accent: #00ff00;");
		return hbar;
	}

	public void decrementHP(){
		currentHP--;
	}
	public void decrementHP(int i){
		currentHP-=i;
	}
	public double curHP(){
		return currentHP;
	}
	public boolean isDead() {
		if (currentHP>0)
			return false;
		return true;
	}
}


interface Evolution {
	public void update(); 
	void up(); 
	void down(); 
	void left(); 
	void right();
	double getX();
	double getY();
	ProgressBar setHbar();
	public void decrementHP();
	double curHP();
}

class FirstCell extends Player implements Evolution{

	Evolution delegate;
	protected int age=0;

	public FirstCell(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp = 20;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/cell.png").toExternalForm());	
		delegate = this;
		setHbar();
		update();
	}


	public void eat(){
		age++;
	}

	public Button[] getOptions() {
		Button[] options = new Button[2];
		
		String btnstyle = "-fx-background-color:\r\n" + 
				"        linear-gradient(#f0ff35, #a9ff00),\r\n" + 
				"        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\r\n" + 
				"    -fx-background-radius: 6, 5;\r\n" + 
				"    -fx-background-insets: 0, 1;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\r\n" + 
				"    -fx-text-fill: #395306;";
		Button choice1 = new Button();
		Button choice2 = new Button();

		choice1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				evolve(choice1.getText());
				update();
				((Pane)choice1.getParent()).getChildren().remove(choice1);
				((Pane)choice2.getParent()).getChildren().remove(choice2);
			}});
		choice2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				evolve(choice2.getText());
				update();
				((Pane)choice1.getParent()).getChildren().remove(choice1);
				((Pane)choice2.getParent()).getChildren().remove(choice2);
			}});

		choice1.setLayoutX(224);
		choice1.setLayoutY(64);
		choice1.setMinWidth(160);
		choice1.setMaxWidth(96);
		choice1.setStyle(btnstyle);
		choice2.setLayoutX(416);
		choice2.setLayoutY(64);
		choice2.setMinWidth(160);
		choice2.setMaxWidth(96);
		choice2.setStyle(btnstyle);

		if (age == 2 && delegate instanceof FirstCell) {
			choice1.setText("Become a vertebrate");
			choice2.setText("Become an invertebrate");
		}
		else if (age == 4 && delegate instanceof Vertebrate) {
			choice1.setText("Become a Cartilaginous fish");
			choice2.setText("I want bony skeleton");
		}
		else if (age == 4 && delegate instanceof Invertebrate) {
			choice1.setText("Become an Arthropod");
			choice2.setText("Become a Cnidarian");
		}
		else if (age == 8 && delegate instanceof Coelacanth) {
			choice1.setText("Become a Ray-Finned fish");
			choice2.setText("I want four limbs");
		}
		else if (age == 8 && delegate instanceof Arthropod) {
			choice1.setText("I want to have jaws");
			choice2.setText("I want to have pincers");
		}
		else if (age == 12 && delegate instanceof Tetrapod) {
			choice1.setText("I want to have an Amniotic egg");
			choice2.setText("Become an Amphibian");
		}
		else if (age == 12 && delegate instanceof Eurypterid) {
			choice1.setText("Become an Insect");
			choice2.setText("Become a Shrimp");
		}
		else if (age == 12 && delegate instanceof Arachnata) {
			choice1.setText("Become an Arachnida");
			choice2.setText("Become a Sea spider");
		}
		else if (age == 16 && delegate instanceof Amniote) {
			choice1.setText("Become a reptile");
			choice2.setText("Become a mammal");
		}
		else if (age == 16 && delegate instanceof Arachnida) {
			choice1.setText("Become a Spider");
			choice2.setText("Become a scorpion");
		}
		else if (age == 20 && delegate instanceof Reptile) {
			choice1.setText("Become a bird");
			choice2.setText("Become a snake");
		}
		else {
			System.out.println("Can't evolve yet");
			return null;
		}
		options[0] = choice1;
		options[1] = choice2;

		return options;
	}
	public void evolve(String type){
		switch (type) {

		case "Become a vertebrate" :	delegate = new Vertebrate(gc,getX(),getY());	
		break;
		case "Become an invertebrate": 	delegate = new Invertebrate(gc,getX(),getY());
		break;


		case "I want bony skeleton":	delegate = new Coelacanth(gc,getX(),getY());
		break; 
		case "Become a "
		+ "Cartilaginous fish":			delegate = new CartilaginousFish(gc,getX(),getY());
		break;
		case "Become an Arthropod" : 	delegate = new Arthropod(gc,getX(),getY());
		break; 
		case "Become a Cnidarian" : 	delegate = new Cnidarian(gc,getX(),getY());
		break;


		case "Become a "
		+ "Ray-Finned fish": 			delegate = new RayfinnedFish(gc,getX(),getY());
		break;
		case "I want four limbs":		delegate = new Tetrapod(gc,getX(),getY());
		break; 
		case "I want to have jaws":		delegate = new Eurypterid(gc,getX(),getY());
		break;
		case "I want to have pincers": 	delegate = new Arachnata(gc,getX(),getY());
		break; 


		case "I want to have "
		+ "an Amniotic egg": 			delegate = new Amniote(gc,getX(),getY());
		break; 
		case "Become an Amphibian":		delegate = new Amphibian(gc,getX(),getY());
		break;
		case "Become an Insect":		delegate = new Insect(gc,getX(),getY());
		break;
		case "Become a Shrimp" :		delegate = new Shrimp(gc,getX(),getY());
		break;
		case "Become an Arachnida":		delegate = new Arachnida(gc,getX(),getY());
		break; 
		case "Become a Sea spider": 	delegate = new SeaSpider(gc,getX(),getY());
		break;


		case "Become a reptile":		delegate = new Reptile(gc,getX(),getY());
		break;
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
			return this.setHbar();
		else
			return delegate.setHbar();
	}
	public void decrementHP(){
		if(delegate instanceof FirstCell) {
			super.decrementHP();
		}
		else {
			delegate.decrementHP();
		}
	}

	public double curHP(){
		if(delegate instanceof FirstCell)
			return super.curHP();
		else
			return delegate.curHP();
	}
	public boolean isDead() {
		if(delegate instanceof FirstCell) 
			if (this.currentHP>0)
				return false;
			else 
				return true;
		else if ( ((Player)delegate).currentHP > 0)
			return false;
		return true; 

	}

}

class Vertebrate extends Player implements Evolution{

	public Vertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp =30;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Myllokunmingia (first vertebrate).png").toExternalForm());
		update();
	}
}

class Invertebrate extends Player implements Evolution{

	public Invertebrate(GraphicsContext gc, double x, double y) {
		super(gc, x, y);
		maxHp =30;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/trilobite (first invertebrate).png").toExternalForm());
		update();}
}

class Cnidarian extends Player implements Evolution{

	public Cnidarian(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 40;
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/jellyfish.png").toExternalForm());
		update();
	}
}

class CartilaginousFish extends Player implements Evolution{

	public CartilaginousFish(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 40;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Shark.png").toExternalForm());
		update();
	}
}


class RayfinnedFish extends Player implements Evolution{

	public RayfinnedFish(GraphicsContext gc, double x, double y) {		
		super(gc, x, y);	
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/fish.png").toExternalForm());
		update();
	}
}

class Amphibian extends Player implements Evolution{

	public Amphibian(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Frog.png").toExternalForm());
		update();
	}
}

class Insect extends Player implements Evolution{

	public Insect(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 50;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/ladybug.png").toExternalForm());
		update();
	}
}

class Shrimp extends Player implements Evolution{

	public Shrimp(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 50;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/shrimp.png").toExternalForm());
		update();
	}
}

class SeaSpider extends Player implements Evolution{

	public SeaSpider(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 20;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Sea_Spider.png").toExternalForm());
		update();
	}
}

class Mammal extends Player implements Evolution{

	public Mammal(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 100;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/black_cat.png").toExternalForm());
		update();
	}
}

class Spider extends Player implements Evolution{

	public Spider(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 60;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/spider.png").toExternalForm());
		update();
	}
}

class Scorpion extends Player implements Evolution{

	public Scorpion(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 80;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Scorpion.png").toExternalForm());
		update();
	}
}

class Bird extends Player implements Evolution{

	public Bird(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/bird.png").toExternalForm());
		update();
	}
}

class Snake extends Player implements Evolution{

	public Snake(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/snake.png").toExternalForm());
		update();
	}
}

class Coelacanth extends Player implements Evolution{

	public Coelacanth(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Coelacanth.png").toExternalForm());
		update();
	}
}

class Arthropod extends Player implements Evolution{

	public Arthropod(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/centipede.png").toExternalForm());
		update();
	}
}

class Tetrapod extends Player implements Evolution{

	public Tetrapod(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/tetrapod.png").toExternalForm());
		update();
	}
}

class Arachnata extends Player implements Evolution{

	public Arachnata(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/mandibulata.png").toExternalForm());
		update();
	}
}

class Eurypterid extends Player implements Evolution{

	public Eurypterid(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Eurypterid.png").toExternalForm());
		update();
	}
}
class Arachnida extends Player implements Evolution{

	public Arachnida(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/horseshoecrab.png").toExternalForm());
		update();
	}
}
class Amniote extends Player implements Evolution{

	public Amniote(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/lizard.png").toExternalForm());
		update();
	}
}

class Reptile extends Player implements Evolution{

	public Reptile(GraphicsContext gc, double x, double y) {

		super(gc, x, y);
		maxHp = 70;		
		currentHP = maxHp;
		img = new Image(GameObject.class.getResource("res/Crocodile.png").toExternalForm());
		update();
	}
}
