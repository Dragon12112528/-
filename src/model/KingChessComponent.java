package model;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent {
    public static void judge(List<ChessboardPoint> list){
        for (int i=0;i<list.size();i++){
            if (list.get(i).getX()>7||list.get(i).getX()<0||list.get(i).getY()>7||list.get(i).getY()<0){
                list.remove(i);
                i--;
            }
        }
    }
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;


    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ArrayList<ChessboardPoint> King=new ArrayList<>();
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()-1,this.getChessboardPoint().getY()-1));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()-1,this.getChessboardPoint().getY()));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()-1,this.getChessboardPoint().getY()+1));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX(),this.getChessboardPoint().getY()-1));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX(),this.getChessboardPoint().getY()+1));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()+1,this.getChessboardPoint().getY()-1));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()+1,this.getChessboardPoint().getY()));
        King.add(new ChessboardPoint(this.getChessboardPoint().getX()+1,this.getChessboardPoint().getY()+1));
        judge(King);
        for (int i = 0; i < King.size(); i++) {
            if (chessComponents[King.get(i).getX()][King.get(i).getY()].getChessColor()==getChessColor()) {
                King.remove(i);
                i--;
            }
        }
        for (int i = 0; i < King.size(); i++) {
            if (King.get(i).getX() == destination.getX() && King.get(i).getY() == destination.getY()) {
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
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
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

