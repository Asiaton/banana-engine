import javafx.scene.canvas.*;
import javafx.scene.*;
import javafx.animation.AnimationTimer;

/**
 * This class is the game loop. It updates the given scene
 * and draws the contents of its canvas on screen.
 */
public class GameLoop {

    /**
     * The scene that will be run in the game loop.
     */
    private GameScene scene;

    /**
     * The canvas from the given scene. It will be drawn
     * on screen at 60fps.
     */
    private GameCanvas canvas;

    /**
     * Constructs the game loop and initializes its attributes.
     * 
     * @param scene Scene that will be run and its canvas drawn.
     */
    public GameLoop(GameScene scene) {
        this.scene = scene;
        canvas = scene.getCanvas();
    }

    /**
     * Starts the game loop. Updates the given scene and draws the
     * contents of its canvas along with a possible tileMap on the screen.
     */
    public void start() {
        new AnimationTimer() {
            @Override public void handle(long currentNanoTime) {
                scene.update();
                if(scene.getTileMap() != null) {
                    scene.getTileMap().draw();
                }
                canvas.drawObjects(scene.getGameCamera());

                // Caps the frames per second at 60.
                try { 
                    Thread.sleep(1000/60);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}