package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.lang.Lang;

public class Controller {

    //MERCI
    
    @FXML
    private ColorPicker colorPicker;

    /*@FXML
    private TextField txtBrushSize;*/

    @FXML
    private Canvas canvas;

    @FXML
    private CheckBox checkBoxEraser;

    @FXML
    private Slider sliderSize;

    @FXML
    Pane pane;


    private boolean toolSelected = false;
    private boolean lineSelected = false;

    private GraphicsContext gcBrushTool;

    Line line=null;


    /**Button Clicks*/
    @FXML
    public void onBtnBrushClick(ActionEvent actionEvent) {
        toolSelected ^= true;
    }

    @FXML
    public void onBtnLineClick(ActionEvent actionEvent) {
        lineSelected ^= true;
    }
    double x,y,x1,y1;
    public Controller(){
        x=0;
        y=0;
        x1=0;
        y1=0;
    }

    int flag =0;

    /**End of Button Clicks*/

    @FXML
    public void initialize(){
        gcBrushTool = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            if(lineSelected){
                x=e.getX();
                y=e.getY();
                x1=e.getX();
                y1=e.getY();
                reDraw(gcBrushTool);
            }
        });
        canvas.setOnMouseReleased(e -> {
            if (lineSelected) {
                x1=e.getX();
                y1=e.getY();
                reDraw(gcBrushTool);
            }
        });
        canvas.setOnMouseDragged(e -> {
            if (lineSelected) {
                pane.getChildren().removeAll(line);
                x1=e.getX();
                y1=e.getY();
                flag=1;
                reDraw(gcBrushTool);
            }
            double size = 0;
            try {
                size = Double.parseDouble(String.valueOf(sliderSize.getValue()).trim());
            } catch (NumberFormatException e1) {
                Lang.showDiag(Alert.AlertType.ERROR, "Error", "Size is not a number", "Enter a valid size!");
            }
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            if(toolSelected && !checkBoxEraser.isSelected())
            {
                gcBrushTool.setFill(colorPicker.getValue());
                gcBrushTool.fillRoundRect(x, y, size, size, size, size);
            }
            else if(checkBoxEraser.isSelected())
            {
                gcBrushTool.setFill(Color.WHITE);
                gcBrushTool.fillRoundRect(x, y, size, size, size, size);
            }
        });
    }

    private void reDraw(GraphicsContext gcBrushTool) {
        //gcBrushTool.clearRect(x1,y1,canvas.getWidth(),canvas.getHeight());
        /*gcBrushTool.beginPath();
        gcBrushTool.moveTo(x,y);
        gcBrushTool.lineTo(x1,y1);
        gcBrushTool.stroke();*/
        line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x1);
        line.setEndY(y1);
        pane.getChildren().add(line);
       // gcBrushTool.strokeLine(x,y,x1,y1);

        //flag=0;
    }

}
