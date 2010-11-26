/*
 * TextElementModel.java
 *
 * Created on January 22, 2007, 3:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import controller.DefaultController;
import java.awt.Font;
import java.beans.PropertyChangeEvent;

/**
 * A sample class that mimics some properties found in a text element, including
 * its X and Y position, rotation, opacity, string, and font.
 *
 * @author Robert Eckstein
 */
public class TextElementModel extends AbstractModel{ 
    /**
     * Default constructor
     */
    public TextElementModel(){
    }

    /**
     * Provides the means to set or reset the model to a default state.
     */   
    public void initDefault() {
                
    }

   /** public void setX(Integer x) {
        
        Integer oldX = this.x;
        this.x = x;
        
        firePropertyChange(DefaultController.ELEMENT_X_PROPERTY, oldX, x);
    }*/  
}
    

