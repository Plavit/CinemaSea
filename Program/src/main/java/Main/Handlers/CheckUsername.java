/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Handlers;

import Main.Database;

/**
 *
 * @author Szeles Marek, Loffler David
 */
public class CheckUsername {
    static Database db = new Database();
    
    public static String checkUsername(String username) {
        String msg = "";
        if(username.isEmpty()){
            msg="You need to choose your username!";
            return msg;
        } else if(db.isUserRegistered(username)) {
            msg="Username already taken!";
            return msg;
        } else {
            msg="OK";
            return msg;
        }
    }
}
