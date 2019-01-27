import java.util.*;

/**
 * Objects of this class make it possible to apply gravity in the game world.
 * Each GameObject can be subjected to gravity if wanted.
 * Each GameObject also has its own personal gravity modifier to make
 * gravity affect different objects in different ways if wanted.
 */
public class Gravity {

    /**
     * Strength of the gravity pull.
     */
    private double strength;

    /**
     * Specifies in which direction the gravity pulls. Default setting is
     * vertical, but it can be changed to horizontal if wanted.
     */
    private GravityType gravityType = GravityType.VERTICAL;

    /**
     * List of GameObjects for which gravity is automatically
     * applied if on.
     */
    private List<GameObject> objectList;

    /**
     * Constructs the object, specifies its strength and the list
     * of objects it will try to pull.
     */
    public Gravity(double strength, GameScene scene) {
        this.strength = strength;
        objectList = scene.getCanvas().getObjectList();
    }

    /**
     * Pulls objects that have their gravity on in the specified direction.
     */
    public void pull() {
        if(gravityType == GravityType.VERTICAL) {
            for (GameObject o : objectList) {
                if (o.getGravityOn()) {
                    o.moveYCheckCollision(strength * o.getPersonalGravity());
                }
            }
        } else if(gravityType == GravityType.HORIZONTAL) {
            for (GameObject o : objectList) {
                if (o.getGravityOn()) {
                    o.moveXCheckCollision(strength * o.getPersonalGravity());
                }
            }
        }
    }

    /**
     * @return Strength of the gravity pull.
     */
    public double getStrength() { return strength; }

    /**
     * @param strength Strength of the gravity pull.
     */
    public void setStrength(double strength) { this.strength = strength; }

    /**
     * @return Whether the gravity pulls vertically or horizontally.
     */
    public GravityType getGravityType() { return gravityType; }

    /**
     * @param type Whether the gravity pulls vertically or horizontally.
     */
    public void setGravityType(GravityType type) { gravityType = type; }
}