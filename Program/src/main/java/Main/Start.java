/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Login.LoginFrame;


/**
 *
 * @author leffl_000
 */
public class Start {
    
    public static void main(String[] args){
        Database db = new Database();
        db.ConnectToServer();
    }
    
}
