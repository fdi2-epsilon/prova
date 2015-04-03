package pasquapro;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import provaepsilon.Pasqua;
/**
 *
 * @author Luca
 */
public class PannelloPasqua extends JPanel{
    JTextArea input = new JTextArea();
    JTextArea output = new JTextArea();
    JButton calcola = new JButton("Calcola");
    JLabel lbl1 = new JLabel("Anno:");
    JLabel lblRes = new JLabel("Risultato: ");
    JButton fanet = new JButton("Nuovo");
    
    public PannelloPasqua() throws Exception{
    super();
    add(lbl1);
    add(input);
    add(lblRes);
    add(output);
    add(calcola);
    add(fanet);
    setLayout(new GridLayout(5, 2));
    stampaData();
    cancella();
    }
    public void stampaData(){
        calcola.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String annoS = input.getText();
                int anno = Integer.parseInt(annoS);
                Pasqua pasqua = new Pasqua(anno);
                output.setText("Nel "+annoS+" Pasqua cade il: "+pasqua.calcolaData()[0]+"/"+pasqua.calcolaData()[1]);
            }
        });
    }
    public void cancella(){
        fanet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                input.setText("");
                output.setText("");
            }
        });
    }
}
