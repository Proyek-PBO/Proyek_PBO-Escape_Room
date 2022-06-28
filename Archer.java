package pbo.Level1;

public class Archer extends Creature {
    
    public Archer() {
    }
    
    @Override
    public void Attack(Creature papan[][], int x, int y) {
        for (int cx = 0; cx < 7; cx++) {
            if (papan[y][cx] != null) {
                if (papan[y][cx] instanceof Goblin) {
                    ((Goblin)papan[y][cx]).getGoblin().setVisible(false);
                    papan[y][cx] = null;
                }
            }
        }
        for (int cy = 0; cy < 7; cy++) {
            if (papan[cy][x] != null) {
                if (papan[cy][x] instanceof Goblin) {
                    ((Goblin)papan[cy][x]).getGoblin().setVisible(false);
                    papan[cy][x] = null;
                }
            }
        }
    }
    
}
