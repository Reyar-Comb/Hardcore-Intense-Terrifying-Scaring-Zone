package edu.hitsz.ui;

import edu.hitsz.application.Game;
import edu.hitsz.dataAccess.ScoreRecordDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LeaderBoard extends JPanel {
    private JPanel leaderBoard;
    private JPanel titlepanel;
    private JLabel title;
    private JTable scoreBoard;
    private JTextArea userName;
    private JButton uploadButton;
    private JPanel button;
    private JScrollPane scroll;
    private JButton deleteButton;

    public ScoreRecordDao dao;

    public LeaderBoard(MainFrame mainFrame, Game gamePanel) {
        this.setLayout(new BorderLayout());
        this.add(leaderBoard, BorderLayout.CENTER);

        scroll.setViewportView(scoreBoard);

        uploadButton.addActionListener(e -> {
            dao.addRecord(gamePanel.score, userName.getText(), gamePanel.difficulty);
            RefreshTable(gamePanel);
        });

        deleteButton.addActionListener(e -> {
            int row = scoreBoard.getSelectedRow();
            dao.deleteRecord()
        })
    }

    public void RefreshTable(Game gamePanel){
        String[] columnName = {"Rank", "Username","Score","Difficulty","Time"};
        String[][] tableData= dao.getRecords(gamePanel.difficulty);

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        scoreBoard.setModel(model);
    }
}
