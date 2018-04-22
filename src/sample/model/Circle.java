package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Map;


public class Circle extends AbstractShape {

    private double radius;
    private Point2D centerPoint;
    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Circle circle;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX=0, orgTranslateY=0;
    Pane p;

    double originalX = 0;
    double originalY = 0;
    double offsetX = 0;
    double offsetY = 0;

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
        this.circle.setCenterX(centerX);
        this.circle.setCenterY(centerY);
        setCenterPoint(new Point2D(circle.getCenterX(), circle.getCenterY()));
        //this.circle.setStroke(getColor());
        this.circle.setStroke(Color.BLACK);
        //this.circle.setStrokeWidth(getStrokeWidth());
        this.circle.setStrokeWidth(1);
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
        circle.getOnMouseClicked();
    }

    @Override
    public void moveDrag(MouseEvent e){
        circle.getOnMouseDragEntered();
    }

    private EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Circle) (e.getSource())).getTranslateY();

                    System.out.println("is clicked");
                    System.out.println("orgtransx = " + orgTranslateX);
                    System.out.println("orgtransy = " + orgTranslateY);

//                    originalX = e.getSceneX();
//                    originalY = e.getSceneY();

                }
            };

    javafx.scene.shape.Circle c2;

    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    //System.out.println("is dragged");

                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;


                    c2=((javafx.scene.shape.Circle) (e.getSource()));
                    c2.setTranslateX(newTranslateX);
                    c2.setTranslateY(newTranslateY);
                    p.getChildren().remove(circle);

                    System.out.println("radius is " + circle.getRadius());
                    System.out.println("x is " + c2.getCenterX());
                    System.out.println("y is " + c2.getCenterY());

                }
            };

    private EventHandler<MouseEvent> circleOnMouseReleasedEventHandler =
            e -> {
               // p.getChildren().remove(circle);
                System.out.println("released");
            };

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(circle);
    }
}