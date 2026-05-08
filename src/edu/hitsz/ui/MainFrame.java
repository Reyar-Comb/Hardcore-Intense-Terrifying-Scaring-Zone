package edu.hitsz.ui;

import edu.hitsz.application.EasyGame;
import edu.hitsz.application.Game;
import edu.hitsz.application.HardGame;
import edu.hitsz.application.NormalGame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public final CardLayout cardLayout = new CardLayout();
    public final JPanel mainContainer = new JPanel(cardLayout);

    public StartMenu startMenu;
    public DifficultyMenu difficultyMenu;
    public LeaderBoard leaderBoard;

    public Game gamePanel;
    public MainFrame() {
        setTitle("Aircraft War");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        startMenu = new StartMenu(this);
        difficultyMenu = new DifficultyMenu(this);
        // initialize default game panel
        gamePanel = new Game();
        leaderBoard = new LeaderBoard(this, gamePanel);
        leaderBoard.dao = gamePanel.dao;


        mainContainer.add(startMenu, "start");
        mainContainer.add(difficultyMenu, "difficulty");
        mainContainer.add(gamePanel, "game");
        mainContainer.add(leaderBoard, "leaderBoard");
        add(mainContainer);
        cardLayout.show(mainContainer, "start");
        mainContainer.setVisible(true);
    }
    public void ChooseDifficulty(Game game, String difficulty) {
        // create a new Game instance according to difficulty and replace the current game panel
        Game newGame;
        switch (difficulty) {
            case "easy":
                newGame = new EasyGame();
                newGame.mainFrame = this;
                break;
            case "hard":
                newGame = new HardGame();
                newGame.mainFrame = this;
                break;
            default:
                newGame = new NormalGame();
                newGame.mainFrame = this;
                break;
        }

        mainContainer.remove(gamePanel);
        gamePanel = newGame;
        mainContainer.add(gamePanel, "game");

        // rebuild leaderBoard so its internal upload button captures the new game instance
        mainContainer.remove(leaderBoard);
        leaderBoard = new LeaderBoard(this, gamePanel);
        leaderBoard.dao = gamePanel.dao;
        mainContainer.add(leaderBoard, "leaderBoard");

        // show game
        cardLayout.show(mainContainer, "game");
        gamePanel.difficulty = difficulty;
        gamePanel.mainFrame = this;
        gamePanel.action();
    }
}
