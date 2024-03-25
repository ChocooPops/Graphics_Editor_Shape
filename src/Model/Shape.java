
package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Shape {
    
    protected Point center; 
    protected double radius; 
    protected Color color; 
    protected String id; 
    protected static int incrementation = 0; 
    
    public Shape(Point p, Color c){
        this.center = p; 
        this.radius = 100; 
        this.color = c; 
        this.id = Integer.toString(Shape.incrementation); 
        Shape.incrementation ++; 
        
    }
    
    public abstract String getType(); 
    public abstract void draw(Graphics g); 
    
     public String toString(int padding) {
        String str = new String();
        str += getType() + "(" + this.center +")";
        return str;
    }
     
    public DefaultMutableTreeNode getJTreeNodes() {
        return new DefaultMutableTreeNode(getType()+ "(Center["+center.x+", "+center.y +"], R["+radius+"]" + " " + this.id);
    }
    public Rectangle getBox(){
        return new Rectangle((int)(center.x), (int)(center.y ), (int)(radius), (int)( radius)); 
    }
    public String getId(){
        return this.id; 
    }
    
    public void setColor(Color new_color){
        this.color = new_color; 
    }
    public void setRadius(Double new_radius){
        this.radius = new_radius; 
    }
    public void setCenterX(int new_x){
        this.center.x = new_x;  
    }
    public void setCenterY(int new_y){
        this.center.y = new_y; 
    }
    
    public int getRadius(){
        return (int) this.radius; 
    }
    public int getCenterX(){
        return this.center.x; 
    }
    public int getCenterY(){
        return this.center.y;
    }
    public Color getColor(){
        return this.color; 
    }
    
    public Point getPoint(){
        return this.center; 
    }
}
