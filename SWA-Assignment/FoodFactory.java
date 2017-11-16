import javafx.scene.canvas.GraphicsContext;

public class FoodFactory implements FactoryIF {

	GraphicsContext gc;
	
	
	public FoodFactory(GraphicsContext gc) {
		super();
		this.gc = gc;
	}


	@Override
	public Food createProduct(String discrim, double x, double y) {
		return new Food(x,y,gc);
	}
}
