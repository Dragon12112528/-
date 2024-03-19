import view.ChessGameFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    static ChessGameFrame mainFrame ;
//一定要注意每个类 里面的属性是不是另外一个类，这样就可以调用属性中这个类的方法！！！
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                mainFrame = new ChessGameFrame(1000, 760);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            mainFrame.setVisible(false);
        });

        //窗口，也是背景图片教学,恢复记得把mainFrame变成不可见先哦
        JFrame jf=new JFrame("                                               come on?");
        jf.setLayout(null);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        ImageIcon bg=new ImageIcon("images\\Window.jpg");
        JLabel label=new JLabel(bg);
        label.setSize(bg.getIconWidth(),bg.getIconHeight());
        jf.getLayeredPane().add(label,Integer.MIN_VALUE);
        JPanel pan=(JPanel)jf.getContentPane();
        pan.setOpaque(false);
        pan.setLayout(null);
        jf.setSize(bg.getIconWidth(),bg.getIconHeight());

        JButton start=new JButton("go!");
        start.addActionListener((e) -> {
            jf.setVisible(false);
            mainFrame.setVisible(true);
        });
        start.setLocation(jf.getHeight()/2, jf.getHeight()/2-60);
        start.setSize(400, 120);
        start.setFont(new Font("Rockwell", Font.BOLD, 60));
        jf.add(start);




    }
}

