package sample.files;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sample.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLFile implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapesList, String filepath) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("shapes");
        document.setRootElement(root);
        Element element;
        try
        {
            FileWriter file = new FileWriter(filepath);
            for (Shape aShapesList : shapesList)
            {
                element = document.getRootElement().addElement("element").addAttribute(
                        "name", ((AbstractShape) aShapesList).getName()).addAttribute(
                        "color", aShapesList.getColor().toString()).addAttribute(
                        "strokeWidth", aShapesList.getStrokeWidth() + "").addAttribute(
                        "fillColor", aShapesList.getFillColor().toString());
                if (aShapesList instanceof Line)
                {
                    element = element.addAttribute("start-x",
                            ((Line) aShapesList).getStartPoint().getX() + "").addAttribute(
                            "start-y", ((Line) aShapesList).getStartPoint().getY() + "").addAttribute(
                            "end-x", ((Line) aShapesList).getEndPoint().getX() + "").addAttribute(
                            "end-y", ((Line) aShapesList).getEndPoint().getY() + "");
                }
                else if (aShapesList instanceof Rectangle)
                {
                    element = element.addAttribute("start-x",
                            ((Rectangle) aShapesList).getStartPoint().getX() + "").addAttribute(
                            "start-y", ((Rectangle) aShapesList).getStartPoint().getY() + "").addAttribute(
                            "end-x", ((Rectangle) aShapesList).getEndPoint().getX() + "").addAttribute(
                            "end-y", ((Rectangle) aShapesList).getEndPoint().getY() + "");
                }
            }
            document.write(file);
            file.flush();
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Shape> load(String filepath) {
        
        return null;
    }
}
