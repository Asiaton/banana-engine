import java.util.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class handles keyboard input. It detects, which keys are being
 * pressed, adds them to a Set, and removes them from the set when
 * they are no longer being pressed. The resulting set can then be retrieved
 * and used for input detection in a game.
 */
public class KeyHandler {

    /**
     * The set that contains all keys that are being currently pressed
     * at any moment.
     */
    private Set<KeyCode> keySet;

    /**
     * The GameScene that this object is used in.
     */
    private GameScene scene;

    /**
     * Constructs the key handler and initializes the necessary attributes.
     * 
     * @param scene The GameScene that this object is used in.
     */
    public KeyHandler(GameScene scene) {
        this.scene = scene;
        keySet = new LinkedHashSet<>();
    }

    /**
     * Whenever a key is pressed, adds it to the key set.
     * Whenever a pressed key is released, removes it from the key set.
     */
    public void updateKeys() {
        scene.setOnKeyPressed(event -> keySet.add(event.getCode()));
        scene.setOnKeyReleased(event -> keySet.remove(event.getCode()));
    }

    /**
     * @return The set that contains all currently pressed keys.
     */
    public Set<KeyCode> getKeySet() { return keySet; }

    /**
     * @param keySet The set that contains all currently pressed keys.
     */
    public void setKeySet(Set<KeyCode> keySet) { this.keySet = keySet; }
}