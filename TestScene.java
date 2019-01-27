import fi.tamk.tiko.bananaengine.*;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import java.nio.file.Paths;

public class TestScene extends GameScene {

    private GameCanvas canvas;
    private GameCanvas currentCanvas;

    private KeyHandler keyHandler;

    private GameObject sun;
    private GameObject sun2;

    private GameCamera camera;
    private Gravity gravity;
    
    private TileMap map;

    public TestScene(Group root) {
        super(root);

        canvas = new GameCanvas(512, 512);
        currentCanvas = canvas;

        keyHandler = new KeyHandler(this);
        root.getChildren().add(canvas.getCanvas());

        sun = new GameObject(400, 100, 50, 50, new Image("images/sun.png"), this);
        sun2 = new GameObject(10, 100, new Image("images/sun.png"), this);

        sun.setPhysicsType(PhysicsType.SOLID);
        sun2.setPhysicsType(PhysicsType.SOLID);
        
        sun.setGravityOn(true);
        sun.setPersonalGravity(-1);

        canvas.add(sun);
        canvas.add(sun2);
        
        //canvas.setBackground(new Image("images/newspace.jpg"));

        map = new TileMap(this, Paths.get("images/map1.tmx"), 32, 32, 32, 32);
        map.createTileSet(new Image("images/tileset1.png"), 2, 2);

        camera = new GameCamera(0, 0, this);
        gravity = new Gravity(2, this);
    }

    public void update() {
        
        camera.center(sun);

        gravity.pull();

        keyHandler.updateKeys();

        if(keyHandler.getKeySet().contains(KeyCode.S)) {
            sun.moveYCheckCollision(5);
        }

        if(keyHandler.getKeySet().contains(KeyCode.D)) {
            sun.moveXCheckCollision(5);
        }

        if(keyHandler.getKeySet().contains(KeyCode.W)) {
            sun.moveYCheckCollision(-5);
        }

        if(keyHandler.getKeySet().contains(KeyCode.A)) {
            sun.moveXCheckCollision(-5);
        }

        if(keyHandler.getKeySet().contains(KeyCode.H)) {
            gravity.setGravityType(GravityType.HORIZONTAL);
        }

        if(keyHandler.getKeySet().contains(KeyCode.V)) {
            gravity.setGravityType(GravityType.VERTICAL);
        }

        //System.out.println(keyHandler.getKeySet());
    }
    
    public GameCanvas getCanvas() { return currentCanvas; }

    public GameCamera getGameCamera() { return camera; }

    public TileMap getTileMap() { return map; }

    public void setCanvas(GameCanvas canvas) { currentCanvas = canvas; }
}