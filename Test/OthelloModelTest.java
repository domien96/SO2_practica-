import lab6.othello.OthelloModel;
import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidBoardSizeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domien on 29/04/2016.
 */
public class OthelloModelTest {
    private int size = 8;
    private OthelloModel model;

    @Before
    public void setUp() throws Exception {
        model = new OthelloModel(size);
    }

    @After
    public void tearDown() throws Exception {
        model=null;
    }

    /**
     *
     * CONSTRUCTOR TESTS
     *
     */

    @Test
    public void testConstructionValidSize() throws Exception {
        // positieve size (even)
        assertNotNull(model);
        assertTrue("Size werd niet correct geinitialiseerd",model.getSize() == size);
    }

    @Test(expected = InvalidBoardSizeException.class)
    public void testConstructionStrictPositiveOdd() throws Exception {
        // positieve size (oneven)
        new OthelloModel(5);
    }

    @Test(expected = InvalidBoardSizeException.class)
    public void testConstructionStrictNegativeEven() throws Exception {
        new OthelloModel(-6);
    }

    @Test(expected = InvalidBoardSizeException.class)
    public void testConstructionStrictNegaveOdd() throws Exception {
        new OthelloModel(-5);
    }

    @Test(expected = InvalidBoardSizeException.class)
    public void testConstructionZero() throws Exception {
        new OthelloModel(0);
    }


    @Test
    public void testInbounds() {
        boolean[] valid = {false,false,true,true,true,false,false};
        int[] values = {-69,-1,0,size/2,size-1,size,size+69};
        for (int idxX=0;idxX<values.length;idxX++) {
            for (int idxY=0;idxY<values.length;idxY++) {
                boolean validCoordinate = valid[idxX] && valid[idxY];
                assertEquals(String.format("Geteste coordinaat: (%d,%d)",values[idxX],values[idxY]),validCoordinate,model.inBounds(values[idxX],values[idxY]));
            }
        }
    }

    @Test(expected = BoardIndexOutOfBoundsException.class)
    public void testSetStateInvalidCoordinate() throws BoardIndexOutOfBoundsException {
        model.setState(-1,0,0);
    }

    @Test(expected = BoardIndexOutOfBoundsException.class)
    public void testGetStateInvalidCoordinate() throws BoardIndexOutOfBoundsException {
        model.getState(-1,0);
    }

    @Test
    public void testGetStateAndSetStateValidCoordinate() throws BoardIndexOutOfBoundsException {
        model.setState(0,size/2,1);
        assertEquals(model.getState(0,size/2),1);
        model.setState(0,size/2,0);
        assertEquals(model.getState(0,size/2),0);
    }

    @Test
    public void testSetTurnGetTurn() {
        model.setTurn(1);
        assertEquals(1,model.getTurn());
        model.setTurn(0);
        assertEquals(0,model.getTurn());
    }

    @Test
    public void testEquals() throws InvalidBoardSizeException, BoardIndexOutOfBoundsException {
        OthelloModel otherModel = new OthelloModel(size);
        assertTrue(model.equals(otherModel));

        assertFalse(model.equals(null));
        assertFalse(model.equals(new Object()));
        assertFalse(model.equals(new OthelloModel(size+2)));
        otherModel.setState(0,0,(model.getState(0,0)+1)%2); // 0|->1 en 1|->0
        assertFalse(model.equals(otherModel));

        otherModel = new OthelloModel(size);
        otherModel.setTurn((model.getTurn()+1)%2);
        assertFalse(model.equals(otherModel));

    }
}