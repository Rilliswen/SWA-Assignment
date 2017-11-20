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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class AppClass extends Application{

	Pane root;
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	ArrayList<Food> food = new ArrayList<Food>();
	
	
	
	GameObject player;
	
	
	
	
	Random rnd = new Random();
	int count = 0;
	FoodFactory f;

	AnimationTimer timer = new AnimationTimer() {

		@Override
		public void handle(long now) {
			boolean intersectFlag=false;
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			
			
			
			
			System.out.println(player.getClass().toGenericString());
			
			
			
			
			gc.drawImage(((FirstCell)player).img, player.getX(), player.getY(), 30, 30);
			System.out.println("Image Location: " + player.getImg().impl_getUrl());
			
				
			
			
			if (count++ > 100) {
				food.add(f.createProduct("", rnd.nextInt(800), rnd.nextInt(600)));
				count =0;				
			}			
			Rectangle p = new Rectangle(player.getX(), player.getY(), 30, 30);
			Iterator<Food> it = food.iterator();
			while (it.hasNext()){
				Food s = it.next();
				Rectangle fishrect = new Rectangle(s.x, s.y, s.img.getWidth(), s.img.getHeight());
				if (p.intersects(fishrect.getX(), fishrect.getY(), 30, 30)){
					intersectFlag = true;
					it.remove();
					player.eat();
				}
				s.update();
			}
			if(intersectFlag){
				gc.setFill(Color.RED);
				gc.fillText("MUNCH!", 400, 500);
			}
			gc.setFill(Color.RED);
			gc.fillText("Age: " + ((FirstCell) player).getAge(), 100, 600);
		}
	};

	EventHandler<KeyEvent> keyhandler = new EventHandler<KeyEvent> () {

		@Override
		public void handle(KeyEvent event) {
			if(event.getCode() == KeyCode.W){
				player.setY(player.getY()-5);
			}
			if(event.getCode() == KeyCode.S){
				player.setY(player.getY()+5);
			}
			if(event.getCode() == KeyCode.A){
				player.setX(player.getX()-5);
			}
			if(event.getCode() == KeyCode.D){
				player.setX(player.getX()+5);
			}
		}		
	};

	public static void main(String[] args) {
		launch(args);
	}

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

		
		
		
		player = new FirstCell(30, 30, gc);
		
		
		
		f = new FoodFactory(gc);

		scene.setOnKeyPressed(keyhandler);
		timer.start();
	}
	
	public Popup createPopup(){
		final Popup popup = new Popup();
		popup.setAutoHide(true);
		popup.setX(300);
		popup.setY(200);
		popup.getContent().addAll(new Button());
		return popup;
	}

}
