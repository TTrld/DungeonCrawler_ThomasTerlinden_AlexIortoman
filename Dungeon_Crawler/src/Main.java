import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main {

    private static JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;



    public Main(JPanel parentPanel) throws Exception {

        System.setProperty("sun.java2d.uiScale", "1.O");

        displayZoneFrame = new JFrame("SDF SOUS CRACK");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the hero
        DynamicSprite hero = new DynamicSprite(200, 300, 48, 50, ImageIO.read(new File("./img/heroTileSheetLowRes.png")));

        // Initialize enemy
        Enemy zombie0 = new Enemy(1000, 300, 65, 95, ImageIO.read(new File("./img/Zombie.png")));
        Enemy zombie1 = new Enemy(1500, 100, 65, 95, ImageIO.read(new File("./img/Zombie.png")));
        Enemy zombie2 = new Enemy(800, 200, 65, 95, ImageIO.read(new File("./img/Zombie.png")));




        // Load level
        Playground level = new Playground("./data/level1.txt");
        ArrayList<Sprite> environment = level.getSpriteList();

        // Create RenderEngine (temporarily without gameEngine)
        renderEngine = new RenderEngine(displayZoneFrame, hero, null);

        // Create GameEngine and assign RenderEngine
        gameEngine = new GameEngine(hero, environment, renderEngine);

        // Re-create RenderEngine with GameEngine reference
        renderEngine = new RenderEngine(displayZoneFrame, hero, gameEngine);

        // Initialize PhysicEngine
        physicEngine = new PhysicEngine();

        // Add hero and environment to engines
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(zombie0);
        renderEngine.addToRenderList(zombie1);
        renderEngine.addToRenderList(zombie2);
        renderEngine.addToRenderList(hero);

        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());
        physicEngine.addEnemy(zombie0);
        physicEngine.addEnemy(zombie1);
        physicEngine.addEnemy(zombie2);


        // Setup frame and listeners
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.addMouseListener(gameEngine);

        // Start timers
        Timer renderTimer = new Timer(10, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(10, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(10, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.setVisible(true);

        // Debugging
        System.out.println("Initialization complete");
    }
    public static void main(String[] args) throws Exception {

        System.setProperty("sun.java2d.uiScale", "1.0");

        // Créer le CardLayout et le panneau principal
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Lancer l'instance de Main pour initialiser le jeu
        Main mainGame = new Main(mainPanel);

        // Ajouter StartMenu à la vue
        StartMenu startMenu = new StartMenu(cardLayout, mainPanel, "./img/startmenu1.png", mainGame);
        mainPanel.add(startMenu, "StartMenu");

        // Ajouter le jeu au panneau principal
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(mainGame.renderEngine, BorderLayout.CENTER); // Intégrer le rendu du jeu
        mainPanel.add(gamePanel, "Game");

        // Créer une fenêtre contenant le panel et l'afficher
        displayZoneFrame.setSize(1024, 1024);
        displayZoneFrame.add(mainPanel);
        displayZoneFrame.setVisible(true);
    }

    // Méthode qui démarre le jeu et active les événements clavier
    public void startGame() {
        displayZoneFrame.setSize(400,600);

        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.requestFocusInWindow();// Demander le focus à la fenêtre
    }

}

