import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CustomMap {

	int[] texture = new int[] {	353,352,292,354,292,352,292,354,352,352,354,352,292,352,352,353,353,354,354,352,292,354,353,352,354,
			354,352,352,353,352,292,292,353,352,292,292,353,352,354,354,292,353,292,354,352,354,352,352,292,352,
			354,-1,-1,352,292,292,353,292,292,353,353,292,354,354,353,352,354,353,292,292,352,292,354,353,292,
			292,-1,-1,292,292,354,352,353,353,352,354,352,353,353,352,353,354,352,292,353,352,352,353,352,292,
			354,-1,-1,292,353,292,292,352,292,353,292,352,292,354,354,353,292,352,352,292,292,354,353,354,354,
			292,-1,-1,354,354,352,354,292,352,352,292,354,292,353,292,352,292,353,354,354,353,353,353,292,352,
			352,-1,289,353,352,352,353,352,353,354,353,354,352,292,292,353,352,354,352,292,292,292,352,292,353,
			292,292,354,352,353,292,352,352,353,354,354,354,353,292,354,354,354,292,352,292,353,354,353,352,352,
			353,353,354,353,292,354,354,352,353,292,292,352,354,353,352,353,352,352,354,354,353,292,354,353,352,
			353,353,292,353,354,352,354,292,352,354,292,292,353,352,292,352,353,353,353,353,354,353,352,352,353,
			321,324,194,354,354,352,352,292,354,353,352,292,196,321,324,197,352,354,354,353,352,354,352,292,354,
			275,276,320,197,352,352,352,292,352,352,352,196,322,-1,-1,320,324,289,289,-1,321,321,324,324,324,
			307,243,276,323,321,321,321,324,324,321,321,-1,-1,-1,-1,-1,-1,289,289,-1,76,77,77,78,-1,
			370,371,243,275,275,275,275,275,275,276,-1,-1,-1,-1,76,77,78,289,289,76,46,174,173,45,78,
			307,370,372,371,371,372,372,307,371,308,-1,-1,-1,76,46,173,45,289,289,46,174,173,174,173,45,
			307,307,307,307,370,371,370,307,371,243,275,276,-1,108,174,173,172,174,172,173,173,173,172,173,173,
			372,307,370,307,370,371,371,370,370,370,370,307,307,46,173,173,174,174,172,174,173,172,172,174,173,
			307,371,307,371,372,372,370,372,372,307,307,307,307,173,173,174,173,173,172,174,173,174,172,172,172,
			371,370,372,372,307,372,371,372,370,307,307,307,307,172,172,174,174,174,173,173,172,173,173,172,174,
			307,371,307,372,372,372,370,307,372,372,372,308,108,172,173,174,172,172,173,174,172,173,174,173,172};

	int[] details = new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,276,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,371,308,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,361,
			-1,-1,371,308,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,64,65,66,-1,-1,-1,-1,-1,
			-1,-1,371,308,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,96,97,98,-1,-1,-1,-1,-1,
			-1,371,211,340,-1,-1,-1,-1,-1,-1,-1,-1,-1,355,-1,-1,-1,128,129,130,-1,355,-1,-1,361,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,355,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,361,-1,-1,357,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,357,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,78,76,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,45,46,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			467,467,467,467,467,468,-1,-1,-1,-1,-1,-1,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			562,562,499,499,563,435,467,467,468,-1,-1,-1,46,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			564,499,562,563,564,562,564,563,500,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};	

	int[] water = new int[] {188,189,188,188,189,125,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			188,188,189,28,156,157,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			188,277,278,157,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			189,309,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			187,309,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			189,309,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			189,60,93,-1,-1,-1,-1,-1,-1,-1,268,269,270,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			187,188,125,-1,-1,-1,-1,-1,-1,-1,300,301,302,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			189,187,60,93,-1,-1,-1,-1,-1,-1,332,333,334,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			187,187,189,60,92,93,-1,-1,-1,-1,-1,-1,91,92,92,93,-1,-1,-1,-1,-1,91,92,92,93,
			189,188,187,189,189,125,-1,-1,-1,-1,-1,-1,123,189,189,60,93,-1,-1,91,92,61,189,188,60,
			188,189,189,187,188,60,92,92,92,92,92,92,61,187,187,188,60,93,91,61,187,187,187,187,189,
			156,29,189,189,187,187,188,189,189,188,188,188,187,187,187,188,189,125,123,188,189,28,156,29,187,
			-1,155,278,278,278,278,278,278,278,279,189,188,188,187,28,156,29,125,123,28,156,157,-1,123,187,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,311,188,188,189,28,157,-1,155,157,155,157,-1,-1,-1,155,156,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,311,187,188,189,125,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,246,278,156,156,157,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,91,92,92,93,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,123,187,28,157,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

	int[] obstacles = new int[] {672,673,674,-1,-1,-1,-1,-1,-1,-1,-1,-1,114,115,116,-1,-1,-1,-1,-1,-1,-1,-1,639,-1,
			704,705,706,-1,-1,-1,-1,-1,-1,-1,-1,-1,146,147,148,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,399,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,399,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,639,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,79,80,81,-1,-1,-1,478,-1,-1,-1,-1,-1,-1,636,-1,-1,-1,399,-1,-1,
			-1,-1,-1,-1,-1,111,176,113,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,668,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,143,144,145,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,800,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,864,-1,-1,-1,-1,639,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,928,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,800,-1,-1,-1,-1,-1,-1,-1,-1,552,-1,-1,-1,
			-1,-1,-1,550,-1,-1,-1,-1,-1,-1,-1,-1,768,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,552,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,928,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,700,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,732,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,509,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,79,80,81,-1,
			463,464,465,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,85,86,87,-1,-1,111,177,113,-1,
			495,496,497,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,117,118,119,-1,-1,143,144,145,-1};

	static int[] BOUNDS = new int[] {	452,452,452,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,
			-1,-1,-1,452,452,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,
			-1,-1,452,-1,-1,-1,-1,-1,-1,-1,452,452,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,452,-1,-1,-1,-1,-1,-1,-1,452,452,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,452,452,-1,-1,-1,-1,-1,-1,452,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,452,452,452,-1,-1,-1,-1,-1,-1,-1,452,452,452,-1,-1,-1,-1,-1,452,452,452,452,
			-1,452,-1,-1,-1,452,452,-1,-1,-1,-1,-1,452,-1,-1,452,452,-1,-1,452,452,452,452,452,452,
			-1,452,-1,-1,-1,452,452,452,452,452,452,452,452,-1,-1,-1,-1,-1,-1,452,452,452,452,452,452,
			-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,452,452,452,452,452,452,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,452,452,452,-1,452,452,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,452,-1,-1,452,452,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

	static int[] DEADLY = new int[] {	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,
			-1,452,-1,-1,-1,-1,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,452,-1,-1,-1,-1,-1,-1,-1};

	int height=32, width=32;
	static Rectangle2D[] TILES;


	Image maptiles = new Image(CustomMap.class.getResource("res/terrain.png").toExternalForm());

	public CustomMap(Pane root) {

		TILES = new Rectangle2D[1024];
		int pos = 0;
		for (int i = 0; i < 1024; i+=32) {
			for (int j = 0; j < 1024; j+=32) {
				TILES[pos] = new Rectangle2D(j, i, width, height);
				pos++;
			}
		}

		writeLayer(texture, root);

		writeLayer(details, root);

		writeLayer(water, root);

		writeLayer(obstacles, root);

	}

	private void writeLayer(int[] layer, Pane root) {
		int pos =0;
		for(int i = 0; i<20; i++) {
			for(int j = 0; j<25; j++) {
				Rectangle2D r;
				if (layer[pos]>=0) {
					r = TILES[layer[pos]];
					ImageView tileset = new ImageView(maptiles);
					tileset.setViewport(r);
					tileset.setX(j*height);
					tileset.setY(i*width+96);
					root.getChildren().add(tileset);
				}
				pos++;
			}
		}
	}
}
