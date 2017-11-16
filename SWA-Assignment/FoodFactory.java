import javafx.scene.canvas.GraphicsContext;

public class FoodFactory implements FactoryIF {

	GraphicsContext gc;
	
	
	public FoodFactory(GraphicsContext gc) {
		super();
		this.gc = gc;
	}


	@Override
	public GameObject createProduct(String discrim, double x, double y) {
		return new GameObject(30,30,gc);
	}
}
