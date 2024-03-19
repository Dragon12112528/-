package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {
    public static void judge(List<ChessboardPoint> list){
        for (int i=0;i<list.size();i++){
            if (list.get(i).getX()>7||list.get(i).getX()<0||list.get(i).getY()>7||list.get(i).getY()<0){
                list.remove(i);
                i--;
            }
        }
    }
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;


    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ArrayList<ChessboardPoint> Knight=new ArrayList<>();
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()+1,this.getChessboardPoint().getY()+2));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()-1,this.getChessboardPoint().getY()+2));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()+2,this.getChessboardPoint().getY()+1));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()+2,this.getChessboardPoint().getY()-1));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()+1,this.getChessboardPoint().getY()-2));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()-1,this.getChessboardPoint().getY()-2));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()-2,this.getChessboardPoint().getY()+1));
        Knight.add(new ChessboardPoint(this.getChessboardPoint().getX()-2,this.getChessboardPoint().getY()-1));
        judge(Knight);
        for (int i = 0; i < Knight.size(); i++) {
            if (chessComponents[Knight.get(i).getX()][Knight.get(i).getY()].getChessColor()==getChessColor()) {
                Knight.remove(i);
                i--;
            }
        }
        for (int i = 0; i < Knight.size(); i++) {
            if (Knight.get(i).getX() == destination.getX() && Knight.get(i).getY() == destination.getY()) {
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
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
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



