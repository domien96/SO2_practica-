package lab5.othelllo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.Serializable;

/**
 * Created by domien on 15/04/2016.
 */
public class Counter implements Serializable {
    private ObjectProperty<Integer> number = new SimpleObjectProperty<>(0);
    private ObjectProperty<String> stateToCount = new SimpleObjectProperty<>("default");

    private GridPane cell = new GridPane();

    public Counter() {
        // initialize...
        Label lbl = new Label();
        lbl.textProperty().bind(this.stateToCount);
        cell.add(lbl,0,0);

        lbl = new Label();
        lbl.textProperty().bind(this.number.to);
        cell.add(lbl,0,1);
    }

    public synchronized Integer getNumber() {
        return number.get();
    }

    public ObjectProperty<Integer> numberProperty() {
        return number;
    }

    public synchronized void setNumber(Integer number) {
        this.number.set(number);
    }

    public synchronized String getStateToCount() {
        return stateToCount.get();
    }

    public ObjectProperty<String> stateToCountProperty() {
        return stateToCount;
    }

    public synchronized void setStateToCount(String stateToCount) {
        cell.get
        this.stateToCount.set(stateToCount);
    }

    public void increment() {
        this.setNumber(this.getNumber()+1);
    }

    public void decrement() {
        this.setNumber(this.getNumber()-1);
    }

    public Pane getContent() {
        return cell;
    }
}
