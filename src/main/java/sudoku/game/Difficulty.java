package sudoku.game;

public enum Difficulty {
    EASY(0, "easy"),
    MEDIUM(1, "medium"),
    HARD(2, "hard");

    private final int level;
    private final String label;

    Difficulty(int level, String label) {
        this.level = level;
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public String getLabel() {
        return label;
    }

    public static Difficulty fromString(String s) {
        return switch (s.trim().toLowerCase()) {
            case "easy" -> EASY;
            case "medium" -> MEDIUM;
            case "hard" -> HARD;
            default -> throw new IllegalArgumentException("Unknown difficulty: " + s);
        };
    }

    public static Difficulty fromLevel(int level) {
        return switch (level) {
            case 0 -> EASY;
            case 1 -> MEDIUM;
            case 2 -> HARD;
            default -> throw new IllegalArgumentException("Unknown level: " + level);
        };
    }
}

