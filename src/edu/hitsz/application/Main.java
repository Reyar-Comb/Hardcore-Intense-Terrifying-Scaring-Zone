package edu.hitsz.application;

import edu.hitsz.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
