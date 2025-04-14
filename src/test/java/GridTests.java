import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.board.Grid;

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
}
