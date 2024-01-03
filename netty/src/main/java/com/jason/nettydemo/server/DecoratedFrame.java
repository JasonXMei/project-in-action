package com.jason.nettydemo.server;

import javax.swing.*;

public class DecoratedFrame extends JFrame {
    public DecoratedFrame() {
        this.getContentPane().add(new JLabel("Just a test."));
        //this.setUndecorated(true); // 去掉窗口的装饰
        //this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG); //采用指定的窗口装饰风格
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(300,150);
    }
    public static void main(String[] args) {
        JFrame frame = new DecoratedFrame();
        frame.setVisible(true);
    }
}