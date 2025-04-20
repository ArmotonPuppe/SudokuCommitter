package sudoku.board;

import java.util.*;

public class Grid {
    private Cell[][] cells; //the grid itself, maybe there is a better datastructure but array is simple
    private final int size; //Size of grid. not sure if necessary
    private final String givens; //String of numbers to be set in the grid at start
    private EnumMap<RegionType, List<Region>> regions;

    public Grid(int size, String givens) {
        cells = new Cell[size][size];
        this.size = size;
        this.givens = givens;
        regions = new EnumMap<>(RegionType.class);
        populateGrid(givens);
        initializeRegions();

    }

    /** populates the grid with empty cells and givens
    * it checks if there is space in the grid to put in all the givens
    * @throws IllegalArgumentException if there isn't enough space or givens is null*/
    public void populateGrid(String givens) throws IllegalArgumentException {
        if(null!=givens && size*size>=givens.length()) {
            int i = 0, x = 0, y = 0;
            while(i<givens.length()) {
                if(givens.charAt(i) == '0') {
                    cells[y][x] = new Cell(size);

                }else{
                    cells[y][x] = new Cell(size, givens.charAt(i)-'0');
                }
                x++;
                if((x==size)) {
                    x = 0;
                    y++;
                }
                i++;
            }
        }
    }
    private void initializeRegions(){
        regions.put(RegionType.ROW, new ArrayList<>());
        regions.put(RegionType.COLUMN, new ArrayList<>());
        regions.put(RegionType.BOX, new ArrayList<>());
        initializeRowRegions();
        initializeColumnRegions();
        initializeBoxRegions();
    }

    private void initializeRowRegions(){
        for(int y = 0; y<size; y++) {
            Cell[] rowCells = cells[y];
            Region row = new Row(rowCells);
            regions.get(RegionType.ROW).add(row);
            for(Cell cell : rowCells) {
                cell.addRegion(row);
            }
        }
    }
    private void initializeColumnRegions(){
        for(int x = 0; x<size; x++) {
            Cell[] columnCells = new Cell[size];
            for(int y = 0; y<size; y++) {
                columnCells[y] = cells[y][x];
            }
            Region column = new Column(columnCells);

            regions.get(RegionType.COLUMN).add(column);

            for(Cell cell : columnCells) {
                cell.addRegion(column);
            }
        }
    }
    private void initializeBoxRegions(){
        for(int i = 0; i<size; i++) {
            Region box = new Box(9);
            regions.get(RegionType.BOX).add(box);
        }
        int boxSize = 3;
        for(int i = 0; i<size*size; i++) {
            int x = i%size;
            int y = i/size;
            Cell cellToAdd = cells[y][x];
            Region region = regions.get(RegionType.BOX).get(getBoxIndex(x, y, boxSize));
            region.add(cellToAdd);
            cellToAdd.addRegion(region);
        }
    }
    private int getBoxIndex(int x, int y, int boxSize){
        return (y/boxSize) * boxSize + x/boxSize;
    }



    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public void setCellNumberAt(int x, int y, int number)throws NullPointerException {

        if(cells[y][x]==null) {
            throw new NullPointerException("Cell is null at: "+x+","+y);
        }
        if(x>size-1 || y>size-1) {
            throw new IllegalArgumentException("Number must be between 0 and "+size);
        }
        if(getCell(x, y).setNumber(number)){
            System.out.format("Cell at position {%d, %d} was set to %d", x, y, number);
        }else{
            System.out.println("Cell not set");
        }
    }

    public Map<RegionType, List<Region>> getRegions() {
        return regions;
    }
    public boolean isFilled() {
        return Arrays.stream(cells).flatMap(Arrays::stream).noneMatch(Cell::isEmpty);
    }

    //Creates a string of the grid
    //0 = empty grid
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Cell[] row : cells) {
            for(Cell cell : row) {
                if(cell.isEmpty()) {
                    result.append('.');
                }else{
                    result.append(cell.getNumber());
                }
            }
        }
        return result.toString();
    }

}
