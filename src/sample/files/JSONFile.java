package sample.files;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.model.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class JSONFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapesList, String filepath) {
        JSONArray list = new JSONArray();
        JSONObject jsonObject;
        try
        {
            FileWriter file = new FileWriter(filepath);
            for(int i=0; i<shapesList.size(); i++)
            {
                jsonObject = new JSONObject();
                jsonObject.put("name", ((AbstractShape)shapesList.get(i)).getName());
                jsonObject.put("color", shapesList.get(i).getColor().toString());
                jsonObject.put("strokeWidth", new Double(shapesList.get(i).getStrokeWidth()));
                jsonObject.put("fillColor", shapesList.get(i).getFillColor().toString());

                if(shapesList.get(i) instanceof Line)
                {
                    jsonObject.put("start-x", ((Line)shapesList.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Line)shapesList.get(i)).getStartPoint().getY());
                    jsonObject.put("end-x", ((Line)shapesList.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Line)shapesList.get(i)).getEndPoint().getY());
                }
                else if(shapesList.get(i) instanceof Rectangle)
                {
                    jsonObject.put("start-x", ((Rectangle)shapesList.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Rectangle)shapesList.get(i)).getStartPoint().getY());
                    jsonObject.put("end-x", ((Rectangle)shapesList.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Rectangle)shapesList.get(i)).getEndPoint().getY());
                }else if(shapesList.get(i) instanceof Circle)
                {
                    jsonObject.put("start-x", ((Circle)shapesList.get(i)).getStartPoint().getX());
                    jsonObject.put("start-y", ((Circle)shapesList.get(i)).getStartPoint().getY());
                    jsonObject.put("end-x", ((Circle)shapesList.get(i)).getEndPoint().getX());
                    jsonObject.put("end-y", ((Circle)shapesList.get(i)).getEndPoint().getY());
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
        ArrayList<Shape> shapesList = new ArrayList<>();
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape;

        double startX=0;
        double startY=0;
        double endX=0;
        double endY=0;
        try
        {
            JSONArray array = (JSONArray) parser.parse(new FileReader(filepath));
            for (Object o : array) {
                JSONObject jsonObject = (JSONObject) o;
                String name = (String) jsonObject.get("name");
                String color = (String) jsonObject.get("color");
                String fillColor = (String) jsonObject.get("fillColor");
                double strokeWidth = (Double) jsonObject.get("strokeWidth");

                if(name.equals("line") || name.equals("rectangle"))
                {
                    startX = (Double) jsonObject.get("start-x");
                    startY = (Double) jsonObject.get("start-y");
                    endX = (Double) jsonObject.get("end-x");
                    endY = (Double) jsonObject.get("end-y");
                }

                shape = shapeFactory.createShape(name);
                shape.setColor(Color.web(color));
                shape.setFillColor(Color.web(fillColor));
                shape.setStrokeWidth(strokeWidth);
                ((AbstractShape)shape).setName(name);

                switch (name) {
                    case "line":
                        ((Line) shape).setStartPoint(new Point2D(startX, startY));
                        ((Line) shape).setEndPoint(new Point2D(endX, endY));
                        System.out.println(((Line) shape).getStartPoint().toString() + " " +
                                ((Line) shape).getEndPoint().toString());
                        break;
                    case "rectangle":
                        ((Rectangle) shape).setStartPoint(new Point2D(startX, startY));
                        ((Rectangle) shape).setEndPoint(new Point2D(endX, endY));
                        break;
                    case "circle":
                        ((Circle) shape).setStartPoint(new Point2D(startX, startY));
                        ((Circle) shape).setEndPoint(new Point2D(endX, endY));
                        break;
                }
                System.out.println(((AbstractShape) shape).getName() + " " +
                        shape.getColor() + " " + shape.getStrokeWidth());

                shapesList.add(shape);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return shapesList;
    }
}
