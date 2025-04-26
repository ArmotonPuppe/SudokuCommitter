package sudoku.game;

import java.io.*;

public class PuzzleFactory {
    public Puzzle createPuzzle(Difficulty difficulty, int size)throws FileNotFoundException {
        String path = "/" + difficulty.getLabel() + ".csv";
        Puzzle puzzle = null;
        InputStream is = getClass().getResourceAsStream(path);

        if(is == null){
            ClassLoader cl = getClass().getClassLoader();
            System.out.println("Classloader: " + cl);
            System.out.println("Classloader root resource: " + cl.getResource(""));
            throw new FileNotFoundException("Could not find Puzzle at " + path);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line = br.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String givens = parts[1];
            String solution = parts[2];
            if(!validatePuzzle(givens, solution)) {
                throw new IllegalStateException("Solution doesn't match the puzzle in file at: " + parts[0]);
            }else{
                System.out.println(System.getProperty("java.class.path"));
                puzzle = new Puzzle(id, size, difficulty, solution, givens);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return puzzle;
    }
    public boolean validatePuzzle(String givens, String solution) {
        for(int i = 0; i < solution.length(); i++){
            if(givens.charAt(i)!='.' && givens.charAt(i)!=solution.charAt(i)){
                return false;
            }
        }
        return true;
    }


}
