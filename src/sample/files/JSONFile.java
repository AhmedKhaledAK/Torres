package sample.files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.model.AbstractShape;
import sample.model.ISaveLoadStrategy;
import sample.model.Shape;

import java.io.*;
import java.util.ArrayList;

public class JSONFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapes, String filepath) {
        JSONArray list = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        for(int i=0; i<shapes.size(); i++)
        {
            jsonObject.put("name", ((AbstractShape)shapes.get(i)).getName());
            list.add(jsonObject);
            jsonObject.put("color", shapes.get(i).getColor().toString());
            list.add(jsonObject);
            jsonObject.put("strokeWidth", new Double(shapes.get(i).getStrokeWidth()));
            list.add(jsonObject);
            jsonObject.put("fillColor", shapes.get(i).getFillColor().toString());
            list.add(jsonObject);
            jsonObject.put("xPosition", new Double(shapes.get(i).getPosition().getX()));
            list.add(jsonObject);
            jsonObject.put("yPosition", new Double(shapes.get(i).getPosition().getY()));
            list.add(jsonObject);
        }

        try
        {
            FileWriter file = new FileWriter(filepath);
            list.writeJSONString(file);
            file.flush();
            file.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Shape> load() {
        return null;
    }
}
