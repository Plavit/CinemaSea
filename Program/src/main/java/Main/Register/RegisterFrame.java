/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Register;

import Main.Database;
import Main.Login.LoginFrame;
import Main.User;
import Main.mainframe;
import Main.Handlers.CheckPassword;
import Main.Handlers.CheckUsername;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class RegisterFrame implements ActionListener{
    
    JLabel logoLabel = new JLabel(new ImageIcon(".\\src\\main\\java\\Main\\Resources\\Logo_label_small.png"));
    JFrame frame = new JFrame("CINSEA - Register");
    JPanel logoPane = new JPanel();
    JPanel btnPane = new JPanel();
    JButton btnLogin = new JButton("SIGN IN");
    JButton btnRegister = new JButton("SIGN UP");
    JTextField nickField = new JTextField(20);
    JPasswordField passField = new JPasswordField(20);
    JPasswordField checkPassField = new JPasswordField(20);
    JLabel nickLabel = new JLabel("Nickname:");
    JLabel passLabel = new JLabel("Password:");
    JLabel checkPassLabel = new JLabel("Retype pass:");
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel RegPane = new JPanel();
    Database db = new Database();
    
    public RegisterFrame() throws IOException {        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        initComponents();
        frame.pack();
        frame.setBounds(0,0,350,220);
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
    }
    
    private void initComponents(){        
       
        
        RegPane.setLayout(new GridBagLayout());
        btnLogin.addActionListener(signinListener);
        btnRegister.addActionListener(this);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridwidth=2;
        logoPane.add(logoLabel);

        RegPane.add(logoPane, gbc);
        
        gbc.gridwidth=1;
        gbc.gridy = 1;
        RegPane.add(nickLabel, gbc);

        gbc.gridy = 2;
        RegPane.add(passLabel, gbc);
        
        gbc.gridy = 3;
        RegPane.add(checkPassLabel, gbc);


        gbc.gridx = 1;
        gbc.gridy = 1;
        RegPane.add(nickField, gbc);

        gbc.gridy = 2;
        RegPane.add(passField, gbc);
        
        gbc.gridy = 3;
        RegPane.add(checkPassField, gbc);

        btnPane.add(btnLogin);
        btnPane.add(btnRegister);

        gbc.gridy = 4;
        RegPane.add(btnPane, gbc);
        
        frame.add(RegPane);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        User user = null;
        String nick = nickField.getText();
        char[] pswC = passField.getPassword();
        char[] checkPswC = checkPassField.getPassword();
        String pass = String.valueOf(pswC);
        String checkPass = String.valueOf(checkPswC);
        
        //TODO: check if username valid and not taken
        String valCheckOutcome=CheckUsername.checkUsername(nick);
        if("OK".equals(valCheckOutcome)){
            //check if passwords are valid
            valCheckOutcome=CheckPassword.checkPasswords(pass,checkPass);
            if("OK".equals(valCheckOutcome)){
                try {
                    pass = db.HashPSW(pass);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                }        

                db.register(pass,nick);
                user = db.login(pass,nick);

                if(user != null){
                    System.out.println("SUCCESFULY REGISTERED AND LOGGED");

                    try {
                        if(db.updateViews()){                
                            mainframe mf = new mainframe();
                            frame.dispose();
                            mf.setMainFrame(user);            
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }           

                }
            }
        }
        if(valCheckOutcome=="OK"){
            
        } else {
            System.out.println("error signup");
            JOptionPane.showMessageDialog(new JFrame(),
                    valCheckOutcome,
                    "Signup error",
                    JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    ActionListener signinListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
            try {
                LoginFrame rf = new LoginFrame();
            } catch (IOException ex) {
                Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.dispose();

        }
    };  

}
