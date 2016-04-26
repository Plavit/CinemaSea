/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Login;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class LoginFrame extends javax.swing.JFrame{

    public LoginFrame() {        
        
        JFrame frame = new JFrame("CINSEA - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new LoginPane());
        frame.pack();
        frame.setBounds(0,0,350,200);
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
        frame.setVisible(true);      
        
    }
    
    public void exitFrame(boolean exit){
        if(exit) dispose();
    }
    
    
    
}
