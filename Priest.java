package pbo.Level1;

import java.awt.Color;
import java.util.ArrayList;

public class Priest extends Creature {

    public Priest() {
    }
    
    @Override
    public void Attack(Creature papan[][], int x, int y) {
    }
    
    public void Heal(javax.swing.JPanel p, javax.swing.JLabel l, javax.swing.JLabel priest_copy, int x, int y, javax.swing.JPanel curr, ArrayList tmp){
        p.setVisible(true);
        l.setVisible(false);
        priest_copy.setLocation(x*100+10, y*100);
        priest_copy.setVisible(true);
        curr.setBackground(Color.WHITE);
        curr.setVisible(false);
        tmp.clear();
    }
    
}
