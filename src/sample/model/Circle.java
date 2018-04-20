package sample.model;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Main;

import java.util.Map;

public class Circle extends AbstractShape {

    private double diameter;
    private Point2D centerPoint;
    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Circle circle;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point2D centerPoint) {
        this.centerPoint = centerPoint;
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
        this.circle = new javafx.scene.shape.Circle();
        double diameter = Math.sqrt(Math.pow(getEndPoint().getY()-getStartPoint().getY(), 2) +
                Math.pow(getEndPoint().getX()-getStartPoint().getX(),2));
        double centerY = (getEndPoint().getY() + getStartPoint().getY()) / 2;
        double centerX = (getEndPoint().getX() + getStartPoint().getX()) / 2;
        this.circle.setCenterX(centerX);
        this.circle.setCenterY(centerY);
        this.circle.setStroke(getColor());
        this.circle.setStrokeWidth(getStrokeWidth());
        this.circle.setFill(Color.TRANSPARENT);
        this.circle.setRadius(diameter/2);
        p.getChildren().add(circle);
    }

    @Override
    public void move(MouseEvent e) {
            circle.setOnMousePressed(circleOnMousePressedEventHandler);
    }

    @Override
    public void moveDrag(MouseEvent e){
        circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    }

    private EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateY();
                }
            };

    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    ((javafx.scene.shape.Circle)(e.getSource())).setTranslateX(newTranslateX);
                    ((javafx.scene.shape.Circle)(e.getSource())).setTranslateY(newTranslateY);
                }
            };

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(circle);
    }
}
