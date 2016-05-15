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
import Main.Register.RegisterFrame;
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
    databasePanel dataPane = new databasePanel();
    ratedPanel ratePane = new ratedPanel();
    searchPanel srchPane = new searchPanel();
    actorsPanel actPane;
    directorsPanel dirPane;
    scenaristsPanel scePane;
    
    
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
    
    // ADDING COMPONENTS
    public void initComponents(){
        moviesPane = new allMoviesPanel(user);
        actPane = new actorsPanel(user.getId());
        dirPane = new directorsPanel(user.getId());
        scePane = new scenaristsPanel(user.getId());
        JButton reloadButt = new JButton("RELOAD");
        reloadButt.addActionListener(reloadListener);
        
        bar.setStringPainted(true);
        bar.setString("Gathering data from database 0%");
        bar.setVisible(false);
        mainPanel.addTab("Home", homePane);
        mainPanel.addTab("Directors", dirPane);
        mainPanel.addTab("Scenarists", scePane);
        mainPanel.addTab("Actors", actPane);
        mainPanel.addTab("Rated", ratePane);
        mainPanel.addTab("All movies", moviesPane);
        mainPanel.addTab(null, reloadButt);
        

        // DOPORUCUJU DOPLNIT KOMPONENTY DO DANYCH CLASS PANELU KVULI PREHLEDNOSTI
        
        
        bar.setIndeterminate(true);
        add(mainPanel);
        add(bar,BorderLayout.SOUTH);
    }
    
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
    
    private void gatherAllData() throws InterruptedException{
        
        Callable<Movie[]> allFilms = new selectAllMovies();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Movie[]> films = executor.submit(allFilms);        
        
        try {
            allMovies = films.get();            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }        
        executor.shutdown();
        
        int onePercent = allMovies.length/100;

        Thread gatherPersons = new Thread() {
            @Override
            public void run() {

                System.out.println("Gathering data, initiated...");
                selectUserMovies thUserMovies = new selectUserMovies(user.getId());
                thUserMovies.start();
                
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
                
                actors = allActors.returnPersonArray();
                directors = allDirectors.returnPersonArray();
                scenarists = allScenarists.returnPersonArray();
                
                int allData = allMovies.length + actors.length + scenarists.length + directors.length;
                int count = 0;
                int percentage = 0;
                for (Movie mv : allMovies) {
                    
                    percentage = (int)(double)(((double)count++ / allData) * 100);
                    bar.setValue(percentage);
                    bar.setString("Gathering data from database " + percentage + "%");
                    
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
                
                int[] tmpId = thUserMovies.getIdMovies();
                Movie[] userMovieTMP = new Movie[tmpId.length]; 
                for(int i = 0; i < tmpId.length; i++){
                    userMovieTMP[i] = allMovies[tmpId[i]-1];                    
                }
                
                user.setRated(userMovieTMP);
                
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

    public void updateMainFrame() throws InterruptedException{        
        
        ratePane.passData(user);
        moviesPane.passData(allMovies,user.isIsAdmin(),actors,directors,scenarists);
        actPane.passData(allMovies, user.isIsAdmin(),actors);
        scePane.passData(allMovies, user.isIsAdmin(),scenarists);
        dirPane.passData(allMovies, user.isIsAdmin(),directors);
    }
    
    ActionListener reloadListener = (ActionEvent actionEvent) -> {
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

    }; 
    
}
