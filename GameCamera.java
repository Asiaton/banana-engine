/**
 * GameCamera is used to control which part of the map is 
 * visible at each moment. By default camera is in the upper
 * left corner of the screen.
 */
public class GameCamera {
    
    /**
     * X-coordinate of the camera.
     */
    private double x;

    /**
     * Y-coordinate of the camera.
     */
    private double y;

    /**
     * Width of the canvas in pixels. Used for correctly
     * positioning the camera on the screen.
     */
    private double canvasWidth;

    /**
     * Height of the canvas in pixels. Used for correctly
     * positioning the camera on the screen.
     */
    private double canvasHeight;

    /**
     * Width of the whole game world in pixels. Used for correctly
     * positioning the camera on the screen.
     */
    private double worldWidth = 0;

    /**
     * Height of the whole game world in pixels. Used for correctly
     * positioning the camera on the screen.
     */
    private double worldHeight = 0;

    /**
     * Default constructor. In case user doesn't want to add a camera,
     * the GameScene class automatically uses this constructor.
     * Puts the camera in the upper left corner of the game world.
     */
    public GameCamera() {
        x = 0;
        y = 0;
    }

    /**
     * More detailed constructor. Specifies camera location along with
     * canvas and game world sizes.
     * 
     * @param x X-coordinate of camera.
     * @param y Y-coordinate of camera.
     * @param scene Scene that the GameCanvas and TileMap are taken from.
     */
    public GameCamera(double x, double y, GameScene scene) {
        this.x = x;
        this.y = y;
        canvasWidth = scene.getCanvas().getWidth();
        canvasHeight = scene.getCanvas().getHeight();

        // Check whether the world has a tiled map or a regular background.
        // If neither, the world size can be specified with set methods.
        if (scene.getTileMap() != null) {
            worldWidth = scene.getTileMap().getMapPixelWidth();
            worldHeight = scene.getTileMap().getMapPixelHeight();
        } else if (scene.getCanvas().getBackground() != null) {
            worldWidth = scene.getCanvas().getBackground().getWidth();
            worldHeight = scene.getCanvas().getBackground().getHeight();
        }
    }

    /**
     * Center the camera on the specified GameObject.
     * 
     * @param object GameObject to center the camera on.
     */
    public void center(GameObject object) {
        setPosition(object.getX() + (object.getWidth() / 2) - (canvasWidth / 2),
                object.getY() + (object.getHeight() / 2) - (canvasHeight / 2));
    }

    /**
     * Sets the position of the camera without allowing it to show 
     * areas outside the game world. If the world is smaller
     * than the canvas, sets the corresponding coordinate to 0.
     * 
     * @param x X-coordinate to be set.
     * @param y Y-coordinate to be set.
     */
    public void setPosition(double x, double y) {
        if (x < 0 || worldWidth < canvasWidth) {
            this.x = 0;
        } else if (x > worldWidth - canvasWidth) {
            this.x = worldWidth - canvasWidth;
        } else {
            this.x = x;
        }
        
        if (y < 0 || worldHeight < canvasHeight) {
            this.y = 0;
        } else if (y > worldHeight - canvasHeight) {
            this.y = worldHeight - canvasHeight;
        } else {
            this.y = y;
        }
    }

    /**
     * @return X-coordinate of the camera.
     */
    public double getX() { return x; }
    
    /**
     * @param x X-coordinate of the camera.
     */
    public void setX(double x) { this.x = x; }
    
    /**
     * @return Y-coordinate of the camera.
     */
    public double getY() { return y; }
        
    /**
     * @param y Y-coordinate of the camera.
     */
    public void setY(double y) { this.y = y; }

    /**
     * @return Width of the game world in pixels.
     */
    public double getWorldWidth() { return worldWidth; }

    /**
     * @param width Width of the game world in pixels.
     */
    public void setWorldWidth(double width) { worldWidth = width; }

    /**
     * @return Height of the game world in pixels.
     */
    public double getWorldHeight() { return worldHeight; }

    /**
     * @param height Height of the game world in pixels.
     */
    public void setWorldHeight(double height) { worldHeight = height; }
}