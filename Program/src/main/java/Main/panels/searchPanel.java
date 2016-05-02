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

import Main.Movie;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.util.Calendar;
import javax.swing.*;

/**
 *
 * @author David Löffler
 */
public class searchPanel extends JPanel{
    
    private Movie[] movies;
    private Movie[] userMovies;
    private Movie[] local;
    private Object[][] found;
    
    
    public searchPanel(){
        setLayout(new BorderLayout());        
        
        initComponents();
    }
    
    private void initComponents(){
        
        // initializing components
        JPanel upperTools = new JPanel();
        JTable tableMovie = new JTable();
        JTextField nameTxtField = new JTextField(10);
        GridBagConstraints gbc = new GridBagConstraints();
        JComboBox sinceYear = new JComboBox();
        JComboBox tillYear = new JComboBox();
        JComboBox fromRating = new JComboBox();
        JComboBox toRating = new JComboBox();
        JButton addActor = new JButton("Add");
        JButton rmActor = new JButton("Remove");
        JButton addScenarists = new JButton("Add");
        JButton rmScenarists = new JButton("Remove");
        JButton addDirectors = new JButton("Add");
        JButton rmDirectors = new JButton("Remove");
        JButton addGenre = new JButton("Add");
        JButton rmGenre = new JButton("Remove");
        JButton addTag = new JButton("Add");
        JButton rmTag = new JButton("Remove");
        JButton showButt = new JButton("Show");
        JButton findButt = new JButton("Find");
        JTextArea listActors = new JTextArea("NEW TEXT AREA\n\nasdf");
        JTextArea listDirectors = new JTextArea("NEW TEXT AREA\n\nasdf");
        JTextArea listScenarists = new JTextArea("NEW TEXT AREA\n\nasdf");
        JTextArea listGenres = new JTextArea("NEW TEXT AREA\n\nasdf");
        JTextArea listTags = new JTextArea("NEW TEXT AREA\n\nasdf");
        JCheckBox checkDatabase = new JCheckBox("Database");
        JCheckBox checkLocal = new JCheckBox("Local");
        JCheckBox checkRated = new JCheckBox("Rated");
        JPanel actorTools = new JPanel();
        JPanel directTools = new JPanel();
        JPanel scenTools = new JPanel();
        JPanel genreTools = new JPanel();
        JPanel tagTools = new JPanel();
        actorTools.setLayout(new BorderLayout());
        directTools.setLayout(new BorderLayout());
        scenTools.setLayout(new BorderLayout());
        genreTools.setLayout(new BorderLayout());
        tagTools.setLayout(new BorderLayout());
        
        // labels
        Label headline = new Label("Search");
        Label actorsLabel = new Label("Actors:");
        Label directorsLabel = new Label("Directors:");
        Label scenaristsLabel = new Label("Scenarists:");
        Label genresLabel = new Label("Genres:");
        Label tagsLabel = new Label("Tags:");
        Label yearLabel = new Label("Year(since):");
        Label ratingLabel = new Label("Rating(from):");
        Label nameLabel = new Label("Name:");
        
        // setting of components
        upperTools.setLayout(new GridBagLayout());        
        gbc.insets = new Insets(2,2,2,2);        
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        fromRating.addItem("-");
        fromRating.addItem("1");
        fromRating.addItem("2");
        fromRating.addItem("3");
        fromRating.addItem("4");
        fromRating.addItem("5");
        toRating.addItem("-");
        toRating.addItem("1");
        toRating.addItem("2");
        toRating.addItem("3");
        toRating.addItem("4");
        toRating.addItem("5");
        sinceYear.addItem("----");
        tillYear.addItem("----");
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        for(int i = year; i >= 1930; i--){
            sinceYear.addItem(String.valueOf(i));
            tillYear.addItem(String.valueOf(i));
        }
        
        
        // Adding and positioning components
        upperTools.add(headline,gbc);
        actorTools.add(addActor,BorderLayout.NORTH);
        actorTools.add(rmActor,BorderLayout.SOUTH);
        directTools.add(addDirectors,BorderLayout.NORTH);
        directTools.add(rmDirectors,BorderLayout.SOUTH);
        scenTools.add(addScenarists,BorderLayout.NORTH);
        scenTools.add(rmScenarists,BorderLayout.SOUTH);
        genreTools.add(addGenre,BorderLayout.NORTH);
        genreTools.add(rmGenre,BorderLayout.SOUTH);
        tagTools.add(addTag,BorderLayout.NORTH);
        tagTools.add(rmTag,BorderLayout.SOUTH);
        
        gbc.gridy = 4;
        gbc.gridx = 6;
        upperTools.add(nameLabel,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        upperTools.add(directorsLabel, gbc);
        gbc.gridy = 2;
        upperTools.add(directTools, gbc);
        gbc.gridx = 1;
        upperTools.add(listDirectors, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        upperTools.add(scenaristsLabel, gbc);
        gbc.gridy = 2;
        upperTools.add(scenTools, gbc);
        gbc.gridx = 3;
        upperTools.add(listScenarists, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        upperTools.add(actorsLabel, gbc);
        gbc.gridy = 2;
        upperTools.add(actorTools, gbc);
        gbc.gridx = 5;
        upperTools.add(listActors, gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        upperTools.add(genresLabel, gbc);
        gbc.gridy = 2;
        upperTools.add(genreTools, gbc);
        gbc.gridx = 7;
        upperTools.add(listGenres, gbc);

        gbc.gridx = 8;
        gbc.gridy = 1;
        upperTools.add(tagsLabel, gbc);
        gbc.gridy = 2;
        upperTools.add(tagTools, gbc);
        gbc.gridx = 9;
        upperTools.add(listTags, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 1;
        upperTools.add(yearLabel, gbc);
        gbc.gridx = 2;
        upperTools.add(sinceYear, gbc);
        gbc.gridx = 3;
        upperTools.add(tillYear, gbc);
        
        gbc.gridx = 4;
        upperTools.add(ratingLabel, gbc);
        gbc.gridx = 5;
        upperTools.add(fromRating, gbc);
        gbc.gridx = 6;
        upperTools.add(toRating, gbc);
        
        gbc.gridx = 7;
        upperTools.add(checkDatabase, gbc);
        gbc.gridx = 8;
        upperTools.add(checkRated, gbc);
        gbc.gridx = 9;
        upperTools.add(checkLocal, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 7;
        upperTools.add(nameTxtField,gbc);
        gbc.gridx = 8;
        upperTools.add(findButt, gbc);
        gbc.gridx = 9;
        upperTools.add(showButt, gbc);

        add(upperTools, BorderLayout.NORTH);
        add(tableMovie, BorderLayout.CENTER);
    }
    
    public void passData(Movie[] all, Movie[] rated, Movie[] local){
        this.movies = all;
        this.userMovies = rated;
        this.local = local;
    }
    
}
