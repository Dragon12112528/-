package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class ClickController {
    private final Chessboard chessboard;

    ArrayList<Chessboard> a=new ArrayList<>();
    private ChessComponent first;
    static int round=1;
    static ChessColor current=ChessColor.BLACK;
    public static JLabel statusLabel = new JLabel("Round"+String.valueOf(round)+" "+current.getName());
    static boolean ai=false;
    static boolean peaceThreeSame=false;

    public static boolean isPeaceThreeSame() {
        return peaceThreeSame;
    }

    public static void setPeaceThreeSame(boolean peaceThreeSame) {
        ClickController.peaceThreeSame = peaceThreeSame;
    }

    public static void setAi(boolean ai) {
        ClickController.ai = ai;
    }

    public static boolean getAi() {
        return ai;
    }

    public static int getRound() {
        return round;
    }

    public static ChessColor getCurrent() {
        return current;
    }

    public static ArrayList<StringBuffer> cunHuiQi=new ArrayList<>();


    public static void setRound(int round) {
        ClickController.round = round;
    }

    public static void setCurrent(ChessColor current) {
        ClickController.current = current;
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setFirst(ChessComponent first) {
        this.first = first;
    }

    public void onClick(ChessComponent chessComponent) {
        a.add(chessboard);
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                for (int i=0;i<chessComponent.canMove().size();i++){
                    if (chessComponent.canMove().get(i).getChessColor()!=chessComponent.getChessColor()) {
                        chessComponent.canMove().get(i).setCanMove(true);
                        chessComponent.canMove().get(i).repaint();
                    }
                }

            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                for (int x=0;x<8;x++){
                    for (int y=0;y<8;y++){
                        Chessboard.getChessComponents()[x][y].setCanMove(false);
                        Chessboard.getChessComponents()[x][y].repaint();
                    }
                }
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                warn(first);
                first.setSelected(false);
                first = null;

                System.out.println(round);
                System.out.println(current.getName());
                //写回合数和行棋方的标签
                round++;
                if (current==ChessColor.BLACK){
                    current=ChessColor.WHITE;
                }else {
                    current=ChessColor.BLACK;
                }
                System.out.println(round);
                System.out.println(current.getName());
                int round= ClickController.getRound();
                String color=ClickController.getCurrent().getName();
                statusLabel.setText("Round"+String.valueOf(round)+" "+color);


                a.add(chessboard);
                //悔棋
                for (int x=0;x<8;x++){
                    for (int y=0;y<8;y++){
                        Chessboard.getChessComponents()[x][y].setCanMove(false);
                        Chessboard.getChessComponents()[x][y].repaint();
                    }
                }
                //存每一步
                chessboard.cunHuiQi();
                //判断无路可走和棋
//                PeaceNoWay();
                //判断局面三次相同和棋
                PeaceThreeSame(peaceThreeSame);
                //再点击之后判断胜负
                judgeWinerNormal();


                //人机，悔棋必须悔2次，并且真人不能超时，因为超时换人机走，但是触发人机的方式是黑棋点击第二次。
                if (getAi()) {
                    renJi();
                }
                //计时器
                ChessGameFrame.setJiShi(30);

            }
        }
    }




    public void renJi(){
    if (getCurrent()==ChessColor.WHITE) {
    ArrayList<ChessComponent> white = new ArrayList<>();
    for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
            if (Chessboard.getChessComponents()[x][y].getChessColor() == ChessColor.WHITE) {
                white.add(Chessboard.getChessComponents()[x][y]);
            }
        }
    }
    Random r = new Random();
    int num = r.nextInt(white.size() - 1);
    while (white.get(num).canMove().size() == 0) {
        num = r.nextInt(white.size() - 1);
    }
    ChessComponent chess1 = white.get(num);
    ChessComponent chess2 = Chessboard.getChessComponents()[chess1.canMove().get(0).getChessboardPoint().getX()][chess1.canMove().get(0).getChessboardPoint().getY()];
    chessboard.swapChessComponents(chess1, chess2);
    chessboard.swapColor();
    setCurrent(ChessColor.BLACK);
    round++;
    int round = ClickController.getRound();
    String color = ClickController.getCurrent().getName();
    statusLabel.setText("Round" + String.valueOf(round) + " " + color);
    //记录人机走的这一步
    chessboard.cunHuiQi();
    judgeWinerAi();
}

    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
    public static void judgeWinerNormal(){
        int bl=0;
        int wh=0;
        for (int i=0;i<cunHuiQi.get(cunHuiQi.size()-1).length();i++){
           if (cunHuiQi.get(cunHuiQi.size()-1).charAt(i)=='K'){
               bl++;
           }
           if (cunHuiQi.get(cunHuiQi.size()-1).charAt(i)=='k'){
               wh++;
           }
        }
        if (bl==0){
            JOptionPane.showMessageDialog(null, " white win ", " 游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }
        if (wh==0){
            JOptionPane.showMessageDialog(null, " black win ", " 游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public  void judgeWinerAi(){
        int bl=0;
        int wh=0;
        for (int i=0;i<cunHuiQi.get(cunHuiQi.size()-1).length();i++){
            if (cunHuiQi.get(cunHuiQi.size()-1).charAt(i)=='K'){
                bl++;
            }
            if (cunHuiQi.get(cunHuiQi.size()-1).charAt(i)=='k'){
                wh++;
            }
        }
        if (bl==0){
            JOptionPane.showMessageDialog(null, " white win ", " 游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public  void warn(ChessComponent chessComponent){
        for (int i=0;i<chessComponent.canMove().size();i++){
            if (chessComponent.canMove().get(i) instanceof KingChessComponent){
        JOptionPane.showMessageDialog(null, " 将军 ", " 将军警告", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public boolean PeaceThreeSameReady(){
        ArrayList<String> peaceThreeSame=new ArrayList<>();
        for (int i=0;i<cunHuiQi.size();i++){
           peaceThreeSame.add(cunHuiQi.get(i).substring(0,64));
        }
        for (int i=0;i<peaceThreeSame.size();i++){
            int times=1;
            String cur= peaceThreeSame.get(i);
            for (int j=0;j<peaceThreeSame.size();j++){
                if (cur.equals(peaceThreeSame.get(j))){
                    times++;
                }
            }
            System.out.println(times);
            if (times>=4){
                return true;
            }
        }
        return false;
    }
    public void PeaceThreeSame(Boolean peaceThreeSame){
        System.out.println(peaceThreeSame);
        if (PeaceThreeSameReady()){
            JOptionPane.showMessageDialog(null, "三次局面相同和棋 ", " 游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }
    }
//    public boolean peaceNoWayReady(){
//        ArrayList<ChessComponent> cur=new ArrayList<>();
//        ArrayList<ChessComponent> diRen=new ArrayList<>();
//        ChessComponent curKing = null;
//        for (int x=0;x<8;x++){
//            for (int y=0;y<8;y++){
//                if (Chessboard.getChessComponents()[x][y].getChessColor()==chessboard.getCurrentColor()&&!(Chessboard.getChessComponents()[x][y] instanceof KingChessComponent)){
//                    cur.add(Chessboard.getChessComponents()[x][y]);
//                }
//                if (Chessboard.getChessComponents()[x][y].getChessColor()==chessboard.getCurrentColor()&&Chessboard.getChessComponents()[x][y] instanceof KingChessComponent){
//                    curKing=Chessboard.getChessComponents()[x][y];
//                }
//                if (!(Chessboard.getChessComponents()[x][y].getChessColor()==chessboard.getCurrentColor())){
//                    diRen.add(Chessboard.getChessComponents()[x][y]);
//                }
//            }
//        }
//        int keZou=0;
//        for (int i=0;i<cur.size();i++){
//            keZou+=cur.get(i).canMove().size();
//        }
//        int buKeZouKing=0;
//        if (keZou==0){
//            //无法判断国王吃掉一个敌方棋子之后是不是会受到别人的攻击。
//            for (int i=0;i<diRen.size();i++){
//                for (int j=0;j<diRen.get(i).canMove().size();j++) {
//                    //不能直接在 棋子后加.getx  要.getchesspoint再.getx
//                    for (int k = 0; k< Objects.requireNonNull(curKing).canMove().size(); k++){
//                    int x = diRen.get(i).canMove().get(j).getChessboardPoint().getX();
//                    int y =diRen.get(i).canMove().get(j).getChessboardPoint().getY();
//                        if (x==curKing.canMove().get(k).getChessboardPoint().getX()&&y==curKing.canMove().get(k).getChessboardPoint().getY()){
//                            buKeZouKing++;
//                            System.out.println(x+"                                 "+y);
//                        }
//                    }
//                }
//            }
//            System.out.println(buKeZouKing+"________________________________________________________________________________");
//            if (buKeZouKing==curKing.canMove().size()){
//                return true;
//            }
//        }
//        return false;
//    }
//    public void PeaceNoWay(){
//        if (peaceNoWayReady()){
//            JOptionPane.showMessageDialog(null, " 和棋 ", " 游戏结束", JOptionPane.INFORMATION_MESSAGE);
//        }
//    }

}
