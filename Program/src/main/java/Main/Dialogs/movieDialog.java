/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Database;
import Main.Movie;
import Main.Person;
import Main.panels.actorsPanel;
import Main.panels.allMoviesPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Löffler
 */
public class movieDialog extends JDialog{
    
    private int lastID;
    private Movie movie;
    private Movie copy;
    private char typeOfDialog;
    private JTextField nameCZField = new JTextField(30);
    private JTextField nameENField = new JTextField(30);
    private JTextField yearField = new JTextField(30);
    private JTextArea descArea = new JTextArea(25, 40);
    private String[] columnNames = {"ID","Name"};
    DefaultTableModel modelA = new DefaultTableModel();
    DefaultTableModel modelD = new DefaultTableModel(); 
    DefaultTableModel modelS = new DefaultTableModel(); 
    private Object[][] dataA = new Object[0][2];
    private Object[][] dataD = new Object[0][2];
    private Object[][] dataS = new Object[0][2];
    private Person[] allActors, allDirectors, allScenarits;

    public movieDialog(int id, Movie movie,Movie copy, char typeOfDialog,Person[] actors, Person[] directors, Person[] scenarists) throws IOException {
        this.lastID = id;
        this.movie = movie;
        this.copy = copy;
        this.allActors = actors;
        this.allDirectors = directors;
        this.allScenarits = scenarists;
        this.typeOfDialog = typeOfDialog;
        setLayout(new BorderLayout());
        
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setBounds(0,0,1150,700);
        setLocationRelativeTo(null);
        setTitle("Data editor");        
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
        
        initComponents();
    }
    
