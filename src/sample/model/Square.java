package sample.model;

import javafx.scene.layout.Pane;

public class Square extends Rectangle {


    public Square() {
        this.name = "square";
    }

    @Override
    public void draw(Object pane) {

        p = (Pane) pane;
        rectangle = new javafx.scene.shape.Rectangle();

        if (getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setX(getStartPoint().getX());
            rectangle.setY(getStartPoint().getY());
        } else if (getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() > getStartPoint().getY()) {
            rectangle.setHeight(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setWidth(getStartPoint().getY() - getEndPoint().getY());
            rectangle.setX(getEndPoint().getX());
            rectangle.setY(getStartPoint().getY());
        } else if (getEndPoint().getX() > getStartPoint().getX() && getEndPoint().getY() < getStartPoint().getY()) {
            rectangle.setHeight(getStartPoint().getY() - getEndPoint().getY());
            rectangle.setWidth(getEndPoint().getY() - getStartPoint().getY());
            rectangle.setX(getStartPoint().getX());
            rectangle.setY(getEndPoint().getY());
        } else if (getEndPoint().getX() < getStartPoint().getX() && getEndPoint().getY() < getStartPoint().getY()) {
            rectangle.setHeight(getStartPoint().getY() - getEndPoint().getY());
            rectangle.setWidth(getStartPoint().getY() - getEndPoint().getY());
            rectangle.setX(getEndPoint().getX());
            rectangle.setY(getEndPoint().getY());
        }

        rectangle.setStroke(getColor());
        rectangle.setStrokeWidth(getStrokeWidth());
        rectangle.setFill(getFillColor());

        rectangle.setOnMousePressed(rectangleOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        rectangle.setOnMouseReleased(rectangleOnMouseReleasedEventHandler);
        rectangle.setOnMouseClicked(rectangleOnMouseClickedEventHandler);
        p.getChildren().add(rectangle);
    }
}
