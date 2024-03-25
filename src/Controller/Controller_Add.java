
package Controller;

import Model.Circle;
import Model.Rectangle;
import Model.Shape;
import Model.ShapeFactory;
import Model.ShapeManager;
import Model.Square;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Controller_Add {
   
    private final ShapeFactory factory; 
    private final ShapeManager data; 
    
    public Controller_Add(ShapeManager d){
        this.data = d; 
        this.factory = new ShapeFactory(); 
    }
    
    public void control(String name, Color color){
        Shape shape; 
        if(name.equals("Circle")){
            shape = this.factory.dessinerCercle(this.data, color);  
        }else if(name.equals("Square")){
            shape = this.factory.dessinerCarre(this.data, color); 
        }else if(name.equals("Rectangle")){
            shape = this.factory.dessinerRectangle(this.data, color); 
        }else{
            JOptionPane.showMessageDialog(null, "Aucun graphique n'a été sélectioné", "Avertissement", JOptionPane.WARNING_MESSAGE);
            shape = null; 
        }
        data.add(shape);
    }
}
