package sample.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import sample.model.Shape;

import java.util.ArrayList;

public interface DrawingEngine {
    void refresh(Object pane);
    void addShape(Shape shape);
    void removeShape(MouseEvent event);
    void updateShape(Shape oldShape, Shape newShape);
    ArrayList<Shape> getShapes();
    void undo();
    void redo();
    void save(String path);
    void load(String path);
}
