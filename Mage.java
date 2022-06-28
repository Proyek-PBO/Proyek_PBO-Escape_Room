package pbo.Level1;

public class Mage extends Creature {
    
    public Mage() {
    }
    
    @Override
    public void Attack(Creature papan[][], int x, int y) {
        int cx = x, cy = y;
        while(++cx < 7 && --cy >= 0){
            if (papan[cy][cx] != null) {
                if (papan[cy][cx] instanceof Goblin) {
                    ((Goblin)papan[cy][cx]).getGoblin().setVisible(false);
                    papan[cy][cx] = null;
                }
            }
        }
        cx = x; cy = y;
        while(++cx < 7 && ++cy < 7){
            if (papan[cy][cx] != null) {
                if (papan[cy][cx] instanceof Goblin) {
                    ((Goblin)papan[cy][cx]).getGoblin().setVisible(false);
                    papan[cy][cx] = null;
                }
            }
        }
        cx = x; cy = y;
        while(--cx >= 0 && ++cy < 7){
            if (papan[cy][cx] != null) {
                if (papan[cy][cx] instanceof Goblin) {
                    ((Goblin)papan[cy][cx]).getGoblin().setVisible(false);
                    papan[cy][cx] = null;
                }
            }
        }
        cx = x; cy = y;
        while(--cx >= 0 && --cy >= 0){
            if (papan[cy][cx] != null) {
                if (papan[cy][cx] instanceof Goblin) {
                    ((Goblin)papan[cy][cx]).getGoblin().setVisible(false);
                    papan[cy][cx] = null;
                }
            }
        }
    }
    
}
