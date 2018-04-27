package sample.model.command;

import sample.controller.Controller;
import sample.model.Shape;

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
        shapeStack.push(shape);
        System.out.println(shapeStack.size());
        //shape.removeShape(controller.getCurrentMouseEvent());
    }

    @Override
    public void undo() {
        shapeStack.peek().draw(controller.getPane());
        Controller.shapesList.add(shapeStack.pop());
    }

    @Override
    public void redo() {
        shapeStack.push(shape);
        shape.removeShape(controller.getCurrentMouseEvent());
    }
}