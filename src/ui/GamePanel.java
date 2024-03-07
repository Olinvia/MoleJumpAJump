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
	static final int MOLE_WIDTH =60;			//摩尔宽度
	static final int MOLE_HEIGHT = 100;			//摩尔高度
	
	public ArrayList<Paddle> paddleList = new ArrayList<Paddle>();	//存储跳板
	int paddleCount = 0;			//统计跳板个数
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
	Image image;								//背景图片
	Graphics graphics;							//画布
	Random random;								//随机数
	Mole mole;									//摩尔
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
		initialPaddles();						//初始化跳板
		initialMole();							//初始化摩尔
		bg = new BackgroundImage();
		this.effect1=new MusicEffect("Music/press.wav");
		
		this.setFocusable(true);				//窗体大小固定
		this.addKeyListener(new AL());			//键盘监听
		this.addMouseListener(new BL());		//鼠标监听	
		this.setPreferredSize(SCREEN_SIZE);		//画布充满窗体
		
		simpleTimer();
		timer.start();
		
		//线程1,开启一个起跳+跳跃的线程,在摩尔跳至中间跳板时暂停1秒
		//供线程2进行新跳板的出现和画面的移动
		new Thread(){		
			public void run(){
				while(true&&paddleCount!=jumpsum + 2&&second >= 0&&win){
					mole.takeOff();
					for(int i = 0;i<10;i++) {
						if(paddleList.get(i).selected == true) {
							paddleList.get(i).takeOff();
						}
					}
					mole.jump();				//抛物线跳跃
					mole.getCondition();		//获取摩尔状态
					try {
						checkCollision();		//检查是否跳到跳板上
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					repaint();					//重绘
					try {
						Thread.sleep(10);		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		//线程2,在线程1暂停时,即摩尔完成跳跃动作并成功跳到跳板上时,
		//出现新的跳板+画面左移
		new Thread(){
			public void run(){
				
				while(true&&paddleCount != jumpsum + 2 &&second>0&&win){
					moveX();		//画面更新和移动
					repaint();		//重绘
					
					
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
		initialMole();							//初始化摩尔
		bg = new BackgroundImage();
		this.effect1=new MusicEffect("Music/press.wav");
		
		this.setFocusable(true);				//窗体大小固定
		this.addKeyListener(new AL());			//键盘监听
		this.addMouseListener(new BL());		//鼠标监听	
		this.setPreferredSize(SCREEN_SIZE);		//画布充满窗体
		
		simpleTimer();
		timer.start();
		
		//线程1,开启一个起跳+跳跃的线程,在摩尔跳至中间跳板时暂停1秒
		//供线程2进行新跳板的出现和画面的移动
		new Thread(){		
			public void run(){
				while(true&&paddleCount!=jumpsum + 2&&second >= 0&&win){
					mole.takeOff();
					for(int i = 0;i<10;i++) {
						if(paddleList.get(i).selected == true) {
							paddleList.get(i).takeOff();
						}
					}
					mole.jump();				//抛物线跳跃
					mole.getCondition();		//获取摩尔状态
					try {
						checkCollision();		//检查是否跳到跳板上
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					repaint();					//重绘
					try {
						Thread.sleep(10);		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		//线程2,在线程1暂停时,即摩尔完成跳跃动作并成功跳到跳板上时,
		//出现新的跳板+画面左移
		new Thread(){
			public void run(){
				
				while(true&&paddleCount != jumpsum + 2 &&second>0&&win){
					moveX();		//画面更新和移动
					repaint();		//重绘
					
					
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
	
	
	//初始化跳板
	public void initialPaddles(int d,int h,int w) {
		//初始化paddleList
		for(int i = 0;i<10;i++) {
			Paddle initialPaddle = new Paddle(GAME_WIDTH/10, GAME_HEIGHT/7*4, PADDLE_WIDTH, PADDLE_HEIGHT, i);		
			paddleList.add(initialPaddle);
			paddleList.get(i).selected = false;
		}
		
		//初始时摩尔在第一个跳板处
		paddleList.get(0).selected = true;
		paddleList.get(0).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		paddleList.get(0).getGamePanelId(id);
		paddleCount ++;
		
		//初始时有两个跳板,所以第二个跳板也要画
		Paddle newPaddle = new Paddle(paddleList.get(0).x + paddleList.get(0).width + d, paddleList.get(0).y-h, PADDLE_WIDTH+w, PADDLE_HEIGHT + h, 1);
		paddleList.set(1 ,newPaddle);
		paddleList.get(1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		paddleList.get(1).getGamePanelId(id);
		paddleCount++;
		
		
	}
	
	//初始化跳板
		public void initialPaddles() {
			//初始化paddleList
			for(int i = 0;i<10;i++) {
				Paddle initialPaddle = new Paddle(GAME_WIDTH/10, GAME_HEIGHT/7*4, PADDLE_WIDTH, PADDLE_HEIGHT, i);		
				paddleList.add(initialPaddle);
				paddleList.get(i).selected = false;
			}
			
			//初始时摩尔在第一个跳板处
			paddleList.get(0).selected = true;
			paddleList.get(0).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
			paddleList.get(0).getGamePanelId(id);
			paddleCount ++;
			
			//初始时有两个跳板,所以第二个跳板也要画
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
	
	
	//初始化摩尔
	public void initialMole() {
		Paddle initialPaddle = paddleList.get(0);
		mole = new Mole(initialPaddle.x+(initialPaddle.width-MOLE_WIDTH)/2,initialPaddle.y-MOLE_HEIGHT,MOLE_WIDTH,MOLE_HEIGHT);
		mole.getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
		mole.getGamePanelId(id);
	}
	
	//初始化
	public void initialDraw(Graphics g) {
		paddleList.get(0).draw(g);
		paddleList.get(1).draw(g);
		mole.draw(g);
	}
	
	//绘制画布
	public void paint(Graphics g) {
		//利用双缓冲画背景图片和摩尔
		BufferedImage bi =(BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, 0, 0, GAME_WIDTH,GAME_HEIGHT,null);
		initialDraw(big);			//初始化
		if(drawFlag == 1) {
			changingDraw(big);			//更新
		}
		if(drawFlag == 2) {
			changingDraw(big,randomDArray,randomWArray,randomHArray);
		}
		big.setFont(new Font("楷体",Font.PLAIN,32+GAME_WIDTH/100));
		big.drawString("倒计时：" + second, GAME_WIDTH/5*2, GAME_HEIGHT/9);
		big.setFont(new Font("楷体",Font.PLAIN,32+GAME_WIDTH/150));
		big.drawString("距离终点还有：" + (jumpsum-paddleCount+2) + "块跳板", GAME_WIDTH/3, GAME_HEIGHT/9+50);
		g.drawImage(bi,0,0,null);
	}
	
	
	//负责新跳板的出现+画面的移动
	public void changingDraw(Graphics g) {
		//flag的目的是让下面的if语句在paddleCount有变时只执行一次
		if(paddleCount == mole.jumpCount + 2 &&flag) {
			random = new Random();
			int d = random.nextInt(GAME_WIDTH/10) + GAME_WIDTH/10;
			int h = random.nextInt(GAME_WIDTH/30) ;
			int w = random.nextInt(GAME_WIDTH/30) ;
			Paddle newPaddle = new Paddle(paddleList.get(paddleCount-2).x + paddleList.get(paddleCount-2).width + d,  paddleList.get(0).y - h, PADDLE_WIDTH + w, PADDLE_HEIGHT + h, paddleCount-1);
			paddleList.set(paddleCount-1 ,newPaddle);
			paddleList.get(paddleCount-1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
			paddleList.get(paddleCount-1).getGamePanelId(id);
			//确定被选中的跳板,即摩尔所在跳板
			paddleList.get(paddleCount-2).selected = true;
			flag = false;
			
		}
		
		if(paddleCount<= jumpsum + 1) {
			for(int i = 2; i < paddleCount;i++) {
				paddleList.get(i).draw(g);			//画跳板
			}
		}
		
		if(paddleCount >= jumpsum + 2) {
			paddleList.set(jumpsum + 1, paddleList.get(jumpsum));
			for(int i = 2; i < paddleCount;i++) {
				paddleList.get(i).draw(g);			//画跳板
			}
			paddleCount = jumpsum + 2;
		}
		
	}
	
	//负责新跳板的出现+画面的移动
		public void changingDraw(Graphics g,int[] da,int[] wa,int[] ha) {
			//flag的目的是让下面的if语句在paddleCount有变时只执行一次
			if(paddleCount == mole.jumpCount + 2 &&flag) {
				int d = da[paddleCount] ;
				int h = ha[paddleCount] ;
				int w = wa[paddleCount] ;
				Paddle newPaddle = new Paddle(paddleList.get(paddleCount-2).x + paddleList.get(paddleCount-2).width + d,  paddleList.get(0).y - h, PADDLE_WIDTH + w, PADDLE_HEIGHT + h, paddleCount-1);
				paddleList.set(paddleCount-1 ,newPaddle);
				paddleList.get(paddleCount-1).getGamePaddle(GAME_WIDTH, GAME_HEIGHT);
				paddleList.get(paddleCount-1).getGamePanelId(id);
				//确定被选中的跳板,即摩尔所在跳板
				paddleList.get(paddleCount-2).selected = true;
				flag = false;
			}
			
			if(paddleCount<= jumpsum + 1) {
				for(int i = 2; i < paddleCount;i++) {
					paddleList.get(i).draw(g);			//画跳板
				}
			}
			
			if(paddleCount >= jumpsum + 2) {
				paddleList.set(jumpsum + 1, paddleList.get(jumpsum));
				for(int i = 2; i < paddleCount;i++) {
					paddleList.get(i).draw(g);			//画跳板
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
	
	
	
	//键盘
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
	
	//鼠标
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
	
	
	//检查摩尔是否跳到跳板上
	public void checkCollision() throws InterruptedException {
		if(mole.condition == 3) {			//只有摩尔在跳跃状态时需要检查
			if(mole.y >= GAME_HEIGHT-MOLE_HEIGHT) {
				mole.yVelocity = 0;
				win = false;
			}
		
			if(mole.intersects(paddleList.get(paddleCount-1))) {	//碰到跳板
				mole.yVelocity = 0;			//停止抛物线运动
				
				Thread.sleep(1000);			//暂停1秒进行线程2
				
				flag = true;				//changingDraw的if语句需执行一次
				paddleCount++;				//跳板数+1
				mole.xVelocity0 = 0.9;		//抛物线的Vx0重新变成初值
				mole.xCount = 0;			//摩尔左移
				for(int i = 0;i<10;i++) {
					paddleList.get(i).xCount = 0;		//跳板左移
				}
				if(mole.y-paddleList.get(paddleCount-2).y+MOLE_HEIGHT<=10) {
					win = true;
					mole.y = paddleList.get(paddleCount-2).y-MOLE_HEIGHT;	//摩尔紧贴跳板,终止intersects
				}
				else {
					win = false;
				}
			}
			
		}
	}
	
	//画面移动
	public void moveX() {
		if(mole.condition == 3) {
			if(mole.intersects(paddleList.get(paddleCount-1))) {
				mole.varX = 1;
				mole.moveX();
				for(int i = 0;i<10;i++) {
					paddleList.get(i).varX = 1;
					paddleList.get(i).moveX();
				}
				paddleList.get(paddleCount-2).recover();		//跳板恢复原状
				
				
			}
		}
	}
}
