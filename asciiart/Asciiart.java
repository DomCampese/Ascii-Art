import java.io.File;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 
import java.awt.Color;
import java.util.HashMap;

public class Asciiart {

    BufferedImage img;
    File file;
    int width;
    int height;
    Color[][] colorData;
    int[][] brightness;
    HashMap<Integer, Character> asciiMap;

    public static void main(String[] args) {
        new Asciiart(args);
    }

    /* Treat like main */
    public Asciiart(String[] args) {
        initializeImage(args);
        loadImageData();
        initializeAsciiMap();
        printAsciiArt();
    }

    public void initializeImage(String[] args) {
        if (args.length != 1) {
            System.out.println("\tUsage: java Asciiart <image file path>");
            System.exit(0);
        }

        try {
            file = new File(args[0]);
            //use ImageIO to initialize the image
            img = ImageIO.read(file);
        } catch(FileNotFoundException e) {
            System.out.println("Error. File not found.\n");
        } catch(Exception ex) {
            System.out.print("Error. Could not load image.\n");
        }

        // Get and print image width and height
        width = img.getWidth();
        height = img.getHeight();
    }

    public void loadImageData() {
        // Color values stored in this matrix as color objects
        colorData = new Color[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                colorData[i][j] = new Color(img.getRGB(i, j), true);
            }
        } 

        // Create a brightness matrix
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
        // Map brightness values to ascii characters
        final char[] asciis = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$".toCharArray();
        final int MAX_BRIGHTNESS = 255;
        asciiMap = new HashMap<>();
     
        for (int i = 0; i < MAX_BRIGHTNESS; i++) {
            asciiMap.put(i, asciis[i/4]);
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
