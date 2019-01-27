import java.util.*;
import javafx.scene.image.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

/**
 * Class for adding a map created by Tiled maps generator. 
 * 
 * It works best with a tiled maps file with a maximum of one tile layer
 * and a maximum of one object layer.
 * 
 * It creates a tile set based on a specified image, reads the .tmx file
 * and creates a 2D array of the tile layer for drawing it with the tile set.
 * To avoid errors, the same tile set image should be used in both Tiled
 * and the program code.
 * 
 * It also creates solid, invisible GameObjects from the object layer 
 * in the .tmx file. The graphics of the invisible objects should be 
 * specified on the tile layer of the .tmx file.
 */
public class TileMap {

    /**
     * Width of the map in tiles.
     */
    private int mapWidth;

    /**
     * Height of the map in tiles.
     */
    private int mapHeight;

    /**
     * Width of a single tile in pixels.
     */
    private double tileWidth;

    /**
     * Height of a single tile in pixels.
     */
    private double tileHeight;

    /**
     * List of images created from a single tileset image, used for
     * efficiently drawing the different tiles on the canvas.
     */
    private List<Image> tileSet;

    /**
     * 2-dimensional array representing the map. Each index has a value
     * depending on the position of the tile's graphics in the tileset image.
     */
    private int[][] tiles;

    /**
     * Canvas that the map is drawn on.
     */
    private GameCanvas canvas;

    /**
     * Scene that contains the canvas and camera for drawing.
     */
    private GameScene scene;

    /**
     * Path of the Tiled maps file used.
     */
    private Path source;

    /**
     * Constructs the map with all the necessary attributes, and 
     * creates the tiles and objects.
     * 
     * @param scene Scene that contains the canvas and camera for drawing.
     * @param source Path of the Tiled maps file used.
     * @param mapWidth Width of the map in tiles.
     * @param mapHeight Height of the map in tiles.
     * @param tileWidth Width of a single tile in pixels.
     * @param tileHeight Width of a single tile in pixels.
     */
    public TileMap(GameScene scene, Path source, int mapWidth, int mapHeight,
                    double tileWidth, double tileHeight) {
        this.scene = scene;
        this.source = source;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        tiles = new int[mapHeight][mapWidth];
        canvas = scene.getCanvas();

        createTileArray();
        createObjects();
    }

    /**
     * Creates a tile set based on a single image with all the tiles.
     * 
     * @param image The image that is divided into smaller pieces.
     * @param rows Number of rows of tiles on the image.
     * @param columns Number of columns of tiles on the image.
     */
    public void createTileSet(Image image, int rows, int columns) {
        tileSet = new ArrayList<>();

        PixelReader reader = image.getPixelReader();

        for (int i = 0 ; i < columns ; i++) {
            for (int j = 0 ; j < rows ; j++) {
                WritableImage newImage = new WritableImage(reader, 
                        j * (int) tileWidth, i * (int) tileHeight, 
                        (int) tileWidth, (int) tileHeight);
                tileSet.add(newImage);
            }
        }
    }

