package sample.files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.model.*;

import java.io.*;
import java.util.ArrayList;

public class JSONFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapes, String filepath) {
        JSONArray list = new JSONArray();


        try
        {
            FileWriter file = new FileWriter(filepath);

            for(int i=0; i<shapes.size(); i++)
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("name", ((AbstractShape)shapes.get(i)).getName());
                jsonObject.put("color", shapes.get(i).getColor().toString());
                jsonObject.put("strokeWidth", new Double(shapes.get(i).getStrokeWidth()));
                jsonObject.put("fillColor", shapes.get(i).getFillColor().toString());

                if(shapes.get(i) instanceof Line)
                {
                    jsonObject.put("start-x", ((Line)shapes.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Line)shapes.get(i)).getStartPoint().getY());
                    jsonObject.put("emd-x", ((Line)shapes.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Line)shapes.get(i)).getEndPoint().getY());
                }
                else if(shapes.get(i) instanceof Rectangle)
                {
                    jsonObject.put("start-x", ((Rectangle)shapes.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Rectangle)shapes.get(i)).getStartPoint().getY());
                    jsonObject.put("emd-x", ((Rectangle)shapes.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Rectangle)shapes.get(i)).getEndPoint().getY());
                }

                list.add(jsonObject);
            }

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
