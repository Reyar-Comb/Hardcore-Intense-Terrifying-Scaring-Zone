package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartPanel extends JPanel {
    private final String username;
    private final OnStartGame callback;

    public interface OnStartGame {
        void start();
    }

    private final JButton startBtn;
    private final JLabel infoLabel;

    public StartPanel(String username, OnStartGame callback) {
        this.username = username;
        this.callback = callback;

        setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        setLayout(null);
        setFocusable(true);

        JLabel welcomeLabel = new JLabel("欢迎, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(0, 200, Main.WINDOW_WIDTH, 50);
        add(welcomeLabel);

        // ---- 开始匹配按钮 ----
        startBtn = new JButton("开始匹配");
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        startBtn.setBounds(Main.WINDOW_WIDTH / 2 - 75, 350, 150, 45);
        add(startBtn);

        // ---- 状态提示 ----
        infoLabel = new JLabel("", SwingConstants.CENTER);
        infoLabel.setForeground(Color.YELLOW);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoLabel.setBounds(0, 420, Main.WINDOW_WIDTH, 30);
        add(infoLabel);

        startBtn.addActionListener(this::onStart);
    }

    private void onStart(ActionEvent e) {
        startBtn.setEnabled(false);
        infoLabel.setText("匹配中");
        callback.start();
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, null);
    }
}
