package sample.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import sample.model.Shape;
import sample.model.ShapeFactory;
import sample.view.View;

import java.util.ArrayList;

public class Controller implements DrawingEngine {

    private static boolean brushSelected = false;
    private static boolean lineSelected = false;
    private static boolean circleSelected = false;
    private static boolean rectangleSelected = false;
    private static double x = 0, y = 0;


//    static Shape shape = null;
//    static ShapeFactory shapeFactory = new ShapeFactory();
//    static ArrayList<Shape> shapesList = new ArrayList<>();

    static View view = new View();


    @Override
    public void refresh(Canvas canvas) {

    }

    @Override
    public void addShape(Shape shape) {

    }

    @Override
    public void removeShape(Shape shape) {

    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {

    }

    @Override
    public Shape[] getShapes() {
        return new Shape[0];
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }


    public static class EventListener implements EventHandler<Event> {


        @Override
        public void handle(Event e) {
            System.out.println("flag2");
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                try {
                    System.out.println(((Control) e.getSource()).getId());
                    String name = ((Control) e.getSource()).getId();
                    System.out.println("flag3");

                    switch (name.trim()) {
                        case "btnRect":
                            rectangleSelected ^= true;
                            System.out.println("flag999");
                            break;
                        case "btnLine":
                            lineSelected ^= true;
                            break;
                        case "btnCircle":
                            circleSelected ^= true;
                            break;
                    }
                } catch (Exception e1) {
                    System.out.println("flag 00");
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                x = ((javafx.scene.input.MouseEvent) e).getSceneX();
                y = ((javafx.scene.input.MouseEvent) e).getSceneY();
                System.out.println("flag4");

                if (((MouseEvent) e).isControlDown()) {
                    view.shape.move(((javafx.scene.input.MouseEvent) e));
                } else {
                    if (lineSelected)
                        view.shape = view.shapeFactory.createShape("line");
                    else if (rectangleSelected)
                        view.shape = view.shapeFactory.createShape("rectangle");
                    else if (circleSelected) {
                        view.shape = view.shapeFactory.createShape("circle");
                    }
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double x1 = ((MouseEvent) e).getSceneX();
                double y1 = ((MouseEvent) e).getSceneY();


                if (((MouseEvent) e).isControlDown()) {
                    view.shape.moveDrag(((MouseEvent) e));
                } else {
                    if (lineSelected) {
                        view.shape.removeDeprecated(view.pane);
                        view.drawLine(x, y, x1, y1, view.shape);
                    } else if (rectangleSelected) {
                        view.shape.removeDeprecated(view.pane);
                        view.drawRectangle(x, y, x1, y1, view.shape);
                    } else if (circleSelected) {
                        view.shape.removeDeprecated(view.pane);
                        view.drawCircle(x, y, x1, y1);
                    } else if (brushSelected)
                        view.drawBrushStroke(x1, y1);
                    // else if(checkBoxEraser.isSelected())
                    //    drawBrushStroke(x1, y1);
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                double x1 = ((javafx.scene.input.MouseEvent) e).getSceneX();
                double y1 = ((javafx.scene.input.MouseEvent) e).getSceneY();

                if(!((javafx.scene.input.MouseEvent) e).isControlDown()) {
                    if (lineSelected)
                        view.drawLine(x, y, x1, y1, view.shape);
                    else if (brushSelected)
                        view.drawBrushStroke(x1, y1);
                    else if (rectangleSelected)
                        view.drawRectangle(x, y, x1, y1, view.shape);
                    else if (circleSelected)
                        view.drawCircle(x, y, x1, y1);
                    /*else if (checkBoxEraser.isSelected())
                        view.drawBrushStroke(x1, y1);*/

                    view.shapesList.add(view.shape);
                }
            }
        }
    }
}


