package lesson5;

import java.awt.*;

public class ChessKnight {

    private int x = 8;
    private int y = 8;
    private int[][] desk;

    public ChessKnight(int x, int y) {
        this.x = x;
        this.y = y;
        desk = new int[this.x][this.y];
    }

    public int[][] arrangeKnights(){
        for (int i = 0; i < ; i++) {

        }
        Point[] pm = getPossibleMove(3, 3);

        return null;
    }

    private boolean pasteKnight(int x, int y){
        Point[] pm = getPossibleMove(x, y);
        for (int i = 0; i < pm.length; i++) {

        }


        return null;
    }


    private Point[] getPossibleMove(int x, int y){
        Point[] possibleMove = new Point[8];
        int n = 0;
        for (int i = x - 2; i <= x + 2 ; i++) {
           int dy = (i % 2 == 0) ? 2 : 1;
           if(i != x){
               possibleMove[n++] = new Point(i, y - dy);
               possibleMove[n++] = new Point(i, y + dy);
           }
        }
        return possibleMove;
    }

}
