import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.board.Grid;
import sudoku.board.Region;
import sudoku.board.Row;
import sudoku.board.RegionType;
import sudoku.board.Cell;
import java.util.List;

public class RegionTests {
    String puzzle = "005007001010052700370000006654000300001090000007300100083760200026100000009020800";
    String solution = "965837421418652739372914586654271398831495672297386145583769214726148953149523867";


    @Test
    public void testValidation(){
        Region region = new Row(9);
        for(int i = 0; i < 7; i++){
            region.addCell(new Cell(9, i+1));
        }
        Cell testCell1 = new Cell(9);
        Cell testCell2 = new Cell(9);
        region.addCell(testCell1);
        region.addCell(testCell2);
        assertTrue(testCell2.setNumber(8));
        assertTrue(testCell1.setNumber(1));
        assertEquals(1, testCell1.getNumber());
        assertTrue(region.regionHasConflict());
        assertTrue(testCell1.setNumber(8));
        assertFalse(region.regionHasConflict());
    }
    @Test
    void regionDetectsConflicts() {
        Cell[] cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(9);
        }

        cells[0].setNumber(5); // not a given
        cells[1] = new Cell(9, 5); // a given

        Region row = new Row(cells);
        assertTrue(row.regionHasConflict());
    }

    @Test
    void regionNoConflictsWhenUnique() {
        Cell[] cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(9, i + 1);
        }
        Region row = new Row(cells);
        assertFalse(row.regionHasConflict());
    }

    @Test
    void getSetCellsOnlyReturnsGivens() {
        Cell[] cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(9);
        }
        cells[0] = new Cell(9, 5); // set

        Region row = new Row(cells);
        List<Cell> setCells = row.getSetCells();

        assertEquals(1, setCells.size());
        assertEquals(5, setCells.get(0).getNumber());
    }

}
