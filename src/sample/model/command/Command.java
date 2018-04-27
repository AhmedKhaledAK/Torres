package sample.model.command;

public interface Command {

    void execute();
    void undo();
    void redo();
}
