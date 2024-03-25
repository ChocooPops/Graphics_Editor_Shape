package Controller;

import Model.ShapeManager;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class Controller_ModifCenter {
    private ShapeManager data;
    
    public Controller_ModifCenter(ShapeManager d){
        this.data = d; 
    }
    
    public void control(String[] tableauId, int new_x, int new_y, boolean op, JTextPane panneau){
        if(tableauId != null && !tableauId[0].equals("") && !tableauId[0].equals(" ")){
            for(int i=0; i<tableauId.length; i++){
                data.modifShapeCenter(tableauId[i], new_x, new_y, op);
                panneau.setText("Changement de coordonée : X:" + new_x + "Y:"+new_y);  
            }
        }else{
            JOptionPane.showMessageDialog(null, "Aucun élément n'a été sélectioné", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }
}
