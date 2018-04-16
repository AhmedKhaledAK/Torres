package sample.model;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Map;

public class Rectangle extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Rectangle rectangle;

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
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
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
        this.width = width;
    }

    @Override
    public double getStrokeWidth() {
        return this.width;
    }

    @Override
    public void draw(Object pane) {

        Pane p = (Pane) pane;
        rectangle = new javafx.scene.shape.Rectangle();

        if(getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY())
        {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getEndPoint().getX() - getStartPoint().getX());
            rectangle.setX(getStartPoint().getX());
            rectangle.setY(getStartPoint().getY());
        }

        else if(getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getStartPoint().getX() - getEndPoint().getX());
            rectangle.setX(getEndPoint().getX());
            rectangle.setY(getStartPoint().getY());
        }
        else if(getEndPoint().getX() > getStartPoint().getX() && this.getEndPoint().getY() < this.getStartPoint().getY()) {
            rectangle.setHeight(getStartPoint().getY() - this.getEndPoint().getY());
            rectangle.setWidth(this.getEndPoint().getX() - this.getStartPoint().getX());
            rectangle.setX(this.getStartPoint().getX());
            this.rectangle.setY(this.getEndPoint().getY());
        }
        else if(this.getEndPoint().getX() < this.getStartPoint().getX() && this.getEndPoint().getY() < this.getStartPoint().getY()) {
            rectangle.setHeight(this.getStartPoint().getY() - this.getEndPoint().getY());
            rectangle.setWidth(this.getStartPoint().getX() - this.getEndPoint().getX());
            rectangle.setX(this.getEndPoint().getX());
            rectangle.setY(this.getEndPoint().getY());
        }

        rectangle.setStroke(getColor());
        rectangle.setStrokeWidth(getStrokeWidth());
        rectangle.setFill(getFillColor());

        p.getChildren().add(rectangle);

    }

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(rectangle);

    }
}
