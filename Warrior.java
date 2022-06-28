package pbo.Level1;

public class Warrior extends Creature {
    
    public Warrior() {
    }
    
    @Override
    public void Attack(Creature papan[][], int x, int y) {
        int[] cx = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
        int[] cy = new int[]{1, 1, 0, -1, -1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            if (y+cy[i] >= 0 && y+cy[i] < 7 && x+cx[i] >= 0 && x+cx[i] < 7) {
                if (papan[y+cy[i]][x+cx[i]] != null) {
                    if (papan[y+cy[i]][x+cx[i]] instanceof Goblin) {
                        ((Goblin)papan[y+cy[i]][x+cx[i]]).getGoblin().setVisible(false);
                        papan[y+cy[i]][x+cx[i]] = null;
                    }
                }
            }
        }
    }

}
