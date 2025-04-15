package sudoku.board;

import java.util.List;

public class Row extends Region {

    public Row(int size){
        super(size);
    }
    public Row(Cell[] cells) {
        super(cells);
    }
}
