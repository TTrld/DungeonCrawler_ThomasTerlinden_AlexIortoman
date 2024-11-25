import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();

    public Playground(String pathName) {
        try {
            // Load images
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));

            // Get image dimensions
            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber;

            while (line != null) {
                columnNumber = 0;  // Reset column number for each new line

                // Process each character in the line
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight, imageTreeWidth, imageTreeHeight, imageTree));
                            break;
                        case ' ':
                            environment.add(new Sprite(columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight, imageGrass));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight, imageRockWidth, imageRockHeight, imageRock));
                            break;
                        default:
                            break;
                    }
                    columnNumber++;
                }
                lineNumber++;
                line = bufferedReader.readLine();  // Read the next line
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SolidSprite> getSolidSpriteList() {
        ArrayList<SolidSprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add((SolidSprite) sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Sprite> getSpriteList() {
        return environment;
    }
}
