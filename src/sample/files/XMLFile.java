package sample.files;

import sample.model.ISaveLoadStrategy;
import sample.model.Shape;

import java.util.ArrayList;

public class XMLFile  implements ISaveLoadStrategy {

    @Override
    public void save(ArrayList<Shape> shapes, String filepath) {

    }

    @Override
    public ArrayList<Shape> load() {
        return null;
    }
}
