package lab6.othello;

import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidMoveException;


public class OthelloController {

    OthelloModel model;
    
    public OthelloController(OthelloModel model){
        this.model = model;
    }
    
    public boolean isValidMove(int x, int y) throws BoardIndexOutOfBoundsException {
        boolean isValid = false;
        int[][] l = {{},{1},{1}};
        //for()
        return model.inBounds(x,y) && isValid;
    }
    
    public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        // TODO Not implemented yet!
    }
    
  
    public int isFinished(){
        // TODO Not implemented yet!
        return 0;
    }
}
