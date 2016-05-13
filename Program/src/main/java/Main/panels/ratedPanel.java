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
import Main.User;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author David Löffler
 */
public class ratedPanel extends JPanel{
    
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"ID", "Name CZ",
                        "Name EN",
                        "Genres",
                        "Release date", "Rating"};
    private Movie[] rated;
    private User user;
    public ratedPanel(){
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        dataTable = new JTable(data,columnNames);        
        dataTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Rated movies");
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
    
    public void passData(User user){
        this.rated = user.getRated();
        this.user = user;
        data = new Object[rated.length][6];
        
        for(int i = 0; i < rated.length; i++){
            data[i][0] = rated[i].getId();
            data[i][1] = rated[i].getNameCZ();
            data[i][2] = rated[i].getNameEN();
            
            String genres = "";
            
            for(int k = 0; k < rated[i].getGenres().length; k++){
                if(rated[i].getGenres().length > 1 && rated[i].getGenres().length != k+1){
                    genres += rated[i].getGenres()[k] + " / ";
                }
                else{
                    genres += rated[i].getGenres()[k];
                }
            }
            
            data[i][3] = genres;
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
                Movie passMovie = null;
                showMovie dialog = null;
                
                for(Movie mv : rated){
                    if(mv.getId() == Integer.parseInt(idMovie.toString())){
                        passMovie = mv;
                    }                        
                }
                
                try {
                    dialog = new showMovie(passMovie,user);
                } catch (IOException ex) {
                    Logger.getLogger(allMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.setVisible(true);
            }
        }
    };
    
    
}
