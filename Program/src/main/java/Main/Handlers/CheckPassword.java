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
public class CheckPassword {

    public static String checkPasswords(String pass, String checkPass) {
        String msg = "";
        if(pass.isEmpty()){
            msg="You need to choose your password!";
            return msg;
        }else if(checkPass.isEmpty()){
            msg="You need to re-enter your password to proceed.";
            return msg;
        }else if(pass.length()<8){
            msg="Password too short! Minimum: 8, Given: " + pass.length();
            return msg;
        }else if(checkPass.isEmpty() || !pass.equals(checkPass)){
            msg="You need to re-enter your password to proceed.";
            return msg;
        }else{
            msg="OK";
            return msg;
        }
    }
}
