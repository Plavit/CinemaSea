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
import Main.Threads.*;
import Main.panels.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class mainframe extends JFrame{
    
    private User user;
    private Movie[] allMovies;
    private Database db;
    private Person[] directors;
    private Person[] scenarists;
    private Person[] actors;
    
    JProgressBar bar = new JProgressBar();
    JTabbedPane mainPanel = new JTabbedPane();
    homePanel homePane = new homePanel();
    allMoviesPanel moviesPane;
    ratedPanel ratePane = new ratedPanel();
    searchPanel srchPane = new searchPanel();
    actorsPanel actPane;
    directorsPanel dirPane;
    scenaristsPanel scePane;
    
    /**
     * Constructor for mainframe, setting the frame
     * @throws MalformedURLException loading of icon
     * @throws IOException loading of icon
     */
    public mainframe() throws MalformedURLException, IOException{
        setTitle("Cinsea - Home");
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();
        setBounds(0,0,1000,600);
        setMinimumSize(new Dimension(1000,600));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * ADDING AND POSITIONING OF COMPONENTS
     */
    public void initComponents(){
        moviesPane = new allMoviesPanel(user);
        actPane = new actorsPanel(user.getId());
        dirPane = new directorsPanel(user.getId());
        scePane = new scenaristsPanel(user.getId());
        JButton reloadButt = new JButton("RELOAD");
        reloadButt.addActionListener(reloadListener);
        mainPanel.addChangeListener(reloadMF);
        
        bar.setStringPainted(true);
        bar.setString("Gathering data from database 0%");
        bar.setVisible(false);
        mainPanel.addTab("Home", homePane);
        mainPanel.addTab("Directors", dirPane);
        mainPanel.addTab("Scenarists", scePane);
        mainPanel.addTab("Actors", actPane);
        mainPanel.addTab("Rated", ratePane);
        mainPanel.addTab("All movies", moviesPane);
        mainPanel.addTab("RELOAD",reloadButt);
        mainPanel.setBackgroundAt(6, java.awt.Color.cyan);

        // DOPORUCUJU DOPLNIT KOMPONENTY DO DANYCH CLASS PANELU KVULI PREHLEDNOSTI
        
        
        bar.setIndeterminate(true);
        add(mainPanel);
        add(bar,BorderLayout.SOUTH);
    }
    
    /**
     * Updates mainframe
     * @param user receive current user
     */
    public void setMainFrame(User user){
        this.user = user;
        initComponents();
        bar.setVisible(true);
        try {
            gatherAllData();
        } catch (InterruptedException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method for gathering all data
     * @throws InterruptedException 
     */
    private void gatherAllData() throws InterruptedException{
        // get all movies
        Callable<Movie[]> allFilms = new selectAllMovies();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Movie[]> films = executor.submit(allFilms);        
        
        // assigne movies from Future to global array
        try {
            allMovies = films.get();            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }        
        executor.shutdown();

        // thread that controls work of all other threads
        Thread gatherPersons = new Thread() {
            @Override
            public void run() {
                
                // progressbar text
                System.out.println("Gathering data, initiated...");
                selectUserMovies thUserMovies = new selectUserMovies(user.getId());
                thUserMovies.start(); // gather user movies
                
                // gather all persons
                selectAllPersons allActors = new selectAllPersons('A');
                selectAllPersons allScenarists = new selectAllPersons('D');
                selectAllPersons allDirectors = new selectAllPersons('S');
                
                allActors.start();
                allScenarists.start();
                allDirectors.start();
                try {
                    allActors.join();
                    allDirectors.join();
                    allScenarists.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // assigne groups of people to their arrays
                actors = allActors.returnPersonArray();
                directors = allDirectors.returnPersonArray();
                scenarists = allScenarists.returnPersonArray();
                
                // the amount of all data and prepating of percentage
                int allData = allMovies.length + actors.length + scenarists.length + directors.length;
                int count = 0;
                int percentage = 0;
                // gather all data related to movies
                for (Movie mv : allMovies) {
                    // re-calculate the progress
                    percentage = (int)(double)(((double)count++ / allData) * 100);
                    bar.setValue(percentage);
                    bar.setString("Gathering data from database " + percentage + "%");
                    
                    // set threads to certain movie
                    selectPersons thActors = new selectPersons(mv.getId(), 'A');
                    selectPersons thScenarists = new selectPersons(mv.getId(), 'S');
                    selectPersons thDirectors = new selectPersons(mv.getId(), 'D');
                    selectAdditions thGenres = new selectAdditions(mv.getId(), 'G');
                    selectAdditions thTags = new selectAdditions(mv.getId(), 'T');
                    selectAdditions thRating = new selectAdditions(mv.getId(), 'R');

                    thActors.start();
                    thScenarists.start();
                    thDirectors.start();
                    thGenres.start();
                    thTags.start();
                    thRating.start();

                    // wait for their END
                    try {
                        thActors.join();
                        thScenarists.join();
                        thDirectors.join();
                        thGenres.join();
                        thTags.join();
                        thRating.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                    }                    

                    // get data from threads
                    mv.setActors(thActors.returnPersonArray());
                    mv.setScenarists(thScenarists.returnPersonArray());
                    mv.setDirectors(thDirectors.returnPersonArray());
                    mv.setGenres(thGenres.getAdditions());
                    mv.setTags(thTags.getAdditions());
                    mv.setRating(thRating.getRating());
                }
                
                try {
                    thUserMovies.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // prepare valid format of movies that user rated
                int[] tmpId = thUserMovies.getIdMovies();
                Movie[] userMovieTMP = new Movie[tmpId.length]; 
                for(int i = 0; i < tmpId.length; i++){
                    for(int k = 0; k < allMovies.length; k++){
                        if(tmpId[i] == allMovies[k].getId()){
                            userMovieTMP[i] = allMovies[k];
                        }
                    }
                    //userMovieTMP[i] = allMovies[tmpId[i]-1];                    
                }
                
                user.setRated(userMovieTMP);
                
                // add all belongings to every Actor
                getPersonsMovies movies;
                for(Person pr : actors){
                    
                    percentage = (int)(double)(((double)count++ / allData) * 100);
                    bar.setValue(percentage);
                    bar.setString("Gathering data from database " + percentage + "%");
                    
                    movies = new getPersonsMovies('A',pr.getId());
                    movies.start();
                    try {
                        movies.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(actorsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pr.setMoviesActed(movies.returnMoviesArray());
                }
                
                // add all belongings to every Director
                for(Person pr : directors){
                    
                    percentage = (int)(double)(((double)count++ / allData) * 100);
                    bar.setValue(percentage);
                    bar.setString("Gathering data from database " + percentage + "%");
                    
                    movies = new getPersonsMovies('D',pr.getId());
                    movies.start();
                    try {
                        movies.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(actorsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pr.setMoviesActed(movies.returnMoviesArray());
                }
                
                // add all belongings to every Scenarist
                for(Person pr : scenarists){
                    
                    percentage = (int)(double)(((double)count++ / allData) * 100);
                    bar.setValue(percentage);
                    bar.setString("Gathering data from database " + percentage + "%");
                    
                    movies = new getPersonsMovies('S',pr.getId());
                    movies.start();
                    try {
                        movies.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(actorsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pr.setMoviesActed(movies.returnMoviesArray());
                }
                
                // EVFERYTHING IS SET SO UPDATE MAINFRAME
                try {
                    updateMainFrame();
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("ALL DATA SET");
                bar.setVisible(false);
            }
        };
        
        gatherPersons.start();
        
    }

    /**
     * Method for updating mainframe
     * @throws InterruptedException 
     */
    public void updateMainFrame() throws InterruptedException{        
        
        ratePane.passData(user);
        moviesPane.passData(allMovies,user.isIsAdmin(),actors,directors,scenarists);
        actPane.passData(allMovies, user.isIsAdmin(),actors);
        scePane.passData(allMovies, user.isIsAdmin(),scenarists);
        dirPane.passData(allMovies, user.isIsAdmin(),directors);
    }
    
    /**
     * function reloads the data from online database by resetting the mainframe
     */
    private void reloadFunc() {
        Database db = new Database();
        try {
            if (db.updateViews()) {
                mainframe mf = new mainframe();
                this.dispose();
                mf.setMainFrame(user);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ActionListener reloadListener = (ActionEvent actionEvent) -> {
        reloadFunc();
    };
    
    ChangeListener reloadMF = new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            if (mainPanel.getSelectedIndex() == 6) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Reload will terminate this window and run new one with long loading procedure. Would you like to continue?", "Reload warning", dialogButton);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    reloadFunc();
                }
            }
        }
    };
    
}
