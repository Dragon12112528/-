package controller;

import model.ChessColor;
import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static controller.ClickController.getRound;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public void resetGame()
    {
        chessboard.resetChessboard();
    }
    public int loadGameFromFile(String path) {
        if (!path.endsWith(".txt")){
            return 104;
        }
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGameF(chessData);
            return chessboard.loadGameF(chessData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void archiveGameFile(String path){
        chessboard.archiveGame(path);
    }
    public void cunQi(List<String> chessData){
        chessboard.loadGame( chessData);
    }
    public void jiShiChange(){
        ClickController.setRound(getRound()+1);
        if (ClickController.getCurrent()== ChessColor.BLACK){
            ClickController.setCurrent(ChessColor.WHITE);
        }else {
            ClickController.setCurrent(ChessColor.BLACK);
        }
        int round= getRound();
        String color=ClickController.getCurrent().getName();
        ClickController.statusLabel.setText("Round"+String.valueOf(round)+" "+color);
        chessboard.swapColor();
    }

}
