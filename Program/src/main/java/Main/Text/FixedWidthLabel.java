/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Text;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Marek
 */
public class FixedWidthLabel {
        public static void main(String[] srgs) {
        String s = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu nulla urna. Donec sit amet risus nisl, a porta enim. Quisque luctus, ligula eu scelerisque gravida, tellus quam vestibulum urna, ut aliquet sapien purus sed erat. Pellentesque consequat vehicula magna, eu aliquam magna interdum porttitor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed sollicitudin sapien non leo tempus lobortis. Morbi semper auctor ipsum, a semper quam elementum a. Aliquam eget sem metus.";
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";

        JOptionPane.showMessageDialog(null, new JLabel(html1+"200"+html2+s));
        JOptionPane.showMessageDialog(null, new JLabel(html1+"300"+html2+s));
    }
}
