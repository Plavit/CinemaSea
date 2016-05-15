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

import Main.Database;

/**
 *
 * @author Szeles Marek, Löffler David
 */
public class CheckUsername {
    static Database db = new Database();
    
    /**
     * Method checking username validity.
     * 
     * @param username the username entered by the user
     * @return status message (error info, or 'OK')
     */
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
