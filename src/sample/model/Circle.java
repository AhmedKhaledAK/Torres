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

public class Circle extends AbstractShape {

    private double radius;
    private Point2D centerPoint;
    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Circle circle;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private double originalX, originalY, deltaX;
    double centerX = 0;
    double centerY = 0;

    Pane p;

    public Circle() {
        this.name = "circle";
    }


    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
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
        this.circle = new javafx.scene.shape.Circle();
        double diameter = Math.sqrt(Math.pow(getEndPoint().getY() - getStartPoint().getY(), 2) +
                Math.pow(getEndPoint().getX() - getStartPoint().getX(), 2));

        double centerY = (getEndPoint().getY() + getStartPoint().getY()) / 2;
        double centerX = (getEndPoint().getX() + getStartPoint().getX()) / 2;

        this.circle.setCenterX(centerX);
        this.circle.setCenterY(centerY);
        setCenterPoint(new Point2D(centerX, centerY));
        this.circle.setStroke(getColor());
        this.circle.setStrokeWidth(getStrokeWidth());
        this.circle.setFill(getFillColor());
        this.circle.setRadius(diameter / 2);
        setRadius(circle.getRadius());
        circle.setOnMousePressed(circleOnMousePressedEventHandler);
        circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        circle.setOnMouseReleased(circleOnMouseReleasedEventHandler);
        circle.setOnMouseClicked(circleOnMouseClickedEventHandler);
        p.getChildren().add(circle);
    }

    @Override
    public void movePress(MouseEvent e) {
        if(Controller.resizeSelected)
        {
            originalX = circle.getCenterX() + circle.getTranslateX() + circle.getRadius();
            originalY = circle.getCenterY() + circle.getTranslateY();
        }
        else if(Controller.moveSelected)
        {
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateX();
            orgTranslateY = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateY();

            centerX = (circle.getBoundsInParent().getMaxX() + circle.getBoundsInParent().getMinX()) / 2;
            centerY = (circle.getBoundsInParent().getMaxY() + circle.getBoundsInParent().getMinY()) / 2;
        }

    }

    @Override
    public void moveDrag(MouseEvent e) {
        if(Controller.resizeSelected)
        {
            deltaX = e.getSceneX() - originalX;
            circle.setRadius(circle.getRadius() + deltaX/2);

            originalX = circle.getCenterX() + circle.getTranslateX() + circle.getRadius();
            originalY = circle.getCenterY() + circle.getTranslateY();

        }
        else if(Controller.moveSelected)
        {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((javafx.scene.shape.Circle) (e.getSource())).setTranslateX(newTranslateX);
            ((javafx.scene.shape.Circle) (e.getSource())).setTranslateY(newTranslateY);

            centerX = (circle.getBoundsInParent().getMaxX() + circle.getBoundsInParent().getMinX()) / 2;
            centerY = (circle.getBoundsInParent().getMaxY() + circle.getBoundsInParent().getMinY()) / 2;
        }
    }

    @Override
    public void moveRelease() {
        int index = searchForCircle();

        if(Controller.resizeSelected){

            ((Circle) Controller.shapesList.get(index)).setCenterPoint(new Point2D(centerX, centerY));

            Controller.shapesList.get(index).setEndPoint(new Point2D(circle.getCenterX() +
                    circle.getTranslateX() + circle.getRadius(),
                    circle.getCenterY() + circle.getTranslateY()));

            Controller.shapesList.get(index).setStartPoint(new Point2D(circle.getCenterX() +
                    circle.getTranslateX() - circle.getRadius(), circle.getCenterY() +
                    circle.getTranslateY()));

        }
        else if(Controller.moveSelected)
        {
            ((Circle) Controller.shapesList.get(index)).setCenterPoint(new Point2D(centerX, centerY));
            Controller.shapesList.get(index).setStartPoint(new Point2D(centerX - circle.getRadius(), centerY));
            Controller.shapesList.get(index).setEndPoint(new Point2D(centerX + circle.getRadius(), centerY));
        }

    }

    @Override
    public void removeShape(MouseEvent e) {
        int index = searchForCircle();
        System.out.println("index is " + index);

        if(Controller.shapesList.size()==0)
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error", "Cannot Delete",
                    "There is nothing to delete");
        else
        {
            p.getChildren().remove(circle);
            Controller.shapesList.remove(index);

        }
    }

    private EventHandler<MouseEvent> circleOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    circle = (javafx.scene.shape.Circle) (e.getTarget());

                    centerX = (circle.getBoundsInParent().getMaxX() + circle.getBoundsInParent().getMinX()) / 2;
                    centerY = (circle.getBoundsInParent().getMaxY() + circle.getBoundsInParent().getMinY()) / 2;

                    if(Controller.deleteSelected) {
                        removeShape(e);
                    }else if (Controller.copySelected) {
                        Circle c = new Circle();
                        c.setStartPoint(new Point2D(getStartPoint().getX()+10, getStartPoint().getY()+10));
                        c.setEndPoint(new Point2D(getEndPoint().getX()+10, getEndPoint().getY()+10));
                        c.setStrokeWidth(circle.getStrokeWidth());
                        c.setColor(getColor());
                        c.setFillColor(getFillColor());
                        c.draw(p);
                        Controller.shapesList.add(c);
                    }

                }
            };


    private EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    movePress(e);
                }
            };


    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    moveDrag(e);
                }
            };

    private EventHandler<MouseEvent> circleOnMouseReleasedEventHandler =
            e -> {
                moveRelease();
            };


    private int searchForCircle() {
        for (int i = 0; i < Controller.shapesList.size(); i++) {
            if (Controller.shapesList.get(i) instanceof Circle) {
                if (((Circle) Controller.shapesList.get(i)).getCenterPoint().getX() == centerPoint.getX()
                        && ((Circle) Controller.shapesList.get(i)).getCenterPoint().getY() == centerPoint.getY()) {
                    return i;
                }
            }
        }
        return -1;
    }


    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(circle);
    }
}