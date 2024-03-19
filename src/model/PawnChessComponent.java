package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;


    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }


    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ArrayList<ChessboardPoint> Pawn=new ArrayList<>();
        if (getChessColor()==ChessColor.BLACK){
            if (getChessboardPoint().getX()==1) {
                for (int i = 1; i <= 2; i++) {
                    Pawn.add(new ChessboardPoint(getChessboardPoint().getX()+i, getChessboardPoint().getY() ));
                    if (chessComponents[getChessboardPoint().getX()+i][getChessboardPoint().getY()].getChessColor()!=ChessColor.NONE){
                        Pawn.remove(Pawn.size()-1);
                        break;
                    }
                }
                if (getChessboardPoint().getY() - 1 >= 0) {
                    if (chessComponents[getChessboardPoint().getX() + 1][getChessboardPoint().getY() - 1].getChessColor() == ChessColor.WHITE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() - 1));
                    }
                }
                if (getChessboardPoint().getY() + 1 <= 7) {
                    if (chessComponents[getChessboardPoint().getX() + 1][getChessboardPoint().getY() + 1].getChessColor() == ChessColor.WHITE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1));
                    }
                }
            }else {
                if (getChessboardPoint().getX() + 1 <=7) {
                    if (chessComponents[getChessboardPoint().getX() + 1][getChessboardPoint().getY() ].getChessColor() == ChessColor.NONE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX()+ 1, getChessboardPoint().getY() ));
                    }
                }


                if (getChessboardPoint().getY() - 1 >= 0&&getChessboardPoint().getX() + 1<=7) {
                    if (chessComponents[getChessboardPoint().getX() + 1][getChessboardPoint().getY() - 1].getChessColor() == ChessColor.WHITE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() +1, getChessboardPoint().getY() - 1));
                    }
                }
                if (getChessboardPoint().getX() + 1 <= 7&&getChessboardPoint().getY() + 1<=7) {
                    if (chessComponents[getChessboardPoint().getX() + 1][getChessboardPoint().getY() +1 ].getChessColor() == ChessColor.WHITE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1));
                    }
                }
            }
        }
        if (getChessColor()==ChessColor.WHITE){
            if (getChessboardPoint().getX()==6) {
                for (int i = 1; i <= 2; i++) {
                    Pawn.add(new ChessboardPoint(getChessboardPoint().getX()-i, getChessboardPoint().getY() ));
                    if (chessComponents[getChessboardPoint().getX()-i][getChessboardPoint().getY()].getChessColor()!=ChessColor.NONE){
                        System.out.println(Pawn.size()-1+"EFWFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                        Pawn.remove(Pawn.size()-1);
                        break;
                    }
                }
                if (getChessboardPoint().getY() - 1 >= 0) {
                    if (chessComponents[getChessboardPoint().getX() - 1][getChessboardPoint().getY() - 1].getChessColor() == ChessColor.BLACK) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1));
                    }
                }
                if (getChessboardPoint().getY() + 1 <= 7) {
                    if (chessComponents[getChessboardPoint().getX() - 1][getChessboardPoint().getY() + 1].getChessColor() == ChessColor.BLACK) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() -1, getChessboardPoint().getY() + 1));
                    }
                }
            }else {
                if (getChessboardPoint().getX() - 1 >=0) {
                    if (chessComponents[getChessboardPoint().getX() - 1][getChessboardPoint().getY() ].getChessColor() == ChessColor.NONE) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX()- 1, getChessboardPoint().getY() ));
                    }
                }
                if (getChessboardPoint().getX() -1>=0&&getChessboardPoint().getY() - 1>=0) {
                    if (chessComponents[getChessboardPoint().getX() - 1][getChessboardPoint().getY() - 1].getChessColor() == ChessColor.BLACK) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1));
                    }
                }
                if (getChessboardPoint().getX() - 1 >=0&&getChessboardPoint().getY() + 1<=7) {
                    if (chessComponents[getChessboardPoint().getX() - 1][getChessboardPoint().getY() + 1].getChessColor() == ChessColor.BLACK) {
                        Pawn.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() + 1));
                    }
                }
            }
        }
        for (int i = 0; i < Pawn.size(); i++) {
            if (Pawn.get(i).getX() == destination.getX() && Pawn.get(i).getY() == destination.getY()) {
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
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
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
