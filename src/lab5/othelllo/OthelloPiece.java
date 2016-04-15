package lab5.othelllo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by domien on 15/04/2016.
 */
public class OthelloPiece
{
    private ObjectProperty<Integer> state = new SimpleObjectProperty<>(0);

    public OthelloPiece(){
        // intialisatie ...
    }

    public synchronized Integer getFoo(){
        return state.getValue();
    }

    public synchronized void setFoo(int newValue){
        state.setValue(newValue);
    }
    
    public ObjectProperty<Integer> fooProperty() {
        return state;
    }
}
