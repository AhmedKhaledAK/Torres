package sample.model;

public class ShapeFactory {

    public Shape createShape(String inst){
        if(inst.equals("line")){
            return new Line();
        }else if(inst.equals("rect")) {
            return new Rectangle();
        }else if(inst.equals("circle")){

        }
        return null;
    }
}
