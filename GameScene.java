import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Class GameScene is an extension of the JavaFX Scene class with
 * added methods to make drawing and running the game possible.
 */
abstract class GameScene extends Scene {

    /**
     * Constructs the game scene and passes the root node to
     * its super constructor (Scene).
     * 
     * @param root Root node of the JavaFX application.
     */
    public GameScene(Group root){
        super(root);
    }

    /**
     * Forces an update method to subclasses to make running
     * the game possible.
     */
    abstract void update();

    /**
     * Forces a canvas get method to subclasses to make 
     * retrieving the canvas-to-be-drawn possible.
     */
    abstract GameCanvas getCanvas();

    /**
     * This method makes retrieving a camera possible in 
     * several classes that need it.
     */
    public GameCamera getGameCamera() {
        GameCamera camera = new GameCamera();
        return camera;
    }

    /**
     * This method makes retrieving a tile map possible in
     * several classes that need it.
     */
    public TileMap getTileMap() {
        return null;
    }
}