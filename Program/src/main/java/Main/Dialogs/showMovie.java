/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Movie;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author David Löffler
 */
public class showMovie extends JDialog{
        
    private final int id;
    private final Movie[] movies;
    private Movie current;
    
    public showMovie(int ID, Movie[] all) {
        this.id = ID;
        this.movies = all;
        findMovie();
    }
    
    private void initComponents(){
        JPanel mainPanel = new JPanel();
        JPanel upperPanel = new JPanel();
        JPanel ratePanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel coverPanel = new JPanel();
        
        ratePanel.setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridBagLayout());
        mainPanel.setLayout(new BorderLayout());
        upperPanel.setLayout(new BorderLayout());
        coverPanel.setLayout(new BorderLayout());
                
        GridBagConstraints gbc = new GridBagConstraints();
        JComboBox rateBox = new JComboBox();
        rateBox.addItem("1");
        rateBox.addItem("2");
        rateBox.addItem("3");
        rateBox.addItem("4");
        rateBox.addItem("5");
        JButton rateButt = new JButton("RATE IT");
        Label rating = new Label("Rating: " + String.valueOf(current.getRating()));
        Label description = new Label("Description: " + current.getDescription());
        Label actors = new Label("Actors: " + current.personsToString('A'));
        Label scenrists = new Label("Scenarists: " + current.personsToString('S'));
        Label directors = new Label("Directors: " + current.personsToString('D'));
        
        Label headline = new Label(current.getNameCZ());
        headline.setFont(new Font("Arial",Font.BOLD,18));
        gbc.insets = new Insets(5, 5, 5, 5);
        
        ratePanel.add(rateBox,gbc);
        gbc.gridx = 1;
        ratePanel.add(rateButt,gbc);
        
        
        upperPanel.add(ratePanel,BorderLayout.EAST);
        upperPanel.add(headline,BorderLayout.WEST);
    }
    
    private void findMovie(){
        
        for(int i = 0; i < movies.length; i++){        
            if(i == id-1){
                current = movies[i];
            }        
        }        
    }
}
