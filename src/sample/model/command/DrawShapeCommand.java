package sample.model.command;

import sample.controller.Controller;
import sample.model.Shape;

import javax.naming.ldap.Control;
import java.util.Stack;

public class DrawShapeCommand implements Command {

    private Shape shape;
    private Controller controller;
    private Stack<Shape> shapeStack;

    public DrawShapeCommand(Shape shape, Controller controller){
        this.shape = shape;
        this.controller = controller;
        shapeStack = new Stack<>();
    }

    @Override
    public void execute() {
        shape.draw(controller.getPane());
    }

    @Override
    public void undo() {

        Shape tempShape = Controller.shapesList.get(Controller.shapesList.size()-1);

        shapeStack.push(tempShape);

        shapeStack.peek().removeShape(controller.getCurrentMouseEvent());

    }

    @Override
    public void redo() {

        Controller.shapesList.add(shapeStack.peek());

        shapeStack.pop().draw(controller.getPane());


    }
}