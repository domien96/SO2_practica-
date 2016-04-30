package lab6.othello;

import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;


public class OthelloController {

    OthelloModel model;
    
    public OthelloController(OthelloModel model){
        this.model = model;
    }
    
    public boolean isValidMove(int x, int y) throws BoardIndexOutOfBoundsException {
        boolean isValid = false;
        int[][] l = {{},{1},{1}};
        for()
        return model.inBounds(x,y) && isValid;
    }

    /**
     * Geetf de buren terug die zich op het bord bevinden.
     * @param x
     * @param y
     * @return : arrays van coordinaten-koppels.
     */
    private List<int[]> neighbours(int x, int y) {
        int[][] relativePos = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        List<int[]> result = new ArrayList<>();
        for (int[] relPos : relativePos) {
            if (model.inBounds(x+relPos[0],y+relPos[1]))
                result.add(new int[]{x+relPos[0],y+relPos[1]});
        }
        return result
    }
    
    public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        // TODO Not implemented yet!
    }
    
  
    public int isFinished(){
        // TODO Not implemented yet!
        return 0;
    }
}
