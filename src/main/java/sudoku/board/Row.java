package sudoku.board;

import java.util.List;

public class Row implements Region {
    public List<Cell> cells;

    public boolean validate() {
        return true;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean contains(Cell cell) {
        return cells.contains(cell);
    }

    public void add(Cell cell) {
        cells.add(cell);
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
