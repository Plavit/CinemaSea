/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Person;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author David Löffler
 */
public class personDialog extends JDialog{
    
    private int idUser;
    private Person person;
    private char typeOfDialog;
    private char Who;
    private JTextField nameField = new JTextField(30);
    private JTextField surnameField = new JTextField(30);
    private JTextField yearField = new JTextField(30);
    private JTextArea descArea = new JTextArea(10, 30);

    public personDialog(int idUser, Person person, char typeOfDialog, char Who) throws IOException {
        this.idUser = idUser;
        this.person = person;
        this.typeOfDialog = typeOfDialog;
        this.Who = Who;
        setLayout(new BorderLayout());
        
        setModalityType(ModalityType.APPLICATION_MODAL);
        setBounds(0,0,500,380);
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
        JLabel header = new JLabel();
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
        
        switch (Who) {
            case 'A':
                header.setText("Actor");
                break;
            case 'S':
                header.setText("Scenarist");
                break;
            case 'D':
                header.setText("Director");
                break;
        }
        
        upperPane.add(header,BorderLayout.WEST);
        upperPane.add(buttonPane,BorderLayout.EAST);
        add(upperPane,BorderLayout.NORTH);
        
        JLabel nameLab = new JLabel("Name:");
        JLabel surnameLab = new JLabel("Surname:");
        JLabel yearLab = new JLabel("Year:");
        JLabel descLab = new JLabel("Description:");        
        JScrollPane scroll = new JScrollPane(descArea);
        
        fieldsPane.add(nameLab,gbc);
        gbc.gridy = 1;
        fieldsPane.add(surnameLab,gbc);
        gbc.gridy = 2;
        fieldsPane.add(yearLab,gbc);
        gbc.gridy = 3;
        fieldsPane.add(descLab,gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        fieldsPane.add(nameField,gbc);
        gbc.gridy = 1;
        fieldsPane.add(surnameField,gbc);
        gbc.gridy = 2;
        fieldsPane.add(yearField,gbc);
        gbc.gridy = 3;
        fieldsPane.add(scroll,gbc);
        add(fieldsPane,BorderLayout.WEST);
        
        nameField.setText(person.getName());
        surnameField.setText(person.getLastName());
        yearField.setText(String.valueOf(person.getYear()));
        descArea.setText(person.getDescription());
        
    }

    
    
    
    
}
