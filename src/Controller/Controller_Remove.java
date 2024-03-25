package Controller;

import Model.ShapeManager;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class Controller_Remove {
    private final ShapeManager data; 
    
    public Controller_Remove(ShapeManager d){
        data = d; 
    }
    
    public void control(String[] tableauId, JTextPane panneau){
        if(tableauId != null && !tableauId[0].equals("") && !tableauId[0].equals(" ")){
            for(int i=0; i<tableauId.length; i++){
                data.remove(tableauId[i]);
            }
            if(tableauId.length > 1){
                panneau.setText( Integer.toString(tableauId.length) + " éléments graphiques ont été supprimés");
            }else{
                panneau.setText("1 élément graphique a été supprimé");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Aucun élément n'a été sélectioné", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }   
}
