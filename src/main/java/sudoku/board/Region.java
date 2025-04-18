package sudoku.board;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Region {
    List<Cell> cells;
    int size;
    RegionType type;

    public Region(RegionType type, int size) {
        this.cells = new ArrayList<>(size);
        this.size = size;
        this.type = type;
    }
    public Region(RegionType type, Cell[] cells) {
        this.size = cells.length;
        this.type = type;
        this.cells = new ArrayList<Cell>(Arrays.asList(cells));

    }

    public RegionType getType() {
        return type;
    }
    public void add(Cell cell) {
        cells.add(cell);
    }
    public void addCells(Cell[] cells){
        this.cells.addAll(Arrays.asList(cells));
    }
    public void addCell(Cell cell) throws IllegalArgumentException {
        if(null==cell){
            throw new IllegalArgumentException("cell is null");
        }
        cells.add(cell);
    }
    public List<Cell> getCells() {
        return cells;
    }


    public boolean regionHasConflict(){
        Set<Integer> setCells = getNumbers(getSetCells());
        if(setCells.isEmpty()){
            return false;
        }
        return cells.stream().filter(cell -> !cell.isSet() && !cell.isEmpty()).anyMatch(cell -> setCells.contains(cell.getNumber()));
    }

    public List<Cell> getSetCells(){
        return cells.stream()
                    .filter(Cell::isSet)
                    .collect(Collectors.toList());
    }
    public Set<Integer> getNumbers(List<Cell> cells) {
        return cells.stream().map(Cell::getNumber).collect(Collectors.toSet());
    }


}

