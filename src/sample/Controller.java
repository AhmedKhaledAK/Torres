package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.files.FileClass;
import sample.model.Shape;
import sample.model.ShapeFactory;

import java.util.ArrayList;

public class Controller {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CheckBox checkBoxEraser;

    @FXML
    private Slider sliderSize;

    @FXML
    Pane pane;

    private boolean brushSelected = false;
    private boolean lineSelected = false;
    private boolean circleSelected = false;
    private boolean rectangleSelected = false;

    ArrayList<Shape> shapesList = new ArrayList<>();

    Circle circle = null;
    Circle brushStroke = null;
    Shape shape = null;
    ShapeFactory shapeFactory = new ShapeFactory();
    private double x=0,y=0;

    @FXML
    public void onBtnBrushClick(ActionEvent actionEvent) {
        brushSelected ^= true;
    }

    @FXML
    public void onBtnLineClick(ActionEvent actionEvent) {
        lineSelected ^= true;
        shape=shapeFactory.createShape("line");
    }

    @FXML
    public void onBtnCircleClick(ActionEvent actionEvent) {
        circleSelected ^= true;
    }

    @FXML
    public void onBtnRectClick(ActionEvent actionEvent) {
        rectangleSelected ^= true;
        shape = shapeFactory.createShape("rect");
    }

    public void onBtnSaveClick(ActionEvent actionEvent) {
        FileClass fileClass = new FileClass("save");
        fileClass.save(shapesList);
    }

    public void onBtnLoadClick(ActionEvent actionEvent) {
    }



    public void onMouseDragged(MouseEvent mouseEvent) {
        double x1=mouseEvent.getSceneX();
        double y1=mouseEvent.getSceneY();

        if (lineSelected) {
            shape.removeDeprecated(pane);
            drawLine(x,y,x1,y1,shape);
        }else if(circleSelected) {
            pane.getChildren().remove(circle);
            drawCircle(x, y, x1, y1);
        }else if(rectangleSelected) {
            shape.removeDeprecated(pane);
            drawRectangle(x, y, x1, y1, shape);
        }else if(brushSelected)
            drawBrushStroke(x1, y1);
        else if(checkBoxEraser.isSelected())
            drawBrushStroke(x1, y1);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        double x1 = mouseEvent.getSceneX();
        double y1 = mouseEvent.getSceneY();

        if(lineSelected)
            drawLine(x,y,x1,y1, shape);
        else if(circleSelected)
            drawCircle(x, y, x1, y1);
        else if(brushSelected)
            drawBrushStroke(x1, y1);
        else if(rectangleSelected)
            drawRectangle(x, y, x1, y1, shape);
        else if(checkBoxEraser.isSelected())
            drawBrushStroke(x1, y1);

        shapesList.add(shape);
    }

    public void drawLine(double startX, double startY, double endX, double endY, Shape shape){
        ((sample.model.Line)shape).setStartPoint(new Point2D(startX,startY));
        ((sample.model.Line)shape).setEndPoint(new Point2D(endX,endY));
        drawShape(shape);
    }

    public void drawCircle(double startX, double startY, double endX, double endY) {
        this.circle = new Circle();

        double diameter = Math.sqrt(Math.pow(endY-startY, 2) + Math.pow(endX-startX,2));
        double centerY = (endY + startY) / 2;
        double centerX = (endX + startX) / 2;

        this.circle.setCenterX(centerX);
        this.circle.setCenterY(centerY);
        this.circle.setStroke(colorPicker.getValue());
        this.circle.setStrokeWidth(sliderSize.getValue());
        this.circle.setFill(Color.TRANSPARENT);
        this.circle.setRadius(diameter/2);
        pane.getChildren().add(circle);
    }

    public void drawBrushStroke(double currentX, double currentY)
    {
        this.brushStroke = new Circle();

        if(brushSelected)
            this.brushStroke.setFill(colorPicker.getValue());
        else if(checkBoxEraser.isSelected())
            this.brushStroke.setFill(Color.WHITE);

        this.brushStroke.setRadius(sliderSize.getValue()/2);
        this.brushStroke.setCenterX(currentX);
        this.brushStroke.setCenterY(currentY);
        pane.getChildren().add(brushStroke);
    }

    public void drawRectangle(double startX, double startY, double endX, double endY, Shape shape)
    {
        ((sample.model.Rectangle)shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Rectangle)shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }

    private void drawShape(Shape shape) {
        shape.setFillColor(Color.TRANSPARENT);
        shape.setColor(colorPicker.getValue());
        shape.setStrokeWidth(sliderSize.getValue());
        shape.draw(pane);
    }

}
