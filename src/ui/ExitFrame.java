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
                JOptionPane.showMessageDialog(null, "��ϲ�㣡��Ϸ�ɹ���");
                System.exit(0);
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(null, "�Ѿ���ʱ,��Ϸʧ�ܣ�������");
                System.exit(0);
                break;
            }
            case 3: {
                JOptionPane.showMessageDialog(null, "��Ϸʧ�ܣ�������");
                System.exit(0);
                break;
            }
            case 4:
            {
                JOptionPane.showMessageDialog(null, "������Ҵ��ƽ��");
                System.exit(0);
                break;

            }
            case 5:
            {
                JOptionPane.showMessageDialog(null, "�Ϸ���һ�ʤ");
                System.exit(0);
                break;
            }
            case 6:
            {
                JOptionPane.showMessageDialog(null, "�·���һ�ʤ");
                System.exit(0);
                break;
            }
            case 7:
            {
            	JOptionPane.showMessageDialog(null, "��ϲ�㣡��һ�أ�");
                new GameFrame3();
                break;
            }
        }
    }
}