    private void initComponents(){
        JPanel buttonPane = new JPanel(new FlowLayout());
        JPanel upperPane = new JPanel(new BorderLayout());
        JPanel fieldsPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel header = new JLabel("Movie");
        header.setFont(new Font("Arial",Font.BOLD,18));
        gbc.insets = new Insets(5,5,5,5);        
        
        JButton insert = new JButton("Insert");
        JButton update = new JButton("Update");
        JButton close = new JButton("Close");
        
        switch(typeOfDialog){
            case 'I':
                buttonPane.add(insert);
                break;
            case 'U':
                buttonPane.add(update);
                break;
        }
        buttonPane.add(close);
        close.addActionListener(closeAction);
        update.addActionListener(updateAction);
        insert.addActionListener(insertAction);        
        
        upperPane.add(header,BorderLayout.WEST);
        upperPane.add(buttonPane,BorderLayout.EAST);
        add(upperPane,BorderLayout.NORTH);
        
        JLabel nameCZLab = new JLabel("Name CZ:");
        JLabel nameENLab = new JLabel("Name EN:");
        JLabel yearLab = new JLabel("Year:");
        JLabel descLab = new JLabel("Description:");
        JScrollPane scrollDesc = new JScrollPane(descArea);
        descArea.setLineWrap(true);
        
        fieldsPane.add(nameCZLab,gbc);
        gbc.gridy = 1;
        fieldsPane.add(nameENLab,gbc);
        gbc.gridy = 2;
        fieldsPane.add(yearLab,gbc);
        gbc.gridy = 3;
        fieldsPane.add(descLab,gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        fieldsPane.add(nameCZField,gbc);
        gbc.gridy = 1;
        fieldsPane.add(nameENField,gbc);
        gbc.gridy = 2;
        fieldsPane.add(yearField,gbc);
        gbc.gridy = 3;
        fieldsPane.add(scrollDesc,gbc);
        
        //PERSONS FIELDS   
        JTable actors, directors, scenarists;
        
        if(movie != null){
            dataA = new Object[movie.getActors().length][2];
            dataD = new Object[movie.getDirectors().length][2];
            dataS = new Object[movie.getScenarists().length][2];
            
            for(int i = 0; i < movie.getActors().length; i++){
                dataA[i][0] = movie.getActors()[i].getId();
                dataA[i][1] = movie.getActors()[i].getFullName();
            }
            
            for(int i = 0; i < movie.getDirectors().length; i++){
                dataD[i][0] = movie.getDirectors()[i].getId();
                dataD[i][1] = movie.getDirectors()[i].getFullName();
            }
            
            for(int i = 0; i < movie.getScenarists().length; i++){
                dataS[i][0] = movie.getScenarists()[i].getId();
                dataS[i][1] = movie.getScenarists()[i].getFullName();
            }

            nameCZField.setText(movie.getNameCZ());
            nameENField.setText(movie.getNameEN());
            yearField.setText(String.valueOf(movie.getYear()));
            descArea.setText(movie.getDescription());
            
        }
        actors = new JTable(dataA,columnNames);
        directors = new JTable(dataD,columnNames);
        scenarists = new JTable(dataS,columnNames);
        
        // Setting of table ACTOR
        actors.getTableHeader().setReorderingAllowed(false);        
        for (int c = 0; c < actors.getColumnCount(); c++) {
            Class<?> col_class = actors.getColumnClass(c);
            actors.setDefaultEditor(col_class, null);        // remove editor
        }        
        actors.setRowSelectionAllowed(true);
        actors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Setting of table Director
        directors.getTableHeader().setReorderingAllowed(false);        
        for (int c = 0; c < directors.getColumnCount(); c++) {
            Class<?> col_class = directors.getColumnClass(c);
            directors.setDefaultEditor(col_class, null);        // remove editor
        }        
        directors.setRowSelectionAllowed(true);
        directors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Setting of table Scenarists
        scenarists.getTableHeader().setReorderingAllowed(false);        
        for (int c = 0; c < scenarists.getColumnCount(); c++) {
            Class<?> col_class = scenarists.getColumnClass(c);
            scenarists.setDefaultEditor(col_class, null);        // remove editor
        }        
        scenarists.setRowSelectionAllowed(true);
        scenarists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollActors = new JScrollPane(actors);
        JScrollPane scrollDirectors = new JScrollPane(directors);
        JScrollPane scrollscenarists = new JScrollPane(scenarists);       
        scrollActors.setPreferredSize(new Dimension(350,100));
        scrollscenarists.setPreferredSize(new Dimension(350,100));
        scrollDirectors.setPreferredSize(new Dimension(350,100));
        JPanel actHolder = new JPanel(new GridBagLayout());
        JPanel scnHolder = new JPanel(new GridBagLayout());
        JPanel dirHolder = new JPanel(new GridBagLayout());
        JPanel personsHolder = new JPanel(new GridBagLayout());
        JPanel actBtns = new JPanel(new FlowLayout());
        JPanel scnBtns = new JPanel(new FlowLayout());
        JPanel dirBtns = new JPanel(new FlowLayout());
        JButton addAct = new JButton("Add actor");
        JButton addDir = new JButton("Add director");
        JButton addScn = new JButton("Add scenarist");
        JButton delAct = new JButton("Remove actor");
        JButton delDir = new JButton("Remove director");
        JButton delScn = new JButton("Remove scenarist");
        actBtns.add(addAct);
        actBtns.add(delAct);
        scnBtns.add(addScn);
        scnBtns.add(delScn);
        dirBtns.add(addDir);
        dirBtns.add(delDir);
        
        // POSITIONING OF PERSONS FIELDS    
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        actHolder.add(actBtns,gbc);
        dirHolder.add(dirBtns,gbc);
        scnHolder.add(scnBtns,gbc);
        gbc.gridy = 1;
        actHolder.add(scrollActors,gbc);
        dirHolder.add(scrollDirectors,gbc);
        scnHolder.add(scrollscenarists,gbc);
        gbc.gridy = 0;
        personsHolder.add(actHolder,gbc);
        gbc.gridy = 1;
        personsHolder.add(dirHolder,gbc);
        gbc.gridy = 2;
        personsHolder.add(scnHolder,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        fieldsPane.add(personsHolder,gbc);
        
        JScrollPane main = new JScrollPane(fieldsPane);
        add(main,BorderLayout.CENTER);
        
        // LISTENERS
        
        addAct.addActionListener(addActorListener);
        addDir.addActionListener(addDirectorListener);
        addScn.addActionListener(addScenaristsListener);
        delAct.addActionListener(delActorListener);
        delScn.addActionListener(delScenaristsListener);
        delDir.addActionListener(delDirectorListener);
        
    }
    
    private boolean checkYear(String year){
        boolean valid = false;        
        try{
            int convert = Integer.parseInt(year);
            valid = true;
        }catch(Exception ex){
            valid = false;
        }
        return valid;
    }

    public boolean updateTable(Person pr, char Who) {
        boolean isThere = false;
        System.out.println("UPDATE TABLE");
        switch (Who) {
            case 'A':
                for (int i = 0; i < movie.getActors().length; i++) {
                    if (dataA[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
                }

                if (!isThere) {
                    movie.addActor(pr);
                    this.dispose();
                    try {
                        movieDialog refresh = new movieDialog(this.lastID, this.movie,this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);                        
                        refresh.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }
                break;
            case 'D':
                for (int i = 0; i < movie.getDirectors().length; i++) {
                    if (dataD[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
                }

                if (!isThere) {
                    movie.addDirector(pr);
                    this.dispose();
                    try {
                        movieDialog refresh = new movieDialog(this.lastID, this.movie,this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);                        
                        refresh.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }
                break;
                
            case 'S':
                for (int i = 0; i < movie.getScenarists().length; i++) {
                    if (dataS[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
                }

                if (!isThere) {
                    movie.addScenarist(pr);
                    this.dispose();
                    try {
                        movieDialog refresh = new movieDialog(this.lastID, this.movie,this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);                        
                        refresh.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }
                break;
        }

        return isThere;
    }

    ActionListener closeAction = (ActionEvent actionEvent) -> {
        this.dispose();
    };
    
    ActionListener updateAction = (ActionEvent actionEvent) -> {
             
        System.out.println("insert");
    };
    
    ActionListener insertAction = (ActionEvent actionEvent) -> {
        
        System.out.println("insert");
        
    };    
    
    
    ActionListener addActorListener = (ActionEvent actionEvent) -> {
        getPersonDialog pd;
        try {
            pd = new getPersonDialog(movieDialog.this, 'A',allActors);
            pd.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    ActionListener addDirectorListener = (ActionEvent actionEvent) -> {
        getPersonDialog pd;
        try {
            pd = new getPersonDialog(movieDialog.this, 'D',allDirectors);
            pd.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    ActionListener addScenaristsListener = (ActionEvent actionEvent) -> {
        getPersonDialog pd;
        try {
            pd = new getPersonDialog(movieDialog.this, 'S',allScenarits);
            pd.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    ActionListener delScenaristsListener = (ActionEvent actionEvent) -> {
    };
    
    ActionListener delActorListener = (ActionEvent actionEvent) -> {
    };
    
    ActionListener delDirectorListener = (ActionEvent actionEvent) -> {
    };    
    
}