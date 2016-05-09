/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Handlers;

/**
 *
 * @author Marek
 */
public class CheckUsername {
    
    public static String checkUsername(String username) {
        String msg = "";
        if(username.isEmpty()){
            msg="You need to choose your username!";
            return msg;
            //TODO: Check username taken
        }else{
            msg="OK";
            return msg;
        }
    }
}
