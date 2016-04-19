/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author leffl_000
 */
public class LoginPane extends JPanel{
    
    public LoginPane(){
        setLayout(new GridBagLayout());
        JPanel btnPane = new JPanel();

        JButton btnLogin = new JButton("SIGN IN");
        JButton btnRegister = new JButton("SIGN UP");
        JTextField nickField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JLabel nickLabel = new JLabel("Nickname:");
        JLabel passLabel = new JLabel("Password:");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        add(nickLabel,gbc);
        
        gbc.gridy = 1;        
        add(passLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nickField,gbc);
        
        gbc.gridy = 1;
        add(passField,gbc);
        
        btnPane.add(btnLogin);
        btnPane.add(btnRegister);

        gbc.gridy = 2;
        add(btnPane,gbc);


    }
    
}
