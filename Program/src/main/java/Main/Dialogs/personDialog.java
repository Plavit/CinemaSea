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
import Main.Person;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Dialog for adding or editing persons
 *  
 * @author David Löffler, Marek Szeles
 */
public class personDialog extends JDialog{
    
    private int lastID;
    private Person person;
    private char typeOfDialog;
    private char Who;
    private JTextField nameField = new JTextField(30);
    private JTextField surnameField = new JTextField(30);
    private JTextField yearField = new JTextField(30);
    private JTextArea descArea = new JTextArea(10, 30);

    /**
     * Constructor of the dialog accepting necessary parameters
     * @param id            last id in database
     * @param person        person being edited
     * @param typeOfDialog  adding or editing
     * @param Who           type of person being edited
     * @throws IOException 
     */
    public personDialog(int id, Person person, char typeOfDialog, char Who) throws IOException {
        this.lastID = id;
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
        close.addActionListener(closeAction);
        update.addActionListener(updateAction);
        insert.addActionListener(insertAction);
        
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
        
        if (person != null) {
            nameField.setText(person.getName());
            surnameField.setText(person.getLastName());
            yearField.setText(String.valueOf(person.getYear()));
            descArea.setText(person.getDescription());
        }
        
        descArea.setLineWrap(true);
        
    }
    
    ActionListener closeAction = (ActionEvent actionEvent) -> {
        this.dispose();
    };
    
    ActionListener updateAction = (ActionEvent actionEvent) -> {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String year = yearField.getText();
        String desc = descArea.getText();
        
        if(!"".equals(name) && !"".equals(surname)){
            Database db = new Database();
            if(!"".equals(year) && checkYear(year)){                
                db.updatePerson(name, surname, year, desc, person.getId(), Who);
                JOptionPane.showMessageDialog(new JFrame(),
                    "Person was updated!",
                    "Data accepted",
                    JOptionPane.PLAIN_MESSAGE);
            }
            else if(!checkYear(year) && !"".equals(year)){
                JOptionPane.showMessageDialog(new JFrame(),
                    "Year is not in valid format!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
            }else{
                db.updatePerson(name, surname, null, desc, person.getId(), Who);
                JOptionPane.showMessageDialog(new JFrame(),
                    "Person was updated!",
                    "Data accepted",
                    JOptionPane.PLAIN_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(),
                    "Name and surname have to be filled in!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }        
        
    };
    
    ActionListener insertAction = (ActionEvent actionEvent) -> {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String year = yearField.getText();
        String desc = descArea.getText();
        
        if(!"".equals(name) && !"".equals(surname)){
            Database db = new Database();
            if(!"".equals(year) && checkYear(year)){                
                db.insertPerson(name, surname, year, desc, lastID+1, Who);
                JOptionPane.showMessageDialog(new JFrame(),
                    "Person was inserted!",
                    "Data accepted",
                    JOptionPane.PLAIN_MESSAGE);
            }
            else if(!checkYear(year) && !"".equals(year)){
                JOptionPane.showMessageDialog(new JFrame(),
                    "Year is not in valid format!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
            }else{
                db.insertPerson(name, surname, null, desc, lastID+1, Who);
                JOptionPane.showMessageDialog(new JFrame(),
                    "Person was inserted!",
                    "Data accepted",
                    JOptionPane.PLAIN_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(),
                    "Name and surname have to be filled in!",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        } 
        
    };
    
    private boolean checkYear(String year){
        boolean valid = false;        
        try{
            int convert = Integer.parseInt(year);
            valid = true;
            Calendar now = Calendar.getInstance();   // Gets the current date and time
            int yearNow = now.get(Calendar.YEAR);
            if(yearNow < convert || convert < 1900) valid = false;
        }catch(Exception ex){
            valid = false;
        }
        return valid;
    }
    
}
