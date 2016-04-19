/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cinemasea.Enter;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.*;


/**
 *
 * @author leffl_000
 */
public class LoginFrame extends javax.swing.JFrame{

    public LoginFrame() {
        Container c = this.getContentPane();
        JTextField nickField = new JTextField(20);        
        JPasswordField passField = new JPasswordField(20);
        JLabel nickLabel = new JLabel("Nickname:");
        JLabel passLabel = new JLabel("Password:");
        LoginButt logButt = new LoginButt();
        JPanel frame = new JPanel();
        JPanel frame2 = new JPanel();
        
        frame.add(nickLabel);
        frame.add(nickField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(logButt,BorderLayout.CENTER);
        
        this.add(frame);
        
        
    }
    
    
    
}
