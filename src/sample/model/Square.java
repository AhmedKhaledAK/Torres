package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.controller.Controller;

import java.util.Map;

public class Square extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Rectangle square;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public Square(){
        this.name = "square";
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
        square = new javafx.scene.shape.Rectangle();

        if(getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY())
        {
            square.setHeight(getEndPoint().getY() - getStartPoint().getY());
            square.setWidth(getEndPoint().getY() - getStartPoint().getY());
            square.setX(getStartPoint().getX());
            square.setY(getStartPoint().getY());
        }

        else if(getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            square.setHeight(getEndPoint().getY() - getStartPoint().getY());
            square.setWidth(getStartPoint().getY() - getEndPoint().getY());
            square.setX(getEndPoint().getX());
            square.setY(getStartPoint().getY());
        }
        else if(getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() < getStartPoint().getY()) {
            square.setHeight(getStartPoint().getY() - getEndPoint().getY());
            square.setWidth(getEndPoint().getY() - getStartPoint().getY());
            square.setX(getStartPoint().getX());
            square.setY(getEndPoint().getY());
        }
        else if(getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() < getStartPoint().getY()) {
            square.setHeight(getStartPoint().getY() - getEndPoint().getY());
            square.setWidth(getStartPoint().getY() - getEndPoint().getY());
            square.setX(getEndPoint().getX());
            square.setY(getEndPoint().getY());
        }

        square.setStroke(getColor());
        square.setStrokeWidth(getStrokeWidth());
        square.setFill(getFillColor());

        square.setOnMousePressed(squareOnMousePressedEventHandler);
        square.setOnMouseDragged(squareOnMouseDraggedEventHandler);
        square.setOnMouseReleased(squareOnMouseReleasedEventHandler);

        p.getChildren().add(square);

    }

    @Override
    public void move(MouseEvent e) {
        square.getOnMousePressed();
    }

    double startX = 0;
    double startY = 0;
    double endX = 0;
    double endY = 0;

    @Override
    public void moveDrag(MouseEvent e){
        square.getOnMouseDragged();
    }

    private EventHandler<MouseEvent> squareOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Rectangle) (e.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Rectangle) (e.getSource())).getTranslateY();

                    startPoint = new Point2D(square.getBoundsInParent().getMinX(),
                            square.getBoundsInParent().getMaxY());

                    endPoint = new Point2D(square.getBoundsInParent().getMaxX(),
                            square.getBoundsInParent().getMinY());
                }
            };


    private EventHandler<MouseEvent> squareOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((javafx.scene.shape.Rectangle)(e.getSource())).setTranslateX(newTranslateX);
                    ((javafx.scene.shape.Rectangle)(e.getSource())).setTranslateY(newTranslateY);

                    startX = square.getBoundsInParent().getMinX() + getStrokeWidth();
                    startY = square.getBoundsInParent().getMaxY() - getStrokeWidth();

                    endX = square.getBoundsInParent().getMaxX() - getStrokeWidth();
                    endY = square.getBoundsInParent().getMinY() + getStrokeWidth();
                }
            };

    private EventHandler<MouseEvent> squareOnMouseReleasedEventHandler =
            e -> {
                System.out.println("----------------------------------");

                for(Shape shape : Controller.shapesList){
                    if(shape instanceof Square){
                        if(((Square)shape).getStartPoint().getX() == startPoint.getX() &&
                                ((Square)shape).getStartPoint().getY() == startPoint.getY() &&
                                ((Square)shape).getEndPoint().getX() == endPoint.getX() &&
                                ((Square)shape).getEndPoint().getY() == endPoint.getY()){

                            ((Square)shape).setStartPoint(new Point2D(startX, startY));
                            ((Square)shape).setEndPoint(new Point2D(endX, endY));

                            System.out.println("found");
                            break;
                        }
                    }
                }

            };


    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(square);

    }
}
