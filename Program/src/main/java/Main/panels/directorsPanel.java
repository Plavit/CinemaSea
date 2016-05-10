/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.panels;

import Main.Dialogs.personDialog;
import Main.Movie;
import Main.Person;
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
 * @author David Loffler, Marek Szeles
 */
public class directorsPanel extends JPanel{
    
    private int idUser;
    private boolean isAdmin = false;
    private ArrayList<Person> rawPeople = new ArrayList<Person>(0);
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"ID","Name",
                        "Surname",
                        "Description",
                        "Year"};
    
    public directorsPanel(int idUser){
        this.idUser = idUser;
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        JPanel buttPane = new JPanel(new FlowLayout());
        dataTable = new JTable(data,columnNames);        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Directors in movies - total:" + dataTable.getRowCount());
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        toolPane.add(headline, BorderLayout.WEST);
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        //btn.addActionListener(showListener);
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
    
    public void passData(Movie[] movies, boolean isAdmin){
        
        this.isAdmin = isAdmin;
        boolean isThere = false;
        Person curPerson;
        
        //get out all the actors
        //System.out.println("getting actors");
        for(int i = 0; i < movies.length; i++){
            //System.out.println("movie nr" + i);
            for(int j = 0; j < movies[i].getDirectors().length; j++){
                //System.out.println("actor nr" + j);
                    rawPeople.add(movies[i].getDirectors()[j]);
                }
        }
        
        //delete duplicate persons
        //System.out.println("removing process commenced");
        for(int i = 0; i < rawPeople.size(); i++){
            for(int j = 1+i; j < rawPeople.size(); j++){
                //System.out.println("index: [" + i + "," + j + "]");
                if(rawPeople.get(i)==rawPeople.get(j)){
                    rawPeople.remove(j);
                    //System.out.println("removed:" + j);
                    j--;
                }
            }
        }
        //System.out.println("removing finished");
        
        
        
        
        data = new Object[rawPeople.size()][5];
        
        //assign relevant people values to table
        
        for(int i = 0; i < rawPeople.size(); i++){
            data[i][0] = rawPeople.get(i).getId();//ID
            data[i][1] = rawPeople.get(i).getName();//name
            data[i][2] = rawPeople.get(i).getLastName();//surname
            data[i][3] = rawPeople.get(i).getDescription();//desc
            data[i][4] = rawPeople.get(i).getYear();//year
            
        }
        
        initComponents();
    }
    
    ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
           System.out.println("none");

        }
    };
    
    ActionListener editListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
           if(dataTable.getSelectedRow() != -1){
                Object idDirector = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Person human = rawPeople.get(Integer.parseInt(idDirector.toString())-1);
                personDialog dialog = null;
                
                for(Person pr : rawPeople){
                    if(pr.getId() == Integer.parseInt(idDirector.toString())){
                        human = pr;
                    }                        
                }
                
                try {
                    dialog = new personDialog(idUser,human,'U','D');
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }

        }
    };
}
