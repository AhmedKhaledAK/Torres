package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;
import sample.controller.Controller;

import java.util.Map;

public class Rectangle extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Rectangle rectangle;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public Rectangle(){
        this.name = "rectangle";
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

        rectangle.setOnMousePressed(rectangleOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        rectangle.setOnMouseReleased(rectangleOnMouseReleasedEventHandler);

        p.getChildren().add(rectangle);

    }

    @Override
    public void move(MouseEvent e) {
        rectangle.getOnMousePressed();
    }

    double startX = 0;
    double startY = 0;
    double endX = 0;
    double endY = 0;

    @Override
    public void moveDrag(MouseEvent e){
        rectangle.getOnMouseDragged();
    }

    private EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Rectangle) (e.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Rectangle) (e.getSource())).getTranslateY();

                    startPoint = new Point2D(rectangle.getBoundsInParent().getMinX(),
                            rectangle.getBoundsInParent().getMaxY());

                    endPoint = new Point2D(rectangle.getBoundsInParent().getMaxX(),
                            rectangle.getBoundsInParent().getMinY());
                }
            };


    private EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((javafx.scene.shape.Rectangle)(e.getSource())).setTranslateX(newTranslateX);
                    ((javafx.scene.shape.Rectangle)(e.getSource())).setTranslateY(newTranslateY);

                    startX = rectangle.getBoundsInParent().getMinX() + getStrokeWidth();
                    startY = rectangle.getBoundsInParent().getMaxY() - getStrokeWidth();

                    endX = rectangle.getBoundsInParent().getMaxX() - getStrokeWidth();
                    endY = rectangle.getBoundsInParent().getMinY() + getStrokeWidth();
                }
            };

    private EventHandler<MouseEvent> rectangleOnMouseReleasedEventHandler =
            e -> {
                System.out.println("----------------------------------");

                for(Shape shape : Controller.shapesList){
                    if(shape instanceof Rectangle){
                        if(((Rectangle)shape).getStartPoint().getX() == startPoint.getX() &&
                                ((Rectangle)shape).getStartPoint().getY() == startPoint.getY() &&
                                ((Rectangle)shape).getEndPoint().getX() == endPoint.getX() &&
                                ((Rectangle)shape).getEndPoint().getY() == endPoint.getY()){

                            ((Rectangle)shape).setStartPoint(new Point2D(startX, startY));
                            ((Rectangle)shape).setEndPoint(new Point2D(endX, endY));

                            System.out.println("found");
                            break;
                        }
                    }
                }

            };



    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(rectangle);

    }
}
