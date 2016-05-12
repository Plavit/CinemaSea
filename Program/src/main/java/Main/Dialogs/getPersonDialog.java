/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

/**
 *
 * @author David Löffler
 */
public class getPersonDialog extends JDialog{

    private movieDialog md;
    private char Who;
    
    public getPersonDialog(movieDialog parent, char Who) throws IOException {        
        this.md = parent;
        this.Who = Who;
        setLayout(new BorderLayout());
        
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setBounds(0,0,1150,700);
        setLocationRelativeTo(null);
        setTitle("Data editor");        
        setIconImage(ImageIO.read(new File(".\\src\\main\\java\\Main\\Resources\\Logo_icon.png")));
        
        initComponents();
    }
    
    public void initComponents(){
        
    }
    
}
