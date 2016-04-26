/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Login;

import Main.Database;
import Main.Register.RegisterFrame;
import Main.User;
import Main.mainframe;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class LoginFrame implements ActionListener{
    
    JFrame frame = new JFrame("CINSEA - Login");
    JPanel btnPane = new JPanel();
    JButton btnLogin = new JButton("SIGN IN");
    JButton btnRegister = new JButton("SIGN UP");
    JTextField nickField = new JTextField(20);
    JPasswordField passField = new JPasswordField(20);
    JLabel nickLabel = new JLabel("Nickname:");
    JLabel passLabel = new JLabel("Password:");
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel LoginPane = new JPanel();
    Database db = new Database();
    
    public LoginFrame() {        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        initComponents();
        frame.pack();
        frame.setBounds(0,0,350,200);
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
        frame.setVisible(true);      
        
    }
    
    private void initComponents(){        
        
        LoginPane.setLayout(new GridBagLayout());
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(signupListener);
        
        gbc.insets = new Insets(5, 5, 5, 5);

        LoginPane.add(nickLabel, gbc);

        gbc.gridy = 1;
        LoginPane.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        LoginPane.add(nickField, gbc);

        gbc.gridy = 1;
        LoginPane.add(passField, gbc);

        btnPane.add(btnLogin);
        btnPane.add(btnRegister);

        gbc.gridy = 2;
        LoginPane.add(btnPane, gbc);
        
        frame.add(LoginPane);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        User user = null;
        String nick = nickField.getText();
        char[] pswC = passField.getPassword();
        String psw = String.valueOf(pswC);
        
        try {
            psw = db.HashPSW(psw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        user = db.login(psw,nick);
        
        if(user != null){
            System.out.println("SUCCESFULY LOGGED");
            db.updateViews();
            mainframe mf = new mainframe();            
            frame.dispose();
            mf.setMainFrame(user);
        }
        else {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Nickname or password is not valid!",
                    "Login error",
                    JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    ActionListener signupListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
            RegisterFrame rf = new RegisterFrame();
            frame.dispose();

        }
    };  

}
