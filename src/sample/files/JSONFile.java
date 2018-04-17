package sample.files;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
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
    }

    @Override
    public ArrayList<Shape> load(String filepath) {
        JSONParser parser = new JSONParser();
        ArrayList<Shape> shapes = new ArrayList<>();
        ShapeFactory shapeFactory = new ShapeFactory();
        double startX=0;
        double startY=0;
        double endX=0;
        double endY=0;
        try
        {
            JSONArray array = (JSONArray) parser.parse(new FileReader(filepath));
            for (Object o : array) {

                JSONObject jsonObject2 = (JSONObject) o;
                String name = (String) jsonObject2.get("name");
                String color = (String) jsonObject2.get("color");
                String fillColor = (String) jsonObject2.get("fillColor");
                double strokeWidth = (Double) jsonObject2.get("strokeWidth");
                if(name.equals("line") || name.equals("rectangle"))
                {
                    startX = (Double) jsonObject2.get("start-x");
                    startY = (Double) jsonObject2.get("start-y");
                    endX = (Double) jsonObject2.get("end-x");
                    endY = (Double) jsonObject2.get("end-y");
                }
                Shape shape = shapeFactory.createShape(name);
                ((AbstractShape)shape).setName(name);
                shape.setColor(Color.web(color));
                shape.setFillColor(Color.web(fillColor));
                shape.setStrokeWidth(strokeWidth);
                if(name.equals("line"))
                {
                    ((Line)shape).setStartPoint(new Point2D(startX,startY));
                    ((Line)shape).setEndPoint(new Point2D(endX,endY));
                }
                else if (name.equals("rectangle"))
                {
                    ((Rectangle)shape).setStartPoint(new Point2D(startX,startY));
                    ((Rectangle)shape).setEndPoint(new Point2D(endX,endY));
                }
                shapes.add(shape);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return shapes;
    }
}
