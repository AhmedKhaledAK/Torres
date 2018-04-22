package sample.controller;

import javafx.scene.canvas.Canvas;
import sample.model.Shape;

public interface DrawingEngine {
    void refresh(Canvas canvas);
    void addShape(Shape shape);
    void removeShape(Shape shape);
    void updateShape(Shape oldShape, Shape newShape);
    Shape[] getShapes();
    void undo();
    void redo();
    void save(String path);
    void load(String path);
}
