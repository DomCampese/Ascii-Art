import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 
import java.awt.Color;
import java.util.HashMap;

/* @author Dom Campese */

public class Asciiart {

    BufferedImage img;
    File file;
    int width;
    int height;
    Color[][] colorData;
    int[][] brightness;
    HashMap<Integer, Character> asciiMap;

    public static void main(String[] args) {
        new Asciiart();
    }

    /********* main *********/
    public Asciiart() {
        initializeImage();
        loadImageData();
        initializeAsciiMap();
        printAsciiArt();
    }

    public void initializeImage() {
        try {
            file = new File("test.jpeg");
            //use ImageIO to initialize the image
            img = ImageIO.read(file);
        } catch(Exception e) {
            System.out.print("There was an error loading the image.");
        }

        //get and print image width and height
        width = img.getWidth();
        height = img.getHeight();
    }

    public void loadImageData() {
        //color values stored in this matrix as color objects
        colorData = new Color[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                colorData[i][j] = new Color(img.getRGB(i, j), true);
            }
        } 

        //now create a brightness matrix
        brightness = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int red = colorData[i][j].getRed();
                int green = colorData[i][j].getGreen();
                int blue = colorData[i][j].getBlue();
                brightness[i][j] = (red + green + blue) / 3; //average of red green and blue vals
            }
        }
    }

    public void initializeAsciiMap() {
        //make a hashmap of ascii characters to brightness values
        final char[] asciis = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$".toCharArray();
        asciiMap = new HashMap<>();
        
        for (int i = 0; i < 255; i++) {
            asciiMap.put(i, asciis[i / 4]);
        }
    }

    public void printAsciiArt() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(asciiMap.get(brightness[i][j]));
            }
            System.out.println();
        }
    }
} 