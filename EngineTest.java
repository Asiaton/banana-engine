import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.*;
import javafx.scene.input.KeyCode;

public class EngineTest extends Application {

    private GameLoop game;
    private TestScene ts;

    public static void main(String [] args) {
        launch(args);
    }

    public void start(Stage stage) {

        stage.setTitle("Space game");
        
        Group root = new Group();
        ts = new TestScene(root);
        game = new GameLoop(ts);
        game.start();

        stage.setScene(ts);
        stage.show();
    }
}