package edu.hitsz.ui;

import edu.hitsz.application.Game;

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

    public final Game gamePanel = new Game();
    public MainFrame() {
        setTitle("Aircraft War");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        startMenu = new StartMenu(this);
        difficultyMenu = new DifficultyMenu(this);
        leaderBoard = new LeaderBoard(this, gamePanel);


        mainContainer.add(startMenu, "start");
        mainContainer.add(difficultyMenu, "difficulty");
        mainContainer.add(gamePanel, "game");
        mainContainer.add(leaderBoard, "leaderBoard");
        add(mainContainer);
        cardLayout.show(mainContainer, "start");
        mainContainer.setVisible(true);
    }
    public void ChooseDifficulty(Game game, String difficulty) {
        cardLayout.show(mainContainer, "game");
        game.difficulty = difficulty;
        game.mainFrame = this;
        leaderBoard.dao = game.dao;
        game.action();
    }
}
