package ui;

import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import java.util.concurrent.locks.Condition;

import javax.swing.*;
import javax.swing.Timer;

import role.Mole;
import role.Paddle;
import util.MusicEffect;

public class GamePanel extends JPanel{
	static final int MOLE_WIDTH =60;			//Ħ�����
	static final int MOLE_HEIGHT = 100;			//Ħ���߶�
	
	public ArrayList<Paddle> paddleList = new ArrayList<Paddle>();	//�洢����
	int paddleCount = 0;			//ͳ���������
	boolean flag = false;			
	
	int GAME_WIDTH;
	int GAME_HEIGHT;
	int PADDLE_WIDTH;
	int PADDLE_HEIGHT;
	int jumpsum;
	int second;
	int drawFlag;
	int randomD;
	int randomW;
	int randomH;
	int[] randomDArray;
	int[] randomWArray;
	int[] randomHArray;
	
	int id;
	BackgroundImage bg;
	Image image;								//����ͼƬ
	Graphics graphics;							//����
	Random random;								//�����
	Mole mole;									//Ħ��
	Timer timer;
	boolean win = true;
	MusicEffect effect1;
	ExitFrame exitf;
	public int exit;
	
	
	GamePanel(int w,int h,int i,int j,int s,int d){
		GAME_WIDTH = w;
		GAME_HEIGHT = h;
		id = i;
		jumpsum = j;
		second = s;
		drawFlag = d;
		
		PADDLE_WIDTH = GAME_WIDTH/8;
		PADDLE_HEIGHT = (int)(PADDLE_WIDTH*1.2);
		Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		initialPaddles();						//��ʼ������
		initialMole();							//��ʼ��Ħ��
		bg = new BackgroundImage();
		this.effect1=new MusicEffect("Music/press.wav");
		
		this.setFocusable(true);				//�����С�̶�
		this.addKeyListener(new AL());			//���̼���
		this.addMouseListener(new BL());		//������	
		this.setPreferredSize(SCREEN_SIZE);		//������������
		
		simpleTimer();
		timer.start();
		
		//�߳�1,����һ������+��Ծ���߳�,��Ħ�������м�����ʱ��ͣ1��
		//���߳�2����������ĳ��ֺͻ�����ƶ�
		new Thread(){		
			public void run(){
				while(true&&paddleCount!=jumpsum + 2&&second >= 0&&win){
					mole.takeOff();
					for(int i = 0;i<10;i++) {
						if(paddleList.get(i).selected == true) {
							paddleList.get(i).takeOff();
						}
					}
					mole.jump();				//��������Ծ
					mole.getCondition();		//��ȡĦ��״̬
					try {
						checkCollision();		//����Ƿ�����������
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					repaint();					//�ػ�
					try {
						Thread.sleep(10);		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		//�߳�2,���߳�1��ͣʱ,��Ħ�������Ծ�������ɹ�����������ʱ,
		//�����µ�����+��������
		new Thread(){
			public void run(){
				
				while(true&&paddleCount != jumpsum + 2 &&second>0&&win){
					moveX();		//������º��ƶ�
					repaint();		//�ػ�
					
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(paddleCount == jumpsum + 2 && second >= 0 && win) {
					exit=1;
				}else if(second == 0) {
					exit=2;
				}
				if(!win && second > 0) {
					exit=3;
				}
			}
		}.start();
		
		if(id == 1) {
			new Thread(){
				public void run(){
					while(true) {
						effect1.playonce(mole.pressed);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();	
		}
		
	}
	
	GamePanel(int w,int h,int i,int j,int s,int d,int rd,int rw,int rh,int[] rda,int[] rdw,int[] rdh){
		GAME_WIDTH = w;
		GAME_HEIGHT = h;
		id = i;
		jumpsum = j;
		second = s;
		drawFlag = d;
		randomD = rd;
		randomW = rw;
		randomH = rh;
		randomDArray = rda;
		randomWArray = rdw;
		randomHArray = rdh;
		
		PADDLE_WIDTH = GAME_WIDTH/8;
		PADDLE_HEIGHT = (int)(PADDLE_WIDTH*1.2);
		Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		
		initialPaddles(randomD, randomH, randomW);
		initialMole();							//��ʼ��Ħ��
		bg = new BackgroundImage();
		this.effect1=new MusicEffect("Music/press.wav");
		
		this.setFocusable(true);				//�����С�̶�
		this.addKeyListener(new AL());			//���̼���
		this.addMouseListener(new BL());		//������	
		this.setPreferredSize(SCREEN_SIZE);		//������������
		
		simpleTimer();
		timer.start();
		
		//�߳�1,����һ������+��Ծ���߳�,��Ħ�������м�����ʱ��ͣ1��
		//���߳�2����������ĳ��ֺͻ�����ƶ�
		new Thread(){		
			public void run(){
				while(true&&paddleCount!=jumpsum + 2&&second >= 0&&win){
					mole.takeOff();
					for(int i = 0;i<10;i++) {
						if(paddleList.get(i).selected == true) {
							paddleList.get(i).takeOff();
						}
					}
					mole.jump();				//��������Ծ
					mole.getCondition();		//��ȡĦ��״̬
					try {
						checkCollision();		//����Ƿ�����������
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					repaint();					//�ػ�
					try {
						Thread.sleep(10);		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		//�߳�2,���߳�1��ͣʱ,��Ħ�������Ծ�������ɹ�����������ʱ,
		//�����µ�����+��������
		new Thread(){
			public void run(){
				
				while(true&&paddleCount != jumpsum + 2 &&second>0&&win){
					moveX();		//������º��ƶ�
					repaint();		//�ػ�
					
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(paddleCount == jumpsum + 2 && second >= 0 && win) {
					exit=1;
				}else if(second == 0) {
					exit=2;
				}
				if(!win && second > 0) {
					exit=3;
				}
				
			}
		}.start();
		
		if(id == 1) {
			new Thread(){
				public void run(){
					while(true) {
						effect1.playonce(mole.pressed);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();	
		}
		
	}
	
	
	//��ʼ������
	public void initialPaddles(int d,int h,int w) {
		//��ʼ��paddleList
		for(int i = 0;i<10;i++) {
			Paddle initialPaddle = new Paddle(GAME_WIDTH/10, GAME_HEIGHT/7*4, PADDLE_WIDTH, PADDLE_HEIGHT, i);		
			paddleList.add(initialPaddle);
			paddleList.get(i).selected = false;
		}
		
		//��ʼʱĦ���ڵ�һ�����崦
		paddleList.get(0).selected = true;
		paddleList.get(0).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		paddleList.get(0).getGamePanelId(id);
		paddleCount ++;
		
		//��ʼʱ����������,���Եڶ�������ҲҪ��
		Paddle newPaddle = new Paddle(paddleList.get(0).x + paddleList.get(0).width + d, paddleList.get(0).y-h, PADDLE_WIDTH+w, PADDLE_HEIGHT + h, 1);
		paddleList.set(1 ,newPaddle);
		paddleList.get(1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		paddleList.get(1).getGamePanelId(id);
		paddleCount++;
		
		
	}
	
	//��ʼ������
		public void initialPaddles() {
			//��ʼ��paddleList
			for(int i = 0;i<10;i++) {
				Paddle initialPaddle = new Paddle(GAME_WIDTH/10, GAME_HEIGHT/7*4, PADDLE_WIDTH, PADDLE_HEIGHT, i);		
				paddleList.add(initialPaddle);
				paddleList.get(i).selected = false;
			}
			
			//��ʼʱĦ���ڵ�һ�����崦
			paddleList.get(0).selected = true;
			paddleList.get(0).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
			paddleList.get(0).getGamePanelId(id);
			paddleCount ++;
			
			//��ʼʱ����������,���Եڶ�������ҲҪ��
			Random	random = new Random();
			int	d = random.nextInt(GAME_WIDTH/10) + GAME_WIDTH/10;
			int	h = random.nextInt(GAME_WIDTH/30) ;
			int	w = random.nextInt(GAME_WIDTH/30) ;
			
			Paddle newPaddle = new Paddle(paddleList.get(0).x + paddleList.get(0).width + d, paddleList.get(0).y-h, PADDLE_WIDTH+w, PADDLE_HEIGHT + h, 1);
			paddleList.set(1 ,newPaddle);
			paddleList.get(1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
			paddleList.get(1).getGamePanelId(id);
			paddleCount++;
		}
	
	
	//��ʼ��Ħ��
	public void initialMole() {
		Paddle initialPaddle = paddleList.get(0);
		mole = new Mole(initialPaddle.x+(initialPaddle.width-MOLE_WIDTH)/2,initialPaddle.y-MOLE_HEIGHT,MOLE_WIDTH,MOLE_HEIGHT);
		mole.getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		mole.getGamePanelId(id);
	}
	
	//��ʼ��
	public void initialDraw(Graphics g) {
		paddleList.get(0).draw(g);
		paddleList.get(1).draw(g);
		mole.draw(g);
	}
	
	//���ƻ���
	public void paint(Graphics g) {
		//����˫���廭����ͼƬ��Ħ��
		BufferedImage bi =(BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, 0, 0, GAME_WIDTH,GAME_HEIGHT,null);
		initialDraw(big);			//��ʼ��
		if(drawFlag == 1) {
			changingDraw(big);			//����
		}
		if(drawFlag == 2) {
			changingDraw(big,randomDArray,randomWArray,randomHArray);
		}
		big.setFont(new Font("����",Font.PLAIN,32+GAME_WIDTH/100));
		big.drawString("����ʱ��" + second, GAME_WIDTH/5*2, GAME_HEIGHT/9);
		big.setFont(new Font("����",Font.PLAIN,32+GAME_WIDTH/150));
		big.drawString("�����յ㻹�У�" + (jumpsum-paddleCount+2) + "������", GAME_WIDTH/3, GAME_HEIGHT/9+50);
		g.drawImage(bi,0,0,null);
	}
	
	
	//����������ĳ���+������ƶ�
	public void changingDraw(Graphics g) {
		//flag��Ŀ�����������if�����paddleCount�б�ʱִֻ��һ��
		if(paddleCount == mole.jumpCount + 2 &&flag) {
			random = new Random();
			int d = random.nextInt(GAME_WIDTH/10) + GAME_WIDTH/10;
			int h = random.nextInt(GAME_WIDTH/30) ;
			int w = random.nextInt(GAME_WIDTH/30) ;
			Paddle newPaddle = new Paddle(paddleList.get(paddleCount-2).x + paddleList.get(paddleCount-2).width + d,  paddleList.get(0).y - h, PADDLE_WIDTH + w, PADDLE_HEIGHT + h, paddleCount-1);
			paddleList.set(paddleCount-1 ,newPaddle);
			paddleList.get(paddleCount-1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
			paddleList.get(paddleCount-1).getGamePanelId(id);
			//ȷ����ѡ�е�����,��Ħ����������
			paddleList.get(paddleCount-2).selected = true;
			flag = false;
			
		}
		
		if(paddleCount<= jumpsum + 1) {
			for(int i = 2; i < paddleCount;i++) {
				paddleList.get(i).draw(g);			//������
			}
		}
		
		if(paddleCount >= jumpsum + 2) {
			paddleList.set(jumpsum + 1, paddleList.get(jumpsum));
			for(int i = 2; i < paddleCount;i++) {
				paddleList.get(i).draw(g);			//������
			}
			paddleCount = jumpsum + 2;
		}
		
	}
	
	//����������ĳ���+������ƶ�
		public void changingDraw(Graphics g,int[] da,int[] wa,int[] ha) {
			//flag��Ŀ�����������if�����paddleCount�б�ʱִֻ��һ��
			if(paddleCount == mole.jumpCount + 2 &&flag) {
				int d = da[paddleCount] ;
				int h = ha[paddleCount] ;
				int w = wa[paddleCount] ;
				Paddle newPaddle = new Paddle(paddleList.get(paddleCount-2).x + paddleList.get(paddleCount-2).width + d,  paddleList.get(0).y - h, PADDLE_WIDTH + w, PADDLE_HEIGHT + h, paddleCount-1);
				paddleList.set(paddleCount-1 ,newPaddle);
				paddleList.get(paddleCount-1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
				paddleList.get(paddleCount-1).getGamePanelId(id);
				//ȷ����ѡ�е�����,��Ħ����������
				paddleList.get(paddleCount-2).selected = true;
				flag = false;
			}
			
			if(paddleCount<= jumpsum + 1) {
				for(int i = 2; i < paddleCount;i++) {
					paddleList.get(i).draw(g);			//������
				}
			}
			
			if(paddleCount >= jumpsum + 2) {
				paddleList.set(jumpsum + 1, paddleList.get(jumpsum));
				for(int i = 2; i < paddleCount;i++) {
					paddleList.get(i).draw(g);			//������
				}
				paddleCount = jumpsum + 2;
			}
		}
	
	
	public void simpleTimer() {
		timer = new Timer(1000,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(second >0) {
				second--;
				}
			}
		});
		
	}
	
	
	
	//����
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			
			for(int i=0;i<10;i++) {
				paddleList.get(i).keyPressed(e);
			}
			mole.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			for(int i=0;i<10;i++) {
				paddleList.get(i).keyReleased(e);
			}
			mole.keyReleased(e);
		}
	}
	
	//���
		public class BL extends MouseAdapter{
			public void mousePressed(MouseEvent e) {
				for(int i=0;i<10;i++) {
					paddleList.get(i).mousePressed(e);
				}
				mole.mousePressed(e);
			}
			
			public void mouseReleased(MouseEvent e) {
				for(int i=0;i<10;i++) {
					paddleList.get(i).mouseReleased(e);
				}
				mole.mouseReleased(e);
			}
		}
	
	
	//���Ħ���Ƿ�����������
	public void checkCollision() throws InterruptedException {
		if(mole.condition == 3) {			//ֻ��Ħ������Ծ״̬ʱ��Ҫ���
			if(mole.y >= GAME_HEIGHT-MOLE_HEIGHT) {
				mole.yVelocity = 0;
				win = false;
			}
		
			if(mole.intersects(paddleList.get(paddleCount-1))) {	//��������
				mole.yVelocity = 0;			//ֹͣ�������˶�
				
				Thread.sleep(1000);			//��ͣ1������߳�2
				
				flag = true;				//changingDraw��if�����ִ��һ��
				paddleCount++;				//������+1
				mole.xVelocity0 = 0.9;		//�����ߵ�Vx0���±�ɳ�ֵ
				mole.xCount = 0;			//Ħ������
				for(int i = 0;i<10;i++) {
					paddleList.get(i).xCount = 0;		//��������
				}
				if(mole.y-paddleList.get(paddleCount-2).y+MOLE_HEIGHT<=10) {
					win = true;
					mole.y = paddleList.get(paddleCount-2).y-MOLE_HEIGHT;	//Ħ����������,��ֹintersects
				}
				else {
					win = false;
				}
			}
			
		}
	}
	
	//�����ƶ�
	public void moveX() {
		if(mole.condition == 3) {
			if(mole.intersects(paddleList.get(paddleCount-1))) {
				mole.varX = 1;
				mole.moveX();
				for(int i = 0;i<10;i++) {
					paddleList.get(i).varX = 1;
					paddleList.get(i).moveX();
				}
				paddleList.get(paddleCount-2).recover();		//����ָ�ԭ״
				
				
			}
		}
	}
}
