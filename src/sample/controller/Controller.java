package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.files.FileClass;
import sample.lang.Lang;
import sample.model.Shape;
import sample.model.ShapeFactory;
import sample.Main;

import java.util.ArrayList;

public class Controller implements DrawingEngine {

    @FXML
    public Button btnBrsuh, btnLine, btnRect, btnCircle, btnSquare, btnSave, btnLoad;
    @FXML
    public Label lblCoordinates;
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
    private boolean squareSelected = false;
    private boolean ellipseSelected = false;


    public static ArrayList<Shape> shapesList = new ArrayList<>();

    Circle brushStroke = null;
    Shape shape = null;
    ShapeFactory shapeFactory = new ShapeFactory();
    private double x = 0, y = 0;

    /**
     * Button Clicks
     */

    public void onBtnBrushClick(ActionEvent actionEvent) {
        brushSelected ^= true;
    }

    public void onBtnLineClick(ActionEvent actionEvent) {
        lineSelected ^= true;
        Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnCircleClick(ActionEvent actionEvent) {
        circleSelected ^= true;
        Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnRectClick(ActionEvent actionEvent) {
        rectangleSelected ^= true;
        Main.scene.setCursor(Cursor.CROSSHAIR);
    }
    public void onBtnSquareClick(ActionEvent actionEvent) {
        squareSelected ^= true;
        Main.scene.setCursor(Cursor.CROSSHAIR);
    }
    public void onBtnEllipseClick(ActionEvent actionEvent) {
        ellipseSelected ^= true;
        Main.scene.setCursor(Cursor.CROSSHAIR);

    }

    public void onBtnSaveClick(ActionEvent actionEvent) {
        FileClass fileClass = new FileClass();
        fileClass.save(shapesList);
    }

    public void onBtnLoadClick(ActionEvent actionEvent) {
        FileClass fileClass = new FileClass();
        shapesList = fileClass.load();

        for (Shape aShapesList : shapesList) aShapesList.draw(pane);
    }

    /*End of Button Clicks*/

    /**
     * Pane Actions
     */

    public void onMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
        if (mouseEvent.isControlDown()) {
            shape.move(mouseEvent);
        } else {
            if (lineSelected)
                shape = shapeFactory.createShape("line");
            else if (rectangleSelected)
                shape = shapeFactory.createShape("rectangle");
            else if (circleSelected)
                shape = shapeFactory.createShape("circle");
            else if (squareSelected)
                shape = shapeFactory.createShape("square");
            else if(ellipseSelected)
                shape = shapeFactory.createShape("ellipse");
        }
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        double x1 = mouseEvent.getSceneX();
        double y1 = mouseEvent.getSceneY();

        if (mouseEvent.isControlDown()) {
            shape.moveDrag(mouseEvent);

        } else {
            if (lineSelected) {
                shape.removeDeprecated(pane);
                drawLine(x, y, x1, y1, shape);
            } else if (rectangleSelected) {
                shape.removeDeprecated(pane);
                drawRectangle(x, y, x1, y1, shape);
            } else if (circleSelected) {
                shape.removeDeprecated(pane);
                drawCircle(x, y, x1, y1);
            } else if(squareSelected) {
                shape.removeDeprecated(pane);
                drawSquare(x, y, x1, y1);
            } else if(ellipseSelected) {
                shape.removeDeprecated(pane);
                drawEllipse(x, y, x1, y1);
            }else if (brushSelected)
                drawBrushStroke(x1, y1);
            else if (checkBoxEraser.isSelected())
                drawBrushStroke(x1, y1);
        }
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        double x1 = mouseEvent.getSceneX();
        double y1 = mouseEvent.getSceneY();

        if (!mouseEvent.isControlDown()) {
            if (lineSelected) {
                shape.removeDeprecated(pane);
                drawLine(x, y, x1, y1, shape);
            } else if (brushSelected)
                drawBrushStroke(x1, y1);
            else if (rectangleSelected) {
                shape.removeDeprecated(pane);
                drawRectangle(x, y, x1, y1, shape);
            } else if (circleSelected) {
                shape.removeDeprecated(pane);
                drawCircle(x, y, x1, y1);
            } else if(squareSelected) {
                shape.removeDeprecated(pane);
                drawSquare(x, y, x1, y1);
            } else if(ellipseSelected){
                shape.removeDeprecated(pane);
                drawEllipse(x,y,x1,y1);
            }else if (checkBoxEraser.isSelected())
                drawBrushStroke(x1, y1);

            addShape(shape);
        }
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        lblCoordinates.setText("X: " + mouseEvent.getSceneX() + ", Y: " + mouseEvent.getSceneY());
    }

    public void onKeyPressed(KeyEvent e) {

        String type = "";
        if (e.getCode() == KeyCode.L) {
            type = "Line";
            lineSelected = true;
            rectangleSelected = false;
            circleSelected = false;
            squareSelected = false;
            Lang.showDiag(Alert.AlertType.INFORMATION, "Info", type, type + " is selected, you can draw it now.");
            Main.scene.setCursor(Cursor.CROSSHAIR);
        } else if (e.getCode() == KeyCode.C) {
            type = "Circle";
            circleSelected = true;
            rectangleSelected = false;
            lineSelected = false;
            squareSelected = false;
            Lang.showDiag(Alert.AlertType.INFORMATION, "Info", type, type + " is selected, you can draw it now.");
            Main.scene.setCursor(Cursor.CROSSHAIR);
        } else if (e.getCode() == KeyCode.R) {
            type = "Rectangle";
            circleSelected = false;
            rectangleSelected = true;
            lineSelected = false;
            squareSelected = false;
            Lang.showDiag(Alert.AlertType.INFORMATION, "Info", type, type + " is selected, you can draw it now.");
            Main.scene.setCursor(Cursor.CROSSHAIR);
        }else if (e.getCode() == KeyCode.S) {
            type = "Square";
            squareSelected = true;
            circleSelected = false;
            rectangleSelected = false;
            lineSelected = false;
            squareSelected = false;
            Lang.showDiag(Alert.AlertType.INFORMATION, "Info", type, type + " is selected, you can draw it now.");
            Main.scene.setCursor(Cursor.CROSSHAIR);
        }

    }
    /*End of Pane Actions*/

    /**
     * Helper Method
     */
    public void drawLine(double startX, double startY, double endX, double endY, Shape shape) {
        ((sample.model.Line) shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Line) shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }

    public void drawCircle(double startX, double startY, double endX, double endY) {
        ((sample.model.Circle) shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Circle) shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }

    public void drawSquare(double startX, double startY, double endX, double endY) {
        ((sample.model.Square) shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Square) shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }

    private void drawEllipse(double startX, double startY, double endX, double endY) {
        ((sample.model.Ellipse) shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Ellipse) shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }


    public void drawBrushStroke(double currentX, double currentY) {
        this.brushStroke = new Circle();

        if (brushSelected)
            this.brushStroke.setFill(colorPicker.getValue());
        else if (checkBoxEraser.isSelected())
            this.brushStroke.setFill(Color.WHITE);

        this.brushStroke.setRadius(sliderSize.getValue() / 2);
        this.brushStroke.setCenterX(currentX);
        this.brushStroke.setCenterY(currentY);
        pane.getChildren().add(brushStroke);
    }

    public void drawRectangle(double startX, double startY, double endX, double endY, Shape shape) {
        ((sample.model.Rectangle) shape).setStartPoint(new Point2D(startX, startY));
        ((sample.model.Rectangle) shape).setEndPoint(new Point2D(endX, endY));
        drawShape(shape);
    }

    private void drawShape(Shape shape) {
        shape.setFillColor(Color.TRANSPARENT);
        shape.setColor(colorPicker.getValue());
        shape.setStrokeWidth(sliderSize.getValue());
        shape.draw(pane);
    }

    /*End of Helper Methods*/

    @FXML
    public void initialize() {
        btnBrsuh.setCursor(Cursor.HAND);
        btnLine.setCursor(Cursor.HAND);
        btnCircle.setCursor(Cursor.HAND);
        btnSquare.setCursor(Cursor.HAND);
        btnLoad.setCursor(Cursor.HAND);
        btnRect.setCursor(Cursor.HAND);
        btnSave.setCursor(Cursor.HAND);
        colorPicker.setCursor(Cursor.HAND);
        sliderSize.setCursor(Cursor.HAND);
        checkBoxEraser.setCursor(Cursor.HAND);
    }

    @Override
    public void refresh(Object pane) {

    }

    @Override
    public void addShape(Shape shape) {
        shapesList.add(shape);
    }

    @Override
    public void removeShape(Shape shape) {
        shapesList.remove(shape);
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {

    }

    @Override
    public ArrayList<Shape> getShapes() {
        return shapesList;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }

}
