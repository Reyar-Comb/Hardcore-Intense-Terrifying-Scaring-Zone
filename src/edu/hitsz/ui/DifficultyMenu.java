package edu.hitsz.ui;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyMenu extends JPanel {
    private JButton EasyButton;
    private JButton NormalButton;
    private JButton HardButton;
    private JPanel DifficultyPanel;
    private JLabel DifficultyTitle;

    public DifficultyMenu(MainFrame mainFrame) {
        this.setLayout(new BorderLayout());
        this.add(DifficultyPanel, BorderLayout.CENTER);

        EasyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.ChooseDifficulty(mainFrame.gamePanel, "easy");
            }
        });
        NormalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.ChooseDifficulty(mainFrame.gamePanel, "normal");
            }
        });
        HardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.ChooseDifficulty(mainFrame.gamePanel, "hard");
            }
        });
    }
}
