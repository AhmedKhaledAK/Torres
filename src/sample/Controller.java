package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.model.Shape;
import sample.model.ShapeFactory;

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

    Line line=null;
    Circle circle = null;
    Circle brushStroke = null;
    Rectangle rectangle = null;
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
            pane.getChildren().remove(rectangle);
            drawRectangle(x, y, x1, y1);
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
            drawRectangle(x, y, x1, y1);
        else if(checkBoxEraser.isSelected())
            drawBrushStroke(x1, y1);
    }

    public void drawLine(double startX, double startY, double endX, double endY, Shape shape){
        ((sample.model.Line)shape).setStartPoint(new Point2D(startX,startY));
        ((sample.model.Line)shape).setEndPoint(new Point2D(endX,endY));
        drawShape(shape);
       /* this.line = new Line();

        this.line.setStroke(colorPicker.getValue());
        this.line.setStrokeWidth(sliderSize.getValue());
        this.line.setStartX(startX);
        this.line.setStartY(startY);
        this.line.setEndX(endX);
        this.line.setEndY(endY);
        pane.getChildren().add(this.line);*/
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

    public void drawRectangle(double startX, double startY, double endX, double endY)
    {
        this.rectangle = new Rectangle();

        if(endX > startX && endY > startY)
        {
            this.rectangle.setHeight((endY-startY));
            this.rectangle.setWidth((endX-startX));
            this.rectangle.setX(startX);
            this.rectangle.setY(startY);
        }
        else if(endX < startX && endY > startY) {
            this.rectangle.setHeight((endY-startY));
            this.rectangle.setWidth((startX-endX));
            this.rectangle.setX(endX);
            this.rectangle.setY(startY);
        }
        else if(endX > startX && endY < startY) {
            this.rectangle.setHeight(startY-endY);
            this.rectangle.setWidth(endX-startX);
            this.rectangle.setX(startX);
            this.rectangle.setY(endY);
        }
        else if(endX < startX && endY < startY) {
            this.rectangle.setHeight(startY-endY);
            this.rectangle.setWidth(startX-endX);
            this.rectangle.setX(endX);
            this.rectangle.setY(endY);
        }

        this.rectangle.setStroke(colorPicker.getValue());
        this.rectangle.setStrokeWidth(sliderSize.getValue());
        this.rectangle.setFill(Color.TRANSPARENT);

        pane.getChildren().add(rectangle);
    }

    private void drawShape(Shape shape) {
        shape.setFillColor(Color.WHITE);
        shape.setColor(colorPicker.getValue());
        shape.setStrokeWidth(sliderSize.getValue());
        shape.draw(pane);
    }

}
