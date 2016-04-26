/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javax.swing.JFrame;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class mainframe extends JFrame{
    
    private User user;
    private Movie[] allMovies;
    private Database db;
    
    public mainframe(){
        
    }
    
    public void initComponents(){
        
    }
    
    public void setUser(User user){
        this.user = user;
        // SPUSTIT VLAKNA NA NATAHANI DAT
    }
    
    
    
}
