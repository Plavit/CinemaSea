/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Person;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Dialog that shows certain group of people to be added
 * @author David Loffler, Marek Szeles
 */
public class getPersonDialog extends JDialog{

    private movieDialog md;
    private char Who;
    private Person[] people;
    private JTable table;
    private Object[][] data;
    private String[] columnNames = {"ID","Name"}; 
    
    /** class constructor initializing the dialog
     * 
     * @param parent    determines which movieDialog this dialog came from
     * @param Who       determines class of personality (actor, director, or scenarist)
     * @param people    the people about to be added
     * @throws IOException 
     */
    public getPersonDialog(movieDialog parent, char Who,Person[] people) throws IOException {        
        this.md = parent;
        this.Who = Who;
        this.people = people;
        setLayout(new BorderLayout());        
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setBounds(0,0,330,350);
        setLocationRelativeTo(null);
        setTitle("Add person");        
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
        setResizable(false);
        initComponents();
    }
    
    /**
     * Positioning and adding all components
     */
    public void initComponents(){        
        JPanel upperTools = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout());        
        JButton close = new JButton("Close");
        JButton addBtn = new JButton("Add");
        
        close.addActionListener(closeAction);
        addBtn.addActionListener(addAction);
        
        buttons.add(addBtn);
        buttons.add(close);
        upperTools.add(buttons,BorderLayout.EAST);
        add(upperTools, BorderLayout.NORTH);
        
        data = new Object[people.length][2];
        // setting of data in table
        for (int i = 0; i < people.length; i++) {
            data[i][0] = people[i].getId();
            data[i][1] = people[i].getFullName();
        }
        
        table = new JTable(data,columnNames);
        
        // Setting of table
        table.getTableHeader().setReorderingAllowed(false);        
        for (int c = 0; c < table.getColumnCount(); c++) {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);        // remove editor
        }        
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableHolder = new JScrollPane(table);
        add(tableHolder,BorderLayout.CENTER);        
    }
    
    ActionListener closeAction = (ActionEvent actionEvent) -> {
        this.dispose();
    }; 
    
    ActionListener addAction = (ActionEvent actionEvent) -> {
        if(table.getSelectedRow() != -1){
                Object idPerson = table.getValueAt(table.getSelectedRow(), 0);
                int id = Integer.parseInt(idPerson.toString());
                Person passPerson = null;
                for(Person prr : people){
                    if(prr.getId() == id) passPerson = prr;
                }
                this.dispose();
                md.updateTable(passPerson,Who);
            }
    };     
    
}
