package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//Ë«ÈËÄ£Ê½
public class GameFrame2 extends JFrame{
	GamePanel gamePanel1;
	GamePanel gamePanel2;
	ExitFrame exitf;
	GameFrame2(){
		int GAME_WIDTH = 1000;
		int GAME_HEIGHT = 350;
		Random random = new Random();
		int d = random.nextInt(GAME_WIDTH/10) + GAME_HEIGHT/10;
		int h = random.nextInt(GAME_WIDTH/30) ;
		int w = random.nextInt(GAME_WIDTH/30) ;
		int []randomDArray = new int[10];
		int []randomWArray = new int[10];
		int []randomHArray = new int[10];
		for(int i=0;i<randomDArray.length;i++){
			randomDArray[i]=(int)(Math.random()*GAME_WIDTH/10)+GAME_WIDTH/10;
		}
		
		for(int i=0;i<randomWArray.length;i++){
			randomWArray[i]=(int)(Math.random()*GAME_WIDTH/30);
		}
		
		for(int i=0;i<randomHArray.length;i++){
			randomHArray[i]=(int)(Math.random()*GAME_WIDTH/30);
		}
		
		gamePanel1 = new GamePanel(1000,350,2,4,30,2,d,h,w,randomDArray,randomWArray,randomHArray);
		gamePanel2 = new GamePanel(1000,350,3,4,30,2,d,h,w,randomDArray,randomWArray,randomHArray);
		this.setBounds(0, 0, 1000, 735);
		this.setTitle("Jump A Jump");
		this.setLayout(null);
		this.add(gamePanel1);
		this.add(gamePanel2);
		gamePanel1.setBounds(0,0,1000,350);
		gamePanel2.setBounds(0,350,1000,350);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		new Thread() {
            public void run() {
                while (gamePanel1.exit<=0||gamePanel2.exit<=0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (gamePanel1.exit > 0&&gamePanel2.exit>0) {
                    if(gamePanel1.paddleCount==gamePanel2.paddleCount)
                    {
                        exitf=new ExitFrame(4);
                    }
                    if(gamePanel1.paddleCount>gamePanel2.paddleCount)
                    {
                        exitf=new ExitFrame(5);
                    }
                    if(gamePanel1.paddleCount<gamePanel2.paddleCount)
                    {
                        exitf=new ExitFrame(6);
                    }
                    setVisible(false);
                }
            }
        }.start();
	
	}
	
	
	
	
}