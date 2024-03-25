
package tpgraphicseditor;

import Model.ShapeManager;
import View.Window;

public class TPGraphicsEditor {
 
    public static void main(String[] args) {

        ShapeManager data = new ShapeManager();
        
        /* Create and display the form */
        new Window(data).setVisible(true);


    }
    
}
