package sample.model;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    void setStrokeWidth(double width);
    double getStrokeWidth();
    void draw(Object pane);
    void move(MouseEvent e);
    void moveDrag(MouseEvent e);
    void removeDeprecated(Pane pane);
}
