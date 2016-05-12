/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Movie;
import Main.Person;
import Main.panels.allMoviesPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author David Löffler
 */
public class getPersonDialog extends JDialog{

    private movieDialog md;
    private char Who;
    private Person[] people;
    private JTable table;
    private Object[][] data;
    private String[] columnNames = {"ID","Name"};
    private JTextField search = new JTextField(10);    
    
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
    
    public void initComponents(){        
        JPanel upperTools = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout());        
        JButton close = new JButton("Close");
        JButton addBtn = new JButton("Add");
        JButton findBtn = new JButton("Find");
        
        close.addActionListener(closeAction);
        addBtn.addActionListener(addAction);
        findBtn.addActionListener(findAction);
        
        buttons.add(findBtn);
        buttons.add(addBtn);
        buttons.add(close);
        upperTools.add(search,BorderLayout.WEST);
        upperTools.add(buttons,BorderLayout.EAST);
        add(upperTools, BorderLayout.NORTH);
        
        data = new Object[people.length][2];

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
    
    ActionListener findAction = (ActionEvent actionEvent) -> {
        System.out.println("FIND THAT");        

        String name = search.getText();
        ArrayList<Person> found = new ArrayList<>(0);
        
        for(int i = 0; i < people.length; i++){
            String pplName = people[i].getName();
            String pplLast = people[i].getLastName();
            String pplFull = people[i].getFullName();
            if(name.equals(pplName) || name.equals(pplLast) || name.equals(pplFull)
               || pplName.contains(name) || pplLast.contains(name) || pplFull.contains(name)){
                found.add(people[i]);
            }
        }
        data = new Object[found.size()][2];
        int count = 0;
        for(Person pr : found){
            data[count][0] = pr.getId();
            data[count][1] = pr.getFullName();
            count++;
        }
        
        table = new JTable(data,columnNames);        
        
    }; 
    
}
