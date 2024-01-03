package com.jason.nettydemo.util;

import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1345343984399264602L;

    public Demo(){
        super("label中内容即垂直又水平居中");
        setBounds(100, 100, 400, 300);

        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("General",JLabel.CENTER),BorderLayout.CENTER);
        p.setBackground(Color.green);

        getContentPane().add(p);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
    }
}
