/*
 * Copyright (C) 2016 David Löffler
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
package Main.panels;

import Main.Dialogs.movieDialog;
import Main.Dialogs.showMovie;
import Main.Movie;
import Main.Person;
import Main.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author David Löffler, Marek Szeles
 */
public class allMoviesPanel extends JPanel{
    
    private User user;
    private boolean isAdmin = false;
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"ID","Name CZ",
                        "Name EN",
                        "Genres",
                        "Release date", "Rating"};
    private Movie[] movies;
    private Person[] allActors, allDirectors, allScenarists;
    
    public allMoviesPanel(User user){
        this.user = user;        
        setLayout(new BorderLayout());
    }
    
    private void initComponents(){
        JPanel buttPane = new JPanel(new FlowLayout());
        dataTable = new JTable(data,columnNames);        
        dataTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("All movies - NO.: " + dataTable.getRowCount());
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        btn.addActionListener(showListener);
        btnAdd.addActionListener(addListener);
        btnEdit.addActionListener(editListener);
        toolPane.add(headline, BorderLayout.WEST);
        
        // ADD CONTROL BUTTONS
        if(isAdmin){
            buttPane.add(btnAdd);
            buttPane.add(btnEdit);
        }
        buttPane.add(btn);
        
        toolPane.add(buttPane, BorderLayout.EAST);
        
        
        // Setting of table
        for (int c = 0; c < dataTable.getColumnCount(); c++) {
            Class<?> col_class = dataTable.getColumnClass(c);
            dataTable.setDefaultEditor(col_class, null);        // remove editor
        }
        
        dataTable.setRowSelectionAllowed(true);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
        add(toolPane,BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void passData(Movie[] Movies, boolean isAdmin, Person[] actors, Person[] directors, Person[] scenarists){
        this.isAdmin = isAdmin;
        this.movies = Movies;        
        this.allActors = actors;
        this.allDirectors = directors;
        this.allScenarists = scenarists;
        data = new Object[Movies.length][6];
        
        for(int i = 0; i < Movies.length; i++){
            data[i][0] = Movies[i].getId();
            data[i][1] = Movies[i].getNameCZ();
            data[i][2] = Movies[i].getNameEN();           
            data[i][3] = Movies[i].genresToString();
            data[i][4] = Movies[i].getYear();
            data[i][5] = Movies[i].getRating();
        }      
        initComponents();
    }
    
    ActionListener showListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
            if(dataTable.getSelectedRow() != -1){
                Object idMovie = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Movie passMovie = null;
                showMovie dialog = null;
                
                for(Movie mv : movies){
                    if(mv.getId() == Integer.parseInt(idMovie.toString())){
                        passMovie = mv;
                    }                        
                }
                
                try {
                    dialog = new showMovie(passMovie,user);
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }
        }
    };
    
    ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

                int idMax = 0;
                for(Movie mv : movies){
                    if(mv.getId() > idMax) idMax = mv.getId();
                }
                
                movieDialog dialog = null;        
                try {
                    dialog = new movieDialog(idMax,new Movie(idMax+1),null,'I',allActors,allDirectors,allScenarists);
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            

        }
    };
    
    ActionListener editListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            
           if(dataTable.getSelectedRow() != -1){
                Object idMovie = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Movie passMovie = null;
                movieDialog dialog = null;
                
                for(Movie mv : movies){
                    if(mv.getId() == Integer.parseInt(idMovie.toString())){
                        passMovie = mv;
                    }                        
                }               
                
                try {
                    dialog = new movieDialog(passMovie.getId(),passMovie,passMovie.clone(),'U',allActors,allDirectors,allScenarists);
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }

        }
    };
}
