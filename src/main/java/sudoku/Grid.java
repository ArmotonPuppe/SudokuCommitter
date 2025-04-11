package sudoku;

public class Grid {
    private Cell[][] cells;
    private final int size;
    private final String givens;

    public Grid(int size, String givens) {
        cells = new Cell[size][size];
        this.size = size;
        this.givens = givens;
    }
    public void populateGrid() throws IllegalArgumentException {
        if(null!=givens && size*size>=givens.length()) {
            int i = 0, x = 0, y = 0;
            while(i<givens.length()) {
                if(givens.charAt(i) == '.') {
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
