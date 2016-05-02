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
        return isValidMove(x,y,model.getTurn());
    }
    
    private boolean isValidMove(int x, int y, int turn) throws BoardIndexOutOfBoundsException {
        if(!(model.inBounds(x,y)) || model.getState(x,y)!=0)
            return false;

        // Op elke richting van deze buren kijken we of we een coin van de HUIDIGE speler tegenkomen
        int otherPlayerState = -2*turn+1; // 1->-1 en 0->1
        int currentPlayerState = 2*turn-1; // 1->1 en 0->-1
        for(int[] negb : neighboursFilteredByState(x,y,otherPlayerState)) {
            int difX = negb[0]-x, difY = negb[1]-y;
            int step =2; // We beginnnen met het vakje NA deze buur.
            int[] cur = {step*difX+x,step*difY+y};
            while(model.inBounds(cur[0],cur[1])) {
                if(model.getState(cur[0],cur[1])==currentPlayerState)
                    return true;
                cur[0] = ++step*difX+x;
                cur[1] = step*difY+y;
            }
        }
        return false;
    }

    /**
     * Geeft de buren terug die zich op het bord bevinden.
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
        return result;
    }

    // Filter buren met state van andere player
    private List<int[]> neighboursFilteredByState(int x,int y,int state) {
        List<int[]> filteredNeighbours = new ArrayList<>(); // Buren met coin van de ANDERE speler.
        for(int[] negb : neighbours(x,y)) {
            if(model.getState(negb[0],negb[1])==state)
                filteredNeighbours.add(negb);
        }
        return filteredNeighbours;
    }
    
    public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
        if(isValidMove(x,y)) {
            int currentPlayerState = 2*model.getTurn()-1; // 1->1 en 0->-1

            //beetje code duplicatie
            for(int[] negb : neighboursFilteredByState(x,y,-2*model.getTurn()+1)) { // -2*model.getTurn()+1 == otherPlayerState
                int difX = negb[0]-x, difY = negb[1]-y;
                int step =2; // We beginnnen met het vakje NA deze buur.
                int[] cur = {step*difX+x,step*difY+y};
                while(model.inBounds(cur[0],cur[1])) {
                    if(model.getState(cur[0],cur[1])==currentPlayerState) {
                        changeLine(x, y, cur[0], cur[1],currentPlayerState);
                        break;
                    }
                    cur[0] = ++step*difX+x;
                    cur[1] = step*difY+y;
                }
            }
            int otherTurn = (model.getTurn()+1)%2;
            if(playerHasPossibleMoves(otherTurn))
                model.setTurn(otherTurn);
        } else {
            throw new InvalidMoveException();
        }
    }

    // Beide coordinaten moeten inbounds zijn. Als dit niet zo is, gebeurt er niets!
    public void changeLine(int xFrom, int yFrom, int xTo, int yTo, int state) throws BoardIndexOutOfBoundsException {
        if(model.inBounds(xFrom,yFrom) && model.inBounds(xTo,yTo)) {
            // Stepsize voor X en Y berekenen.
            int difX = (int) Math.signum(xTo-xFrom),
            difY = (int) Math.signum(yTo-yFrom);
            int x=xFrom,y=yFrom;
            while(!(x==xTo && y==yTo)) {
                model.setState(x,y,state);
                x += difX;
                y += difY;
            }
        }
    }
  
    public int isFinished(){
        int countBlack=0, countWhite=0;
        for(int row=0;row<model.getSize();row++) {
            for(int col=0;col<model.getSize();col++) {
                switch(model.getState(row,col)) {
                    case -1:
                        countBlack++;
                        break;
                    case 0:
                        try {
                            if(isValidMove(row,col,0) || isValidMove(row,col,1))
                                return 0;
                        } catch (BoardIndexOutOfBoundsException e) {
                            // should not happen
                        }
                        break;
                    case 1:
                        countWhite++;
                        break;
                }
            }
        }
        // FROM here : No possible moves for both players anymore.
        int res = (int) Math.signum(countWhite-countBlack); // fancy
        return  res == 0? 2 : res;
    }

    /**
     * Kijkt of speler nog geldige zetten heeft.
     * @param turn : 0 black; 1 wit.
     * @return
     */
    private boolean playerHasPossibleMoves(int turn) {
        for(int row=0;row<model.getSize();row++) {
            for(int col=0;col<model.getSize();col++) {
                if (model.getState(row,col)==0) {
                    try {
                        if(isValidMove(row,col,turn))
                            return true;
                    } catch (BoardIndexOutOfBoundsException e) {
                        // should not happen
                    }
                }
            }
        }
        return false;
    }
}
