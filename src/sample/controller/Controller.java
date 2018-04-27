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
import sample.Main;
import sample.model.command.RemoveShapeCommand;
import sample.files.FileClass;
import sample.lang.Lang;
import sample.model.Shape;
import sample.model.ShapeFactory;
import sample.model.command.Command;
import sample.model.command.CommandControl;
import sample.model.command.DrawShapeCommand;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Controller implements DrawingEngine {

    @FXML
    public Button btnBrush, btnEraser, btnEllipse, btnTriangle, btnLine,
            btnRect, btnCircle, btnSquare, btnSave, btnLoad, btnMove, btnUndo, btnRedo, btnResize, btnDelete;

    @FXML
    public Label lblCoordinates;

    @FXML
    private ColorPicker colorPicker, backgroundColorPicker, fillColorPicker;

    @FXML
    private Slider sliderSize;

    @FXML
    Pane pane, buttonPane;


    public Pane getPane() {
        return pane;
    }

    private boolean brushSelected = false;
    private boolean eraserSelected = false;
    private boolean lineSelected = false;
    private boolean circleSelected = false;
    private boolean rectangleSelected = false;
    private boolean squareSelected = false;
    private boolean ellipseSelected = false;
    private boolean triangleSelected = false;
    public static boolean deleteSelected = false;
    public static boolean resizeSelected = false;
    public static boolean moveSelected = false;
    public static boolean undoSelected = false;

    private MouseEvent currentMouseEvent;


    public static ArrayList<Shape> shapesList = new ArrayList<>();

    Circle brushStroke = null;
    Shape shape = null;
    ShapeFactory shapeFactory = new ShapeFactory();
    Command command = null;
    private double x = 0, y = 0;


    /**
     * Button Clicks
     */

    public void onBtnBrushClick(ActionEvent actionEvent) {
        brushSelected ^= true;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;
    }

    public void onBtnLineClick(ActionEvent actionEvent) {
        lineSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnCircleClick(ActionEvent actionEvent) {
        circleSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnRectClick(ActionEvent actionEvent) {
        rectangleSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnSquareClick(ActionEvent actionEvent) {
        squareSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnEllipseClick(ActionEvent actionEvent) {
        ellipseSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);

    }

    public void onBtnSaveClick(ActionEvent actionEvent) {
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        FileClass fileClass = new FileClass();
        fileClass.save(shapesList);
    }

    public void onBtnLoadClick(ActionEvent actionEvent) {
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        FileClass fileClass = new FileClass();
        shapesList = fileClass.load();
        pane.getChildren().clear();
        pane.getChildren().add(buttonPane);
        for (Shape aShapesList : shapesList) aShapesList.draw(pane);
    }

    public void onBtnDeleteClick(ActionEvent actionEvent) {
        deleteSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;

        command = new RemoveShapeCommand(shape, this);
    }

    public void onBtnResizeClick(ActionEvent event) {
        resizeSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        moveSelected = false;
        undoSelected = false;

        if (toolSelected())
            Main.scene.setCursor(Cursor.CROSSHAIR);
    }

    public void onBtnEraserClick(ActionEvent actionEvent) {
        eraserSelected ^= true;
        brushSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        deleteSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;
    }

    public void onBtnTriangleClick(ActionEvent actionEvent) {
        triangleSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        deleteSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        resizeSelected = false;
        moveSelected = false;
        undoSelected = false;
    }

    public void onBtnMoveClick(ActionEvent actionEvent) {
        moveSelected ^= true;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        resizeSelected = false;
        deleteSelected = false;
        undoSelected = false;
    }

    public void onBtnUndoClick(ActionEvent event) {
        undoSelected = true;
        deleteSelected = false;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        resizeSelected = false;
        moveSelected = false;

        CommandControl commandControl = new CommandControl(command);
        commandControl.pressUndo();
    }

    public void onBtnRedoClick(ActionEvent mouseEvent) {
        deleteSelected = false;
        brushSelected = false;
        eraserSelected = false;
        lineSelected = false;
        rectangleSelected = false;
        squareSelected = false;
        triangleSelected = false;
        circleSelected = false;
        ellipseSelected = false;
        resizeSelected = false;
        moveSelected = false;

        CommandControl commandControl = new CommandControl(command);
        commandControl.pressRedo();
    }

    /*End of Button Clicks*/

    /**
     * Pane Actions
     */
    public void onMouseClicked(MouseEvent mouseEvent) {
        setCurrentMouseEvent(mouseEvent);

        if (deleteSelected) {
            command.execute();
        }
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        setCurrentMouseEvent(mouseEvent);

        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
        if (lineSelected) {
            shape = shapeFactory.createShape("line");
            command = new DrawShapeCommand(shape, this);
        } else if (rectangleSelected) {
            shape = shapeFactory.createShape("rectangle");
            command = new DrawShapeCommand(shape, this);
        } else if (circleSelected) {
            shape = shapeFactory.createShape("circle");
            command = new DrawShapeCommand(shape, this);
        } else if (squareSelected) {
            shape = shapeFactory.createShape("square");
            command = new DrawShapeCommand(shape, this);
        } else if (ellipseSelected) {
            shape = shapeFactory.createShape("ellipse");
            command = new DrawShapeCommand(shape, this);
        } else if (triangleSelected) {
            shape = shapeFactory.createShape("triangle");
            command = new DrawShapeCommand(shape, this);
        }
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        setCurrentMouseEvent(mouseEvent);

        double x1 = mouseEvent.getSceneX();
        double y1 = mouseEvent.getSceneY();
        if (toolSelected()) {
            shape.removeDeprecated(pane);
            drawShape(shape, x, y, x1, y1);
            if (brushSelected) {
                drawBrushStroke(x1, y1);
            }
            else if (eraserSelected) {
                drawBrushStroke(x1, y1);
            }
        }
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        setCurrentMouseEvent(mouseEvent);

        double x1 = mouseEvent.getSceneX();
        double y1 = mouseEvent.getSceneY();

        if (deleteSelected) {
            System.out.println("nothing");
        } else if (toolSelected()) {
            shape.removeDeprecated(pane);
            drawShape(shape, x, y, x1, y1);
            addShape(shape);
            if (brushSelected) {
                drawBrushStroke(x1, y1);
            }
            else if (eraserSelected) {
                drawBrushStroke(x1, y1);
            }
        }
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        DecimalFormat df = new DecimalFormat("#.##");
        lblCoordinates.setText("X: " + df.format(mouseEvent.getSceneX()) + ", Y: " + df.format(mouseEvent.getSceneY()));
        Color c = backgroundColorPicker.getValue();
        pane.setStyle("-fx-background-color: " + toRGBCode(c));
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
        } else if (e.getCode() == KeyCode.S) {
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
     * Helper Methods
     */
    private void drawBrushStroke(double currentX, double currentY) {
        this.brushStroke = new Circle();

        if (brushSelected)
            this.brushStroke.setFill(colorPicker.getValue());
        else if (eraserSelected)
            this.brushStroke.setFill(Color.WHITE);

        this.brushStroke.setRadius(sliderSize.getValue() / 2);
        this.brushStroke.setCenterX(currentX);
        this.brushStroke.setCenterY(currentY);
        pane.getChildren().add(brushStroke);
    }

    private void drawShape(Shape shape, double startX, double startY, double endX, double endY) {
        shape.setStartPoint(new Point2D(startX, startY));
        shape.setEndPoint(new Point2D(endX, endY));
        shape.setFillColor(Color.TRANSPARENT);
        shape.setColor(colorPicker.getValue());
        shape.setStrokeWidth(sliderSize.getValue());
        shape.draw(pane);
    }

    private boolean toolSelected() {
        return (lineSelected || rectangleSelected || circleSelected || squareSelected || ellipseSelected || triangleSelected);
    }

    public MouseEvent getCurrentMouseEvent() {
        return currentMouseEvent;
    }

    public void setCurrentMouseEvent(MouseEvent currentMouseEvent) {
        this.currentMouseEvent = currentMouseEvent;
    }

    private static String toRGBCode(Color color)
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    /*End of Helper Methods*/

    @FXML
    public void initialize() {
        btnBrush.setCursor(Cursor.HAND);
        btnEraser.setCursor(Cursor.HAND);

        btnLine.setCursor(Cursor.HAND);
        btnCircle.setCursor(Cursor.HAND);
        btnSquare.setCursor(Cursor.HAND);
        btnRect.setCursor(Cursor.HAND);
        btnEllipse.setCursor(Cursor.HAND);
        btnTriangle.setCursor(Cursor.HAND);

        btnResize.setCursor(Cursor.HAND);
        btnMove.setCursor(Cursor.HAND);
        btnDelete.setCursor(Cursor.HAND);

        btnLoad.setCursor(Cursor.HAND);
        btnSave.setCursor(Cursor.HAND);
        btnUndo.setCursor(Cursor.HAND);
        btnRedo.setCursor(Cursor.HAND);

        backgroundColorPicker.setCursor(Cursor.HAND);
        colorPicker.setCursor(Cursor.HAND);
        sliderSize.setCursor(Cursor.HAND);

        Color c = backgroundColorPicker.getValue();
        pane.setStyle("-fx-background-color: " + toRGBCode(c));
        buttonPane.setStyle("-fx-background-color: #26C6DA");
    }

    @Override
    public void refresh(Object pane) {

    }

    @Override
    public void addShape(Shape shape) {
        shapesList.add(shape);
    }

    @Override
    public void removeShape(MouseEvent event) {
        shape.removeShape(event);
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