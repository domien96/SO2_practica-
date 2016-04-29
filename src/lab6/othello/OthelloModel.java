package lab6.othello;


import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidBoardSizeException;

public class OthelloModel {

    // the game board
    private int[][] board;
    // who's turn it is (-1/1)
    private int turn;
    
    public OthelloModel(int size) throws InvalidBoardSizeException {
       // TODO Not implemented yet!
    }
    
    public int getState(int x, int y){
        // TODO Not implemented yet!
        return 0;
    }
    
    public void setState(int x, int y, int state) throws BoardIndexOutOfBoundsException {
        // TODO Not implemented yet!
        throw new BoardIndexOutOfBoundsException();
    }
    
    public boolean inBounds(int x, int y){
        // TODO Not implemented yet!
        return false;
    }
    
    public int getTurn(){
        // TODO Not implemented yet!
        return 0;
    }
    
    public void setTurn(int turn){
        // TODO Not implemented yet!
    }
    
    public int getSize(){
        // TODO Not implemented yet!
        return 0;
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
        // TODO Not implemented yet!
        return false;
    }
    
}
