package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.controller.Controller;

import java.util.Map;

public class Circle extends AbstractShape {

    private double radius;
    private Point2D centerPoint;
    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Circle circle;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    Pane p;

    public Circle(){
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
        double diameter = Math.sqrt(Math.pow(getEndPoint().getY()-getStartPoint().getY(), 2) +
                Math.pow(getEndPoint().getX()-getStartPoint().getX(),2));

        double centerY = (getEndPoint().getY() + getStartPoint().getY()) / 2;
        double centerX = (getEndPoint().getX() + getStartPoint().getX()) / 2;

        System.out.println("end point x and y = " + getEndPoint().getX());
        this.circle.setCenterX(centerX);
        this.circle.setCenterY(centerY);
        setCenterPoint(new Point2D(centerX, centerY));
        this.circle.setStroke(getColor());
        this.circle.setStrokeWidth(getStrokeWidth());
        this.circle.setFill(Color.TRANSPARENT);
        this.circle.setRadius(diameter/2);
        setRadius(circle.getRadius());
        circle.setOnMousePressed(circleOnMousePressedEventHandler);
        circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        circle.setOnMouseReleased(circleOnMouseReleasedEventHandler);
        p.getChildren().add(circle);
    }

    @Override
    public void move(MouseEvent e) {
        circle.getOnMousePressed();
    }

    double centerX = 0;
    double centerY = 0;

    @Override
    public void moveDrag(MouseEvent e){
        circle.getOnMouseDragged();
    }

    private EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateY();

                    centerX = (circle.getBoundsInParent().getMaxX() + circle.getBoundsInParent().getMinX())/2;
                    centerY = (circle.getBoundsInParent().getMaxY() + circle.getBoundsInParent().getMinY())/2;
                    System.out.println("x is " + centerX);
                    System.out.println("y is " + centerY);

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

                    centerX = (circle.getBoundsInParent().getMaxX() + circle.getBoundsInParent().getMinX())/2;
                    centerY = (circle.getBoundsInParent().getMaxY() + circle.getBoundsInParent().getMinY())/2;
                }
            };

    private EventHandler<MouseEvent> circleOnMouseReleasedEventHandler =
            e -> {

                for(Shape shape : Controller.shapesList){
                    if(shape instanceof Circle){
                        if(((Circle)shape).getCenterPoint().getX() == circle.getCenterX()
                                && ((Circle)shape).getCenterPoint().getY() == circle.getCenterY()){

                            ((Circle)shape).setCenterPoint(new Point2D(centerX, centerY));
                            ((Circle)shape).setStartPoint(new Point2D(centerX - circle.getRadius(), centerY));
                            ((Circle)shape).setEndPoint(new Point2D(centerX + circle.getRadius(), centerY));

                            break;
                        }
                    }
                }


            };



    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(circle);
    }
}
