package sample.files;

import sample.model.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                    jsonObject.put("end-x", ((Line)shapes.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Line)shapes.get(i)).getEndPoint().getY());
                }
                else if(shapes.get(i) instanceof Rectangle)
                {
                    jsonObject.put("start-x", ((Rectangle)shapes.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Rectangle)shapes.get(i)).getStartPoint().getY());
                    jsonObject.put("end-x", ((Rectangle)shapes.get(i)).getEndPoint().getX());
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

        System.out.println("loading...");
        JSONParser parser = new JSONParser();

        try
        {
            JSONArray array = (JSONArray) parser.parse(new FileReader(filepath));
            for (Object o : array) {
                JSONObject jsonObject2 = (JSONObject) o;
                String name = (String) jsonObject2.get("name");
                String color = (String) jsonObject2.get("color");
                String fillColor = (String) jsonObject2.get("fillColor");
                double startX = (Double) jsonObject2.get("start-x");
                double startY = (Double) jsonObject2.get("start-y");
                double endX = (Double) jsonObject2.get("end-x");
                double endY = (Double) jsonObject2.get("end-y");

                System.out.println(name + " " + color + " " + fillColor
                        + " " + startX +" " +  startY + " " +  endX + " " +  endY);

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Shape> load() {
        return null;
    }
}
