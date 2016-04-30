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

import Main.Movie;
import Main.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import static java.awt.font.TextAttribute.FONT;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author David Löffler
 */
public class ratedPanel extends JPanel{
    
    private User user;
    private Object[][] data;    
    private JTable dataTable;
    private String[] columnNames = {"Name CZ",
                        "Name EN",
                        "Genres",
                        "Release date"};
    GridBagConstraints gbc = new GridBagConstraints();
    
    public ratedPanel(){
        setLayout(new BorderLayout());
    }    
    
    private void initComponents(){
        dataTable = new JTable(data,columnNames);        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        JPanel toolPane = new JPanel();
        toolPane.setLayout(new BorderLayout());
        Label headline = new Label("Rated movies");
        headline.setFont(new Font("Arial",Font.PLAIN,18));
        JButton btn = new JButton("Show");
        toolPane.add(headline, BorderLayout.WEST);
        toolPane.add(btn, BorderLayout.EAST);
        
        add(toolPane,BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
    }
    
    public void passData(Movie[] rated){
        
        data = new Object[rated.length][4];
        
        for(int i = 0; i < rated.length; i++){
            data[i][0] = rated[i].getNameCZ();
            data[i][1] = rated[i].getNameEN();
            
            String genres = "";
            
            for(int k = 0; k < rated[i].getGenres().length; k++){
                if(rated[i].getGenres().length > 1 && rated[i].getGenres().length != k+1){
                    genres += rated[i].getGenres()[k] + " / ";
                }
                else{
                    genres += rated[i].getGenres()[k];
                }
            }
            
            data[i][2] = genres;
            data[i][3] = rated[i].getYear();
        }      
        initComponents();
    }
    
    
    
    
}
