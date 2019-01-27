import java.awt.Rectangle;
import java.util.*;
import javafx.scene.image.Image;

/**
 * All objects in the game are objects of this class. It contains information
 * of the location of the objects, their bounding rectangles, their graphics,
 * whether they can collide or not, and how gravity applies to them, if at all.
 */
public class GameObject{

    /**
     * X-coordinate of the GameObject.
     */
    private double x;

    /**
     * Y-coordinate of the GameObject.
     */
    private double y;

    /**
     * Width of the GameObject.
     */
    private double width;

    /**
     * Height of the GameObject.
     */
    private double height;

    /**
     * The rectangle that bounds the GameObject. Used for collision checking.
     */
    private Rectangle bounds;

    /**
     * The graphics of the GameObject.
     */
    private Image image;

    /**
     * List of GameObjects that is used for collision checking.
     */
    private List<GameObject> objectList;

    /**
     * Determines whether the GameObject can collide or not.
     */
    private PhysicsType physicsType;

    /**
     * Determines whether gravity applies to this GameObject or not.
     */
    private boolean gravityOn = false;

    /**
     * A multiplier for gravity, used to make gravity affect GameObjects
     * in different ways from each other.
     */
    private double personalGravity = 1.0;
    
    /**
     * Constructs GameObject with image, bounding rectangle and location.
     * The size will be the size of the image.
     * 
     * @param x X-coordinate of GameObject.
     * @param y Y-coordinate of GameObject.
     * @param image Image that will be drawn in the location
     *              of the GameObject.
     * @param scene Scene that contains canvas that contains list
     *              of GameObjects.
     */
    public GameObject(double x, double y, Image image, GameScene scene) {
        this.x = x;
        this.y = y;
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();

        bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
        objectList = scene.getCanvas().getObjectList();
    }

