package lab6.othello;


import lab5.othello.*;
import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidBoardSizeException;

public class OthelloModel {

    private final int size;
    // the game board
    private int[][] board;
    // who's turn it is (-1/1) = (black/white)
    private int turn;
    
    public OthelloModel(int size) throws InvalidBoardSizeException {
        if (size>0 && size%2 ==0)
            this.size=size;
        else
            throw new InvalidBoardSizeException();

        // bord initializeren
        board = new int[size][size];
    }
    
    public int getState(int x, int y){
        return inBounds(x,y)? board[x][y] : 0;
    }
    
    public void setState(int x, int y, int state) throws BoardIndexOutOfBoundsException {
        if(inBounds(x,y))
            board[x][y] = state;
        else
            throw new BoardIndexOutOfBoundsException();
    }
    
    public boolean inBounds(int x, int y){
        return x >= 0 && y>=0 && x<size && y<size;
    }
    
    public int getTurn(){
        return turn;
    }
    
    public void setTurn(int turn){
        this.turn = turn;
    }
    
    public int getSize(){
        return size;
    }
    
    public String toString(){
        String s = "";
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                s+="\t"+board[i][j];
            }
            s+="\n";
        }
        return s;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof OthelloModel) {
            OthelloModel other = (OthelloModel) o;
            if(other.size == this.size && other.turn == this.turn) {
                for(int row=0;row<size;row++) {
                    for(int col=0;col<size;col++) {
                        if (!(board[row][col] == other.board[row][col]))
                            return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
}
