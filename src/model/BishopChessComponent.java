package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BishopChessComponent extends ChessComponent{
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;


    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ArrayList<ChessboardPoint> Bishop=new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() + i > 7 || getChessboardPoint().getY() + i > 7) {
                break;
            }
            Bishop.add(new ChessboardPoint(getChessboardPoint().getX() + i, getChessboardPoint().getY() + i));
            if (!(chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY() + i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() - i < 0 || getChessboardPoint().getY() - i < 0) {
                break;
            }
            Bishop.add(new ChessboardPoint(getChessboardPoint().getX() - i, getChessboardPoint().getY() - i));
            if (!(chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY() - i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() + i > 7 || getChessboardPoint().getY() - i < 0) {
                break;
            }
            Bishop.add(new ChessboardPoint(getChessboardPoint().getX() + i, getChessboardPoint().getY() - i));
            if (!(chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY() -i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() - i < 0 || getChessboardPoint().getY() + i > 7) {
                break;
            }
            Bishop.add(new ChessboardPoint(getChessboardPoint().getX() - i, getChessboardPoint().getY() + i));
            if (!(chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY() + i] instanceof EmptySlotComponent)) {
                break;
            }
        }

        for (int i = 0; i < Bishop.size(); i++) {
            if (chessComponents[Bishop.get(i).getX()][Bishop.get(i).getY()].getChessColor()==getChessColor()) {
                Bishop.remove(i);
                i--;
            }
        }

        for (int i = 0; i < Bishop.size(); i++) {
            if (Bishop.get(i).getX() == destination.getX() && Bishop.get(i).getY() == destination.getY()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<ChessComponent> canMove() {
        ArrayList<ChessComponent> moveAble=new ArrayList<>();
        for (int x=0;x<8;x++){
            for (int y=0;y<8;y++){
                if (canMoveTo(Chessboard.getChessComponents(),new ChessboardPoint(x,y))){
                    moveAble.add(Chessboard.getChessComponents()[x][y]);
                }
            }
        }
        return moveAble;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if (isCanMove()) { // Highlights the model if selected.
            g.setColor(Color.blue);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if (eM) { // Highlights the model if selected.
            g.setColor(Color.green);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
