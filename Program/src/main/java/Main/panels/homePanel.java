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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author David Löffler, Marek Szeles
 */
public class homePanel extends JPanel{
    
    JPanel welcomePage = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    
    public homePanel(){
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents(){
     
        //setting the logo
        JLabel logoLabel = new JLabel(new ImageIcon(".\\src\\main\\java\\Main\\Resources\\Logo_label.png"));
        
        //setting text fields
        JLabel heading = new JLabel("Welcome to CinemaSea!");
        heading.setFont(new Font("Arial",Font.BOLD,42));
        
        JLabel intro = new JLabel("CinemaSea is a simple tool to keep track of the movies you watch, and rate them! \n \n In the tabs above, you can pick xyz \n \nLet's get started!");
        intro.setFont(new Font("Arial",Font.PLAIN,18));
        
        welcomePage.setLayout(new GridBagLayout());
        
        gbc.insets = new Insets(5, 5, 5, 5);
        //adding the logo and text to the layout
        welcomePage.add(logoLabel,gbc);
        
        gbc.gridy = 1;
        welcomePage.add(heading,gbc);
        
        gbc.gridy = 2;
        welcomePage.add(intro,gbc);
       
        
        add(welcomePage,BorderLayout.NORTH);
        add(welcomePage,BorderLayout.WEST);
    }
    
}
