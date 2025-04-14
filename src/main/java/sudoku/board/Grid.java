package sudoku.board;

public class Grid {
    private Cell[][] cells; //the grid itself, maybe there is a better datastructure but array is simple
    private final int size; //Size of grid. not sure if necessary
    private final String givens; //String of numbers to be set in the grid at start

    public Grid(int size, String givens) {
        cells = new Cell[size][size];
        this.size = size;
        this.givens = givens;
        populateGrid(givens);
    }

    /* populates the grid with empty cells and givens
    * it checks if there is space in the grid to put in all the givens*/
    public void populateGrid(String givens) throws IllegalArgumentException {
        if(null!=givens && size*size>=givens.length()) {
            int i = 0, x = 0, y = 0;
            while(i<givens.length()) {
                if(givens.charAt(i) == '0') {
                    cells[y][x] = new Cell(size);

                }else{
                    cells[y][x] = new Cell(size, givens.charAt(i));
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
    //Creates a string of the grid
    //0 = empty grid
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Cell[] row : cells) {
            for(Cell cell : row) {
                if(cell.isEmpty()) {
                    result.append('0');
                }else{
                    result.append((char)cell.getNumber());
                }
            }
        }
        return result.toString();
    }

}
