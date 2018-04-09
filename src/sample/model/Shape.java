package sample.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface Shape {
    void setPosition(Point2D position);
    Point2D getPosition();
    void setProperties(Map<String, Double> properties);
    Map<String, Double> getProperties();
    void setColor(Color color);
    Color getColor();
    void setFillColor(Color color);
    Color getFillColor();
    void draw(GraphicsContext gc);
}
