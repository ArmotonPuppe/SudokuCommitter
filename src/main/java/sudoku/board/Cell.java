package sudoku.board;

public class Cell {

    private int number;
    private final boolean isSet; //If cell is a given or a hint the cell is set and cannot be changed
    final int bound; //upper limit for what number can be set in the cell

    /**
     * Constructor for Cells with given and hint numbers.
     * Sets isSet to true
     * @param bound Upper limit for number in cell, based on Grid size.
     * @param number the number in the cell
     */
    Cell(int bound, int number) {
        isSet = true;
        this.bound = bound;
        this.number = number;
    }

    /**
     * Constructor for empty cells
     * @param bound //cell number upperlimit
     */
    public Cell(int bound) {
        this.bound = bound;
        this.isSet = false;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Sets number in the cell.
     * Cannot be set if number is negative or larger than bound.
     * @param number number to be set
     */
    public void setNumber(int number) {
        this.number = (number>bound || isSet) ? 0 : number;
    }
    public boolean isEmpty() {
        return number == 0;
    }

    public boolean isSet() {
        return isSet;
    }

}
