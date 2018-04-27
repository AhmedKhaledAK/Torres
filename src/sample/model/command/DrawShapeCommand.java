package sample.model.command;

import javafx.scene.control.Alert;
import sample.controller.Controller;
import sample.lang.Lang;
import sample.model.Shape;

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

        if(Controller.shapesList.size()==0)
        {
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error",  "", "Cannot undo any more steps.");

        }
        else
        {
            Shape tempShape = Controller.shapesList.get(Controller.shapesList.size()-1);
            shapeStack.push(tempShape);
            shapeStack.peek().removeShape(controller.getCurrentMouseEvent());
        }
    }

    @Override
    public void redo() {
        if(shapeStack.isEmpty())
        {
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error",  "", "Cannot redo any more steps.");

        }
        else
        {
            Controller.shapesList.add(shapeStack.peek());
            shapeStack.pop().draw(controller.getPane());
        }
    }
}
