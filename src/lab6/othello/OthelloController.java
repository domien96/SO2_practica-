
package othello;

import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidMoveException;


public class OthelloController {

    OthelloModel model;
    
    public OthelloController(OthelloModel model){
        this.model = model;
    }
    
    public boolean isValidMove(int x, int y) throws BoardIndexOutOfBoundsException {
        // TODO Not implemented yet!
        return false;
    }
    
    public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        // TODO Not implemented yet!
    }
    
  
    public int isFinished(){
        // TODO Not implemented yet!
        return 0;
    }
}
