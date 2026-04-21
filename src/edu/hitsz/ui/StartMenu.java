package edu.hitsz.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {
    private JPanel startPanel;
    private JPanel titlePanel;
    private JPanel startButtonPanel;
    private JLabel title;
    private JButton startButton;

    public StartMenu(MainFrame mainFrame) {
        this.setLayout(new BorderLayout());
        this.add(startPanel, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.cardLayout.show(mainFrame.mainContainer, "difficulty");
            }
        });
    }
}
