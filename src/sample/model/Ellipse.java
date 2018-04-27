package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.controller.Controller;
import sample.lang.Lang;

import java.util.Map;

import static java.lang.Math.abs;

public class Ellipse extends AbstractShape {

    private javafx.scene.shape.Ellipse ellipse;
    private Point2D radius;
    private Point2D centerPoint;
    private Point2D startPoint;
    private Point2D endPoint;
    private double originalX, originalY, deltaX;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    Pane p;

    public Ellipse() {
        this.name = "ellipse";
    }

    public Point2D getRadius() {
        return radius;
    }

    public void setRadius(Point2D radius) {
        this.radius = radius;
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
        p = (Pane) pane;
        ellipse = new javafx.scene.shape.Ellipse();
        double centerY = (abs(getEndPoint().getY()) + abs(getStartPoint().getY())) / 2;
        double centerX = (abs(getEndPoint().getX()) + abs(getStartPoint().getX())) / 2;
        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        setCenterPoint(new Point2D(centerX, centerY));
        if (getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            ellipse.setRadiusY((getEndPoint().getY() - getStartPoint().getY()) / 2);
            ellipse.setRadiusX((getEndPoint().getX() - getStartPoint().getX()) / 2);
        } else if (getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() < getStartPoint().getY()) {
            ellipse.setRadiusX((getStartPoint().getX() - getEndPoint().getX()) / 2);
            ellipse.setRadiusY((getStartPoint().getY() - getEndPoint().getY()) / 2);
        } else if (getStartPoint().getX() > getEndPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            ellipse.setRadiusX((getStartPoint().getX() - getEndPoint().getX()) / 2);
            ellipse.setRadiusY((getEndPoint().getY() - getStartPoint().getY()) / 2);
        } else if (getEndPoint().getX() > getStartPoint().getX() && getStartPoint().getY() > getEndPoint().getY()) {
            ellipse.setRadiusX((getEndPoint().getX() - getStartPoint().getX()) / 2);
            ellipse.setRadiusY((getStartPoint().getY() - getEndPoint().getY()) / 2);
        }
        setRadius(new Point2D(ellipse.getRadiusX(), ellipse.getRadiusY()));
        ellipse.setStroke(getColor());
        ellipse.setFill(getFillColor());
        ellipse.setStrokeWidth(getStrokeWidth());
        ellipse.setOnMousePressed(ellipseOnMousePressedEventHandler);
        ellipse.setOnMouseDragged(ellipseOnMouseDraggedEventHandler);
        ellipse.setOnMouseReleased(ellipseOnMouseReleasedEventHandler);
        ellipse.setOnMouseClicked(ellipseOnMouseClickedEventHandler);
        p.getChildren().add(ellipse);
    }

    @Override
    public void movePress(MouseEvent e) {

    }

    @Override
    public void moveDrag(MouseEvent e) {

    }

    @Override
    public void moveRelease() {

    }

    @Override
    public void removeShape(MouseEvent e) {
        int index = searchForEllipse();
        System.out.println("index is " + index);

        if (Controller.shapesList.size() == 0)
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error", "Cannot Delete",
                    "There is nothing to delete");
        else {
            p.getChildren().remove(ellipse);
            Controller.shapesList.remove(index);

        }
    }

    private double centerX = 0;
    private double centerY = 0;


    private EventHandler<MouseEvent> ellipseOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    ellipse = (javafx.scene.shape.Ellipse) (e.getTarget());

                    centerX = (ellipse.getBoundsInParent().getMaxX() + ellipse.getBoundsInParent().getMinX()) / 2;
                    centerY = (ellipse.getBoundsInParent().getMaxY() + ellipse.getBoundsInParent().getMinY()) / 2;
                    if (Controller.deleteSelected) {
                        removeShape(e);
                    }
                }
            };


    private EventHandler<MouseEvent> ellipseOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    if (Controller.resizeSelected) {
                        originalX = ellipse.getCenterX() + ellipse.getRadiusX();
                        originalY = ellipse.getCenterY();
                    } else {
                        orgSceneX = e.getSceneX();
                        orgSceneY = e.getSceneY();
                        orgTranslateX = ((javafx.scene.shape.Ellipse) (e.getSource())).getTranslateX();
                        orgTranslateY = ((javafx.scene.shape.Ellipse) (e.getSource())).getTranslateY();
                        centerX = (ellipse.getBoundsInParent().getMaxX() + ellipse.getBoundsInParent().getMinX()) / 2;
                        centerY = (ellipse.getBoundsInParent().getMaxY() + ellipse.getBoundsInParent().getMinY()) / 2;
                        System.out.println("x is " + centerX);
                        System.out.println("y is " + centerY);
                    }
                }
            };

    private EventHandler<MouseEvent> ellipseOnMouseDraggedEventHandler =
            e -> {

                if (Controller.resizeSelected) {
                    deltaX = e.getSceneX() - originalX;
                    ellipse.setRadiusX(ellipse.getRadiusX() + deltaX / 2);

                    originalX = ellipse.getCenterX() + ellipse.getRadiusX();
                    originalY = ellipse.getCenterY();

                } else {
                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    ((javafx.scene.shape.Ellipse) (e.getSource())).setTranslateX(newTranslateX);
                    ((javafx.scene.shape.Ellipse) (e.getSource())).setTranslateY(newTranslateY);
                    centerX = (ellipse.getBoundsInParent().getMaxX() + ellipse.getBoundsInParent().getMinX()) / 2;
                    centerY = (ellipse.getBoundsInParent().getMaxY() + ellipse.getBoundsInParent().getMinY()) / 2;
                    System.out.println("x is " + centerX);
                    System.out.println("y is " + centerY);
                }
            };

    private EventHandler<MouseEvent> ellipseOnMouseReleasedEventHandler =
            e -> {
                System.out.println("----------------------------------");
                System.out.println(centerX);
                System.out.println(centerY);

                int index = searchForEllipse();

                if (Controller.resizeSelected) {
                    ((Ellipse) Controller.shapesList.get(index)).setCenterPoint(new Point2D(centerX, centerY));
                    ((Ellipse) Controller.shapesList.get(index)).setEndPoint(new Point2D(ellipse.getCenterX()
                            + ellipse.getRadiusX(), ellipse.getCenterY() + ellipse.getRadiusY()));
                    ((Ellipse) Controller.shapesList.get(index)).setStartPoint(new Point2D(ellipse.getCenterX() -
                            ellipse.getRadiusX(), ellipse.getCenterY() + ellipse.getRadiusY()));
                }

                ((Ellipse) Controller.shapesList.get(index)).setCenterPoint(new Point2D(centerX, centerY));
                ((Ellipse) Controller.shapesList.get(index)).setStartPoint(new Point2D(centerX - ellipse.getRadiusX(),
                        centerY - ellipse.getRadiusY()));
                ((Ellipse) Controller.shapesList.get(index)).setEndPoint(new Point2D(centerX + ellipse.getRadiusX(),
                        centerY + ellipse.getRadiusY()));

            };

    private int searchForEllipse() {
        for (int i = 0; i < Controller.shapesList.size(); i++) {
            if (Controller.shapesList.get(i) instanceof Ellipse) {
                if (((Ellipse) Controller.shapesList.get(i)).getCenterPoint().getX() == centerPoint.getX()
                        && ((Ellipse) Controller.shapesList.get(i)).getCenterPoint().getY() == centerPoint.getY()) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(ellipse);
    }
}
