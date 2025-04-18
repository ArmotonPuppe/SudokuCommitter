package sudoku.board;
import java.util.EnumMap;
public class Cell {

    private int number;
    private final boolean isSet; //If cell is a given or a hint the cell is set and cannot be changed
    final int bound;//upper limit for what number can be set in the cell
    private EnumMap<RegionType, Region> regions;//map of regions the cell is in
    /**
     * Constructor for Cells with given and hint numbers.
     * Sets isSet to true
     * @param bound Upper limit for number in cell, based on Grid size.
     * @param number the number in the cell
     */
    public Cell(int bound, int number) {
        isSet = true;
        this.bound = bound;
        this.number = number;
        regions = new EnumMap<>(RegionType.class);
    }

    /**
     * Constructor for empty cells
     * @param bound //cell number upperlimit
     */
    public Cell(int bound) {
        this.bound = bound;
        this.isSet = false;
        regions = new EnumMap<>(RegionType.class);
    }

    public int getNumber() {
        return number;
    }

    /**
     * Sets number in the cell.
     * Cannot be set if number is negative or larger than bound.
     * @param number number to be set
     */
    public boolean setNumber(int number) throws IllegalArgumentException{
        if(number<=0 || number>bound || isSet){
            return false;
        }
        this.number = number;
        return true;
    }
    public boolean isEmpty() {
        return number == 0;
    }

    public boolean isSet() {
        return isSet;
    }
    public void addRegion(Region region) {
        regions.put(region.getType(), region);
    }


}
