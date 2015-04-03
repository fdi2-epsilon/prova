/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasquapro;

import javax.swing.JFrame;

/**
 *
 * @author Luca
 */
public class MainPasqua {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Calcola Pasqua");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.add(new PannelloPasqua());
        frame.setVisible(true);
    }
}
