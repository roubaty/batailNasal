/*
 * Main.java
 *
 * Created on January 10, 2007, 1:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import controller.DefaultController;
import model.DocumentModel;
import model.TextElementModel;
import view.DisplayViewPanel;
import view.PropertiesViewPanel;

/**
 *
 * @author Robert Eckstein
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
        
        TextElementModel textElementModel = new TextElementModel();
        DocumentModel documentModel = new DocumentModel();

        DefaultController controller = new DefaultController();
        
        DisplayViewPanel displayViewPanel = new DisplayViewPanel(controller);       
        PropertiesViewPanel propertiesViewPanel = new PropertiesViewPanel(controller);
        
        controller.addView(displayViewPanel);
        controller.addView(propertiesViewPanel);
        controller.addModel(textElementModel);
        controller.addModel(documentModel);
        
        textElementModel.initDefault();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
    }
    
}
