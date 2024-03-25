package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ShapeManager extends Observable {
    private Shape root; 
    private Shape selected; 
    private Group groupe;
    
    public ShapeManager()
    {
        root = null; 
        selected = null;
        this.groupe = new Group(new Point(), Color.BLACK); 

    }
    
    public void add(Shape newShape){
        this.groupe.add(newShape);
        if (root == null){
            root = newShape; 
        }else{
            root = this.groupe.shapeSuivant();
        }
        setChanged();
        notifyObservers();
    }

    public void modifShapeColor(String id, Color new_color){
        this.selected = this.groupe.getShapeById(id); 
        this.groupe.modifColor(this.selected, new_color, groupe.getGroupForShape(id));
        setChanged();
        notifyObservers();
    }
    public void modifShapeRadius(String id, Double new_radius){
        this.selected = this.groupe.getShapeById(id); 
        this.groupe.modifRadius(this.selected, new_radius, groupe.getGroupForShape(id));
        setChanged();
        notifyObservers();
    }
    public void modifShapeCenter(String id, int new_x, int new_y, boolean op){
        this.selected = this.groupe.getShapeById(id); 
        if(op){
            this.groupe.modifCenterX(this.selected, new_x, groupe.getGroupForShape(id));
        }else{
            this.groupe.modifCenterY(this.selected, new_y, groupe.getGroupForShape(id));
        }
        
        setChanged();
        notifyObservers();
    }
    
    public void groupSelectedNode(String id) {
        Shape selectedShape = groupe.getShapeById(id);
        groupe.group(selectedShape);

        setChanged();
        notifyObservers();
    }
    
    public void groupSelectedNodes(TreePath[] selectedPaths) {
        Group newGroup = new Group(new Point(), Color.BLACK);

        for (TreePath selectedPath : selectedPaths) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            String selectedValue = selectedNode.getUserObject().toString();
            String id = selectedValue.split(" ")[3];
            Shape selectedShape = groupe.getShapeById(id);

            if (selectedShape != null && !newGroup.containsShape(selectedShape) && !isShapeSelected(id)) {
                newGroup.add(selectedShape);
            }
        }

        if (!newGroup.getList().isEmpty()) {
            // Remove selected shapes from the group
            for (Shape selectedShape : newGroup.getList()) {
                groupe.remove(selectedShape, groupe);
            }

            // Add the new group to the list of shapes
            groupe.add(newGroup);

            setChanged();
            notifyObservers();
        }
    }
    
    public void remove(String id){
        this.selected = this.groupe.getShapeById(id); 
        this.groupe.remove(this.selected, groupe.getGroupForShape(id));
        setChanged(); 
        notifyObservers();
    }
    
    public Shape getShapeById(String id){
        return this.groupe.getShapeById(id);
    }
    
    public void removeSelectedShape(TreePath selectedPath) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        String selectedValue = selectedNode.getUserObject().toString();
        String id = selectedValue.split(" ")[3];
        remove(id);
    }
    @Override
    public String toString() {
        return "Data{\n" + "  shape{\n" + root.toString() + "  }\n}";
    }

    public void draw(Graphics graphics) {
        for(Shape shape : this.groupe.getList()){
            shape.draw(graphics);
        }
    }
    
    public boolean isShapeSelected(String id) {
    return this.selected != null && this.selected.getId().equals(id);
}
    
   public DefaultTreeModel getTreeModel() {
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Root");
        addShapesToTreeNode(rootTreeNode, groupe.getList());
        return new DefaultTreeModel(rootTreeNode);
    }

    private void addShapesToTreeNode(DefaultMutableTreeNode parentTreeNode, Iterable<Shape> shapes) {
        for (Shape shape : shapes) {
            DefaultMutableTreeNode shapeTreeNode = shape.getJTreeNodes();
            parentTreeNode.add(shapeTreeNode);

            if (shape instanceof Group) {
                addShapesToTreeNode(shapeTreeNode, ((Group) shape).getList());
            }
        }
    }
    
    public void ungroupSelectedNodes(TreePath[] selectedPaths) {
    for (TreePath selectedPath : selectedPaths) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            String selectedValue = selectedNode.getUserObject().toString();
            String id = selectedValue.split(" ")[3];
            ungroup(id);
        }
    }

    private void ungroup(String id) {
        Shape selectedShape = groupe.getShapeById(id);
        if (selectedShape instanceof Group) {
            Group selectedGroup = (Group) selectedShape;
            for (Shape s : selectedGroup.getList()) {
                groupe.add(s);
            }
            groupe.remove(selectedGroup, groupe);
            setChanged();
            notifyObservers();
        }
    }
    
    
    
    public Group getGroupe(){
        return this.groupe; 
    }
}
