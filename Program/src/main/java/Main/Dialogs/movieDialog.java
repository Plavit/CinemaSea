/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Database;
import Main.Movie;
import Main.Person;
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

/**
 *
 * @author David Löffler
 */
public class movieDialog  extends JDialog{
    
    private int lastID;
    private Movie movie;
    private char typeOfDialog;
    private JTextField nameCZField = new JTextField(30);
    private JTextField nameENField = new JTextField(30);
    private JTextField yearField = new JTextField(30);
    private JTextArea descArea = new JTextArea(25, 40);
    private String[] columnNames = {"ID","Name"};
    private Object[][] dataA = new Object[0][2];
    private Object[][] dataD = new Object[0][2];
    private Object[][] dataS = new Object[0][2];

    public movieDialog(int id, Movie movie, char typeOfDialog) throws IOException {
        this.lastID = id;
        this.movie = movie;
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
        JLabel actLab = new JLabel("Actors:");
        JLabel dirLab = new JLabel("Directors:");
        JLabel scnLab = new JLabel("Scenarists:");
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
        
        JScrollPane scrollActors = new JScrollPane(actors);
        JScrollPane scrollDirectors = new JScrollPane(directors);
        JScrollPane scrollscenarists = new JScrollPane(scenarists);       
        
        // POSITIONING OF PERSONS FIELDS
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        fieldsPane.add(actLab,gbc);        
        gbc.gridx = 3;
        fieldsPane.add(scrollActors,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPane.add(dirLab,gbc);
        gbc.gridx = 1;
        fieldsPane.add(scrollDirectors,gbc);
        gbc.gridx = 2;
        fieldsPane.add(scnLab,gbc);
        gbc.gridx = 3;
        fieldsPane.add(scrollscenarists,gbc);        
        
        JScrollPane main = new JScrollPane(fieldsPane);
        add(main,BorderLayout.CENTER);
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
    
}