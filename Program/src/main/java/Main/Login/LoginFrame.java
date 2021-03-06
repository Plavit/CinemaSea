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
 * Class representing the login window shown at program startup
 *
 * @author Löffler David, Szeles Marek
 */
public class LoginFrame implements ActionListener{
    
    JFrame frame = new JFrame("CINSEA - Login");
    JPanel logoPane = new JPanel();
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
    
    public LoginFrame() throws IOException {        
        
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

       
        frame.getRootPane().setDefaultButton(btnLogin);
        //LoginFrame.setDefaultButton(btnLogin);

 
        JLabel logoLabel = new JLabel(new ImageIcon(".\\src\\main\\java\\Main\\Resources\\Logo_label_small.png"));

        LoginPane.setLayout(new GridBagLayout());
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(signupListener);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridwidth=2;
        logoPane.add(logoLabel);

        LoginPane.add(logoPane, gbc);
        
        gbc.gridwidth=1;
        gbc.gridy = 1;
        LoginPane.add(nickLabel, gbc);

        gbc.gridy = 2;
        LoginPane.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        LoginPane.add(nickField, gbc);

        gbc.gridy = 2;
        LoginPane.add(passField, gbc);

        btnPane.add(btnLogin);
        btnPane.add(btnRegister);

        gbc.gridy = 3;
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
            
            try {
                if(db.updateViews()){                
                    mainframe mf = new mainframe();
                    frame.dispose();
                    mf.setMainFrame(user);            
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }           
            
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
            
            try {
                RegisterFrame rf = new RegisterFrame();
            } catch (IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.dispose();

        }
    };  

}
