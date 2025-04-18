package sudoku.board;

public class Box extends Region{

    public Box(int size){
        super(RegionType.BOX, size);
    }
    public Box(Cell[] cells) {
        super(RegionType.BOX, cells);
    }

}
