package view;


import controller.ClickController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private static final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private  ClickController clickController = new ClickController(this);
    private  int CHESS_SIZE;

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

       //  FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 5, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 6, ChessColor.WHITE);
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 0, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 2, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 3, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 4, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 5, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 6, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 7, ChessColor.WHITE);
    }

    public static ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;

    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        //clickController.setFirst(null);在每个按钮处加是为了防止 在选中一个棋子，没有走也没有取消的情况下点按钮出问题。
        clickController.setFirst(null);
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessData.get(i).charAt(j) == 'R') {
                    initRookOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'r') {
                    initRookOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'K') {
                    initKingOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'k') {
                    initKingOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'Q') {
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'q') {
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'B') {
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'b') {
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'N') {
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'n') {
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'P') {
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'p') {
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                }
            }
        }
        if (chessData.get(8).charAt(0)=='b') {
            ClickController.setCurrent(ChessColor.BLACK);
            currentColor = ChessColor.BLACK;
        }else {
            ClickController.setCurrent(ChessColor.WHITE);
            currentColor = ChessColor.WHITE;
        }
        ClickController.setRound(Integer.parseInt (chessData.get(8).substring(1)));
    }

    public int loadGameF(List<String> chessData) {

        if (chessData.size()!=9||(!Objects.equals(chessData.get(8).charAt(0), 'w') && !Objects.equals(chessData.get(8).charAt(0), 'b'))){
            return 103;
        }

        for (int i = 0; i < 8; i++) {
            if (chessData.get(i).length() != 8) {
                return 101;
            }
        }

        for (int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (chessData.get(i).charAt(j) != 'R' && chessData.get(i).charAt(j) != 'r' && chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'b' && chessData.get(i).charAt(j) != 'K' && chessData.get(i).charAt(j) != 'k' && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'q' && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'n' && chessData.get(i).charAt(j) != 'P' && chessData.get(i).charAt(j) != 'p' && chessData.get(i).charAt(j) != '_') {
                    return 102;
                }
            }
        }

        //clickController.setFirst(null);在每个按钮处加是为了防止 在选中一个棋子，没有走也没有取消的情况下点按钮出问题。
        clickController.setFirst(null);
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessData.get(i).charAt(j) == 'R') {
                    initRookOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'r') {
                    initRookOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'K') {
                    initKingOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'k') {
                    initKingOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'Q') {
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'q') {
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'B') {
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'b') {
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'N') {
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'n') {
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                }if (chessData.get(i).charAt(j) == 'P') {
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                }if (chessData.get(i).charAt(j) == 'p') {
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                }
            }
        }
        if (chessData.get(8).charAt(0)=='b') {
            ClickController.setCurrent(ChessColor.BLACK);
            currentColor = ChessColor.BLACK;
        }else {
            ClickController.setCurrent(ChessColor.WHITE);
            currentColor = ChessColor.WHITE;
        }
        ClickController.setRound(Integer.parseInt (chessData.get(8).substring(1)));
        return 0;
    }



    public void archiveGame(String path) {
        clickController.setFirst(null);
        StringBuilder chessBoard = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getChessComponents()[i][j] instanceof RookChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'R':'r');

                }
                if (getChessComponents()[i][j] instanceof KingChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'K':'k');

                }
                if (getChessComponents()[i][j] instanceof QueenChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'Q':'q');

                }
                if (getChessComponents()[i][j] instanceof BishopChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'B':'b');

                }
                if (getChessComponents()[i][j] instanceof PawnChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'P':'p');

                }
                if (getChessComponents()[i][j] instanceof KnightChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'N':'n');

                }
                if (getChessComponents()[i][j] instanceof EmptySlotComponent ){
                    chessBoard.append('_');

                }
            }
            //一行8个
            chessBoard.append("\n");
        }
        chessBoard.append(currentColor==ChessColor.BLACK?'b':'w');
        chessBoard.append(ClickController.getRound());
        try {
            FileWriter fw = new FileWriter("resource\\" + path+".txt");
            fw.write(chessBoard.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public  void cunHuiQi() {
        StringBuffer chessBoard = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getChessComponents()[i][j] instanceof RookChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'R':'r');

                }
                if (getChessComponents()[i][j] instanceof KingChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'K':'k');

                }
                if (getChessComponents()[i][j] instanceof QueenChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'Q':'q');

                }
                if (getChessComponents()[i][j] instanceof BishopChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'B':'b');

                }
                if (getChessComponents()[i][j] instanceof PawnChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'P':'p');

                }
                if (getChessComponents()[i][j] instanceof KnightChessComponent ){
                    chessBoard.append(getChessComponents()[i][j].getChessColor()== ChessColor.BLACK? 'N':'n');

                }
                if (getChessComponents()[i][j] instanceof EmptySlotComponent ){
                    chessBoard.append('_');
                }
            }
        }
        chessBoard.append(currentColor==ChessColor.BLACK?'b':'w');
        chessBoard.append(ClickController.getRound());
        ClickController.cunHuiQi.add(chessBoard);
    }

    public void resetChessboard() {
        clickController.setFirst(null);
        currentColor = ChessColor.BLACK;
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 5, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 6, ChessColor.WHITE);
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 0, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 2, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 3, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 4, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 5, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 6, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 7, ChessColor.WHITE);
    }
 }

