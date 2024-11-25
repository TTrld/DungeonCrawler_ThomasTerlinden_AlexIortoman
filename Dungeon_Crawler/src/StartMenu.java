import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartMenu extends JPanel {

    private Image backgroundImage;
    private JButton buttonStart;
    private JButton buttonSettings;
    private JButton buttonExit;

    // Référence au CardLayout et panneau parent pour la gestion des vues
    private CardLayout cardLayout;
    private JPanel parentPanel;
    private Main mainGame; // Ajouter une référence à Main

    public StartMenu(CardLayout cardLayout, JPanel parentPanel, String imagePath, Main mainGame) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.mainGame = mainGame; // Initialiser mainGame

        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading background image.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        int imageWidth = 1005;
        int imageHeight = 1025;

        this.setLayout(null);
        this.setPreferredSize(new Dimension(imageWidth, imageHeight));

        // Fond de l'écran
        JPanel backgroundPanel = new BackgroundPanel(imagePath);
        backgroundPanel.setBounds(0, 0, imageWidth, imageHeight);
        this.add(backgroundPanel);

        // Bouton Start Game
        buttonStart = new JButton("");
        buttonStart.setBounds(100, 828, 198, 166);
        buttonStart.setContentAreaFilled(false);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lors du clic, basculer sur la carte "Game"
                cardLayout.show(parentPanel, "Game");

                // Appeler la méthode dans Main pour activer les événements clavier
                mainGame.startGame();
            }
        });
        this.add(buttonStart);

        // Bouton Settings (pas de fonction pour l'instant)
        buttonSettings = new JButton("");
        buttonSettings.setBounds(539, 876, 113, 28);
        buttonSettings.setContentAreaFilled(false);
        this.add(buttonSettings);

        // Bouton Exit
        buttonExit = new JButton("");
        buttonExit.setBounds(715, 819, 200, 190);
        buttonExit.setContentAreaFilled(false);
        buttonExit.addActionListener(e -> System.exit(0)); // Quitter l'application
        this.add(buttonExit);
    }
}