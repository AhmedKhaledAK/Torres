package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import sample.controller.Controller;
import sample.lang.Lang;

import java.util.Map;

public class Rectangle extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Rectangle rectangle;
    private double orgSceneX, orgSceneY, originalX, originalY;
    private double orgTranslateX, orgTranslateY;
    double startX = 0;
    double startY = 0;
    double endX = 0;
    double endY = 0;
    Pane p;

    public Rectangle() {
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

        p = (Pane) pane;
        rectangle = new javafx.scene.shape.Rectangle();

        if (getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getEndPoint().getX() - getStartPoint().getX());
            rectangle.setX(getStartPoint().getX());
            rectangle.setY(getStartPoint().getY());
        } else if (getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getStartPoint().getX() - getEndPoint().getX());
            rectangle.setX(getEndPoint().getX());
            rectangle.setY(getStartPoint().getY());
        } else if (getEndPoint().getX() > getStartPoint().getX() && this.getEndPoint().getY() < this.getStartPoint().getY()) {
            rectangle.setHeight(getStartPoint().getY() - this.getEndPoint().getY());
            rectangle.setWidth(this.getEndPoint().getX() - this.getStartPoint().getX());
            rectangle.setX(this.getStartPoint().getX());
            this.rectangle.setY(this.getEndPoint().getY());
        } else if (this.getEndPoint().getX() < this.getStartPoint().getX()
                && this.getEndPoint().getY() < this.getStartPoint().getY()) {
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
        rectangle.setOnMouseClicked(rectangleOnMouseClickedEventHandler);
//        rectangle.setOnMouseMoved(rectangleOnMouseMovedEventHandler);
        p.getChildren().add(rectangle);
    }

    @Override
    public void movePress(MouseEvent e) {
        if(Controller.resizeSelected)
        {
            originalX = rectangle.getX() + rectangle.getWidth();
            originalY = rectangle.getY() + rectangle.getHeight();
        }
        else {

            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = ((javafx.scene.shape.Rectangle) (e.getTarget())).getTranslateX();
            orgTranslateY = ((javafx.scene.shape.Rectangle) (e.getTarget())).getTranslateY();
        }

        startPoint = new Point2D(rectangle.getBoundsInParent().getMinX(),
                rectangle.getBoundsInParent().getMaxY());

        endPoint = new Point2D(rectangle.getBoundsInParent().getMaxX(),
                rectangle.getBoundsInParent().getMinY());
    }

    @Override
    public void moveDrag(MouseEvent e) {
        if(Controller.resizeSelected)
        {
            double deltaX = e.getSceneX() - originalX;
            double deltaY = e.getSceneY() - originalY;

            rectangle.setHeight(rectangle.getHeight() + deltaY);
            rectangle.setWidth(rectangle.getWidth() + deltaX);

            originalX = rectangle.getX() + rectangle.getWidth();
            originalY = rectangle.getY() + rectangle.getHeight();


        }
        else
        {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((javafx.scene.shape.Rectangle) (e.getTarget())).setTranslateX(newTranslateX);
            ((javafx.scene.shape.Rectangle) (e.getTarget())).setTranslateY(newTranslateY);

            startX = rectangle.getBoundsInParent().getMinX() + getStrokeWidth() / 2;
            startY = rectangle.getBoundsInParent().getMaxY() - getStrokeWidth() / 2;

        }

        endX = rectangle.getBoundsInParent().getMaxX() - getStrokeWidth() / 2;
        endY = rectangle.getBoundsInParent().getMinY() + getStrokeWidth() / 2;
    }

    @Override
    public void moveRelease(MouseEvent e) {
        int index = searchForRectangle();
        if(Controller.resizeSelected){
            ((Rectangle) Controller.shapesList.get(index)).setEndPoint(new Point2D(originalX, originalY));
            ((Rectangle) Controller.shapesList.get(index)).setStartPoint(new Point2D(rectangle.getX(), rectangle.getY()));
        }
        else {
            ((Rectangle) Controller.shapesList.get(index)).setStartPoint(new Point2D(startX, startY));
            ((Rectangle) Controller.shapesList.get(index)).setEndPoint(new Point2D(endX, endY));
        }
    }

    @Override
    public void removeShape(MouseEvent e) {

        int index = searchForRectangle();
        System.out.println("index is " + index);


        if(Controller.shapesList.size()==0)
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error", "Cannot Delete",
                    "There is nothing to delete");
        else
        {
            p.getChildren().remove(rectangle);
            Controller.shapesList.remove(index);

        }

    }

//    private EventHandler<MouseEvent> rectangleOnMouseMovedEventHandler =
//            new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    if(e.getSceneX()>=rectangle.getX() && e.getSceneX()<=rectangle.getX()+rectangle.getStrokeWidth())
//                        Main.scene.setCursor(Cursor.W_RESIZE);
//
//                    else if(e.getSceneX()<=rectangle.getX()+rectangle.getWidth() &&
//                            e.getSceneX()>=rectangle.getX()+ rectangle.getWidth()-rectangle.getStrokeWidth())
//                        Main.scene.setCursor(Cursor.E_RESIZE);
//
//                    else if(e.getSceneY()>=rectangle.getY() && e.getSceneY()<rectangle.getY()-rectangle.getStrokeWidth())
//                        Main.scene.setCursor(Cursor.N_RESIZE);
//
//                    else if(e.getSceneY()<=rectangle.getY() - rectangle.getHeight() &&
//                            e.getSceneY()>rectangle.getY() - rectangle.getHeight() +rectangle.getStrokeWidth())
//                        Main.scene.setCursor(Cursor.S_RESIZE);
//
//                    else
//                        Main.scene.setCursor(Cursor.DEFAULT);
//
//
//                }
//            };


    private EventHandler<MouseEvent> rectangleOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    rectangle = (javafx.scene.shape.Rectangle) (e.getTarget());

                    startX = rectangle.getBoundsInParent().getMinX() + getStrokeWidth() / 2;
                    startY = rectangle.getBoundsInParent().getMaxY() - getStrokeWidth() / 2;

                    endX = rectangle.getBoundsInParent().getMaxX() - getStrokeWidth() / 2;
                    endY = rectangle.getBoundsInParent().getMinY() + getStrokeWidth() / 2;

                    if(Controller.deleteSelected || Controller.undoSelected)
                        removeShape(e);
                }
            };

    private EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    movePress(e);
                }
            };

    private EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    moveDrag(e);

                }
            };

    private EventHandler<MouseEvent> rectangleOnMouseReleasedEventHandler =
            e -> {
                moveRelease(e);
            };

    private int searchForRectangle() {
        for (int i = 0; i < Controller.shapesList.size(); i++) {
            if (Controller.shapesList.get(i) instanceof Rectangle) {
                if (((Rectangle) Controller.shapesList.get(i)).getStartPoint().getX() == startPoint.getX() &&
                        ((Rectangle) Controller.shapesList.get(i)).getStartPoint().getY() == startPoint.getY() &&
                        ((Rectangle) Controller.shapesList.get(i)).getEndPoint().getX() == endPoint.getX() &&
                        ((Rectangle) Controller.shapesList.get(i)).getEndPoint().getY() == endPoint.getY()) {

                    return i;
                }
            }
        }
        return -1;
    }


    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(rectangle);

    }
}
