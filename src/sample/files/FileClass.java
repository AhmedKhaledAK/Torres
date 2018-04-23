package sample.files;

import javafx.stage.FileChooser;
import sample.view.Main;
import sample.model.ISaveLoadStrategy;
import sample.model.Shape;
import java.io.File;
import java.util.ArrayList;

public class FileClass {
    private ISaveLoadStrategy strategy;
    private String extension;
    private File file;
    private FileChooser fileChooser;

    public FileClass(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON or XML",
                "*.json", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    public void save(ArrayList<Shape> shapes){
        fileChooser.setTitle("Save");
        file = fileChooser.showSaveDialog(Main.stage);
        extension = getFileExtension(file.getName());
        System.out.println(extension);
        if (extension.equals("json")) strategy = new JSONFile();
        else strategy = new XMLFile();
        strategy.save(shapes, file.getAbsolutePath());
    }

    public ArrayList<Shape> load(){
        fileChooser.setTitle("Open");
        file = fileChooser.showOpenDialog(Main.stage);
        extension = getFileExtension(file.getName());
        if(extension.equals("json")) strategy = new JSONFile();
        else strategy = new XMLFile();
        return strategy.load(file.getAbsolutePath());
    }

    private String getFileExtension(String extension){
        return extension.substring(extension.lastIndexOf(".")+1);
    }

}
