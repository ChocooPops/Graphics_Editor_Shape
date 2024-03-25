
package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Rectangle extends Shape{
    
    public Rectangle(Point p, Color c){
        super(p, c);  
    }
    
    @Override
    public String getType() {
        return "Rectangle"; 
    }

    @Override
    public void draw(Graphics g) {
       g.setColor(color);
       g.fillRect(center.x, center.y, (int)(radius*1.4), (int)radius);
       g.setColor(Color.black);
       g.drawRect(center.x, center.y, (int)(radius*1.4), (int)radius);
    } 
}
