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
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author David Löffler, Marek Szeles
 */
public class homePanel extends JPanel{
    
    JPanel welcomePage = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    Font h1;
    Font subheading;
    Font plaintext;
    
    public homePanel(){
        
        //TODO: Move to separate package
        try {
            h1 = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\Main\\Resources\\Fonts\\BCGHENSANSBOLD.TTF"));
            h1 = h1.deriveFont(36F);
            
            subheading = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\Main\\Resources\\Fonts\\BCGHENSANSITALIC.TTF"));
            subheading = subheading.deriveFont(28F);
            
            plaintext = Font.createFont(Font.TRUETYPE_FONT, new File(".\\src\\main\\java\\Main\\Resources\\Fonts\\BCGHENSANSLIGHT.TTF"));
            plaintext = plaintext.deriveFont(20F);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(homePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents(){
     
        //setting the logo
        JLabel logoLabel = new JLabel(new ImageIcon(".\\src\\main\\java\\Main\\Resources\\Logo_label.png"));
        
        //setting text fields
        JLabel heading = new JLabel("Welcome to CinemaSea!");
        heading.setFont(h1);
        
        JLabel intro = new JLabel("<html><body style='width: 350px; text-align:left'>CinemaSea is a simple tool to keep track of the movies you watch, and rate them! <br><br> In the tabs above, you can choose to view the full database of movies, movies rated by you, actors, and other people from the Movie world! Each person has their own profile you can view by clicking them. <br><br> Let's get started! <br><br> Remember - CinemaSea is still Work in progress!</body></html>");
        intro.setFont(plaintext);
        
        welcomePage.setLayout(new GridBagLayout());
        //set gridbag alignment
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 20, 20);
        //adding the logo and text to the layout
        welcomePage.add(logoLabel,gbc);
        
        gbc.gridy = 1;
        welcomePage.add(heading,gbc);
        
        gbc.gridy = 2;
        welcomePage.add(intro,gbc);
        
        add(welcomePage, BorderLayout.NORTH);
    }
    
}
