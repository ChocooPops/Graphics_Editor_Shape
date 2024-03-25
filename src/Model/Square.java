package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square extends Shape{

    public Square(Point p, Color c){
        super(p, c); 
    }
    
    @Override
    public String getType() {
        return "Square"; 
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.center.x, this.center.y, (int)radius, (int)radius);
        g.setColor(Color.black);
        g.drawRect(this.center.x, this.center.y, (int)radius, (int)radius);
    }
}
