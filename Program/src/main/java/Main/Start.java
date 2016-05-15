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
