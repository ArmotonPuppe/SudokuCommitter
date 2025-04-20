package sudoku.game;
import sudoku.board.Grid;

public class Puzzle {
    private Grid grid;
    private final int id;
    private final Difficulty difficulty;
    private final String solution;
    private final String givens;
    public boolean solved = false;

    public Puzzle(int id, int size, Difficulty difficulty, String solution, String givens) {
        this.id = id;
        grid = new Grid(size, givens);
        this.difficulty = difficulty;
        this.solution = solution;
        this.givens = givens;
    }

    public boolean isSolved(){
        if(grid.isFilled() && checkSolution()){
            solved = true;
            return true;
        }
        return false;
    }

    public Grid getGrid() {
        return grid;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getSolution() {
        return solution;
    }
    public String getGivens() {
        return givens;
    }

    public int getId() {
        return id;
    }

    public boolean checkSolution(){
        return solution.equals(grid.toString());
    }

}
