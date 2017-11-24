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

			//TODO fix this bug with evolution


			//player.delegate.update();
			player.update();


			if (count++ > 100) {
				food.add(f.createProduct("", rnd.nextInt(800), rnd.nextInt(600)));
				count =0;				
			}	


			Rectangle p = new Rectangle(player.x, player.y, 30, 30);
			Iterator<Food> it = food.iterator();
			while (it.hasNext()){
				Food s = it.next();
				Rectangle fishrect = new Rectangle(s.x, s.y, s.img.getWidth(), s.img.getHeight());
				if (p.intersects(fishrect.getX(), fishrect.getY(), 30, 30)){
					intersectFlag = true;
					it.remove();
					player.eat();
					if ( ((FirstCell)player).age == 2 ) {
						createChoice("Become a vertebrate", "Become an invertebrate");	
					}
					else if ( ((FirstCell)player).age == 4 )
						createChoice("Become a Cartilaginous fish", "I want bony skeleton");
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
				player.y = player.y-5;
			}
			if(event.getCode() == KeyCode.S){
				player.y = player.y+5;
			}
			if(event.getCode() == KeyCode.A){
				player.x = player.x-5;
			}
			if(event.getCode() == KeyCode.D){
				player.x = player.x+5;
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
				System.out.println(choice1.getText());
				root.getChildren().remove(choice1);
				root.getChildren().remove(choice2);
			}});
		choice2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				((FirstCell)player).evolve(choice2.getText());
				System.out.println(choice2.getText());
				root.getChildren().remove(choice1);
				root.getChildren().remove(choice2);
			}});

		root.getChildren().add(choice1);
		root.getChildren().add(choice2);

	}

}
