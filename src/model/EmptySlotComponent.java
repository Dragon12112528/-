package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
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

//        Image squarePhoto = BACKGROUND_PHOTO[(chessboardPoint.getX() + chessboardPoint.getY()) % 2];
//        g.drawImage(squarePhoto, 0, 0, getWidth() , getHeight() , this);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());




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
