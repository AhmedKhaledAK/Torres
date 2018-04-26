package sample.model.command;

public class CommandControl {

    Command theCommand;

    public CommandControl(Command newCommand)
    {
        theCommand = newCommand;
    }

    public void addOperation(){
        theCommand.execute();
    }

    public void pressUndo(){
        theCommand.undo();
    }

    public void  pressRedo(){
        theCommand.redo();
    }
}
