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
        try {
            gatherAllData();
        } catch (InterruptedException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gatherAllData() throws InterruptedException{
        
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
        executor.shutdown();
        /*
        for(Movie mv : allMovies){
            System.out.println(mv.getNameCZ() + "   " + String.valueOf(mv.getYear()));            
        }        
        */
        for(Movie mv : allMovies){
            selectPersons threadA = new selectPersons(mv.getId(),'A');
            selectPersons threadS = new selectPersons(mv.getId(),'S');
            selectPersons threadD = new selectPersons(mv.getId(),'D');
            
            threadA.start();
            threadS.start();
            threadD.start();
            
            threadA.join();
            threadS.join();
            threadD.join();            
            
            mv.setActors(threadA.returnPersonArray());
            mv.setScenarists(threadS.returnPersonArray());
            mv.setDirectors(threadD.returnPersonArray());
        }
        
        
    }
    
    
    
}
