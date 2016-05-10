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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author David Löffler
 */
public class databasePanel extends JPanel{
    
    public databasePanel(){
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents(){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
       
        // ACTOR COLUMN
        JPanel actor = new JPanel(new GridBagLayout());
        JButton actEdit = new JButton("EDIT");
        JButton actAdd = new JButton("ADD");
        JLabel actHeader = new JLabel("ACTORS");
        actHeader.setFont(new Font("Arial",Font.BOLD,12));
        
        actor.add(actHeader, gbc);
        gbc.gridy = 1;
        actor.add(actAdd,gbc);
        gbc.gridy = 2;
        actor.add(actEdit,gbc);
        
        // DIRECTOR COLUMN
        JPanel director = new JPanel(new GridBagLayout());
        JButton dirEdit = new JButton("EDIT");
        JButton dirAdd = new JButton("ADD");
        JLabel dirHeader = new JLabel("DIRECTORS");
        dirHeader.setFont(new Font("Arial",Font.BOLD,12));
        
        gbc.gridy = 0;
        director.add(dirHeader,gbc);
        gbc.gridy = 1;
        director.add(dirAdd,gbc);
        gbc.gridy = 2;
        director.add(dirEdit,gbc);
        
        // SCENARISTS COLUMN
        JPanel scenarists = new JPanel(new GridBagLayout());
        JButton scnEdit = new JButton("EDIT");
        JButton scnAdd = new JButton("ADD");
        JLabel scnHeader = new JLabel("SCENARISTS");
        scnHeader.setFont(new Font("Arial",Font.BOLD,12));
        
        gbc.gridy = 0;
        scenarists.add(scnHeader,gbc);
        gbc.gridy = 1;
        scenarists.add(scnAdd,gbc);
        gbc.gridy = 2;
        scenarists.add(scnEdit,gbc);
        
        // MOVIE COLUMN
        JPanel movie = new JPanel(new GridBagLayout());
        JButton mvEdit = new JButton("EDIT");
        JButton mvAdd = new JButton("ADD");
        JLabel mvHeader = new JLabel("MOVIES");
        mvHeader.setFont(new Font("Arial",Font.BOLD,12));
        
        gbc.gridy = 0;
        movie.add(mvHeader,gbc);
        gbc.gridy = 1;
        movie.add(mvAdd,gbc);
        gbc.gridy = 2;
        movie.add(mvEdit,gbc);
        
        // ADDING IT INTO MAINPANEL
        gbc.gridy = 0;
        mainPanel.add(movie,gbc);
        gbc.gridx = 1;
        mainPanel.add(actor,gbc);
        gbc.gridx = 2;
        mainPanel.add(director,gbc);
        gbc.gridx = 3;
        mainPanel.add(scenarists,gbc);
        
        // ADDING INTO THIS PANEL
        JLabel header = new JLabel("Database editor");
        header.setFont(new Font("Arial",Font.BOLD,18));
        add(header,BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);
    }
    
}
