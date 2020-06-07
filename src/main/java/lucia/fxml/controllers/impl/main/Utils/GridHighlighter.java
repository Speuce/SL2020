package main.java.lucia.fxml.controllers.impl.main.Utils;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Util for adding highlighting AND selecting to a gridpane
 * @author Matt Kwiatkowski
 */
public class GridHighlighter {

    public static final String BORDER_STYLE = "-fx-border-color: black;-fx-border-width: 0 0 1 1;";

    /**
     * The currently selected row
     */
    private int highLightedRow = -1;

    /**
     * Stores frequently used eventhandlers
     */
    private final EventHandler<? super MouseEvent> highlightHandler;
    private final EventHandler<? super MouseEvent> unHighlightHandler;
    private final EventHandler<? super MouseEvent> selectHandler;

    private final Set<Consumer<Boolean>> selectHandlers;

    /**
     * Creates a new grid highlighter with background color white
     * @param p the gridpane to attach this to
     */
    public GridHighlighter(GridPane p){
        this(p, "FFFFFF");
    }

    /**
     * Creates a new grid highlighter
     * @param pane the gridpane to attach this to
     * @param backgroundColor the background color to revert to after it is highlighted
     */
    public GridHighlighter(GridPane pane, String backgroundColor) {
        /*
         * The background color of the gridpane
         */
        selectHandlers = new HashSet<>();
        /*
         * the pane for which this highlighter is added to
         */
        highlightHandler = e -> pane.getChildren().forEach(c -> {
            Integer targetIndex = GridPane.getRowIndex((Node)e.getSource());
            if(targetIndex.equals(highLightedRow)){
                return;
            }
            if (GridPane.getRowIndex(c).equals(targetIndex)) {
                c.setStyle("-fx-background-color:#f9f3c5;" + BORDER_STYLE);
            }
        });
        unHighlightHandler = e -> pane.getChildren().forEach(c -> {
            Integer targetIndex = GridPane.getRowIndex((Node)e.getSource());
            if(targetIndex.equals(highLightedRow)){
                return;
            }
            if (GridPane.getRowIndex(c).equals(targetIndex)) {
                c.setStyle("-fx-background-color: "+backgroundColor+";" + BORDER_STYLE);
            }
        });
        selectHandler = e -> {
            int targetIndex = GridPane.getRowIndex((Node)e.getSource());
            for(Node n: pane.getChildren()){
                int index = GridPane.getRowIndex(n);
                if(targetIndex == highLightedRow && index == targetIndex){
                    n.setStyle("-fx-background-color:#f9f3c5;" + BORDER_STYLE);
                }else{
                    if(index == targetIndex){
                        n.setStyle("-fx-background-color:#f0e381;" + BORDER_STYLE);
                    }else if(index == highLightedRow) {
                        n.setStyle("-fx-background-color:"+backgroundColor+";" + BORDER_STYLE);
                    }
                }
            }
            if(targetIndex == highLightedRow){
                setHighLightedRow(-1);
            }else{
                setHighLightedRow(targetIndex);
            }
        };
    }

    /**
     * adds a runnable that will be called every time a new row a hightlighted
     * @param r the {@link Runnable} to add
     */
    public void addSelectListener(Consumer<Boolean> r){
        selectHandlers.add(r);
    }

    /**
     * Calls the select handlers
     * @param opened {@code true} if the area is being selected, {@code false} if unselected
     */
    private void callHandlers(Boolean opened){
        selectHandlers.forEach(r -> r.accept(opened));
    }

    /**
     * registers a node to the pane
     * @param n the {@link Node} to add
     */
    public void registerNode(Node n){
        n.setStyle("-fx-border-color: black;-fx-border-width: 0 0 1 1;");
        n.setOnMouseEntered(highlightHandler);
        n.setOnMouseExited(unHighlightHandler);
        n.setOnMouseClicked(selectHandler);
    }

    public int getHighLightedRow() {
        return highLightedRow;
    }

    private void setHighLightedRow(int highLightedRow){
        if(this.highLightedRow == -1 && highLightedRow>-1){
            //paymentPane.setVisible(true);
            this.highLightedRow = highLightedRow;
            callHandlers(true);
        }
        if(highLightedRow == -1){
            //paymentPane.setVisible(false);
            callHandlers(false);
        }
        this.highLightedRow = highLightedRow;

    }

    /**
     * Resets the highlighted row
     */
    public void reset(){
        highLightedRow = -1;
    }


}
