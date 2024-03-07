package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//extends ActionListener

public class ExitFrame {
    private int flag;
    public int id = 4;
    public int jumpCount = 3;
    public int second = 60;
    public ExitFrame(int flag) {
        this.flag = flag;
        switch (flag) {
            case 1: {
                JOptionPane.showMessageDialog(null, "恭喜你！游戏成功！");
                System.exit(0);
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(null, "已经超时,游戏失败！！！！");
                System.exit(0);
                break;
            }
            case 3: {
                JOptionPane.showMessageDialog(null, "游戏失败！！！！");
                System.exit(0);
                break;
            }
            case 4:
            {
                JOptionPane.showMessageDialog(null, "上下玩家打成平手");
                System.exit(0);
                break;

            }
            case 5:
            {
                JOptionPane.showMessageDialog(null, "上方玩家获胜");
                System.exit(0);
                break;
            }
            case 6:
            {
                JOptionPane.showMessageDialog(null, "下方玩家获胜");
                System.exit(0);
                break;
            }
            case 7:
            {
            	JOptionPane.showMessageDialog(null, "恭喜你！下一关！");
                new GameFrame3();
                break;
            }
        }
    }
}
