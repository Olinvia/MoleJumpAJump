package ui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//单人模式
public class GameFrame extends JFrame{
	GamePanel gamePanel;
	ExitFrame exitf;
	GameFrame(){
		gamePanel = new GamePanel(1000,700,1,3,30,1);
		this.setTitle("Jump A Jump");
		this.add(gamePanel);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		new Thread() {
			public void run() {
				while (gamePanel.exit<=0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
				if (gamePanel.exit > 0) {
					exitf=new ExitFrame(gamePanel.exit);
					setVisible(false);
				}
			}
		}.start();
	}
}