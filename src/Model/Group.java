package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Group extends Shape{
    private ArrayList<Shape> shape = new ArrayList<>(); 
    
    public Group(Point p, Color c){
        super(p, c); 
    }
    public void add(Shape newShape){
        this.shape.add(newShape); 
    }
    public void remove(Shape oldShape, Group g){
        g.shape.remove(oldShape); 
    }
    
    public Shape getRacine(){
        return this.shape.get(0); 
    }
    
    public Shape shapeSuivant(){
        return this.shape.get(shape.size()-1); 
    }
    public ArrayList<Shape> getList(){
        return this.shape; 
    }
    
   public Shape getShapeById(String id) {
        for (Shape s : this.shape) {
            if (s.getId().equals(id)) {
                return s;
            } else if (s instanceof Group) {
                Shape foundShape = ((Group) s).getShapeById(id);
                if (foundShape != null) {
                    return foundShape;
                }
            }
        }
        return null;
    }
    
    public Shape getShapeIntoListe(Shape newShape, Group g){
        Shape shape = null; 
        for (Shape s : g.shape){
            if(newShape == s){
                shape = s; 
            }
        }
        return shape; 
    }
   
    public void modifColor(Shape shape, Color new_Color, Group g){
        getShapeIntoListe(shape, g).setColor(new_Color); 
    }
    public void modifRadius(Shape shape, Double new_radius, Group g){
        getShapeIntoListe(shape, g).setRadius(new_radius);
    }
    public void modifCenterX(Shape shape, int new_x, Group g){
        getShapeIntoListe(shape, g).setCenterX(new_x); 
    }
    public void modifCenterY(Shape shape, int new_y, Group g){
        getShapeIntoListe(shape, g).setCenterY(new_y);
    }

    @Override
    public String getType() {
        return "Groupe"; 
    }

     @Override
   public void draw(Graphics g) {
        for (Shape shape : this.shape) {
            shape.draw(g);
        }
    }
    
    public boolean containsShape(Shape shape) {
        return this.shape.contains(shape);
    }
    
    
    
    public void group(Shape selectedShape) {
       
        if (!(selectedShape instanceof Group)) {
            
            Group newGroup = new Group(selectedShape.getPoint(), selectedShape.getColor());
            newGroup.add(selectedShape);

            shape.remove(selectedShape);

            shape.add(newGroup);
        }
    }
    
     public Group getGroupForShape(String id) {
        for (Shape s : this.shape) {
            if (s.getId().equals(id)) {
                return this;
            } else if (s instanceof Group) {
                Group groupForShape = ((Group) s).getGroupForShape(id);
                if (groupForShape != null) {
                    return groupForShape;
                }
            }
        }
        return null;
    }
}
