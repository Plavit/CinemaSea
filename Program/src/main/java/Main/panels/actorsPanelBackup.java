/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.panels;

import Main.Movie;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Marek
 */
public class actorsPanelBackup extends JPanel{
    
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"Name CZ",
                        "Name EN",
                        "Genres",
                        "Release date", 
                        "Actors"};
    
    public actorsPanel(){
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        dataTable = new JTable(data,columnNames);        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Actors in movies");
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        toolPane.add(headline, BorderLayout.WEST);
        toolPane.add(btn, BorderLayout.EAST);
        
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
    
    public void passData(Movie[] rated){
        
        data = new Object[rated.length][5];
        
        for(int i = 0; i < rated.length; i++){
            data[i][0] = rated[i].getNameCZ();
            data[i][1] = rated[i].getNameEN();
            
            String genres = "";
            
            for(int k = 0; k < rated[i].getGenres().length; k++){
                if(rated[i].getGenres().length > 1 && rated[i].getGenres().length != k+1){
                    genres += rated[i].getGenres()[k] + " / ";
                }
                else{
                    genres += rated[i].getGenres()[k];
                }
            }
            
            data[i][2] = genres;
            data[i][3] = rated[i].getYear();
            
            String actors = "";
            for(int k = 0; k < rated[i].getActors().length; k++){
                if(rated[i].getActors().length > 1 && rated[i].getActors().length != k+1){
                    actors += rated[i].getActors()[k].getFullName() + " / ";
                }
                else{
                    actors += rated[i].getActors()[k].getFullName();
                }
            }
            data[i][4] = actors;
        }      
        initComponents();
    }
}
