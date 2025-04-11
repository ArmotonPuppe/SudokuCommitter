import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.Grid;

public class SudokuSolutionTest {

    @Test
    public void testPuzzleMatches(){
        String puzzle = "005007001010052700370000006654000300001090000007300100083760200026100000009020800";
        String solution = "965837421418652739372914586654271398831495672297386145583769214726148953149523867";

        for(int i = 0; i < puzzle.length(); i++){
            if(puzzle.charAt(i) != '0'){
                assertEquals(solution.charAt(i), puzzle.charAt(i), "didn't match at: " + i);
            }
        }
    }
}