    /**
     * Constructs GameObject with image, bounding rectangle, location
     * and size.
     * 
     * @param x X-coordinate of GameObject.
     * @param y Y-coordinate of GameObject.
     * @param width Width of GameObject.
     * @param height Height of GameObject.
     * @param image Image that will be drawn in the location
     *              of the GameObject.
     * @param scene Scene that contains canvas that contains list
     *              of GameObjects.
     */
    public GameObject(double x, double y, double width, double height,
                      Image image, GameScene scene) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
        objectList = scene.getCanvas().getObjectList();
    }

    /**
     * Constructs GameObject with bounding rectangle and location,
     * but without image. Meant for solid objects created by TileMap
     * class.
     * 
     * @param x X-coordinate of GameObject.
     * @param y Y-coordinate of GameObject.
     * @param width Width of GameObject.
     * @param height Height of GameObject.
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
        physicsType = PhysicsType.SOLID;
    }

    /**
     * Checks whether given GameObject intersects with this GameObject.
     * 
     * @param go GameObject that is checked for intersection.
     * @return True if objects intersect, false if not.
     */
    public boolean collides(GameObject go) {
        return this.bounds.intersects(go.getBounds());
    }
    
    /**
     * Move object horizontally without checking for collisions.
     * 
     * @param dx Number of pixels that the object is moved.
     *           Positive: move right, negative: move left.
     */
    public void moveX(double dx) {
        setX(x + dx);
    }

    /**
     * Move object horizontally while checking for collisions
     * with solid objects.
     * 
     * @param dx Number of pixels that the object is moved.
     *           Positive: move right, negative: move left.
     */
    public void moveXCheckCollision(double dx) {
        setXCheckCollision(x + dx);
    }

    /**
     * Move object vertically without checking for collisions.
     * 
     * @param dy Number of pixels that the object is moved.
     *           Positive: move down, negative: move up.
     */
    public void moveY(double dy) {
        setY(y + dy);
    }

    /**
     * Move object vertically while checking for collisions
     * with solid objects.
     * 
     * @param dy Number of pixels that the object is moved.
     *           Positive: move down, negative: move up.
     */
    public void moveYCheckCollision(double dy) {
        setYCheckCollision(y + dy);
    }
    
    /**
     * @return X-coordinate of GameObject.
     */
    public double getX() { return x; }

    /**
     * Sets x-coordinate without checking for collisions.
     * 
     * @param x X-coordinate of GameObject.
     */
    public void setX(double x) {
        this.x = x;
        bounds.x = (int) x;   
    }

    /**
     * Checks whether the given coordinate is free of solid objects,
     * so another solid object can be moved there. If coordinate is
     * free or if this object is not solid, sets the given parameter
     * as the x-coordinate.
     * 
     * @param x X-coordinate of GameObject.
     */
    public void setXCheckCollision(double x) {
        boolean isFree = true;

        // Ethereal objects don't collide; only check solid objects
        if(physicsType == PhysicsType.SOLID) {

            Rectangle tempRectangle = new Rectangle(bounds);
            tempRectangle.setLocation((int) x, (int) getY());

            // Iterate objectList to check if given new location would
            // intersect with another solid object.
            for (GameObject o : objectList) {

                if(o != this 
                    && o.getPhysicsType() == PhysicsType.SOLID 
                    && o.getBounds().intersects(tempRectangle)) {
                    isFree = false;
                }                                                               
            }
        }

        // If given new location is free of solid objects, move this object.
        if(isFree) {
            this.x = x;
            bounds.x = (int) x;   
        }
    }

    /**
     * @return Y-coordinate of GameObject.
     */
    public double getY() { return y; }

    /**
     * Sets y-coordinate without checking for collisions.
     * 
     * @param y Y-coordinate of GameObject.
     */
    public void setY(double y) {
        this.y = y;
        bounds.y = (int) y;    
    }

    /**
     * Checks whether the given coordinate is free of solid objects,
     * so another solid object can be moved there. If coordinate is
     * free or if this object is not solid, sets the given parameter
     * as the y-coordinate.
     * 
     * @param y Y-coordinate of GameObject.
     */
    public void setYCheckCollision(double y) {
        boolean isFree = true;

        // Ethereal objects don't collide; only check solid objects
        if(physicsType == PhysicsType.SOLID) {

            Rectangle tempRectangle = new Rectangle(bounds);
            tempRectangle.setLocation((int) getX(), (int) y);
            
            // Iterate objectList to check if given new location would
            // intersect with another solid object.
            for (GameObject o : objectList) {
                if(o != this 
                    && o.getPhysicsType() == PhysicsType.SOLID 
                    && o.getBounds().intersects(tempRectangle)) {
                    isFree = false;
                }                                                               
            }
        }

        // If given new location is free of solid objects, move this object.
        if(isFree) {
            this.y = y;
            bounds.y = (int) y;
        }
    }

    /**
     * @return Bounding rectangle of GameObject.
     */
    public Rectangle getBounds() { return bounds; }

    /**
     * @param bounds Bounding rectangle of GameObject.
     */
    public void setBounds(Rectangle bounds) { this.bounds = bounds; }

    /**
     * @return Width of GameObject in pixels.
     */
    public double getWidth() { return width; }

    /**
     * Sets the width of the GameObject and its bounding rectangle.
     * 
     * @param width Width of GameObject in pixels.
     */
    public void setWidth(double width) {
        this.width = width;
        bounds.setSize((int) width, (int) height);
    }

    /**
     * @return Height of GameObject in pixels.
     */
    public double getHeight() { return height; }

    /**
     * Sets the height of the GameObject and its bounding rectangle.
     * 
     * @param height Height of GameObject in pixels.
     */
    public void setHeight(double height) {
        this.height = height;
        bounds.setSize((int) width, (int) height);
    }

    /**
     * @return Image that will be drawn in the location of
     *         the GameObject.
     */
    public Image getImage() { return image; }

    /**
     * @param image Image that will be drawn in the location of
     *              the GameObject.
     */
    public void setImage(Image image) { this.image = image; }

    /**
     * @return Physics type of the object = whether it can collide or not.
     */
    public PhysicsType getPhysicsType() { return physicsType; }

    /**
     * @param type Physics type of the object = whether it can collide or not.
     */
    public void setPhysicsType(PhysicsType type) { physicsType = type; }

    /**
     * @return Whether gravity applies to this object or not.
     */
    public boolean getGravityOn() { return gravityOn; }

    /**
     * @param gravityOn Whether gravity applies to this object or not.
     */
    public void setGravityOn(boolean gravityOn) { this.gravityOn = gravityOn; }

    /**
     * @return Personal multiplier for how gravity affects this object.
     */
    public double getPersonalGravity() { return personalGravity; }

    /**
     * @param gravity Personal multiplier for how gravity affects this object.
     */
    public void setPersonalGravity(double gravity) { personalGravity = gravity; }
}