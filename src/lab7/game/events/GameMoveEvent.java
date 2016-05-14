package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameMoveEvent extends Event {

    private static final String TYPE = "Move";

    public int turn, row,col;

    public GameMoveEvent(String message,int col,int turn,int row) {
        super(TYPE, message);
        this.col = col;
        this.row = row;
        this.turn = turn;

    }

    public GameMoveEvent(int row, int col,int turn) {
        super(TYPE,"Move");
        this.col = col;
        this.row = row;
        this.turn = turn;

    }
}
