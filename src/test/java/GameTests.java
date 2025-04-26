import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sudoku.board.Grid;
import sudoku.board.RegionType;
import sudoku.board.Cell;
import sudoku.game.Difficulty;
import sudoku.game.Game;
import sudoku.game.Settings;

import java.io.IOException;

public class GameTests {

    @Test
    public void testGameConstructors() throws IOException {
        Game game = new Game();
        assertNotNull(game);
    }
    @Test
    public void testGameSettings()throws IOException {
        Settings settings = new Settings();
        Game game = new Game();
        assertNotNull(settings);
        assertNull(settings.getDifficulty());
        assertEquals(0, settings.getGridSize());
        settings.load(game.getPath());
        assertNotNull(settings.getDifficulty());
        assertEquals(Difficulty.EASY, settings.getDifficulty());
        assertEquals(9, settings.getGridSize());
        assertNotNull(game.getSettings());
        game.start();
        assertEquals(settings.getDifficulty(), game.getSettings().getDifficulty());
        assertEquals(settings.getGridSize(), game.getSettings().getGridSize());
    }
}
