package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessComponent;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import static controller.ClickController.getRound;
//标签按钮最新点480（有）
/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public  int CHESSBOARD_SIZE;


    private GameController gameController;//     可以用gameController里的chessboard   Frame到chessboard

    ChessComponent[][] ChessBoard;
    JLabel background = new JLabel();
    int changeBackground = 0;

    static int jiShi=30;

    static int huiFang=0;
    static int width=200;

    public void setCHESSBOARD_SIZE(int CHESSBOARD_SIZE) {
        this.CHESSBOARD_SIZE = CHESSBOARD_SIZE;
    }

    public static void setJiShi(int jiShi) {
        ChessGameFrame.jiShi = jiShi;
    }

    public ChessGameFrame(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        System.out.println(WIDTH);

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);//可以自己布局按钮标签

        StringBuffer tou=new StringBuffer("RNBQKBNRPPPPPPPP________________________________pppppppprnbqkbnrb1");
        ClickController.cunHuiQi.add(tou);



        addChessboard();
        addLabel1();
        addLabel();
        addHelloButton2();
        addHelloButton();
        //加载输入文件名必须换成英文输入不然报错
        addLoadButtonL();
        addHelloButton1();
        addLoadButtonA();
        addHelloButtonAi();
        addChangeBackgroundButton();
        addIniBackgroundPicture();
        addBackgroundMusic();


    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    private void resetChessboard() {
        gameController.resetGame();
        ClickController.setCurrent(ChessColor.BLACK);
        ClickController.setRound(1);
        ClickController.statusLabel.setText("Round" + String.valueOf(getRound()) + " " + ClickController.getCurrent().getName());
        repaint();
    }
    public void huiQi(ArrayList<StringBuffer> a){
        if (a.size()>=2) {
            System.out.println(a.get(a.size() - 1));
            System.out.println(a.get(a.size() - 2));
            ArrayList<String> huiQi = new ArrayList<>();
            huiQi.add(a.get(a.size() - 2).substring(0, 8));
            huiQi.add(a.get(a.size() - 2).substring(8, 16));
            huiQi.add(a.get(a.size() - 2).substring(16, 24));
            huiQi.add(a.get(a.size() - 2).substring(24, 32));
            huiQi.add(a.get(a.size() - 2).substring(32, 40));
            huiQi.add(a.get(a.size() - 2).substring(40, 48));
            huiQi.add(a.get(a.size() - 2).substring(48, 56));
            huiQi.add(a.get(a.size() - 2).substring(56, 64));
            huiQi.add(a.get(a.size() - 2).substring(64, 65) + a.get(a.size() - 2).substring(65, a.get(a.size() - 2).length()));
            a.remove(a.size() - 1);
            gameController.cunQi(huiQi);
            repaint();
        }
    }
    public void huiFang(ArrayList<StringBuffer> a,int num){
            ArrayList<String> huiFang = new ArrayList<>();
            huiFang.add(a.get(num).substring(0, 8));
            huiFang.add(a.get(num).substring(8, 16));
            huiFang.add(a.get(num).substring(16, 24));
            huiFang.add(a.get(num).substring(24, 32));
            huiFang.add(a.get(num).substring(32, 40));
            huiFang.add(a.get(num).substring(40, 48));
            huiFang.add(a.get(num).substring(48, 56));
            huiFang.add(a.get(num).substring(56, 64));
            huiFang.add(a.get(num).substring(64, 65) + a.get(num).substring(65, a.get(num).length()));
            gameController.cunQi(huiFang);
            repaint();
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel1() {
        JLabel statusLabel = ClickController.statusLabel;
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addLabel() {

        JLabel statusLabel = new JLabel(String.valueOf(jiShi));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jiShi--;
                statusLabel.setText(String.valueOf(jiShi));
                if (jiShi==0){
                    gameController.jiShiChange();
                    jiShi=30;
                }
            }
        });
        timer.start();
        statusLabel.setLocation(HEIGTH, HEIGTH / 10+60);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Repentance");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            huiQi(ClickController.cunHuiQi);
        ClickController.statusLabel.setText("Round" + String.valueOf(getRound()) + " " + ClickController.getCurrent().getName());
            setJiShi(30);
        });
    }
    private void addHelloButton2() {
        JButton button = new JButton("playback");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            if (huiFang<ClickController.cunHuiQi.size()) {
                huiFang(ClickController.cunHuiQi, huiFang);
                ClickController.statusLabel.setText("Round" + String.valueOf(getRound()) + " " + ClickController.getCurrent().getName());
                setJiShi(30);
                huiFang++;
            }
        });
    }

    private void addLoadButtonA() {
        JButton button = new JButton("archive");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Name here");
            gameController.archiveGameFile(path);
            setJiShi(30);
            repaint();
        });
    }

    private void addHelloButton1() {
        JButton button = new JButton("restart");
        button.addActionListener((e) -> {
            setJiShi(30);
            //把原来存的清空
            if (ClickController.cunHuiQi.size()>=2) {
                for (int i = 1;i<ClickController.cunHuiQi.size();i++){
                    ClickController.cunHuiQi.remove(i);
                    i--;
                }
            }
            System.out.println(ClickController.cunHuiQi.size());
            resetChessboard();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addHelloButtonAi() {
        JButton button = new JButton("ai_mode");
        button.addActionListener((e) -> {
            ClickController.setAi(!ClickController.getAi());
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButtonL() {
        JButton button = new JButton("Load the archive");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.showDialog(new JLabel(), "file_choose");
            File file = chooser.getSelectedFile();

            int a=0;
            if (file!=null){
             a = gameController.loadGameFromFile(file.getAbsolutePath());
            }
            if (a == 101) {
                JOptionPane.showMessageDialog(this, "chessboard not 8*8");
            }
            if (a == 102) {
                JOptionPane.showMessageDialog(this, "The chess pieces are not one of the six types, and the chess pieces are not black and white");
            }
            if (a == 103) {
                JOptionPane.showMessageDialog(this, "The imported data only has a chessboard, and there is no hint of which side to move next");
            }
            if (a == 104) {
                JOptionPane.showMessageDialog(this, "The file format is incorrect");
            }

//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this, "Input Name here");
//            gameController.loadGameFromFile(path);
            ClickController.statusLabel.setText("Round" + String.valueOf(getRound()) + " " + ClickController.getCurrent().getName());
            setJiShi(30);
            repaint();
        });
    }

    public void addChangeBackgroundButton() {
        JButton button = new JButton("Change the background");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(width, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click ChangeBackground");
            if (changeBackground == 0) {
                remove(background);
                addChangeBackgroundPicture();
            } else {
                remove(background);
                addIniBackgroundPicture();
            }

            ChessComponent.setChangeBoardPhoto(!ChessComponent.isChangeBoardPhoto());
            repaint();
        });
    }

    public void addIniBackgroundPicture() {
        background = new JLabel(new ImageIcon("images/老阿姨.jpg"));
        background.setBounds(0, 0, WIDTH, HEIGTH);
        getContentPane().add(background);
        changeBackground = 0;
    }

    public void addChangeBackgroundPicture() {
        background = new JLabel(new ImageIcon("images/小儿子.jpg"));
        background.setBounds(0, 0, WIDTH, HEIGTH);
        getContentPane().add(background);
        changeBackground = 1;
    }
    public static void addBackgroundMusic() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        File file = new File("music/8ibuf-j27jv.wav");
        URI uri = file.toURI();
        System.out.println(uri);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
