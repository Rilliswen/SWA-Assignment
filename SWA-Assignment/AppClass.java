import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AppClass extends Application{

	Pane root;
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	ArrayList<GameObject> food = new ArrayList<GameObject>();
	ArrayList<GameObject> enemies = new ArrayList<GameObject>();


	GameObject player;


	Random rnd = new Random();
	int foodCounter = 0, enemyCounter = 0;
	
	FoodFactory f;

	public static void main(String[] args) {
		launch(args);
	}


	AnimationTimer timer = new AnimationTimer() {

		@Override
		public void handle(long now) {
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

			//player.update();
			
			((FirstCell)player).afterMove();
			
			enemyCollisionCheck();		
					

			// Factory for food & enemies
			if (foodCounter++ > 100) {
				food.add(f.createProduct("food", rnd.nextInt(800), rnd.nextInt(600)));
				foodCounter =0;				
			}	
			
			if (enemyCounter++ > 200 && enemies.size() < 6) {
				enemies.add(f.createProduct("enemy", rnd.nextInt(800), rnd.nextInt(600)));
				enemyCounter = 0;
			}

			// Redraw food & enemies
			for (GameObject s: food)
				s.update();	
			
			for (GameObject e: enemies)
				e.update();
			
			
			//*** XXX   DIAGNOSTICS    ***//
//			gc.setFill(Color.RED);
			gc.fillText("Age: " + ((FirstCell) player).getAge(), 100, 550);
//			gc.fillText("Player Image: " + player.img.impl_getUrl(), 100, 450);
		}
	};

	EventHandler<KeyEvent> keyhandler = new EventHandler<KeyEvent> () {

		@Override
		public void handle(KeyEvent event) {
			if(event.getCode() == KeyCode.W){
				//player.y = player.y-30;	
				
				((FirstCell)player).up();
			}
			if(event.getCode() == KeyCode.S){
				//player.y = player.y+30;
				((FirstCell)player).down();
			}
			if(event.getCode() == KeyCode.A){
				//player.x = player.x-30;
				((FirstCell)player).left();
			}
			if(event.getCode() == KeyCode.D){
				//player.x = player.x+30;
				((FirstCell)player).right();
			}
			//player.update();
			//((FirstCell)player).afterMove();
			
			
			//System.out.println("IMG WHILE MOVING :" + player.img.impl_getUrl());
			
			//*** XXX    MONITOR MOVEMENT     ***//
//			gc.setFill(Color.YELLOW);
//			gc.fillRect(((FirstCell)player).getX(), ((FirstCell)player).getY(), 15, 15);							
			foodCollisionCheck();
		}		
	};

	@Override
	public void start(Stage stage) throws Exception {
		root = new Pane();
		scene  = new Scene(root, 800,600);
		stage.setScene(scene);
		stage.show();

		canvas = new Canvas(800,600);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		root.getChildren().add(canvas);



		startGameMenu();
		



		f = new FoodFactory(gc);		
	}
	
	public void startGameMenu(){
		Button start = new Button();
		Button exit = new Button();
		start.setText("Start");
		exit.setText("Exit");
		start.setLayoutX(300);
		start.setLayoutY(300);
		exit.setLayoutX(300);
		exit.setLayoutY(400);
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				player = new FirstCell(gc, 30, 30);
				scene.setOnKeyPressed(keyhandler);
				timer.start();
				
				root.getChildren().remove(start);
				root.getChildren().remove(exit);
			}			
		});
		root.getChildren().add(start);
		root.getChildren().add(exit);
	}

	public void enemyCollisionCheck() {
		Rectangle p = new Rectangle(((FirstCell)player).getX(), ((FirstCell)player).getY(), 30, 30);
		for (GameObject e: enemies) {
			Rectangle enemy = new Rectangle(e.x, e.y, e.img.getWidth(), e.img.getHeight());
			if (p.intersects(enemy.getX(),enemy.getY(), 30, 30)) {					
				scene.setOnKeyPressed(null);	

				gc.setFill(Color.RED);					
				gc.fillText("YOU DIED!!", 300, 400);
				timer.stop();
				restartGame();
			}
		}
	}
	
	public void foodCollisionCheck() {
		boolean intersectFlag=false;
		Rectangle p = new Rectangle(((FirstCell)player).getX(), ((FirstCell)player).getY(), 30, 30);
		Iterator<GameObject> it = food.iterator();
		while (it.hasNext()){
			GameObject s = it.next();
			Rectangle fishrect = new Rectangle(s.x, s.y, s.img.getWidth(), s.img.getHeight());
			if (p.intersects(fishrect.getX(), fishrect.getY(), 30, 30)){
				intersectFlag = true;
				it.remove();
				((FirstCell)player).eat();
				if ( ((FirstCell)player).getAge() == 2 ) {
					createChoice("Become a vertebrate", "Become an invertebrate");	
				}
				else if ( ((FirstCell)player).getAge() == 4 && ((FirstCell)player).delegate instanceof Vertebrate)
					createChoice("Become a Cartilaginous fish", "I want bony skeleton");
				else if ( ((FirstCell)player).getAge() == 4 && ((FirstCell)player).delegate instanceof Invertebrate)
					createChoice("Become an Arthropod", "Become a Cnidarian");
			}
		}
		if(intersectFlag){
			gc.setFill(Color.RED);
			gc.fillText("MUNCH!", 400, 500);
		}
	}
	
	public void createChoice(String opt1, String opt2){
		Button choice1 = new Button();
		Button choice2 = new Button();
		choice1.setText(opt1);
		choice2.setText(opt2);
		choice1.setLayoutX(100);
		choice2.setLayoutX(200);
		choice1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				((FirstCell)player).evolve(choice1.getText());
				player.update();
				
					
				System.out.println("AppClass: " + player.img.impl_getUrl());
				
				
				root.getChildren().remove(choice1);
				root.getChildren().remove(choice2);
			}});
		choice2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				((FirstCell)player).evolve(choice2.getText());
				player.update();
				
				
				System.out.println("AppClass: " + player.img.impl_getUrl());

				
				root.getChildren().remove(choice1);
				root.getChildren().remove(choice2);
			}});

		root.getChildren().add(choice1);
		root.getChildren().add(choice2);

	}
	
	int restartTimer =0;
	
	AnimationTimer restart = new AnimationTimer(){
		@Override
		public void handle(long now) {
			restartTimer++;
			gc.setFill(Color.BLUE);
			gc.fillText("RestartTimer: " + restartTimer, 400, 500);	
			if (restartTimer > 200){
				timer.start();
				enemies.clear();
				food.clear();
				foodCounter = 0;
				enemyCounter = 0;
				restart.stop();
				scene.setOnKeyPressed(keyhandler);
				restartTimer=0;
				player = new FirstCell(gc, 30, 30);
			}
		}};
	public void restartGame(){
			restart.start();			
	}

}


