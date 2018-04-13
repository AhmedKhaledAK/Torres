package sample.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Map;

public abstract class AbstractShape implements Shape {
    Point2D position;
    Map<String, Double> properties;
    Color color;
    Color fillColor;
    double width;
}
