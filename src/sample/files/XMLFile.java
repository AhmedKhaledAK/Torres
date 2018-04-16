package sample.files;

import sample.model.ISaveLoadStrategy;
import sample.model.Shape;

import java.util.ArrayList;

public class XMLFile  implements ISaveLoadStrategy {
    @Override
    public void save(ArrayList<Shape> shapes) {

    }

    @Override
    public ArrayList<Shape> load() {
        return null;
    }
}
