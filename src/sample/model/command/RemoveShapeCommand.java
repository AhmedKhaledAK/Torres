package sample.model.command;

import sample.controller.Controller;
import sample.model.Shape;

import javax.naming.ldap.Control;
import java.util.Stack;

public class RemoveShapeCommand implements Command {

    private Shape shape;
    private Controller controller;
    private Stack<Shape> shapeStack;

    public RemoveShapeCommand(Shape shape, Controller controller){
        this.shape = shape;
        this.controller = controller;
        shapeStack = new Stack<>();
    }

    @Override
    public void execute() {

        System.out.println("stack before deleting: " + shapeStack.size());
        shapeStack.push(shape);
        System.out.println("stack after deleting: " + shapeStack.size());

        shape.removeShape(controller.getCurrentMouseEvent());

    }

    @Override
    public void undo() {

        shape = shapeStack.pop();
        shape.draw(controller.getPane());
        Controller.shapesList.add(shape);

    }

    @Override
    public void redo() {
        shapeStack.pop().removeShape(controller.getCurrentMouseEvent());
    }
}