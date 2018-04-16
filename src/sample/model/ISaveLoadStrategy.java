package sample.model;

import sample.model.Shape;

import java.util.ArrayList;

public interface ISaveLoadStrategy {
    void save(ArrayList<Shape> shapes);
    ArrayList<Shape> load();
}