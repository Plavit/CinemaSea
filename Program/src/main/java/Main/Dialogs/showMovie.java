/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import Main.Database;
import Main.Login.LoginFrame;
import Main.Movie;
import Main.Person;
import Main.User;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author David Löffler
 */
public class showMovie extends JDialog{
        
    private User user;
    private Movie movie;    
    JComboBox rateBox = new JComboBox();
        
    public showMovie(Movie mv, User user) throws IOException {        
        this.movie = mv;
        this.user = user;
        initComponents();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setBounds(0,0,900,620);
        setLocationRelativeTo(null);
        setTitle("Movie detail - " + mv.getNameCZ());        
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
    }
    
    private void initComponents() throws IOException{        
        
        for(Person pr : movie.getScenarists()){
            System.out.println(pr.getId() + " " + pr.getFullName());
        }
        
        JPanel upperPanel = new JPanel();
        JPanel ratePanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel coverPanel = new JPanel();
        
        ratePanel.setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridBagLayout());
        upperPanel.setLayout(new BorderLayout());
        coverPanel.setLayout(new BorderLayout());     
        
        GridBagConstraints gbc = new GridBagConstraints();
        rateBox.addItem("1");
        rateBox.addItem("2");
        rateBox.addItem("3");
        rateBox.addItem("4");
        rateBox.addItem("5");
        JButton rateButt = new JButton("RATE IT");
        rateButt.addActionListener(rateAction);
        Label rating = new Label("Rating: " + String.valueOf(movie.getRating()));
        Label genres = new Label("Genres: " + movie.genresToString());
        Label actors = new Label("Actors: " + movie.personsToString('A'));
        Label scenarists = new Label("Scenarists: " + movie.personsToString('S'));
        Label directors = new Label("Directors: " + movie.personsToString('D'));
        JTextArea descriptArea = new JTextArea("Description: " + prepareDescription(movie.getDescription()));
        descriptArea.setEditable(false);
        descriptArea.setHighlighter(null);
        descriptArea.setOpaque(false);
        
        // SETTING OF UPPER PANEL
        Label headline = new Label(movie.getNameCZ());
        headline.setFont(new Font("Arial",Font.BOLD,18));
        gbc.insets = new Insets(5, 5, 5, 5);
        
        ratePanel.add(rateBox,gbc);
        gbc.gridx = 1;
        ratePanel.add(rateButt,gbc);        
        
        upperPanel.add(ratePanel,BorderLayout.EAST);
        upperPanel.add(headline,BorderLayout.WEST);        
        
        // SETING OF INFO PANEL
        gbc.gridx = 0;
        gbc.gridy = 0;        
        infoPanel.add(rating,gbc);
        gbc.gridy = 1;
        infoPanel.add(genres,gbc);
        gbc.gridy = 2;
        infoPanel.add(directors,gbc);
        gbc.gridy = 3;
        infoPanel.add(scenarists,gbc);
        gbc.gridy = 4;
        infoPanel.add(actors,gbc);
        gbc.gridy = 5;
        infoPanel.add(descriptArea,gbc);
        
        
        // SETTING OF COVER IMAGE
        Image image = null;
        JLabel cvImage = null;
      
        if (!"null".equals(movie.getCoverImage())) {
            try {
                URL url = new URL(movie.getCoverImage());
                image = ImageIO.read(url);
            } catch (IOException e) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else{
            image = ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\nocover.jpg"));
        }
        cvImage = new JLabel(new ImageIcon(image));
        coverPanel.add(cvImage,BorderLayout.CENTER);
        
        // ADDING THE PANELS        
        add(upperPanel,BorderLayout.NORTH);
        add(coverPanel,BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        
    }
    
    private String prepareDescription(String desc){
        char[] arr = desc.toCharArray();
        String info = "";
        
        for(int i = 0; i < arr.length; i++){
            if(i % 100 == 0){
                info += "\n";
            }
            info += arr[i];
        }
        return info;
    }
    
    ActionListener rateAction = (ActionEvent actionEvent) -> {
        Database db = new Database();
        Object obj = rateBox.getSelectedItem();
        double rate = Double.valueOf(obj.toString());
        db.rateMovie(rate, movie.getId(),user);
        JOptionPane.showMessageDialog(new JFrame(),
                    "Movie has been rated!",
                    "Rating accepted",
                    JOptionPane.PLAIN_MESSAGE);
    }; 
   
}
