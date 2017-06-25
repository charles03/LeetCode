package com.system.design.objectOrientedDesign;

/**
 * Created by charles on 9/19/16.
 *
 * Factory is a design pattern in common usage. Implement a ShapeFactory that can generate correct shape.
 You can assume that we have only tree different shapes: Triangle, Square and Rectangle.

 Example
 ShapeFactory sf = new ShapeFactory();
 Shape shape = sf.getShape("Square");
 shape.draw();
 >>  ----
 >> |    |
 >> |    |
 >>  ----

 shape = sf.getShape("Triangle");
 shape.draw();
 >>   /\
 >>  /  \
 >> /____\

 shape = sf.getShape("Rectangle");
 shape.draw();
 >>  ----
 >> |    |
 >>  ----
 */
/**
 * Your object will be instantiated and called as such:
 * ShapeFactory sf = new ShapeFactory();
 * Shape shape = sf.getShape(shapeType);
 * shape.draw();
 */
interface Shape {
    void draw();
}

class Rectangle implements Shape {
    @Override
    public void draw() {

    }
}

class Square implements Shape {
    @Override
    public void draw() {

    }
    // Write your code here
}

class Triangle implements Shape {
    @Override
    public void draw() {

    }
    // Write your code here
}

public class ShapeFactory {
    /**
     * @param shapeType a string
     * @return Get object of type Shape
     */
    public Shape getShape(String shapeType) {
        // Write your code here
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("Rectangle")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("Square")) {
            return new Square();
        } else if (shapeType.equalsIgnoreCase("Triangle")) {
            return new Triangle();
        }
        return null;
    }
}
