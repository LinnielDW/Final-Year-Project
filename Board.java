package Linniel;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private int y, x, minecount, possibleMoves, randx, randy, adjcount;
    private int[][][] board;
    private boolean first;
    Random rand = new Random();
    boolean zeroFfirst;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int[][][] getBoard(){
        return board;
    }
    public int getminecount(){
        return minecount;
    }

    //Initialises board
    public void initboard(int x, int y, int minecount){
        //+2 height and width for edge cases when placing mines and adjacent mine detection.
        board = new int[2][y+4][x+4];
        this.y = y;
        this.x = x;
        this.minecount = minecount;
        first = true;
        possibleMoves = y*x;
        zeroFfirst = false;

        for(int i=0;i<y+4;i++){
            for(int j=0;j<x+4;j++){
                board[0][i][j] = 0;
                board[1][i][j] = 0;
            }
        }
        renderboard(board);
    }

    //Reveals board space coordinate
    public boolean reveal(int y , int x){
        boolean alive;
        if(first){
            first = false;
            generateboard(y,x);
        }
        alive = minecheck(y,x);
        //System.out.println("Real Learner.Linniel.Board");
        renderboard(board);
        return alive;
    }


    //Used to generate a random board
    public void generateboard(int y, int x){
        //Mine Allocation
        for(int i = 0; i < minecount;){
            randy = ThreadLocalRandom.current().nextInt(2, this.y + 2); //+1 to cover cells 2-limit +1 to set proper bound
            randx = ThreadLocalRandom.current().nextInt(2, this.x + 2);
            if(board[1][randy][randx]!=-1 && (randx != x+1 && randy != y+1)){
                board[1][randy][randx] = -1;
                //  System.out.println(randx+", "+randy); //Used to print the mine locations for validation
                i++;
            }
        }
        //Mine Clues Placed
        for (int  i = 2; i < this.y+2; i++) {
            for (int j = 2; j < this.x+2; j++) {
                if (board[1][i][j]!=-1){
                    adjcount = 0;
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (board[1][k][l] == -1) {
                                adjcount++;
                            }
                        }
                    }
                    board[1][i][j]=adjcount;
                }
            }
        }
    }

    //Used to check if the newly revealed cell is a mine or not
    public boolean minecheck(int y , int x) {
        boolean alive = true;
        if (board[0][y+1][x+1]!=1) {
            board[0][y+1][x+1]=1;
            possibleMoves--;
            switch(board[1][y+1][x+1]){
                case -1:
                    alive = false;
                    break;
                case 0:
                    zeroFfirst = true;
                    for (int i = y; i <= y + 2; i++) {
                        for (int j = x; j <= x + 2; j++) {
                            if (((i <= this.y+1 && i >= 2) && (j <= this.x+1 && j >= 2)) && board[0][i][j] == 0) {
                                minecheck(i-1, j-1);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return alive;
    }


    //Used to show the board. Mostly unused by default, only in winning games does it show the board.
    public void renderboard(int[][][] b){
        for (int i = 2; i < y+2; i++) {
            for (int  j = 2; j < x+2; j++) {
                if (b[0][i][j] == 0) {
                    System.out.print("_ ");
                }
                else {
                    if (b[1][i][j]!= -1) {
                        System.out.print(b[1][i][j] + " ");
                    }
                    else{
                        System.out.print("* ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int moves(){
        return possibleMoves;
    }
}
