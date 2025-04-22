import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.game.*;
import sudoku.board.*;

public class PuzzleTests {

    @Test
    public void testPuzzleFactory() {

        PuzzleFactory puzzleFactory = new PuzzleFactory();
        Difficulty difficulty = Difficulty.EASY;
        String givens = "..2.......4...79.83..9.516......2.1.......8....7.98.52..64........2.3.85..3.86.9.";
        String solution = "962814573541367928378925164839542716625731849417698352786459231194273685253186497";
        int gridSize = 9;
        assertDoesNotThrow(()-> {
            Puzzle puzzle = puzzleFactory.createPuzzle(difficulty, gridSize);
            assertNotNull(puzzle);
            assertEquals(difficulty, puzzle.getDifficulty());
            assertEquals(gridSize, puzzle.getGrid().getSize());
            assertEquals(solution, puzzle.getSolution());
            assertEquals(givens, puzzle.getGivens());
            assertEquals(givens, puzzle.getGrid().toString());
        });

    }
    @Test
    public void testPuzzleValidation()throws Exception{

        String givens = ".".repeat(81);
        String solution = "1".repeat(81);
        Difficulty difficulty = Difficulty.EASY;
        int gridSize = 9;
        Puzzle puzzle = new Puzzle(1, gridSize, difficulty, solution, givens);
        assertNotNull(puzzle);
        assertFalse(puzzle.isSolved());
        for(int i = 0; i < solution.length(); i++){
            puzzle.getGrid().setCellNumberAt(i%gridSize, i/gridSize, 1);
        }
        assertEquals(solution, puzzle.getGrid().toString());
        assertTrue(puzzle.getGrid().isFilled());
        assertTrue(puzzle.isSolved());

    }

}

