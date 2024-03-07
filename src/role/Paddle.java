package role;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{
	
	int id;						//������
	public int varX;					
	int varY;
	Image paddleImage;
	public boolean selected;			//�ж��������Ƿ���Ħ��
	public int xCount = 0;				//������������
	boolean pressed;
	int PADDLE_WIDTH;
	int PADDLE_HEIGHT;
	int GAME_WIDTH;
	int GAME_HEIGHT;
	int gamePanelId ;
	
	public Paddle(int x, int y, int w,int h,int id){
		super(x,y,w,h);
		PADDLE_WIDTH = w;
		PADDLE_HEIGHT = h;
		this.id = id;
		paddleImage = new ImageIcon("image/Stone.png").getImage();
	}
	
	public void getGamePaddle(int w,int h) {
		GAME_WIDTH = w;
		GAME_HEIGHT = h;
	}
	
	public void draw(Graphics g) {
		g.drawImage(paddleImage, x, y, width, height, null);
	}
	
	public void getGamePanelId(int i) {
		gamePanelId = i;
	}
	
	//����
	public void moveX() {
		if(xCount<100 ) {
			x = x - varX*4;
			xCount++;
		}
	}
	
	//�ָ�
	public void recover() {
		while (height < PADDLE_HEIGHT) {
			y = y - 1;
			height = height + 1;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			//����ֻ��ѡ�е���������
			if(selected == true) {
				pressed = true;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			pressed = false;
			//���̱��ͷ�,����ȡ��ѡ��
			selected = false;
		}		
	}
	
	
	public void mousePressed(MouseEvent e) {
		//ֻ��ѡ�е���������
		if(selected == true) {
			pressed = true;
			
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		//��걻�ͷ�,����ȡ��ѡ��
		selected = false;
	}
	
	//����
		public void takeOff() {
			if(pressed == true && y < GAME_HEIGHT/4*3) {
				y = y + 1;
				height = height - 1; 
			}
		}
}
