package sample.files;

import org.json.simple.JSONObject;
import sample.model.AbstractShape;
import sample.model.ISaveLoadStrategy;
import sample.model.Shape;

import java.io.*;
import java.util.ArrayList;

public class JSONFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapes, String filepath) {
        File file =  new File(filepath);
        FileOutputStream fos = null;
        try {
            FileWriter fileWriter = new FileWriter(file);
            for(int i = 0; i < shapes.size(); i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", ((AbstractShape) shapes.get(i)).getName());
                jsonObject.put("color", shapes.get(i).getColor());
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Shape> load() {
        return null;
    }
}
