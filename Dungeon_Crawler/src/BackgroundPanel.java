import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;
    private int imageWidth;
    private int imageHeight;

    public BackgroundPanel(String imagePath) {

        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading background image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getimageWidth() {
        imageWidth = backgroundImage.getWidth(this);
    }

    public void getimageHeight() {
        imageHeight = backgroundImage.getHeight(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}