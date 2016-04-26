/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Login;

import Main.Database;
import Main.User;
import Main.mainframe;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

/**
 *
 * @author Löffler David, Szeles Marek
 * 
 */
public class LoginPane extends JPanel implements ActionListener{
    
    JPanel btnPane = new JPanel();
    JButton btnLogin = new JButton("SIGN IN");
    JButton btnRegister = new JButton("SIGN UP");
    JTextField nickField = new JTextField(20);
    JPasswordField passField = new JPasswordField(20);
    JLabel nickLabel = new JLabel("Nickname:");
    JLabel passLabel = new JLabel("Password:");
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel label;

    public LoginPane() {
        
        setLayout(new GridBagLayout());
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(new registerAction());
        
        gbc.insets = new Insets(5, 5, 5, 5);

        add(nickLabel, gbc);

        gbc.gridy = 1;
        add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nickField, gbc);

        gbc.gridy = 1;
        add(passField, gbc);

        btnPane.add(btnLogin);
        btnPane.add(btnRegister);

        gbc.gridy = 2;
        add(btnPane, gbc);
        
        ImageIcon imageIcon = new ImageIcon("D:\\wheel.gif");
        label = new JLabel(imageIcon);        
        gbc.gridx = 0;        
        add(label,gbc);
        label.setVisible(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = null;
        String nick = nickField.getText();
        char[] pswC = passField.getPassword();
        String psw = String.valueOf(pswC);
        
        
        
        label.setVisible(true);
        
        try {
            psw = HashPSW(psw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(LoginPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Database db = new Database();
        user = db.login(psw,nick);
        
        if(user != null){
            System.out.println("SUCCESFULY LOGGED");
           
            mainframe mf = new mainframe();
            mf.setUser(user);
            
           // TODO
           // OTEVRIT MAINFRAME A SPUSTIT VLAKNA NA NATAHANI DAT Z DATABAZE
           
        }
        else {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Nickname or password is not valid!",
                    "Login error",
                    JOptionPane.ERROR_MESSAGE);
        }
        
        label.setVisible(false);
        

    }
    
    private String HashPSW(String init) throws InvalidKeySpecException, NoSuchAlgorithmException{
        byte[] salt = {15,32,54,3,45,2,5,3,1,4,87,9,6,89,99,17};
        KeySpec spec = new PBEKeySpec(init.toCharArray(), salt, 98434, 256);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        /*
        System.out.printf("salt: %s%n", enc.encodeToString(salt));
        System.out.printf("hash: %s%n", enc.encodeToString(hash));
        */
        return enc.encodeToString(hash);
    }

}
