import javafx.scene.canvas.*;
import javafx.scene.*;
import java.util.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class that contains a canvas and a list of game objects to be drawn
 * on that canvas. Can be used as a sort of "level" by changing which
 * canvas is the currently shown one in GameScenes.
 */
public class GameCanvas {

    /**
     * Canvas that the game objects are drawn on.
     */
    private Canvas canvas;

    /**
     * This object's methods are used for drawing game objects on the canvas.
     */
    private GraphicsContext gc;

    /**
     * List of game objects that the GameCanvas will draw.
     */
    private List<GameObject> objectList = new ArrayList<>();

    /**
     * Background that will be drawn behind all game objects.
     */
    private Image background;

    /**
     * Constructs a GameCanvas with the necessary attributes.
     * 
     * @param width Canvas width in pixels.
     * @param height Canvas height in pixels.
     */
    public GameCanvas(double width, double height) {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Adds specified GameObject to list of GameObjects, which is used
     * for drawing. The index in the list determines in which order
     * GameObjects are drawn on the canvas:
     * 
     * Smallest index = drawn first (under all others)
     * Largest index = drawn last (on top of all others)
     * 
     * @param object GameObject to be added to list
     */
    public void add(GameObject object) {
        objectList.add(object);
    }

    /**
     * Draws background and all GameObjects on the canvas, adjusting the
     * drawing location depending on the position of the camera.
     * 
     * @param camera Camera that adjusts the drawing location.
     */
    public void drawObjects(GameCamera camera) {
        double cameraX = camera.getX();
        double cameraY = camera.getY();

        if(background != null) {
            gc.drawImage(background, 0 - cameraX, 0 - cameraY);
        }

        for (GameObject o : objectList) {
            if(o.getImage() != null) {
                gc.drawImage(o.getImage(), 
                             o.getX() - cameraX, 
                             o.getY() - cameraY,
                             o.getWidth(),
                             o.getHeight());
            }
        }
    }

    /**
     * Draws specified image on specified location, adjusting the
     * drawing location depending on the position of the camera.
     * 
     * @param camera Camera that adjusts the drawing location.
     * @param image Image that will be drawn in the specified location.
     * @param x X-coordinate the image will be drawn in.
     * @param y Y-coordinate the image will be drawn in.
     */
    public void draw(GameCamera camera, Image image, double x, double y) {
        double cameraX = camera.getX();
        double cameraY = camera.getY();

        gc.drawImage(image, x - cameraX, y - cameraY);
    }
 
    /**
     * @return List of game objects.
     */
    public List<GameObject> getObjectList() { return objectList; }

    /**
     * @param list List of game objects.
     */
    public void setObjectList(List<GameObject> list) { objectList = list; }

    /**
     * @return Canvas that game objects are drawn on.
     */
    public Canvas getCanvas() { return canvas; }

    /**
     * @param canvas Canvas that game objects are drawn on.
     */
    public void setCanvas(Canvas canvas) { this.canvas = canvas; }

    /**
     * @return Canvas width in pixels.
     */
    public double getWidth() { return canvas.getWidth(); }

    /**
     * @param width Canvas width in pixels.
     */
    public void setWidth(double width) { canvas.setWidth(width); }

    /**
     * @return Canvas height in pixels.
     */
    public double getHeight() { return canvas.getHeight(); }

    /**
     * @param height Canvas height in pixels.
     */
    public void setHeight(double height) { canvas.setHeight(height); }

    /**
     * @return Background image for canvas.
     */
    public Image getBackground() { return background; }

    /**
     * @param background Background image for canvas.
     */
    public void setBackground(Image background) { this.background = background; }
}