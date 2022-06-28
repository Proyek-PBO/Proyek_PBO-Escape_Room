package pbo.Level1;

import javax.swing.JLabel;

public class Goblin extends Creature {
    private javax.swing.JLabel goblin;
    
    public Goblin() {
    }

    public Goblin(JLabel goblin) {
        this.goblin = goblin;
    }

    @Override
    public void Attack(Creature papan[][], int x, int y) {
    }

    public JLabel getGoblin() {
        return goblin;
    }

    public void setGoblin(JLabel goblin) {
        this.goblin = goblin;
    }
    
}
