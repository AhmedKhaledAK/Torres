package sample.model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.controller.Controller;
import sample.lang.Lang;

import java.util.Map;

public class Line extends AbstractShape {

    private Point2D startPoint;
    private Point2D endPoint;
    private javafx.scene.shape.Line line;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private double startX = 0;
    private double endX = 0;
    private double startY = 0;
    private double endY = 0;
    Pane p;

    public Line() {
        this.name = "line";
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
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
        line = new javafx.scene.shape.Line();
        line.setStartX(getStartPoint().getX());
        line.setStartY(getStartPoint().getY());
        line.setEndX(getEndPoint().getX());
        line.setEndY(getEndPoint().getY());
        line.setStroke(getColor());
        line.setFill(getFillColor());
        line.setStrokeWidth(getStrokeWidth());
        line.setOnMousePressed(lineOnMousePressedEventHandler);
        line.setOnMouseDragged(lineOnMouseDraggedEventHandler);
        line.setOnMouseReleased(lineOnMouseReleasedEventHandler);
        line.setOnMouseClicked(lineOnMouseClickedEventHandler);
        p.getChildren().add(line);
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
        int index = searchForLine();
        System.out.println("index is " + index);

        if(Controller.shapesList.size()==0)
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error", "Cannot Delete",
                    "There is nothing to delete");
        else
        {
            p.getChildren().remove(line);
            Controller.shapesList.remove(index);

        }

    }

    private EventHandler<MouseEvent> lineOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    line = (javafx.scene.shape.Line) (e.getSource());

                    startX = line.getStartX() + line.getTranslateX();
                    startY = line.getStartY() + line.getTranslateY();
                    endX = line.getEndX() + line.getTranslateX();
                    endY = line.getEndY() + line.getTranslateY();

                    if(Controller.deleteSelected) {
                        removeShape(e);
                    } else if(Controller.copySelected){
                        Line l = new Line();
                        l.setStartPoint(new Point2D(getStartPoint().getX()+10, getStartPoint().getY()+10));
                        l.setEndPoint(new Point2D(getEndPoint().getX()+10, getEndPoint().getY()+10));
                        l.setStrokeWidth(line.getStrokeWidth());
                        l.setColor(getColor());
                        l.setFillColor(getFillColor());
                        l.draw(p);
                        Controller.shapesList.add(l);
                    }

                }
            };


    private EventHandler<MouseEvent> lineOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    if(Controller.resizeSelected)
                    {
                        startX = line.getStartX() + line.getTranslateX();
                        startY = line.getStartY() + line.getTranslateY();
                    }
                    else if(Controller.moveSelected)
                    {
                        orgSceneX = e.getSceneX();
                        orgSceneY = e.getSceneY();
                        orgTranslateX = ((javafx.scene.shape.Line) (e.getSource())).getTranslateX();
                        orgTranslateY = ((javafx.scene.shape.Line) (e.getSource())).getTranslateY();
                        startPoint = new Point2D(line.getBoundsInParent().getMinX(), line.getBoundsInParent().getMaxX());
                        endPoint = new Point2D(line.getBoundsInParent().getMaxX(),
                                line.getBoundsInParent().getMinY());
                    }

                }
            };

    private EventHandler<MouseEvent> lineOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    if(Controller.resizeSelected)
                    {
                        line.setEndX(e.getSceneX());
                        line.setEndY(e.getSceneY());
                    }
                    else if(Controller.moveSelected)
                    {
                        double offsetX = e.getSceneX() - orgSceneX;
                        double offsetY = e.getSceneY() - orgSceneY;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;
                        ((javafx.scene.shape.Line) (e.getSource())).setTranslateX(newTranslateX);
                        ((javafx.scene.shape.Line) (e.getSource())).setTranslateY(newTranslateY);
                        startX = line.getBoundsInParent().getMaxX();
                        startY = line.getBoundsInParent().getMaxY();

                        endX = line.getBoundsInParent().getMinX();
                        endY = line.getBoundsInParent().getMinY();
                    }

                }
            };

    private EventHandler<MouseEvent> lineOnMouseReleasedEventHandler =
            e -> {
                System.out.println("----------------------------------");

                int index = searchForLine();

                if(Controller.resizeSelected)
                {
                    endX = e.getSceneX();
                    endY = e.getSceneY();

                    line.setEndX(endX);
                    line.setEndY(endY);

                    Controller.shapesList.get(index).setStartPoint(new Point2D(startX, startY));
                    ((Line) Controller.shapesList.get(index)).setEndPoint(new Point2D(endX, endY));

                }
                else if(Controller.moveSelected)
                {
                    ((Line) Controller.shapesList.get(index)).setStartPoint(new Point2D(startX, startY));
                    ((Line) Controller.shapesList.get(index)).setEndPoint(new Point2D(endX, endY));

                }

            };

    private int searchForLine() {
        for (int i = 0; i < Controller.shapesList.size(); i++) {
            if (Controller.shapesList.get(i) instanceof Line) {
                if (((Line) Controller.shapesList.get(i)).getStartPoint().getX() == startPoint.getX() &&
                        ((Line) Controller.shapesList.get(i)).getStartPoint().getY() == startPoint.getY() &&
                        ((Line) Controller.shapesList.get(i)).getEndPoint().getX() == endPoint.getX() &&
                        ((Line) Controller.shapesList.get(i)).getEndPoint().getY() == endPoint.getY()) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void removeDeprecated(Pane pane) {
        pane.getChildren().remove(line);
    }
}
