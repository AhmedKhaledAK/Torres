package sample.model;

public class ShapeFactory {

    public Shape createShape(String inst){
        switch (inst) {
            case "line":
                return new Line();
            case "rectangle":
                return new Rectangle();
            case "circle":
                return new Circle();
            case "square":
                return new Square();
            case "ellipse":
                return new Ellipse();
        }
        return null;
    }
}