    /**
     * Creates a 2-dimensional array representing the tiles on the map.
     * Each tile has a value which determines what image will be drawn
     * in its position.
     */
    public void createTileArray() {
        List<String> strings = Arrays.asList("");
        int startingLine = 0;
        
        try {
            strings = Files.readAllLines(source);
            
            // The line before the tile layer description in the .tmx file 
            // starts with "<data". The index of that line is used as a 
            // starting point for converting the layer description into 
            // integers.
            for (String s : strings) {
                if (s.contains("<data")) {
                    startingLine = strings.indexOf(s);
                }
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Iterate the rows starting from the line below the starting point,
        // splitting each row to a String array containing only numbers
        // in String form.
        for (int i = 0 ; i < mapHeight ; i++) { 
            String[] tmp = strings.get(i + startingLine + 1).split(",");

            // Convert the resulting String array into integers and assign
            // the integers into the 2-dimensional array.
            for (int j = 0 ; j < tmp.length ; j++) {
                String s = tmp[j];
                tiles[i][j] = Integer.parseInt(s);
            }
        }
    }

    /**
     * Reads the .tmx file and creates solid GameObjects from its
     * object layer. Does nothing if there is no object layer.
     */
    public void createObjects() {
        List<String> strings = Arrays.asList("");
        int startingLine = 0;
        int endingLine = 0;
        
        try {
            strings = Files.readAllLines(source);
            
            // The line before the object group description in the .tmx file
            // starts with "<objectgroup". The index of that line is used as a
            // starting point for converting the layer description into
            // Strings, which are later converted into integers.
            for (String s : strings) {
                if (s.contains("<objectgroup")) {
                    startingLine = strings.indexOf(s);
                }

                // The line after the object group description in the .tmx file
                // starts with "</objectgroup". The index of that line is used
                // as an ending point for converting the layer description into
                // Strings, which are later converted into integers.
                if (s.contains("</objectgroup")) {
                    endingLine = strings.indexOf(s);
                }
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Only iterate the array if startingLine or endingLine have been
        // changed, which means the .tmx file contains an object layer.
        if (startingLine != 0 || endingLine != 0){

            // Iterate the rows starting from the line below the starting point,
            // splitting each row to a String array.
            for (int i = startingLine + 1 ; i < endingLine ; i++) {
                String[] tmp = strings.get(i).split(" ");

                /** Initialize the attributes used to create GameObjects
                * out of the object layer.
                * x = X-coordinate of the GameObject.
                * y = Y-coordinate of the GameObject.
                * w = Width of the GameObject.
                * h = Height of the GameObject.
                */
                double x = 0;
                double y = 0;
                double w = 0;
                double h = 0;

                // Iterate the temporary String array and check for rows
                // that specify each distinct attribute. When found, remove
                // characters other than numbers, '.' and '-'. Convert the
                // remaining String to a double.
                for (String s : tmp) {

                    if (s.contains("x=")) {
                        x = Double.parseDouble(s.replaceAll("[^\\d.-]", ""));
                    }

                    if (s.contains("y=")) {
                        y = Double.parseDouble(s.replaceAll("[^\\d.-]", ""));
                    }

                    if (s.contains("width=")) {
                        w = Double.parseDouble(s.replaceAll("[^\\d.-]", ""));
                    }

                    if (s.contains("height=")) {
                        h = Double.parseDouble(s.replaceAll("[^\\d.-]", ""));
                    }

                    // When all four attributes have been specified, create
                    // a new GameObject with the attributes, and reset the
                    // attributes to 0.
                    if (x != 0 && y != 0 && w != 0 && h != 0) {
                        canvas.add(new GameObject(x, y, w, h));
                        x = 0;
                        y = 0;
                        w = 0;
                        h = 0;
                    }
                }
            }
        }
    }

    /**
     * Draw the TileMap on the canvas using the created tileset.
     */
    public void draw() {
        for (int i = 0 ; i < mapHeight ; i++) {
            for (int j = 0 ; j < mapWidth ; j++) {
                canvas.draw(scene.getGameCamera(),
                            tileSet.get(tiles[i][j] - 1),
                            j * tileWidth,
                            i * tileHeight);
            }
        }
    }

    /**
     * @return Map width in pixels.
     */
    public double getMapPixelWidth() { return mapWidth * tileWidth;}
    
    /**
     * @return Map height in pixels.
     */
    public double getMapPixelHeight() { return mapHeight * tileHeight; }

    /**
     * @return Map width in tiles.
     */
    public int getMapWidth() { return mapWidth; }

    /**
     * @param width Map width in tiles.
     */
    public void setMapWidth(int width) { mapWidth = width; }

    /**
     * @return Map height in tiles.
     */
    public int getMapHeight() { return mapHeight; }

    /**
     * @param height Map height in tiles.
     */
    public void setMapHeight(int height) { mapHeight = height; }

    /**
     * @return Width of a single tile in pixels.
     */
    public double getTileWidth() { return tileWidth; }

    /**
     * @param width Width of a single tile in pixels.
     */
    public void setTileWidth(double width) { tileWidth = width; }

    /**
     * @return Height of a single tile in pixels.
     */
    public double getTileHeight() { return tileHeight; }

    /**
     * @param height Height of a single tile in pixels.
     */
    public void setTileHeight(double height) { tileHeight = height; }

    /**
     * @return Path of the Tiled maps file used.
     */
    public Path getSource() { return source; }
    
    /**
     * @param source Path of the Tiled maps file used.
     */
    public void setSource(Path source) { this.source = source; }
    
    /**
     * @return Tileset used to draw the tiles on the canvas.
     */
    public List<Image> getTileSet() { return tileSet; }

    /**
     * @return 2-dimensional array representing the map with each
     *         tile having an integer corresponding to a tile
     *         in the tileset.
     */
    public int[][] getTiles() { return tiles; }
}