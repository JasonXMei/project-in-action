package com.jason.nettydemo.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by abo on 2019/5/29.
 */
public class ControlWindow extends JFrame implements ActionListener{
    private static final long serialVersionUID = -374177591619018452L;
    private JLabel label;

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    // 设置窗体默认大小,使其适应屏幕大小
    private int DEFAULE_WIDTH = 1000;
    private int DEFAULE_HEIGH = 618;

    // 设置窗体位置,使其在屏幕居中
    private int Location_x;
    private int Location_y;
    int mouseAtX = 0;
    int mouseAtY = 0;

    private JButton minimumBtn;
    private JButton maximizeBtn;
    private JButton closeBtn;

    private int btnWidth = 90;

    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }

    private void initWindow(boolean flag){
        if(flag){
            DEFAULE_WIDTH = (int) (toolkit.getScreenSize().getWidth() * 0.4);
            DEFAULE_HEIGH = (int) (toolkit.getScreenSize().getHeight() * 0.5);
        }else{
            DEFAULE_WIDTH = (int) (toolkit.getScreenSize().getWidth());
            DEFAULE_HEIGH = (int) (toolkit.getScreenSize().getHeight());
        }
        Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
        Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    }

    public ControlWindow(){
        initWindow(true);
        this.setTitle("远程控制程序");
        setUndecorated(true);//设置窗体的标题栏不可见
        /*
         * 设置窗体可拖动
         */
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
            /*
             * 获取点击鼠标时的坐标
             */
            mouseAtX = e.getPoint().x;
            mouseAtY = e.getPoint().y;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
            setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
            }
        });

        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/img/logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                sendEvent(e);
            }

            public void keyPressed(KeyEvent e) {
                sendEvent(e);
            }
        });

        label.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                sendEvent(e);
            }

            public void mousePressed(MouseEvent e) {
                sendEvent(e);
            }

            public void mouseClicked(MouseEvent e) {
                sendEvent(e);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });*/

        /*
         * 实例化简单组件
         */
        minimumBtn = new JButton("最小化");
        maximizeBtn = new JButton("最大化");
        closeBtn = new JButton("断开连接");

        minimumBtn.addActionListener(this);
        maximizeBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        setBtnBounds();
        this.add(closeBtn);
        this.add(maximizeBtn);
        this.add(minimumBtn);

        /*JLabel label = new JLabel("无边框窗体的演示",JLabel.CENTER);
        label.setBounds(280,280,450,45);
        label.setFont(new Font("华文新魏", Font.BOLD,40));

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setLayout(null);

        contentPane.add(b1);
        contentPane.add(maximizeBtn);
        contentPane.add(closeBtn);
        contentPane.add(label);
        setContentPane(contentPane);//设置ContentPane*/

        label = new JLabel("正在连接远程桌面,请稍候...", JLabel.CENTER);
        JPanel p = new JPanel(new BorderLayout());
        p.add(label, BorderLayout.CENTER);
        /*p.add(minimumBtn);
        p.add(maximizeBtn);
        p.add(closeBtn);*/

        /*JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.add(minimumBtn);
        contentPane.add(maximizeBtn);
        contentPane.add(closeBtn);
        contentPane.add(label);
        this.setContentPane(contentPane);*/

        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);

        setSize(DEFAULE_WIDTH,DEFAULE_HEIGH);// 设置窗体默认大小,使其适应屏幕大小
        setLocation(Location_x, Location_y);//设置窗体在屏幕中的位置
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); //设置窗体可见
    }

    /*public void sendEvent(InputEvent event){
        NettyUtil.sendGoogleProtocolMsg(Constants.MSG_EVENT, 0, 1, null,
                ByteObjConverter.objectToByte(event), null, (NioSocketChannel) CIMClient.channel);
    }*/

    public static void main(String[] args) {
        new ControlWindow();
    }

    public void setBtnBounds(){
        minimumBtn.setBounds(DEFAULE_WIDTH/2 - btnWidth/2 - btnWidth - 5,5,btnWidth,30);
        maximizeBtn.setBounds(DEFAULE_WIDTH/2 - btnWidth/2,5,btnWidth,30);
        closeBtn.setBounds(DEFAULE_WIDTH/2 + btnWidth/2 + 5,5,btnWidth,30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "最大化") {
            initWindow(false);
            setBtnBounds();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            maximizeBtn.setText("还原");
        }

        if (e.getActionCommand() == "还原") {
            initWindow(true);
            setBtnBounds();
            setExtendedState(JFrame.NORMAL);
            maximizeBtn.setText("最大化");
        }

        if (e.getActionCommand() == "最小化") {
            setExtendedState(JFrame.ICONIFIED);
        }

        if (e.getActionCommand() == "断开连接") {
            /*System.exit(0);*/
            this.setVisible(false);
        }
    }
}
