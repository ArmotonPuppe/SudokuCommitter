package sudoku.board;
import java.util.List;

public class Column implements Region {
    public List<Cell> cells;

    public boolean validate() {
        return true;
    }

    public boolean contains(Cell cell) {
        return cells.contains(cell);
    }
    public void add(Cell cell) {

    }

}
