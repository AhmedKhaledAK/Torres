package sample.model;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Map;

public class Line extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Line line;

    public Line(){
        this.name ="line";
    }

    @Override
    public void setPosition(Point2D position) {
         this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return this.properties;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return this.fillColor;
    }

    @Override
    public void setStrokeWidth(double width) {
        this.width=width;
    }

    @Override
    public double getStrokeWidth() {
        return this.width;
    }

    @Override
    public void draw(Object pane) {
        Pane p = (Pane) pane;
        line = new javafx.scene.shape.Line();
        line.setStartX(getStartPoint().getX());
        line.setStartY(getStartPoint().getY());
        line.setEndX(getEndPoint().getX());
        line.setEndY(getEndPoint().getY());
        line.setStroke(getColor());
        line.setFill(getFillColor());
        line.setStrokeWidth(getStrokeWidth());
        p.getChildren().add(line);
    }

    @Override
    public void removeDeprecated(Pane pane){
        pane.getChildren().remove(line);
    }
}
