package role;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{
	
	int id;						//跳板编号
	public int varX;					
	int varY;
	Image paddleImage;
	public boolean selected;			//判断跳板上是否有摩尔
	public int xCount = 0;				//控制跳板左移
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
	
	//左移
	public void moveX() {
		if(xCount<100 ) {
			x = x - varX*4;
			xCount++;
		}
	}
	
	//恢复
	public void recover() {
		while (height < PADDLE_HEIGHT) {
			y = y - 1;
			height = height + 1;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			//按键只对选中的跳板有用
			if(selected == true) {
				pressed = true;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			pressed = false;
			//键盘被释放,跳板取消选中
			selected = false;
		}		
	}
	
	
	public void mousePressed(MouseEvent e) {
		//只对选中的跳板有用
		if(selected == true) {
			pressed = true;
			
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		//鼠标被释放,跳板取消选中
		selected = false;
	}
	
	//起跳
		public void takeOff() {
			if(pressed == true && y < GAME_HEIGHT/4*3) {
				y = y + 1;
				height = height - 1; 
			}
		}
}
