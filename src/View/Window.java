package View;

import Controller.Controller_Add;
import Controller.Controller_ModifCenter;
import Controller.Controller_ModifColor;
import Controller.Controller_ModifRadius;
import Controller.Controller_Remove;
import Controller.Controller_UnGroup;
import Model.Group;
import Model.Shape;
import Model.ShapeManager;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JColorChooser;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Window extends javax.swing.JFrame implements Observer {

    private final GraphicsPainter painter;
    private ShapeManager data;
    private boolean radiusOn = false;  
    private String shapeId; 
    private DefaultTreeModel treeModel;
    
    public Window(ShapeManager _data) { 
        initComponents();
        shapeId = ""; 
        data = _data;
        data.addObserver(this);
        
        painter = new GraphicsPainter(data);

        jPanel1.setLayout(new java.awt.GridLayout(1, 1));
        jPanel1.add(painter);
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Empty");
        treeModel = new DefaultTreeModel(root);
        jTree_Objects.setModel(treeModel);
        
        
        ActionBoutonRemove(); 
        activerJSpinner(); 
        actionJSpinner(); 
        actionJSPinnerCenter(); 
        ActionBoutonGroup(); 
        ActionBoutonUnGroup(); 
        pack();
    }
    
    public void ActionBoutonUnGroup() {
           this.jButton_UnGroup.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   TreePath[] selectedPaths = jTree_Objects.getSelectionPaths();
                   boolean op = false; 
                   if (selectedPaths != null && selectedPaths.length >= 1) {
                       data.ungroupSelectedNodes(selectedPaths);
                   }
                    String [] tab = null;  
                    if(jTree_Objects.getSelectionPaths()!= null){
                        tab = new String[selectedPaths.length]; 
                        int i = 0; 
                        if (selectedPaths != null) {
                            for (TreePath selectedPath : selectedPaths) {
                                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                                String selectedValue = selectedNode.getUserObject().toString();
                                tab[i] = selectedValue.split(" ")[3];
                                i++; 
                            }
                        }
                    }           
                    new Controller_UnGroup(data).control(tab, jTextPaneInformations);
               }
           });
    }
    
    public void ActionBoutonGroup(){
        this.jButton_Group.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                TreePath[] selectedPaths = jTree_Objects.getSelectionPaths();
                if (selectedPaths != null && selectedPaths.length > 1) {
                    data.groupSelectedNodes(selectedPaths);
                }else{
                    String [] tab = null;  
                    if(jTree_Objects.getSelectionPaths()!= null){
                        tab = new String[selectedPaths.length]; 
                        int i = 0; 
                        if (selectedPaths != null) {
                            for (TreePath selectedPath : selectedPaths) {
                                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                                String selectedValue = selectedNode.getUserObject().toString();
                                tab[i] = selectedValue.split(" ")[3];
                                i++; 
                            }
                        }
                        data.groupSelectedNode(tab[0]);
                        if(tab.length > 1){
                            jTextPaneInformations.setText(tab.length + "élements ont été groupés");
                        }else{
                            jTextPaneInformations.setText("1 élement a été groupé");
                        }
                        
                    }    
                }
            } 
        }); 
    }
   
    public void Remove(){
        Controller_Remove controller = new Controller_Remove(data); 
                String [] tab = null;  
                if(jTree_Objects.getSelectionPaths()!= null){
                    TreePath[] selectedPaths = jTree_Objects.getSelectionPaths();
                    tab = new String[selectedPaths.length]; 
                    int i = 0; 
                    if (selectedPaths != null) {
                        for (TreePath selectedPath : selectedPaths) {
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                            String selectedValue = selectedNode.getUserObject().toString();
                            tab[i] = selectedValue.split(" ")[3];
                            i++; 
                        }
                    }
                }           
                controller.control(tab, jTextPaneInformations);
    }
    public void ActionBoutonRemove(){
        this.jButton_Remove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Remove(); 
            }
        });
    }
    
    public void actionJSpinner(){
        this.jSpinnerRadius.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                double new_radius = (double) (int) jSpinnerRadius.getValue(); 
                String[] tab = {shapeId}; 
                if(jSpinnerPositionX.isEnabled()){
                     new Controller_ModifRadius(data).control(tab, new_radius, jTextPaneInformations);
                }
            }  
        });
    }
    
    public void actionJSPinnerCenter(){
        this.jSpinnerPositionX.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                int center = (int) jSpinnerPositionX.getValue(); 
                String[] tab = {shapeId}; 
                 if(jSpinnerPositionX.isEnabled()){
                     new Controller_ModifCenter(data).control(tab, center, center, true, jTextPaneInformations);
                 }
            }  
        });
       
        this.jSpinnerPositionY.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                int center = (int) jSpinnerPositionY.getValue(); 
                String[] tab = {shapeId}; 
                if(jSpinnerPositionX.isEnabled()){
                    new Controller_ModifCenter(data).control(tab, center, center, false, jTextPaneInformations);
                }
                
            }  
        }); 
    }
    public void activerJSpinner(){
        this.jTree_Objects.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    
                    TreePath selectedPath = jTree_Objects.getSelectionPath();
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    String selectedValue = selectedNode.getUserObject().toString();
                    String id = selectedValue.split(" ")[3];
                    shapeId = id; 
                    Shape shape = data.getGroupe().getShapeById(id); 
                    
                    jSpinnerPositionX.setValue(shape.getCenterX());
                    jSpinnerPositionY.setValue(shape.getCenterY());
                    jSpinnerRadius.setValue(shape.getRadius());
                    
                    jSpinnerPositionX.setEnabled(true);
                    jSpinnerPositionY.setEnabled(true);
                    jSpinnerRadius.setEnabled(true);
                 
                    jTextPaneInformations.setText(shape.getType() + " :  X : " + shape.getCenterX() + " Y : " + shape.getCenterY() + "Radius :" + shape.getRadius());
                    
                }else{
                    jSpinnerPositionX.setEnabled(false);
                    jSpinnerPositionY.setEnabled(false);
                    jSpinnerRadius.setEnabled(false);
                }
            }
        });
    }
    
    
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton_Remove = new javax.swing.JButton();
        jButton_Group = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree_Objects = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jButton_UnGroup = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerPositionX = new javax.swing.JSpinner();
        jSpinnerPositionY = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSpinnerRadius = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jButtonColor = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPaneInformations = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jRadioCircle = new javax.swing.JRadioButton();
        jRadioSquare = new javax.swing.JRadioButton();
        jRadioRectangle = new javax.swing.JRadioButton();
        jButton_Add = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Graphics Editor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton_Remove.setText("Remove");

        jButton_Group.setText("Group");

        jScrollPane1.setViewportView(jTree_Objects);

        jLabel1.setText("Properties");

        jButton_UnGroup.setText("UnGroup");

        jLabel2.setText("Center");

        jSpinnerPositionX.setEnabled(false);
        jSpinnerPositionX.setMinimumSize(new java.awt.Dimension(80, 22));

        jSpinnerPositionY.setEnabled(false);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("x");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("y");

        jLabel5.setText("Radius");

        jSpinnerRadius.setEnabled(false);

        jLabel6.setText("Color");

        jButtonColor.setBackground(new java.awt.Color(255, 0, 0));
        jButtonColor.setText("   ");
        jButtonColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonColorActionPerformed(evt);
            }
        });

        jTextPaneInformations.setEnabled(false);
        jScrollPane2.setViewportView(jTextPaneInformations);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpinnerPositionX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinnerRadius)
                    .addComponent(jButtonColor, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerPositionY, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton_Remove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Group)
                        .addGap(8, 8, 8)
                        .addComponent(jButton_UnGroup))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerPositionX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerPositionY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinnerRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButtonColor))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Group)
                    .addComponent(jButton_Remove)
                    .addComponent(jButton_UnGroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 100));

        buttonGroup1.add(jRadioCircle);
        jRadioCircle.setText("Circle");

        buttonGroup1.add(jRadioSquare);
        jRadioSquare.setText("Square");
        jRadioSquare.setToolTipText("");

        buttonGroup1.add(jRadioRectangle);
        jRadioRectangle.setText("Rectangle");

        jButton_Add.setText("Add");
        jButton_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioSquare, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioRectangle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Add)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioCircle)
                    .addComponent(jRadioSquare)
                    .addComponent(jRadioRectangle)
                    .addComponent(jButton_Add))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed

        Controller_Add ca = new Controller_Add(data);
        
        if (jRadioCircle.isSelected())
        {
            ca.control("Circle", jButtonColor.getBackground());
            jTextPaneInformations.setText("Circle added");
        }else if(this.jRadioSquare.isSelected()){
            ca.control("Square", jButtonColor.getBackground());
            jTextPaneInformations.setText("Square added");
        }else if(this.jRadioRectangle.isSelected()){
            ca.control("Rectangle", jButtonColor.getBackground());
            jTextPaneInformations.setText("Rectangle added");
        }
    }//GEN-LAST:event_jButton_AddActionPerformed

    private void jButtonColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonColorActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
        
        jButtonColor.setBackground(newColor);
        jTextPaneInformations.setText("Color Changed");
        
        String [] tab = null;  
        if(jTree_Objects.getSelectionPaths()!= null){
            TreePath[] selectedPaths = jTree_Objects.getSelectionPaths();
            tab = new String[selectedPaths.length]; 
            int i = 0; 
            if (selectedPaths != null) {
                for (TreePath selectedPath : selectedPaths) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    String selectedValue = selectedNode.getUserObject().toString();
                    tab[i] = selectedValue.split(" ")[3];
                    i++; 
                }
            }
            new Controller_ModifColor(data).control(tab, newColor, jTextPaneInformations);
        }        
    }//GEN-LAST:event_jButtonColorActionPerformed

    @Override
    public void update(Observable o, Object arg) {
        painter.revalidate();
        painter.repaint();
        
        jTree_Objects.setModel( data.getTreeModel());
        expandAllNodes(jTree_Objects);
        //jTree_Objects.setSelectionPaths(selectionPaths);
        
        // Properties
        jSpinnerPositionX.setEnabled(false);
        jSpinnerPositionY.setEnabled(false);
        jSpinnerRadius.setEnabled(false);
       
    }
    
    private void expandAllNodes(JTree tree) {
        int j = tree.getRowCount();
        int i = 0;
        while(i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonColor;
    private javax.swing.JButton jButton_Add;
    private javax.swing.JButton jButton_Group;
    private javax.swing.JButton jButton_Remove;
    private javax.swing.JButton jButton_UnGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioCircle;
    private javax.swing.JRadioButton jRadioRectangle;
    private javax.swing.JRadioButton jRadioSquare;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerPositionX;
    private javax.swing.JSpinner jSpinnerPositionY;
    private javax.swing.JSpinner jSpinnerRadius;
    private javax.swing.JTextPane jTextPaneInformations;
    private javax.swing.JTree jTree_Objects;
    // End of variables declaration//GEN-END:variables

}
