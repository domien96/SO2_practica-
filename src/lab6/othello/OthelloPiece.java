package lab6.othello;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

/**
 * Created by domien on 15/04/2016.
 */
public class OthelloPiece implements Serializable, EventHandler<MouseEvent>
{
    private ObjectProperty<Integer> state = new SimpleObjectProperty<>(0);
    private StackPane cell = new StackPane();
    private Circle[] circles = { new Circle(25, Color.BLACK), new Circle(25,Color.LIGHTGRAY), new Circle(25,Color.WHITE)};

    public OthelloPiece(){
        // intialisatie ...
        cell.setStyle("-fx-background-color: lightgray");
        cell.setPrefSize(50,50);
        cell.setMinSize(50,50);
        // default grijze cirkel
        cell.setOnMouseClicked((e) -> {
            handle(e);
        }
        );

    }

    public synchronized Integer getState(){
        return state.getValue();
    }

    public synchronized void setState(int newValue){
        state.setValue(newValue);
        cell.getChildren().clear();
        cell.getChildren().add(circles[newValue + 1]);
        cell.setAlignment(Pos.CENTER);
    }

    public ObjectProperty<Integer> stateProperty() {
        return state;
    }

    public Pane getContent() {
        return cell;
    }

    @Override
    public void handle(MouseEvent event) {
        if (this.getState() == -1) {
            this.setState(1);
        } else {
            this.setState(getState() - 1);
        }
    }
}
