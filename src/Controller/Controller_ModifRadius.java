package Controller;

import Model.ShapeManager;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class Controller_ModifRadius {
    private ShapeManager data; 
    
    public Controller_ModifRadius(ShapeManager d) {
       this.data = d; 
    }

    public void control(String[] tableauId, Double new_radius, JTextPane panneau) {
         if(tableauId != null && !tableauId[0].equals("") && !tableauId[0].equals(" ")){
            for(int i=0; i<tableauId.length; i++){
                data.modifShapeRadius(tableauId[i], new_radius);
                panneau.setText("Changement de radius : " + new_radius);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Aucun élément n'a été sélectioné", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }   
}
