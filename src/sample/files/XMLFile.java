package sample.files;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import sample.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapesList, String filepath) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("shapes");
        document.setRootElement(root);
        Element element;
        try {
            FileWriter file = new FileWriter(filepath);
            for (Shape aShapesList : shapesList) {
                element = document.getRootElement().addElement("element").addAttribute(
                        "name", ((AbstractShape) aShapesList).getName()).addAttribute(
                        "color", aShapesList.getColor().toString()).addAttribute(
                        "strokeWidth", aShapesList.getStrokeWidth() + "").addAttribute(
                        "fillColor", aShapesList.getFillColor().toString()).addAttribute("start-x",
                        aShapesList.getStartPoint().getX() + "").addAttribute(
                        "start-y", aShapesList.getStartPoint().getY() + "").addAttribute(
                        "end-x", aShapesList.getEndPoint().getX() + "").addAttribute(
                        "end-y", aShapesList.getEndPoint().getY() + "");

                if (aShapesList instanceof Circle) {
                    element.addAttribute(
                            "center-x", ((Circle) aShapesList).getCenterPoint().getX() + "").addAttribute(
                            "center-y", ((Circle) aShapesList).getCenterPoint().getY() + "").addAttribute(
                            "radius", ((Circle) aShapesList).getRadius() + "");
                } else if (aShapesList instanceof Ellipse) {
                    element.addAttribute(
                            "center-x", ((Ellipse) aShapesList).getCenterPoint().getX() + "").addAttribute(
                            "center-y", ((Ellipse) aShapesList).getCenterPoint().getY() + "").addAttribute(
                            "radius-x", ((Ellipse) aShapesList).getRadius().getX() + "").addAttribute(
                            "radius-y", ((Ellipse) aShapesList).getRadius().getY() + "");
                }
            }
            document.write(file);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Shape> load(String filepath) {
        String[] array = new String[20];
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape;
        ArrayList<Shape> shapesList = new ArrayList<>();
        int j = 0;

        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new FileReader(filepath));
            Element root = document.getRootElement();

            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();

                for (Iterator<Attribute> i = element.attributeIterator(); i.hasNext(); ) {
                    Attribute attribute = i.next();
                    System.out.println(attribute.getValue());

                    array[j++] = attribute.getValue();
                }
                j = 0;

                shape = shapeFactory.createShape(array[0]);
                shape.setColor(Color.web(array[1]));
                shape.setStrokeWidth(Double.parseDouble(array[2]));
                shape.setFillColor(Color.web(array[3]));
                ((AbstractShape) shape).setName(array[0]);
                shape.setStartPoint(new Point2D(Double.parseDouble(array[4]), Double.parseDouble(array[5])));
                shape.setEndPoint(new Point2D(Double.parseDouble(array[6]), Double.parseDouble(array[7])));

                if (shape instanceof Circle) {
                    ((Circle) shape).setCenterPoint(new Point2D(Double.parseDouble(array[8]), Double.parseDouble(array[9])));
                    ((Circle) shape).setRadius(Double.parseDouble(array[10]));
                } else if (shape instanceof Ellipse) {
                    ((Ellipse) shape).setCenterPoint(new Point2D(Double.parseDouble(array[8]), Double.parseDouble(array[9])));
                    ((Ellipse) shape).setRadius(new Point2D(Double.parseDouble(array[10]), Double.parseDouble(array[11])));
                }

                shapesList.add(shape);
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return shapesList;
    }
}
