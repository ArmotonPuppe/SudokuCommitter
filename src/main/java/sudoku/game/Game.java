package sudoku.game;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class Game {
    private Puzzle puzzle;
    private Player player;
    private Settings settings;
    private Path path;
    private Instant startTime;
    private Instant endTime;

    public Game() {
        Settings settings = new Settings();
        path = Paths.get("config/setting.properties");
    }

    public Game(Settings settings, Path path) {
        this.settings = settings;
        this.path = path;
    }
    public void start() throws IOException {
        settings.load(path);
        PuzzleFactory factory = new PuzzleFactory();
        puzzle = factory.createPuzzle(settings.getDifficulty(), settings.getGridSize());
        startTime = Instant.now();
    }
    public void newGame(Difficulty difficulty, int gridSize) throws IOException {
        settings.setDifficulty(difficulty);
        settings.setGridSize(gridSize);
        settings.save(path);
        start();
    }
    public void checkSolution() {
        if(isSolved()){
            end();
        }
    }
    public void end() {
        endTime = Instant.now();
    }
    public boolean isSolved() {
        return puzzle.isSolved();
    }

    public void setNumber(int x, int y, int number){
        int prev = puzzle.getGrid().getCell(x, y).getNumber();
        puzzle.getGrid().setCellNumberAt(x, y, number);
    }

    public Instant getStartTime() {
        return startTime;
    }
    public Instant getEndTime() {
        return endTime;
    }

    public Settings getSettings() {
        return settings;
    }
    public Puzzle getPuzzle() {
        return puzzle;
    }

}
