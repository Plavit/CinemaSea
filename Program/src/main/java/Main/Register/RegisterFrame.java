package Main.Register;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Implements a registration form that lets a user create a new profile in the program, using a login name and password
 * @author Löffler David, Szeles Marek
 */
public class RegisterFrame extends javax.swing.JFrame
{
    public RegisterFrame() 
    {        
        
        JFrame frame = new JFrame("CINSEA - Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new RegisterPane());
        frame.pack();
        frame.setBounds(0,0,350,300);
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
