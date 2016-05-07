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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author David Loffler, Marek Szeles
 */
public class actorsPanel extends JPanel{
    
    private ArrayList rawPeople = new ArrayList(0);
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"Name",
                        "Surname",
                        "Description",
                        "Year"};
    
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
    
    public void passData(Movie[] movies){
        
        data = new Object[movies.length][4];
        boolean isThere = false;
        
        //get out all the actors
        System.out.println("getting actors");
        for(int i = 0; i < movies.length; i++){
            System.out.println("movie nr" + i);
            for(int j = 0; j < movies[i].getActors().length; j++){
                System.out.println("actor nr" + j);
                //if set of actors is empty, just add actor
                if(rawPeople.isEmpty()){
                    rawPeople.add(movies[i].getActors()[j]);
                }
                //if not, first check whether he is already there - if not, add him
                else{
                    for(int k = 0; k < rawPeople.size(); k++){
                        System.out.println("rawP progress" + k + "out of" + rawPeople.size());
                        if(movies[i].getActors()==rawPeople.get(k)){
                            isThere=true;
                        }
                        else{
                            
                        }
                    }
                    if(isThere==false){
                        rawPeople.add(movies[i].getActors()[j]);
                    }
                }
            }
        }
        
        //assign relevant people values to table
        
        for(int i = 0; i < rawPeople.size(); i++){
            
            data[i][0] = rawPeople.get(i);//getName();//name
            data[i][1] = rawPeople.get(i);//surname
            data[i][2] = rawPeople.get(i);//desc
            data[i][3] = rawPeople.get(i);//year
            
        }
        
        initComponents();
    }
}
