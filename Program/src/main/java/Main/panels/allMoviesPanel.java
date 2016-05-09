/*
 * Copyright (C) 2016 David Löffler
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
package Main.panels;

import Main.Dialogs.showMovie;
import Main.Movie;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author David Löffler
 */
public class allMoviesPanel extends JPanel{
    
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"ID","Name CZ",
                        "Name EN",
                        "Genres",
                        "Release date", "Rating"};
    private Movie[] movies;
    
    public allMoviesPanel(){
        setLayout(new BorderLayout());
    }
    
    private void initComponents(){
        dataTable = new JTable(data,columnNames);        
        dataTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("All movies - NO.: " + dataTable.getRowCount());
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        btn.addActionListener(showListener);
        toolPane.add(headline, BorderLayout.WEST);
        toolPane.add(btn, BorderLayout.EAST);
        
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
    
    public void passData(Movie[] rated){
        this.movies = rated;        
        data = new Object[rated.length][6];
        
        for(int i = 0; i < rated.length; i++){
            data[i][0] = rated[i].getId();
            data[i][1] = rated[i].getNameCZ();
            data[i][2] = rated[i].getNameEN();           
            data[i][3] = rated[i].genresToString();
            data[i][4] = rated[i].getYear();
            data[i][5] = rated[i].getRating();
        }      
        initComponents();
    }
    
    ActionListener showListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {            
            
            if(dataTable.getSelectedRow() != -1){
                Object idMovie = dataTable.getValueAt(dataTable.getSelectedRow(), 0);
                Movie passMovie = movies[Integer.parseInt(idMovie.toString()) - 1];
                showMovie dialog = null;
                try {
                    dialog = new showMovie(passMovie);
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }

        }
    }; 
    
    
}
