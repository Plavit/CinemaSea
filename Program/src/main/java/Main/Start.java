/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Login.LoginFrame;
import java.io.IOException;


/**
 *
 * File containing the main class to start up the program by invoking 
 * the login screen.
 * 
 * @author Löffler David, Szeles Marek
 * 
 */
public class Start {
    
    public static void main(String[] args) throws IOException{
        new LoginFrame();
    }
    
}
