package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import util.MusicUtil;

//��ʼҳ
public class StartUp implements ActionListener{ 
	JFrame frame = new JFrame();
    JButton Button1 = new JButton("����ģʽ");
    JButton Button2 = new JButton("˫��ģʽ");
    JButton Button3 = new JButton("��غ�ģʽ");
    JLayeredPane layeredPane;
    JPanel panel;
    JLabel label;
    ImageIcon image;
    public StartUp() {
        this.Button1.setBounds(200, 300, 100, 50);
        this.Button1.addActionListener(this);
        this.Button1.setBackground(Color.PINK);
        
        this.Button2.setBounds(200, 400, 100, 50);
        this.Button2.addActionListener(this);
        this.Button2.setBackground(Color.PINK);

        this.Button3.setBounds(200, 500, 100, 50);
        this.Button3.addActionListener(this);
        this.Button3.setBackground(Color.PINK);


        layeredPane = new JLayeredPane();
        image = new ImageIcon("image/StartBg.jpg");
        panel = new JPanel();
        panel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());//��ͼƬ�����������
        label = new JLabel(image);//JLabel ���������ʾ�ı���ͼ���ͬʱ��ʾ����

        panel.add(label);

        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(Button1, JLayeredPane.MODAL_LAYER);
        layeredPane.add(Button2, JLayeredPane.MODAL_LAYER);
        layeredPane.add(Button3, JLayeredPane.MODAL_LAYER);

        frame.setTitle("��һ��");
        frame.setLayeredPane(layeredPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getIconWidth(),image.getIconHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				MusicUtil.playBackground();
			}
		}).start();
		

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.Button1) {
            this.frame.dispose();
            try {
                new GameFrame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (e.getSource() == this.Button2) {
            this.frame.dispose();
            try {
                new GameFrame2();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (e.getSource() == this.Button3) {
            this.frame.dispose();
            try {
                new GameFrame3();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
