
package Controller;

import Model.ShapeManager;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;


public class Controller_ModifColor {
    private ShapeManager data;
    
    public Controller_ModifColor(ShapeManager d) {
        this.data = d; 
    }

    public void control(String[] tableauId, Color color, JTextPane panneau) {
        if(tableauId != null && !tableauId[0].equals("") && !tableauId[0].equals(" ")){
            for(int i=0; i<tableauId.length; i++){
                data.modifShapeColor(tableauId[i], color);
                panneau.setText("Changement de couleur : " + color);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Aucun élément n'a été sélectioné", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }
}
