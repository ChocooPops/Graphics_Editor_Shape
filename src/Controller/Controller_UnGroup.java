
package Controller;

import Model.ShapeManager;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class Controller_UnGroup {
    private ShapeManager data; 
    
    public Controller_UnGroup(ShapeManager d){
        this.data = d; 
    }
    
    
    public void control(String[] tableauId, JTextPane panneau){
        if(tableauId != null && !tableauId[0].equals("") && !tableauId[0].equals(" ")){
            for(int i=0; i<tableauId.length; i++){
                data.add(data.getShapeById(tableauId[i]));
                data.remove(tableauId[i]);
            }
            if(tableauId.length > 1){
                panneau.setText( Integer.toString(tableauId.length) + " éléments ont été déplacés à la racine");
            }else{
                panneau.setText("1 élément a été déplacé à la racine");
            }
        }
    }
}
