package lab6.othello;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;

/**
 * Created by domien on 15/04/2016.
 */
public class Counter implements Serializable, ChangeListener<Integer> {
    private ObjectProperty<Integer> number = new SimpleObjectProperty<>(0);
    private ObjectProperty<Integer> stateToCount = new SimpleObjectProperty<>(0); // Empty by default

    private GridPane cell = new GridPane();

    public Counter() {
        // initialize...
        Label lbl0 = new Label("Empty");
        lbl0.setStyle("-fx-font-weight: bold");
        this.stateToCount.addListener((e) -> {
            int newVal = getStateToCount();
            lbl0.setText(newVal == -1? "Black" : (newVal==1? "White" : "Empty"));
        });
        cell.add(lbl0,0,0);

        Label lbl = new Label();
        lbl.textProperty().bind(Bindings.convert(this.number));
        cell.add(lbl,0,1);

        //border
        cell.setStyle("-fx-border-color: black");
        cell.setPadding(new Insets(3));

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

    public synchronized Integer getStateToCount() {
        return stateToCount.get();
    }

    public ObjectProperty<Integer> stateToCountProperty() {
        return stateToCount;
    }

    public synchronized void setStateToCount(Integer stateToCount) {
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

    @Override
    public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        // othello piece changed
        int state = getStateToCount();
        if(oldValue==state) {
            decrement();
        } // geen else if , want als oldvalue == newvalue zal dit niet meer kloppen.
        if (newValue == state) {
            increment();
        }
    }
}
