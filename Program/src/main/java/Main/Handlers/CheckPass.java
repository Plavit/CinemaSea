/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Handlers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marek
 */
public class CheckPass {

    public static boolean checkPasswords(String pass, String checkPass) {
        boolean isok = false;
                    JOptionPane.showMessageDialog(new JFrame(),
                    "Nickname or password is not valid!",
                    "Login error",
                    JOptionPane.ERROR_MESSAGE);
        return isok;
    }
}
