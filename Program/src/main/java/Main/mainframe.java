/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Threads.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class mainframe extends JFrame{
    
    private User user;
    private Movie[] allMovies;
    private Database db;
    
    JProgressBar bar = new JProgressBar();
    JPanel mainPanel = new JPanel();
    
    public mainframe(){
        setTitle("Cinsea - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,800,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void initComponents(){
        
    }
    
    public void setUser(User user){
        this.user = user;
        // SPUSTIT VLAKNA NA NATAHANI DAT
        
        Callable<Movie[]> allFilms = new selectAllMovies();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Movie[]> films = executor.submit(allFilms);
        
        
        try {
            allMovies = films.get();            
        } catch (InterruptedException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        for(Movie mv : allMovies){
            System.out.println(mv.getNameCZ() + "   " + String.valueOf(mv.getYear()));            
        }
        */
    }
    
    
    
}
