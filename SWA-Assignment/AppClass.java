import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppClass extends Application{

	Pane root;
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	ArrayList<GameObject> food = new ArrayList<GameObject>();
	ArrayList<GameObject> enemies = new ArrayList<GameObject>();
	static int TILE_WIDTH = 32;
	static int TILE_HEIGHT = 32;
	static int SCREEN_WIDTH = 800;
	static int SCREEN_HEIGHT = 640;

	GameObject player;

	static Random rnd = new Random();
	int foodCounter = 0, enemyCounter = 0;

	Factory f;

	public static void main(String[] args) {
		launch(args);
	}


	AnimationTimer timer = new AnimationTimer() {

		@Override
		public void handle(long now) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear the canvas
			((FirstCell)player).afterMove(); //update the player image
			enemyCollisionCheck();		// check if any enemies have moved into the player zone

			root.getChildren().add(((FirstCell) player).hbar()); // update the healthbar of the player

			// Factory for food & enemies
			spawnfood();
			spawnenemy(); 

			// Redraw food & enemies
			for (GameObject s: food)
				s.update();	

			for (GameObject e: enemies)
				e.update();
			
			//if the player has fallen in a trap, stop the game
			if(((FirstCell) player).isDead()) {
				soundEffect("death");
				player = NullObj.getInstance();
				stopgame();
			}
		}
	};

	EventHandler<KeyEvent> keyhandler = new EventHandler<KeyEvent> () {

		//handles the player movement
		@Override
		public void handle(KeyEvent event) {
			if(event.getCode() == KeyCode.W){
				((FirstCell)player).up();
			}
			if(event.getCode() == KeyCode.S){
				((FirstCell)player).down();
			}
			if(event.getCode() == KeyCode.A){
				((FirstCell)player).left();
			}
			if(event.getCode() == KeyCode.D){
				((FirstCell)player).right();
			}						
			foodCollisionCheck();
		}		
	};

	@Override
	public void start(Stage stage) throws Exception {
		root = new Pane();
		scene  = new Scene(root, 800,864);
		stage.setScene(scene);
		stage.show();
		playMusic();
		Pane mapdisplay = new Pane();		
		//draw the map on a separate pane		
		CustomMap map = new CustomMap(mapdisplay);
		root.getChildren().add(mapdisplay);
		root.setStyle("-fx-background-color: #666666");
		canvas = new Canvas(SCREEN_WIDTH,SCREEN_HEIGHT); //places a canvas on top of the map for the players and enemies to be drawn
		canvas.setLayoutY(96);

		gc = canvas.getGraphicsContext2D();

		root.getChildren().addAll(canvas);

		setInstructions();//sets the instructions for the player
		startGameMenu(); //initialises the start game menu
		f = new Factory(gc); //creates a new instance 	
	}

	public void startGameMenu(){
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Button start = new Button();
		Button exit = new Button();
		String buttonstyle = 	"-fx-background-color:" + 
				"	linear-gradient(#ffd65b, #e68400)," + 
				"	linear-gradient(#ffef84, #f2ba44)," + 
				"	linear-gradient(#ffea6a, #efaa22)," + 
				"	linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," + 
				"	linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" + 
				"-fx-background-radius: 30;" + 
				"-fx-background-insets: 0,1,2,3,0;" + 
				"-fx-text-fill: #654b00;" + 
				"-fx-font-weight: bold;" + 
				"-fx-font-size: 14px;" + 
				"-fx-padding: 10 20 10 20; ";
		start.setText("Start");
		exit.setText("Exit");
		start.setLayoutX(288);
		start.setLayoutY(300);
		start.setMinWidth(224);
		exit.setLayoutX(288);
		exit.setLayoutY(400);
		exit.setMinWidth(224);

		start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				player = new FirstCell(gc, 32, 32);
				root.getChildren().add(((FirstCell) player).hbar());


				scene.setOnKeyPressed(keyhandler);
				timer.start();

				root.getChildren().remove(start);
				root.getChildren().remove(exit);
			}			
		});

		exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Platform.exit();
			}

		});
		start.setStyle(buttonstyle);
		exit.setStyle(buttonstyle);
		root.getChildren().add(start);
		root.getChildren().add(exit);
	}

	public void enemyCollisionCheck() {
		Rectangle p = new Rectangle(((FirstCell)player).getX(), ((FirstCell)player).getY(), TILE_WIDTH, TILE_HEIGHT);
		for (GameObject e: enemies) {
			Rectangle enemy = new Rectangle(e.x, e.y, e.img.getWidth(), e.img.getHeight());
			if (p.intersects(enemy.getX(),enemy.getY(), TILE_WIDTH, TILE_HEIGHT)) {
				soundEffect("damage");
				if(((FirstCell) player).isDead()){
					soundEffect("death");
					stopgame();
				}
				else
					((FirstCell) player).decrementHP();
			}
		}
	}
	public void stopgame() {
		scene.setOnKeyPressed(null);	
				
		Text death = new Text("You Died!");
		death.setX(336);
		death.setY(336);
		death.setFill(Color.RED);
		death.setFont(Font.font("Verdana", 24));
		death.setStyle("-fx-font-weight: bold;"
				+ "-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		root.getChildren().add(death);
		soundEffect("death");
		timer.stop();
		restartGame();
	}

	public void foodCollisionCheck() {
		boolean intersectFlag=false;
		Rectangle p = new Rectangle(((FirstCell)player).getX(), ((FirstCell)player).getY(),  TILE_WIDTH, TILE_HEIGHT);
		Iterator<GameObject> it = food.iterator();
		while (it.hasNext()){
			GameObject s = it.next();
			if (p.intersects(s.x+1, s.y+1, TILE_WIDTH-2, TILE_HEIGHT-2)){
				intersectFlag = true;
				it.remove();
				((FirstCell)player).eat();
				soundEffect("food");

				if (((FirstCell)player).getOptions()!= null) {
					root.getChildren().addAll(((FirstCell)player).getOptions());
				}
			}
		}
		if(intersectFlag){
			gc.setFill(Color.RED);
			gc.fillText("MUNCH!", 400, 500);
		}
	}

	int restartTimer =0;

	AnimationTimer restart = new AnimationTimer(){
		@Override
		public void handle(long now) {
			restartTimer++;
			gc.setFill(Color.BLUE);
			gc.fillText("RestartTimer: " + restartTimer, 400, 500);	
			if (restartTimer > 200){
				restart.stop();
				enemies.clear();
				food.clear();
				foodCounter = 0;
				enemyCounter = 0;
				startGameMenu();

				enemies.clear();
				food.clear();

				restart.stop();
				restartTimer=0;

			}
		}};
		public void restartGame(){
			restart.start();			
		}

		public void spawnfood() {
			int x = rnd.nextInt(24)*32;
			int y = rnd.nextInt(19)*32;
			if (foodCounter++ > 50) {
				if (CustomMap.BOUNDS[(((int)(y/32)*25)+(int)(x/32))]==-1) {
					food.add(f.createProduct("food", x, y));
					foodCounter =0;	
				}
				else spawnfood();
			}	
		}
		public void spawnenemy() {
			int x = rnd.nextInt(24)*32;
			int y = rnd.nextInt(19)*32;
			if (enemyCounter++ > 50 && enemies.size() < 4) {
				if (CustomMap.BOUNDS[(((int)(y/32)*25)+(int)(x/32))]==-1) {
					enemies.add(f.createProduct("enemy", x, y));
					enemyCounter = 0;
				}
				else spawnenemy();
			}
		}

		public void setInstructions() {
			Image keys = new Image(AppClass.class.getResource("res/wasd-qwerty.png").toExternalForm());


			ImageView wasd = new ImageView(keys);
			wasd.setFitHeight(288);
			wasd.setFitWidth(128);
			wasd.setPreserveRatio(true);
			wasd.setX(64);
			wasd.setY(736);		
			Text D = new Text("to move around");
			D.setX(64);
			D.setY(848);
			D.setFill(Color.LIMEGREEN);
			D.setFont(Font.font("Verdana", 18));
			Text evolve = new Text("Eat seeds to evolve");
			evolve.setX(384);
			evolve.setY(760);
			evolve.setFill(Color.LIMEGREEN);
			evolve.setFont(Font.font("Verdana", 18));
			Text death = new Text("Run away from natural disasters");
			death.setX(384);
			death.setY(792);
			death.setFill(Color.LIMEGREEN);
			death.setFont(Font.font("Verdana", 18));
			Text traps = new Text("Don't fall into holes or lava");
			traps.setX(384);
			traps.setY(824);
			traps.setFill(Color.LIMEGREEN);
			traps.setFont(Font.font("Verdana", 18));

			Text health = new Text("Heath: ");
			health.setX(192);
			health.setY(48);
			health.setFill(Color.LIMEGREEN);
			health.setFont(Font.font("Verdana", 18));

			root.getChildren().addAll(D, evolve, death, traps, health, wasd);
		}

		public void playMusic() {
			MediaPlayer a =new MediaPlayer(new Media(AppClass.class.getResource("res/07 Home at Last.mp3").toExternalForm()));
			a.setVolume(0.2);
			a.setOnEndOfMedia(new Runnable() {
				public void run() {
					a.seek(Duration.ZERO);
				}
			});
			a.play();
		}

		public void soundEffect(String s) {
			MediaPlayer p = null;
			String res = null;
			switch (s) {
			case "food" : res = "res/270342__littlerobotsoundfactory__pickup-03.wav"; break;
			case "damage" : res = "res/270343__littlerobotsoundfactory__shoot-01.wav"; break;
			case "death": res = "res/270334__littlerobotsoundfactory__jingle-lose-01.wav"; break;	
			}
			p = new MediaPlayer(new Media(AppClass.class.getResource(res).toExternalForm()));
			p.play();
			p.setVolume(0.6);
		}
}


