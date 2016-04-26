/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Register;

import Main.Database;
import Main.User;
import Main.mainframe;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;

/**
 *
 * @author Löffler David, Szeles Marek
 * 
 */
public class RegisterPane extends JPanel implements ActionListener{
    
    JPanel btnPane = new JPanel();
    JButton btnLogin = new JButton("SIGN IN");
    JButton btnRegister = new JButton("SIGN UP");
    JTextField nickField = new JTextField(20);
    JPasswordField passField = new JPasswordField(20);
    JLabel nickLabel = new JLabel("Nickname:");
    JLabel passLabel = new JLabel("Password:");

    public RegisterPane() {
        setLayout(new GridBagLayout());
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(new registerAction());

        GridBagConstraints gbc = new GridBagConstraints();
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = null;
        String nick = nickField.getText();
        char[] pswC = passField.getPassword();
        String psw = String.valueOf(pswC);
        
        try {
            psw = HashPSW(psw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RegisterPane.class.getName()).log(Level.SEVERE, null, ex);
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
