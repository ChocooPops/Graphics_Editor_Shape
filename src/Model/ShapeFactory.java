
package Model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class ShapeFactory {
    
    
    public Shape dessinerCercle(ShapeManager data, Color color){
        Random rand= new Random(System.currentTimeMillis());
        Circle circle = new Circle(new Point(rand.nextInt(250), rand.nextInt(300)), color);
        return circle; 
    }
    
    public Shape dessinerCarre(ShapeManager data, Color color){
        Random rand= new Random(System.currentTimeMillis());
        Square square = new Square(new Point(rand.nextInt(250), rand.nextInt(300)), color);
        return square;
    }
    
    public Shape dessinerRectangle(ShapeManager data, Color color){
        Random rand= new Random(System.currentTimeMillis());
        Rectangle rectangle = new Rectangle(new Point(rand.nextInt(250), rand.nextInt(300)), color);
        return rectangle;
    }
}
