package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QueenChessComponent extends ChessComponent{
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;


    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }



    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ArrayList<ChessboardPoint> Queen = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getY() + i > 7) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() + i));
            if (!(chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY() + i] instanceof EmptySlotComponent)) {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getY() - i < 0) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() - i));
            if (!(chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY() - i] instanceof EmptySlotComponent)) {
                break;
            }

        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() + i > 7) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() + i, getChessboardPoint().getY()));
            if (!(chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY() ] instanceof EmptySlotComponent)) {
                break;
            }

        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() - i < 0) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() - i, getChessboardPoint().getY()));
            if (!(chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY() ] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() + i > 7 || getChessboardPoint().getY() + i > 7) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() + i, getChessboardPoint().getY() + i));
            if (!(chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY() + i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() - i < 0 || getChessboardPoint().getY() - i < 0) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() - i, getChessboardPoint().getY() - i));
            if (!(chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY() - i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() + i > 7 || getChessboardPoint().getY() - i < 0) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() + i, getChessboardPoint().getY() - i));
            if (!(chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY() -i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getChessboardPoint().getX() - i < 0 || getChessboardPoint().getY() + i > 7) {
                break;
            }
            Queen.add(new ChessboardPoint(getChessboardPoint().getX() - i, getChessboardPoint().getY() + i));
            if (!(chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY() + i] instanceof EmptySlotComponent)) {
                break;
            }
        }
        for (int i = 0; i < Queen.size(); i++) {
            if (chessComponents[Queen.get(i).getX()][Queen.get(i).getY()].getChessColor()==getChessColor()) {
                Queen.remove(i);
                i--;
            }
        }
        for (int i = 0; i < Queen.size(); i++) {
            if (Queen.get(i).getX() == destination.getX() && Queen.get(i).getY() == destination.getY()) {
                return true;
            }
        }
        return false;
    }

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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
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

