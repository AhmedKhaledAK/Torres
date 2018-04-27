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
                jsonObject.put("start-x", (shapesList.get(i)).getStartPoint().getX());
                jsonObject.put("start-y", shapesList.get(i).getStartPoint().getY());
                jsonObject.put("end-x", shapesList.get(i).getEndPoint().getX());
                jsonObject.put("end-y", shapesList.get(i).getEndPoint().getY());

                if(shapesList.get(i) instanceof Circle)
                {
                    jsonObject.put("center-x", ((Circle)shapesList.get(i)).getCenterPoint().getY());
                    jsonObject.put("center-y",((Circle)shapesList.get(i)).getCenterPoint().getY());
                    jsonObject.put("radius",((Circle)shapesList.get(i)).getRadius());
                }
                else if(shapesList.get(i) instanceof Ellipse)
                {
                    jsonObject.put("center-x", ((Ellipse)shapesList.get(i)).getCenterPoint().getY());
                    jsonObject.put("center-y",((Ellipse)shapesList.get(i)).getCenterPoint().getY());
                    jsonObject.put("radius-x",((Ellipse)shapesList.get(i)).getRadius().getX());
                    jsonObject.put("radius-y",((Ellipse)shapesList.get(i)).getRadius().getY());
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

        double startX;
        double startY;
        double endX;
        double endY;
        double centerX = 0;
        double centerY = 0;
        double radius = 0;
        double radiusX = 0;
        double radiusY = 0;
        try
        {
            JSONArray array = (JSONArray) parser.parse(new FileReader(filepath));
            for (Object o : array) {
                JSONObject jsonObject = (JSONObject) o;
                String name = (String) jsonObject.get("name");
                String color = (String) jsonObject.get("color");
                String fillColor = (String) jsonObject.get("fillColor");
                double strokeWidth = (Double) jsonObject.get("strokeWidth");
                startX = (Double) jsonObject.get("start-x");
                startY = (Double) jsonObject.get("start-y");
                endX = (Double) jsonObject.get("end-x");
                endY = (Double) jsonObject.get("end-y");

                if(name.equals("circle")){
                    centerX = (Double) jsonObject.get("center-x");
                    centerY = (Double) jsonObject.get("center-y");
                    radius = (Double) jsonObject.get("radius");
                }
                else if(name.equals("ellipse")){
                    centerX = (Double) jsonObject.get("center-x");
                    centerY = (Double) jsonObject.get("center-y");
                    radiusX = (Double) jsonObject.get("radius-x");
                    radiusY = (Double) jsonObject.get("radius-y");
                }

                shape = shapeFactory.createShape(name);
                shape.setColor(Color.web(color));
                shape.setFillColor(Color.web(fillColor));
                shape.setStrokeWidth(strokeWidth);
                ((AbstractShape)shape).setName(name);
                shape.setStartPoint(new Point2D(startX, startY));
                shape.setEndPoint(new Point2D(endX, endY));

                switch (name) {
                    case "circle":
                        ((Circle) shape).setCenterPoint(new Point2D(centerX, centerY));
                        ((Circle) shape).setRadius(radius);
                        break;
                    case "ellipse":
                        ((Ellipse) shape).setCenterPoint(new Point2D(centerX, centerY));
                        ((Ellipse) shape).setRadius(new Point2D(radiusX,radiusY));
                        break;
                }
                shapesList.add(shape);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return shapesList;
    }
}