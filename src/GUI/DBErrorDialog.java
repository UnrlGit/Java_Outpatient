/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JOptionPane;

/**
 *
 * @author Chaos
 */
public class DBErrorDialog extends JOptionPane{

    public DBErrorDialog() {
        Start("Database error.");
    }
    public DBErrorDialog(String message) {
        Start(message);
    }

    private void Start(String message) {
        JOptionPane.showMessageDialog(this,
    message,
    "WARNING",
    JOptionPane.WARNING_MESSAGE);
    }
    
    

    
    
    
    
}
