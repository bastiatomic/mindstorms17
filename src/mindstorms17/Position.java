package mindstorms17;

public class Position {

    public int x;
    public int y;
    public boolean headSwitch;
    public boolean fixer;

    public Position(int x, int y, boolean headSwitch, boolean fixer){
        this.x = x;
        this.y = y;
        this.headSwitch = headSwitch;
        this.fixer = fixer;
    }
    public Position(int x, int y, boolean headSwitch){
        this.x = x;
        this.y = y;
        this.headSwitch = headSwitch;
        this.fixer = false;
    }
    
}
