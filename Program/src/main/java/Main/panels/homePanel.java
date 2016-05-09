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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author David Löffler, Marek Szeles
 */
public class homePanel extends JPanel{
    
    public homePanel(){
        setLayout(new BorderLayout());
        initComponents();
    }
    
    private void initComponents(){
        JPanel welcomePage = new JPanel();
        
        //setting the logo
        JLabel logoLabel = new JLabel(new ImageIcon(".\\src\\main\\java\\Main\\Resources\\Logo_label.png"));
        
        //setting text fields
        JLabel heading = new JLabel("Welcome to CinemaSea!");
        heading.setFont(new Font("Arial",Font.PLAIN,18));
        
        //adding the logo and text to the layout
        welcomePage.add(logoLabel);
        welcomePage.add(heading);
        
        add(welcomePage,BorderLayout.NORTH);
        add(welcomePage,BorderLayout.WEST);
    }
    
}
