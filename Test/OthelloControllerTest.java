import lab6.othello.OthelloController;
import lab6.othello.OthelloModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by domien on 29/04/2016.
 */
public class OthelloControllerTest {

    private OthelloController ctrl;
    private int boardSize = 4;
    private OthelloModel m;

    @Before
    public void setUp() throws Exception {
        m = new OthelloModel(boardSize);
        m.setState(1,1,1);
        m.setState(1,2,-1);
        m.setState(2,1,-1);
        m.setState(2,2,1);
        this.ctrl = new OthelloController(m);
    }

    @After
    public void tearDown() throws Exception {
        ctrl = null;
    }

    @Test
    public void testIsValidMove() throws Exception {
        int[][] legalForBlack = {{0,1},{1,0},{2,3},{3,2}}; // in volgorde van loop!
        int[][] legalForWhite = {{0,2},{1,3},{2,0},{3,1}}; // in volgorde van loop!
        int curBlack = 0, curWhite = 0;
        for (int x=0;x<boardSize;x++) {
            for (int y=0;y<boardSize;y++) {
                int[] bufferBlack = curBlack<legalForBlack.length? legalForBlack[curBlack] : null,
                        bufferWhite = curWhite<legalForWhite.length? legalForWhite[curWhite] : null;
                if (bufferBlack!=null && x==bufferBlack[0] && y==bufferBlack[1]) {
                    curBlack++;
                    assertTrue(String.format("Geteste coordinaat: (%d,%d)",x,y),ctrl.isValidMove(x,y));
                } else  if (bufferWhite!=null && x==bufferWhite[0] && y==bufferWhite[1]) {
                    curWhite++;
                    m.setTurn(1);
                    assertTrue(String.format("Geteste coordinaat: (%d,%d)",x,y),ctrl.isValidMove(x, y));
                    m.setTurn(0);
                } else {
                    assertFalse(String.format("Geteste coordinaat: (%d,%d)",x,y),ctrl.isValidMove(x,y));
                }
            }
        }

        //ongeldige coordinaat
        assertFalse(ctrl.isValidMove(-1,0));
        assertFalse(ctrl.isValidMove(boardSize,0));
        assertFalse(ctrl.isValidMove(boardSize+1,0));
    }

    @Test
    public void doMove() throws Exception {
        // ALLE COORDINATEN VAN DE VORM (X,Y) = (RIJ,COL)

        // {0,1}
        setUp();
        m.setTurn(0); // black
        ctrl.doMove(0,1);
        assertEquals(1,m.getTurn());

        int[][] black={{0,1},{1,1},{1,2},{2,1}}, empty = {{0,0},{1,0},{2,0},{3,0},{3,1},{3,2},{3,3},{2,3},{1,3},{0,3},{0,2}};
        assertEquals(m.getState(2,2),1); // wit
        for (int[] coord: empty)
            assertEquals(0,m.getState(coord[0],coord[1]));

        for (int[] coord: black)
            assertEquals(-1,m.getState(coord[0],coord[1]));


        // {1,0}
        setUp();
        m.setTurn(0); // black
        ctrl.doMove(1,0);
        assertEquals(m.getTurn(),1);


        empty= new int[][]{{0,0},{0,1},{0,2},{0,3},{1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{2,0}};
        black= new int[][]{{1,0},{1,1},{1,2},{2,1}};
        assertEquals(m.getState(2,2),1); // wit
        for (int[] coord: empty)
                assertEquals(0,m.getState(coord[0],coord[1]));

        for (int[] coord: black)
            assertEquals(-1,m.getState(coord[0],coord[1]));


        // {2,3}
        setUp();
        m.setTurn(0); // black
        ctrl.doMove(2,3);
        assertEquals(m.getTurn(),1);

        empty = new int[][]{{0,0},{1,0},{2,0},{3,0},{3,1},{3,2},{3,3},{1,3},{0,3},{0,2},{0,1}};
        black = new int[][]{{2,3},{2,2},{1,2},{2,1}};
        assertEquals(m.getState(1,1),1); // wit
        for (int[] coord: empty)
            assertEquals(0,m.getState(coord[0],coord[1]));

        for (int[] coord: black)
            assertEquals(-1,m.getState(coord[0],coord[1]));


        // {3,2}
        setUp();
        m.setTurn(0); // black
        ctrl.doMove(3,2);
        assertEquals(m.getTurn(),1);

        empty = new int[][]{{0,0},{1,0},{2,0},{3,0},{3,1},{3,3},{2,3},{1,3},{0,3},{0,2},{0,1}};
        black = new int[][]{{3,2},{2,2},{1,2},{2,1}};
        assertEquals(m.getState(1,1),1); // wit
        for (int[] coord: empty)
            assertEquals(0,m.getState(coord[0],coord[1]));

        for (int[] coord: black)
            assertEquals(-1,m.getState(coord[0],coord[1]));


    }

    @Test
    public void testIsFinished() throws Exception {
        // nog niet afgelopen
        m.setTurn(0); //zwart begint
        ctrl.doMove(1,0);
        ctrl.doMove(0,2);
        assertEquals(0,ctrl.isFinished());


        //1.1 zwart wint, bord vol
        ctrl.doMove(1,3);
        ctrl.doMove(2,0);
        ctrl.doMove(3,2);
        ctrl.doMove(3,1);
        ctrl.doMove(0,0);
        ctrl.doMove(0,1);
        ctrl.doMove(0,3);
        ctrl.doMove(3,3);
        ctrl.doMove(3,0);
        m.setState(2,3,1); // geen zteten meer: bord opvullen.
        assertEquals(-1,ctrl.isFinished());


        //1.2 zwart wint, geen geldige zetten meer voor wit en zwart

        OthelloModel allBlackModel = new OthelloModel(boardSize);
        OthelloController allBlackController = new OthelloController(allBlackModel);
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
                allBlackModel.setState(i, j, -1);
            }
        }
        assertEquals(allBlackController.isFinished(), -1);
        allBlackModel.setState(0, 0, 0);
        assertEquals(allBlackController.isFinished(), -1);


        //2.1 wit wint, bord vol
        OthelloModel allWhiteModel = new OthelloModel(boardSize);
        OthelloController allWhiteController = new OthelloController(allWhiteModel);
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
                allWhiteModel.setState(i, j, 1);
            }
        }
        assertEquals(allWhiteController.isFinished(), 1);


        //2.2 wit wint, geen geldige zetten meer voor wit en zwart
        allWhiteModel.setState(0, 0, 0);
        assertEquals(allWhiteController.isFinished(), 1);


        //3.1 zgelijk, bord vol
        for(int row=0;row<boardSize/2;row++) {
            for (int col=0;col<boardSize;col++)
                m.setState(row,col,-1);
        }
        for(int row=boardSize/2;row<boardSize;row++) {
            for (int col=0;col<boardSize;col++)
                m.setState(row,col,1);
        }
        assertEquals(2,ctrl.isFinished());


        //3.2 gelijk, geen geldige zetten meer voor wit en zwart
        for(int row=0;row<boardSize/2;row++) {
            for (int col=0;col<boardSize;col++)
                m.setState(row,col,-1);
        }
        m.setState(boardSize/2-1,3,0);
        for(int row=boardSize/2;row<boardSize;row++) {
            for (int col=0;col<boardSize;col++)
                m.setState(row,col,1);
        }
        m.setState(boardSize/2,3,0);
        assertEquals(2,ctrl.isFinished());


    }
}