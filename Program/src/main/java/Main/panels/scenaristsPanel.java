/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.panels;

import Main.Dialogs.personDialog;
import Main.Dialogs.showMovie;
import Main.Dialogs.showPerson;
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
 * @author David Löffler, Marek Szeles
 */
public class scenaristsPanel extends JPanel{
    
    private int id_user;
    private boolean isAdmin = false;
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"ID","Name",
                        "Surname",
                        "Description",
                        "Year"};
    private Person[] people;
    private Person[] scenarists;
    
    public scenaristsPanel(int idUser){
        this.id_user = idUser;
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        JPanel buttPane = new JPanel(new FlowLayout());
        dataTable = new JTable(data,columnNames);      
        dataTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Scenarists in movies - total:" + dataTable.getRowCount());
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        toolPane.add(headline, BorderLayout.WEST);
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
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
    
    public void passData(Movie[] movies, boolean isAdmin,Person[] Scenarists){
        this.scenarists = Scenarists;
        this.isAdmin = isAdmin;        
        
        data = new Object[scenarists.length][5];
        
        //assign relevant people values to table
        
        for(int i = 0; i < scenarists.length; i++){
            data[i][0] = scenarists[i].getId(); // id
            data[i][1] = scenarists[i].getName();//name
            data[i][2] = scenarists[i].getLastName();//surname
            data[i][3] = scenarists[i].getDescription();//desc
            data[i][4] = scenarists[i].getYear();//year
            
        }
        
        initComponents();
    }
    
    ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            int lastID = 0;
            for(Person pr : scenarists){
                if(pr.getId() > lastID) lastID = pr.getId();
            }
            
           try {
                personDialog dialog = new personDialog(lastID, null, 'I', 'S');
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
                Object idScenarists = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Person human = scenarists[Integer.parseInt(idScenarists.toString())-1];
                personDialog dialog = null;
                
                for(Person pr : scenarists){
                    if(pr.getId() == Integer.parseInt(idScenarists.toString())){
                        human = pr;
                    }                        
                }
                
                try {
                    dialog = new personDialog(id_user,human,'U','S');
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
            people = new Person[scenarists.length];
            for(int i=0;i<people.length;i++){
                people[i]=scenarists[i];
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
