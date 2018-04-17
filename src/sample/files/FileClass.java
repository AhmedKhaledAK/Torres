package sample.files;

import javafx.stage.FileChooser;
import sample.Main;
import sample.model.ISaveLoadStrategy;
import sample.model.Shape;

import java.io.File;
import java.util.ArrayList;

public class FileClass {
    ISaveLoadStrategy strategy;
    String extension;
    File file;
    String filepath, charset;
    private boolean append, autoFlush;
    String type;
    FileChooser fileChooser;
    FileChooser.ExtensionFilter extensionFilter;

    public FileClass(String type){
        this.type=type;
        this.append = false;
        this.autoFlush = true;
        fileChooser = new FileChooser();
        extensionFilter = new FileChooser.ExtensionFilter("JSON or XML",
                "*.json", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    public void save(ArrayList<Shape> shapes){
        if(type.trim().equals("save")){
            fileChooser.setTitle("Save");
            file = fileChooser.showSaveDialog(Main.stage);
            extension = getFileExtension(file.getName());
            System.out.println(extension);
            if(extension.equals("json")) strategy = new JSONFile();
            else strategy = new XMLFile();
            strategy.save(shapes,file.getAbsolutePath());
        }
        else {
            fileChooser.setTitle("Open");
            file = fileChooser.showOpenDialog(Main.stage);
            extension = getFileExtension(file.getName());
            if(extension.equals("json")) strategy = new JSONFile();
            else strategy = new XMLFile();
            strategy.load();
        }
    }

    private String getFileExtension(String extension){
        return extension.substring(extension.lastIndexOf(".")+1);
    }

}
