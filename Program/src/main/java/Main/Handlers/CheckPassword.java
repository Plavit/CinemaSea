/*
 * Copyright (C) 2016 CinemaSea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Main.Handlers;

/**
 *
 * @author Szeles Marek, Löffler David
 */
public class CheckPassword {
    
    /**
     * Method checking password validity.
     * 
     * Accepts @pass as entered password, @checkPass as a control 
     * re-typing value of password
     * 
     * @param pass password entered by user
     * @param checkPass password re-typed by the user in the check password
     *        field.
     * @return status message (error info, or 'OK')
     */
    
    public static String checkPasswords(String pass, String checkPass) {
        String msg = "";
        if(pass.isEmpty()){
            msg="You need to choose your password!";
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
