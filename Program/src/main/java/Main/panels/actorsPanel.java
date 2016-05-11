/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.panels;

import Main.Dialogs.personDialog;
import Main.Dialogs.showPerson;
import Main.Movie;
import Main.Person;
import Main.Threads.getPersonsMovies;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author David Löffler, Marek Szeles
 */
public class actorsPanel extends JPanel{
    
    private int idUser;
    private boolean isAdmin = false;
    private Object[][] data;    
    private JTable dataTable;
    private final String[] columnNames = {"ID","Name",
                        "Surname",
                        "Description",
                        "Year"};
    private Person[] people;
    private Person[] actors;
    
    public actorsPanel(int idUser){
        this.idUser = idUser;
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        JPanel buttPane = new JPanel(new FlowLayout());
        dataTable = new JTable(data,columnNames);        
        dataTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Actors in movies - total:" + dataTable.getRowCount());
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        toolPane.add(headline, BorderLayout.WEST);
        btn.addActionListener(showListener);
        btnAdd.addActionListener(addListener);
        btnEdit.addActionListener(editListener);        
        
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
    
    public void passData(Movie[] movies, boolean isAdmin, Person[] Actors) throws InterruptedException{
        this.actors = Actors;
        this.isAdmin = isAdmin;     
        /*
        Thread getMovies = new Thread(){
            @Override
            public void run(){
                getPersonsMovies movies;
                for(Person pr : actors){
                    movies = new getPersonsMovies('A',pr.getId());
                    movies.start();
                    try {
                        movies.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(actorsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pr.setMoviesActed(movies.returnMoviesArray());
                }
            }
        };
        getMovies.start();
        getMovies.join();
        */
        data = new Object[actors.length][5];
        
        //assign relevant people values to table        
        
        for(int i = 0; i < actors.length; i++){
            data[i][0] = actors[i].getId();//ID
            data[i][1] = actors[i].getName();//name
            data[i][2] = actors[i].getLastName();//surname
            data[i][3] = actors[i].getDescription();//desc
            data[i][4] = actors[i].getYear();//year            
        }
        
        initComponents();
    }
    
    ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int lastID = 0;
            for(Person pr : actors){
                if(pr.getId() > lastID) lastID = pr.getId();
            }

            try {
                personDialog dialog = new personDialog(lastID, null, 'I', 'A');
                dialog.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
    
    ActionListener editListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
           if(dataTable.getSelectedRow() != -1){
                Object idActor = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Person human = null;
                
                for(Person pr : actors){
                    if(pr.getId() == Integer.parseInt(idActor.toString())){
                        human = pr;
                    }                        
                }
                
                personDialog dialog = null;
                try {
                    dialog = new personDialog(idUser,human,'U','A');
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }

        }
    };
        ActionListener showListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {     
            //fill up people array from rawPeople arraylist
            people = new Person[actors.length];
            for(int i=0;i<people.length;i++){
                people[i]=actors[i];
            }
            if(dataTable.getSelectedRow() != -1){
                Object idPerson = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Person passPerson = people[dataTable.getSelectedRow()];
                showPerson dialog = null;
                try {
                    dialog = new showPerson(passPerson);
                } catch (IOException ex) {
                    Logger.getLogger(actorsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }

        }
    };
}
