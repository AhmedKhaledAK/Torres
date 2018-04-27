package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import sample.controller.Controller;
import sample.lang.Lang;

import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Triangle extends AbstractShape {
    private Polygon triangle;
    Pane p;
    private Point2D startPoint;
    private Point2D endPoint;
    private double orgSceneX, orgSceneY, originalX, originalY;
    private double orgTranslateX, orgTranslateY;
    double startX = 0;
    double startY = 0;
    double endX = 0;
    double endY = 0;

    public Triangle() {
        this.name = "triangle";
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
        p = (Pane) pane;
        triangle = new Polygon();
        double x3 = sqrt(pow(getStartPoint().getX(), 2) + pow(getEndPoint().getX(), 2));
        double y3 = getEndPoint().getY();
        triangle.getPoints().addAll(getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY(), x3, y3);
        triangle.setStroke(getColor());
        triangle.setFill(getFillColor());
        triangle.setStrokeWidth(getStrokeWidth());
        triangle.setOnMouseClicked(triangleOnMouseClickedEventHandler);
        triangle.setOnMousePressed(triangleOnMousePressedEventHandler);
        triangle.setOnMouseDragged(triangleOnMouseDraggedEventHandler);
        triangle.setOnMouseReleased(triangleOnMouseReleasedEventHandler);
        p.getChildren().add(triangle);
    }

    @Override
    public void movePress(MouseEvent e) {

        orgSceneX = e.getSceneX();
        orgSceneY = e.getSceneY();
        orgTranslateX = ((javafx.scene.shape.Polygon) (e.getTarget())).getTranslateX();
        orgTranslateY = ((javafx.scene.shape.Polygon) (e.getTarget())).getTranslateY();

        startPoint = new Point2D(triangle.getBoundsInParent().getMinX(),
                triangle.getBoundsInParent().getMaxY());

        endPoint = new Point2D(triangle.getBoundsInParent().getMaxX(),
                triangle.getBoundsInParent().getMinY());

    }

    @Override
    public void moveDrag(MouseEvent e) {

        double offsetX = e.getSceneX() - orgSceneX;
        double offsetY = e.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((javafx.scene.shape.Polygon) (e.getTarget())).setTranslateX(newTranslateX);
        ((javafx.scene.shape.Polygon) (e.getTarget())).setTranslateY(newTranslateY);

        startX = triangle.getBoundsInParent().getMaxX() + getStrokeWidth() / 2;
        startY = triangle.getBoundsInParent().getMaxY() - getStrokeWidth() / 2;
        endX = triangle.getBoundsInParent().getMinX() - getStrokeWidth() / 2;
        endY = triangle.getBoundsInParent().getMinY()+ getStrokeWidth() / 2;
    }

    @Override
    public void moveRelease() {
        int index = searchForTriangle();
        Controller.shapesList.get(index).setStartPoint(new Point2D(startX, startY));
        Controller.shapesList.get(index).setEndPoint(new Point2D(endX, endY));
    }

    @Override
    public void removeShape(MouseEvent e) {
        int index = searchForTriangle();
        System.out.println("index is " + index);

        if (Controller.shapesList.size() == 0)
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error", "Cannot Delete",
                    "There is nothing to delete");
        else {
            p.getChildren().remove(triangle);
            Controller.shapesList.remove(index);

        }
    }


    private EventHandler<MouseEvent> triangleOnMouseClickedEventHandler =
            e -> {
                triangle = (javafx.scene.shape.Polygon) (e.getTarget());

                startY = triangle.getBoundsInParent().getMaxY() - getStrokeWidth() / 2;
                startX = triangle.getBoundsInParent().getMaxX() + getStrokeWidth() / 2;

                endX = triangle.getBoundsInParent().getMaxX() - getStrokeWidth() / 2;
                endY = triangle.getBoundsInParent().getMaxY() + getStrokeWidth() / 2;

                if (Controller.deleteSelected) {
                    removeShape(e);
                }
            };

    private EventHandler<MouseEvent> triangleOnMousePressedEventHandler =
            this::movePress;

    private EventHandler<MouseEvent> triangleOnMouseDraggedEventHandler =
            this::moveDrag;

    private EventHandler<MouseEvent> triangleOnMouseReleasedEventHandler =
            e -> moveRelease();


    private int searchForTriangle() {
        for (int i = 0; i < Controller.shapesList.size(); i++) {
            if (Controller.shapesList.get(i) instanceof Triangle) {
                if (Controller.shapesList.get(i).getStartPoint().getX() == startPoint.getX() &&
                        Controller.shapesList.get(i).getStartPoint().getY() == startPoint.getY() &&
                        Controller.shapesList.get(i).getEndPoint().getX() == endPoint.getX() &&
                        Controller.shapesList.get(i).getEndPoint().getY() == endPoint.getY()) {

                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(triangle);
    }
}
