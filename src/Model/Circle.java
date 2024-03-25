package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Shape {
   
    public Circle(Point p, Color c){
       super(p, c); 
    }

    @Override
    public String getType(){
        return "Circle"; 
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(center.x,center.y,(int)radius,(int)radius);
        g.setColor(Color.black);
        g.drawOval(center.x,center.y,(int)radius,(int)radius);
    }

}
