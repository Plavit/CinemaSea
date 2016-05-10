/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Database;
import Main.Login.LoginFrame;
import Main.Movie;
import Main.Person;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Marek Szeles, David Löffler
 */
public class showPerson extends JDialog {
private Person person;    
private String acting;
private String directing;
private String screenwriting;
        
    public showPerson(Person person) throws IOException {        
        this.person = person;
        initComponents();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setBounds(0,0,900,620);
        setLocationRelativeTo(null);
        setTitle("Person detail - " + person.getFullName());        
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
    }
    
    private void initComponents() throws IOException{        
        
        String subHeadingText="";
        acting="";
        //TODO proper description
        if(person.getMoviesActed().length!=0){

            for (int i = 0; i < person.getMoviesActed().length; i++){
                acting+=person.getMoviesActed()[i].getNameCZ()+"[EN: "+person.getMoviesActed()[i].getNameEN()+"]";
                subHeadingText+="Actor";
                if(person.getMoviesDirected().length!=0||person.getMoviesScreenwritten().length!=0){
                    subHeadingText+=", ";
                }
            }
        }

        
        directing="";
        for (int i = 0; i < person.getMoviesDirected().length; i++){
            directing+=person.getMoviesDirected()[i].getNameCZ()+"[EN: "+person.getMoviesDirected()[i].getNameEN()+"]";
        }
        
        screenwriting="";
        for (int i = 0; i < person.getMoviesScreenwritten().length; i++){
            screenwriting+=person.getMoviesScreenwritten()[i].getNameCZ()+"[EN: "+person.getMoviesScreenwritten()[i].getNameEN()+"]";
        }
        
        JPanel upperPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel coverPanel = new JPanel();
        
        infoPanel.setLayout(new GridBagLayout());
        upperPanel.setLayout(new BorderLayout());
        coverPanel.setLayout(new BorderLayout());     
        
        GridBagConstraints gbc = new GridBagConstraints();
        //TODO formatting
        Label actedIn = new Label("Starring in movies: " + acting + "Directing movies: " + directing);
        JTextArea descriptArea = new JTextArea("Description: " + prepareDescription(person.getDescription()));
        descriptArea.setEditable(false);
        descriptArea.setHighlighter(null);
        descriptArea.setOpaque(false);
        
        // SETTING OF UPPER PANEL
        Label headline = new Label(person.getFullName());
        headline.setFont(new Font("Arial",Font.BOLD,18));
        
        Label subHeading = new Label(subHeadingText);
        headline.setFont(new Font("Arial",Font.ITALIC,14));
        
        gbc.insets = new Insets(5, 5, 5, 5);      
        
        upperPanel.add(headline,BorderLayout.WEST);        
        
        // SETING OF INFO PANEL
        gbc.gridx = 1;
        gbc.gridy = 0;        
        
        infoPanel.add(subHeading,gbc);   
        
        gbc.gridy = 4;
        infoPanel.add(actedIn,gbc);
        gbc.gridy = 5;
        infoPanel.add(descriptArea,gbc);
        
        
        
        // ADDING THE PANELS        
        add(upperPanel,BorderLayout.NORTH);
        add(coverPanel,BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        
    }
    
    private String prepareDescription(String desc){
        char[] arr = desc.toCharArray();
        String info = "";
        
        for(int i = 0; i < arr.length; i++){
            if(i % 100 == 0){
                info += "\n";
            }
            info += arr[i];
        }
        return info;
    }
}
