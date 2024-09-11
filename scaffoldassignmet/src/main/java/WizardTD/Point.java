package WizardTD;

public class Point {
    private int row, col;

    /**
     * Constructor of point.
     * @param row
     * @param col
     */
    public Point(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
