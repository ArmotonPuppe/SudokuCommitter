import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.board.Grid;
import sudoku.board.RegionType;
import sudoku.board.Cell;

public class GridTests {
private final String PUZZLE = "005007001010052700370000006654000300001090000007300100083760200026100000009020800";
    @Test
    public void populateGridTest() {
        int size = 9;
        String givens = PUZZLE;
        System.out.println(givens.length());
        Grid grid = new Grid(size, givens);
        assertDoesNotThrow(() -> grid.populateGrid(givens));
        assertEquals(givens, grid.toString());
    }
    @Test
    void gridInitializesCellsCorrectly() {
        Grid grid = new Grid(9, "123456789" + "0".repeat(72));
        Cell[][] cells = grid.getCells();

        assertEquals(9, cells.length);
        assertEquals(9, cells[0].length);
        assertEquals(1, cells[0][0].getNumber());
        assertTrue(cells[0][0].isSet());
        assertTrue(cells[1][0].isEmpty());
    }

    @Test
    void settingANumberUpdatesCell() {
        Grid grid = new Grid(9, "0".repeat(81));
        grid.setCellNumberAt(0, 0, 5);
        assertEquals(5, grid.getCell(0, 0).getNumber());
    }

    @Test
    void toStringMatchesGivens() {
        String givens = "123456789" + "0".repeat(72);
        Grid grid = new Grid(9, givens);
        assertEquals(givens, grid.toString());
    }

    @Test
    void allRegionsAreInitializedCorrectly() {
        Grid grid = new Grid(9, "0".repeat(81));
        assertEquals(9, grid.getRegions().get(RegionType.ROW).size());
        assertEquals(9, grid.getRegions().get(RegionType.COLUMN).size());
        assertEquals(9, grid.getRegions().get(RegionType.BOX).size());
    }
}

