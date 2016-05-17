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
import java.util.ArrayList;
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
 * Dialog for adding or editing movies
 *  
 * @author David Löffler, Marek Szeles
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
    JTable actors, directors, scenarists;

    public movieDialog() {
    }

    /**
     * Constructor of the dialog accepting necessary parameters
     * @param id            highest id in database
     * @param movie         movie to be edited
     * @param copy          copy of last movie
     * @param typeOfDialog  adding or editing movie
     * @param actors        actors to be added
     * @param directors     directors
     * @param scenarists    scenarists
     * @throws IOException 
     */
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
        
        if(movie != null){
            
            if(!movie.isEmpty('A')){
            dataA = new Object[movie.getActors().length][2];
            for(int i = 0; i < movie.getActors().length; i++){
                dataA[i][0] = movie.getActors()[i].getId();
                dataA[i][1] = movie.getActors()[i].getFullName();
            }
            }
            
            if(!movie.isEmpty('D')){
            dataD = new Object[movie.getDirectors().length][2];
            for(int i = 0; i < movie.getDirectors().length; i++){
                dataD[i][0] = movie.getDirectors()[i].getId();
                dataD[i][1] = movie.getDirectors()[i].getFullName();
            }
            }
            
            if(!movie.isEmpty('S')){
            dataS = new Object[movie.getScenarists().length][2];
            for(int i = 0; i < movie.getScenarists().length; i++){
                dataS[i][0] = movie.getScenarists()[i].getId();
                dataS[i][1] = movie.getScenarists()[i].getFullName();
            }
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
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        movie.setNameCZ(namecz);
        movie.setNameEN(nameen);
        movie.setDescription(description);
        if(checkYear(year)) movie.setYear(Integer.valueOf(year));
        boolean isThere = false;        
        switch (Who) {
            case 'A':
                if(!movie.isEmpty('A')){
                for (int i = 0; i < movie.getActors().length; i++) {
                    if (dataA[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
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
                if(!movie.isEmpty('D')){
                for (int i = 0; i < movie.getDirectors().length; i++) {
                    if (dataD[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
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
                if(!movie.isEmpty('S')){
                for (int i = 0; i < movie.getScenarists().length; i++) {
                    if (dataS[i][0] == Integer.valueOf(pr.getId())) {
                        isThere = true;
                    }
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
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        movie.setNameCZ(namecz);
        movie.setNameEN(nameen);
        movie.setDescription(description);
        if(checkYear(year)) movie.setYear(Integer.valueOf(year));
        
        ArrayList<Integer> rmActors = new ArrayList<>(0);
        ArrayList<Integer> rmDirectors = new ArrayList<>(0);
        ArrayList<Integer> rmScenarists = new ArrayList<>(0);
        ArrayList<Integer> addActors = new ArrayList<>(0);
        ArrayList<Integer> addDirectors = new ArrayList<>(0);
        ArrayList<Integer> addScenarists = new ArrayList<>(0);
    
        if (!namecz.equals("") && !nameen.equals("") && checkYear(year)) {
            // ACTORS EDITTED
            if (!movie.isEmpty('A') && !copy.isEmpty('A')) {
                
                // DELETED PERSONS
                rmActors = new ArrayList<>(findPeople(movie.getActors(), copy.getActors()));
                // ADDED PERSONS
                addActors = new ArrayList<>(findPeople(copy.getActors(), movie.getActors()));
                
                
            } else if (!movie.isEmpty('A') && copy.isEmpty('A')) {
                for (Person pr : movie.getActors()) {
                    addActors.add(pr.getId());
                }
            } else if (movie.isEmpty('A') && !copy.isEmpty('A')) {
                for (Person pr : movie.getActors()) {
                    rmActors.add(pr.getId());
                }
            }
            
            // DIRECTORS EDITTED
            if (!movie.isEmpty('D') && !copy.isEmpty('D')) {
                // DELETED PERSONS
                rmDirectors = new ArrayList<>(findPeople(movie.getDirectors(), copy.getDirectors()));
                // ADDED PERSONS
                addDirectors = new ArrayList<>(findPeople(copy.getDirectors(), movie.getDirectors()));
            } else if (!movie.isEmpty('D') && copy.isEmpty('D')) {
                for (Person pr : movie.getDirectors()) {
                    addDirectors.add(pr.getId());
                }
            } else if (movie.isEmpty('D') && !copy.isEmpty('D')) {
                for (Person pr : movie.getDirectors()) {
                    rmDirectors.add(pr.getId());
                }
            }
            
            // SCENARISTS EDITTED
            if (!movie.isEmpty('S') && !copy.isEmpty('S')) {
                // DELETED PERSONS
                rmScenarists = new ArrayList<>(findPeople(movie.getScenarists(), copy.getScenarists()));
                // ADDED PERSONS
                addScenarists = new ArrayList<>(findPeople(copy.getScenarists(), movie.getScenarists()));
            } else if (!movie.isEmpty('S') && copy.isEmpty('S')) {
                for (Person pr : movie.getScenarists()) {
                    addScenarists.add(pr.getId());
                }
            } else if (movie.isEmpty('S') && !copy.isEmpty('S')) {
                for (Person pr : movie.getScenarists()) {
                    rmScenarists.add(pr.getId());
                }
            }
            
            Database db = new Database();
            db.updateMovie(movie,addActors,addDirectors,addScenarists,rmActors,rmDirectors,rmScenarists);
            
            JOptionPane.showMessageDialog(new JFrame(),
                    "Movie update has been sucesfull!",
                    "Movie updated",
                    JOptionPane.PLAIN_MESSAGE);
            this.copy = movie.clone();

        }else{
            JOptionPane.showMessageDialog(new JFrame(),
                    "Name CZ,EN and Year have to be filled in!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }
    };
    
    ActionListener insertAction = (ActionEvent actionEvent) -> {
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        int id = this.lastID+1;
        int[] acts = new int[dataA.length];
        int[] dirs = new int[dataD.length];
        int[] scns = new int[dataS.length];
        
        for(int i = 0; i < dataA.length; i++){
            acts[i] = Integer.valueOf(dataA[i][0].toString());
        }
        
        for(int i = 0; i < dataD.length; i++){
            dirs[i] = Integer.valueOf(dataD[i][0].toString());
        }
        
        for(int i = 0; i < dataS.length; i++){
            scns[i] = Integer.valueOf(dataS[i][0].toString());
        }       
        
        if(!namecz.equals("") && !nameen.equals("") && checkYear(year)){
            
            Database db = new Database();
            db.inserMovie(id,namecz,nameen,year,description,acts,dirs,scns);
            
            JOptionPane.showMessageDialog(new JFrame(),
                    "Movie insert has been sucesfull!",
                    "Movie inserted",
                    JOptionPane.PLAIN_MESSAGE);
            
        }else{
            JOptionPane.showMessageDialog(new JFrame(),
                    "Name CZ,EN and Year have to be filled in!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }
 
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
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        movie.setNameCZ(namecz);
        movie.setNameEN(nameen);
        movie.setDescription(description);
        if(checkYear(year)) movie.setYear(Integer.valueOf(year));
        if (scenarists.getSelectedRow() != -1) {
            Object idPerson = scenarists.getValueAt(scenarists.getSelectedRow(), 0);
            int id = Integer.parseInt(idPerson.toString());
            movie.rmScenarist(id);
            this.dispose();
            try {
                movieDialog refresh = new movieDialog(this.lastID, this.movie, this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);
                refresh.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    ActionListener delActorListener = (ActionEvent actionEvent) -> {
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        movie.setNameCZ(namecz);
        movie.setNameEN(nameen);
        movie.setDescription(description);
        if(checkYear(year)) movie.setYear(Integer.valueOf(year));
        if (actors.getSelectedRow() != -1) {
            Object idPerson = actors.getValueAt(actors.getSelectedRow(), 0);
            int id = Integer.parseInt(idPerson.toString());
            movie.rmActor(id);
            this.dispose();
            try {
                movieDialog refresh = new movieDialog(this.lastID, this.movie, this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);
                refresh.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    ActionListener delDirectorListener = (ActionEvent actionEvent) -> {
        String namecz = nameCZField.getText();
        String nameen = nameENField.getText();
        String year = yearField.getText();
        String description = descArea.getText();
        movie.setNameCZ(namecz);
        movie.setNameEN(nameen);
        movie.setDescription(description);
        if(checkYear(year)) movie.setYear(Integer.valueOf(year));
        if (directors.getSelectedRow() != -1) {
            Object idPerson = directors.getValueAt(directors.getSelectedRow(), 0);
            int id = Integer.parseInt(idPerson.toString());
            movie.rmDirector(id);
            this.dispose();
            try {
                movieDialog refresh = new movieDialog(this.lastID, this.movie, this.copy, this.typeOfDialog, this.allActors, this.allDirectors, this.allScenarits);
                refresh.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(movieDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };    
    
    // FOR UNIT TEST THIS METHOD IS PUBLIC
    // FUNCTION for finding duplicits
    public ArrayList<Integer> findPeople(Person[] film, Person[] backup){
        ArrayList<Integer> ids = new ArrayList<>(0);
        for (Person prc : backup) {
            boolean found = false;
            for (Person prm : film) {
                if (prc.getId() == prm.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ids.add(prc.getId());
            }
        }
        return ids;
    }
    
}