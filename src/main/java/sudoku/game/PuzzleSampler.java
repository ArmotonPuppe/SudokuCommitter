package sudoku.game;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PuzzleSampler {

    private static final int SAMPLE_SIZE = 100;
    private static final String SOURCE_PATH = "src/main/resources/sudoku-3m.csv";
    private static final String EASY_PATH = "src/main/resources/easy.csv";
    private static final String MEDIUM_PATH = "src/main/resources/medium.csv";
    private static final String HARD_PATH = "src/main/resources/hard.csv";

    public static void main(String[] args) throws IOException {
        List<String> easy = new ArrayList<>();
        List<String> medium = new ArrayList<>();
        List<String> hard = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(SOURCE_PATH))) {
            String line;
            Random rand = new Random();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                try {
                    double difficulty = Double.parseDouble(parts[4]);

                    // Reservoir sampling for each difficulty level
                    if (difficulty < 3.0) addToSample(easy, line, rand, SAMPLE_SIZE);
                    else if (difficulty < 6.0) addToSample(medium, line, rand, SAMPLE_SIZE);
                    else addToSample(hard, line, rand, SAMPLE_SIZE);

                } catch (NumberFormatException ignored) {}
            }
        }

        Files.write(Paths.get(EASY_PATH), easy);
        Files.write(Paths.get(MEDIUM_PATH), medium);
        Files.write(Paths.get(HARD_PATH), hard);

        System.out.println("Puzzles written to easy.csv, medium.csv, and hard.csv");
    }

    // Helper for reservoir sampling
    private static void addToSample(List<String> sample, String line, Random rand, int maxSize) {
        if (sample.size() < maxSize) {
            sample.add(line);
        } else {
            int index = rand.nextInt(sample.size() + 1);
            if (index < maxSize) {
                sample.set(index, line);
            }
        }
    }
}
