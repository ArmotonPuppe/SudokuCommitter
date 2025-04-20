package sudoku.game;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {
    private Difficulty difficulty;
    private int gridSize;

    public void load(Path path) throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(path)) {
            props.load(in);
        }
        this.difficulty = Difficulty.valueOf(props.getProperty("difficulty", "EASY"));
        this.gridSize = Integer.parseInt(props.getProperty("gridSize", "9"));
    }

    public void save(Path path) throws IOException {
        Properties props = new Properties();
        props.setProperty("difficulty", difficulty.name());
        props.setProperty("gridSize", String.valueOf(gridSize));
        try (OutputStream out = Files.newOutputStream(path)) {
            props.store(out, "Game Settings");
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getGridSize() {
        return gridSize;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
