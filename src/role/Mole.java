package role;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Mole extends Rectangle{

	static final int MOLE_WIDTH = 60;
	static final int MOLE_HEIGHT = 100;
	int GAME_WIDTH;
	int GAME_HEIGHT;
	int gamePanelId;
	
	public double xVelocity0 = 1;		//Vx0��ֵ1
	public int yVelocity = 0;				
	public int varX;
	int varY;
	double initialSpeed = 3.8;
	public int condition ;					//״̬
	public int jumpCount = 0;				//��Ծ����
	Image moleImage;
	public int xCount = 0;
	public boolean pressed;
	
	public Mole(int x,int y,int MOLE_WIDTH,int MOLE_HEIGHT){
		super(x,y,MOLE_WIDTH,MOLE_HEIGHT);
		moleImage = new ImageIcon("image/Mole.png").getImage();
	}
	
	public void getGamePaddle(int w,int h) {
		GAME_WIDTH = w;
		GAME_HEIGHT = h;
	}
	
	public void getGamePanelId(int i) {
		gamePanelId = i;
	}
	
	//��Ծ
	public void jump() {
		x = (int)(x  + yVelocity*initialSpeed);
		y = (int)(y + (x*(0.025/xVelocity0/xVelocity0)-4.5/xVelocity0)*yVelocity*initialSpeed);
	}
	
	//��ȡĦ����״̬
	public int getCondition() {
		if(xVelocity0 == 1 && yVelocity == 0) {
			condition = 1;					//1Ϊ��ֹ״̬
		}
		
		if(xVelocity0 > 1 && yVelocity == 0) {
			condition = 2;					//2Ϊ����״̬
		}
		
		if(xVelocity0 > 1 && yVelocity == 1) {
			condition = 3;					//3Ϊ��Ծ״̬
		}
		
		return condition;
	}
	
	//����
	public void moveX() {
		if(xCount<100 ) {
			x = x - varX*4;
			xCount++;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(moleImage, x, y, width, height, null);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			pressed = true;
		}	
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			pressed = false;
			yVelocity = 1;
			jumpCount ++;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		yVelocity = 1;
		jumpCount ++;
	}
	
	//����
	public void takeOff() {
		if(pressed == true && y <GAME_HEIGHT/4*3-MOLE_HEIGHT) {
			y = y + 1;
			xVelocity0 = xVelocity0 + 0.02 ;
		}
	}
}
