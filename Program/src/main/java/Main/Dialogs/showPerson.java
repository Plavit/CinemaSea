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
 * @author David Löffler
 */
public class showPerson extends JDialog {
private Person person;    
        
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
        
        JPanel upperPanel = new JPanel();
        JPanel ratePanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel coverPanel = new JPanel();
        
        ratePanel.setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridBagLayout());
        upperPanel.setLayout(new BorderLayout());
        coverPanel.setLayout(new BorderLayout());     
        
        GridBagConstraints gbc = new GridBagConstraints();
        Label actedIn = new Label("Starrs in movies: " + person.getMoviesActed());
        JTextArea descriptArea = new JTextArea("Description: " + prepareDescription(person.getDescription()));
        descriptArea.setEditable(false);
        descriptArea.setHighlighter(null);
        descriptArea.setOpaque(false);
        
        // SETTING OF UPPER PANEL
        Label headline = new Label(person.getFullName());
        headline.setFont(new Font("Arial",Font.BOLD,18));
        gbc.insets = new Insets(5, 5, 5, 5);      
        
        upperPanel.add(ratePanel,BorderLayout.EAST);
        upperPanel.add(headline,BorderLayout.WEST);        
        
        // SETING OF INFO PANEL
        gbc.gridx = 0;
        gbc.gridy = 0;        
        
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
