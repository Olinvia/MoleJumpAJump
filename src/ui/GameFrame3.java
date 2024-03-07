package ui;

import javax.swing.JFrame;

public class GameFrame3 extends JFrame{
	GamePanel gamePanel;
	ExitFrame exitf;
	private static int count = 0;
	GameFrame3(){
		gamePanel = new GamePanel(1000,700,4+count,3+count,45+5*count,1);
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
					if(gamePanel.exit == 1) {
						exitf=new ExitFrame(gamePanel.exit + 6);
					}else {
						exitf=new ExitFrame(gamePanel.exit );
					}
					setVisible(false);
				}
			}
		}.start();
		count++;
	}
}
