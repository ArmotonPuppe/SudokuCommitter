package sudoku.board;
import java.util.List;

public class Column extends Region{

    public Column(int size){
        super(RegionType.COLUMN, size);
    }
    public Column(Cell[] cells) {
        super(RegionType.COLUMN, cells);
    }

}
