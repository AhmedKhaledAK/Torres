package sample.model.command;

import javafx.scene.control.Alert;
import sample.lang.Lang;

public class CommandControl {

    private Command command;

    public CommandControl(Command command) {
        this.command = command;
    }

    public void addOperation(){
        command.execute();
    }

    public void pressUndo(){
        if(command!=null)
            command.undo();
        else
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error",  "", "No steps to undo.");

    }

    public void  pressRedo(){
        if(command!=null)
            command.redo();
        else
            Lang.showDiag(Alert.AlertType.INFORMATION, "Error",  "", "No steps to redo.");

    }
}
