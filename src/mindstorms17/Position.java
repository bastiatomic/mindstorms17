package mindstorms17;

class Position {
    //every position the robot is going to visit is a Position object

    int x;
    int y;
    boolean headSwitch;

    Position(int x, int y, boolean headSwitch){
        this.x = x;
        this.y = y;
        this.headSwitch = headSwitch;
    }
    
}
