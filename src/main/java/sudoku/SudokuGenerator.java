
package sudoku;

import java.util.Random;
import java.util.HashSet;

public class SudokuGenerator {
	
	static final int SYMMETRY_NONE = 0;
	static final int SYMMETRY_HORZ = 1;
	static final int SYMMETRY_VERT = 2;
	static final int SYMMETRY_DIAGCW = 3; // NW -> SE
	static final int SYMMETRY_DIAGCCW = 4; // NE -> SW
	
	private int gridX;
	private int gridY;
	private int blockX;
	private int blockY;
	
	private int seed;
	private int staticNum; // difficulty essentially, number of ready/clue/given numbers on grid
	private int symmetry; // maybe
	
	private HashSet<Integer> staticIndexes;
	
	private String gridString;
	private String puzzleString;
	
	
	////////////////////////////////////////////////////////////////
	//	constructores
	////////////////////////////////////////////////////////////////
	
	
	public SudokuGenerator(
		int x, int y,
		int blockX, int blockY,
		int seed,
		int staticNum,
		int symmetry
	) {
		this.gridX = x;
		this.gridY = y;
		this.blockX = blockX;
		this.blockY = blockY;
		this.seed = seed;
		this.staticNum = staticNum;
		this.symmetry = symmetry;
		this.gridString = generateGrid();
		this.staticIndexes = generateStaticIndexes();
		this.puzzleString = generatePuzzleGrid(staticIndexes);
	}
	
	
	////////////////////////////////////////////////////////////////
	//	methodes
	////////////////////////////////////////////////////////////////
	
	
	private String generateGrid() {
		/*
			puijataan, siinä gridi jonka generoin ihan ite
				123 456 789
				456 789 123
				789 123 456
				
				231 564 897
				564 897 231
				897 231 564
				
				312 645 978
				645 978 312
				978 312 645
			ja jota generaattori vaan pyörittää
		*/
		String hähähää = "123456789456789123789123456231564897564897231897231564312645978645978312978312645";
		
		return hähähää;
	}
	
	
	private String generatePuzzleGrid( HashSet<Integer> statics ) {
		int max = gridX*gridY;
		StringBuilder out = new StringBuilder();
		
		for (int i = 0; i < max; i++) {
			char c = gridString.charAt(i);
			out.append(statics.contains(i) ? c : '0');
		}
		
		return out.toString();
	}
	
	
	////////////////////////////////////////////////////////////////
	//	statics and symmetry
	////////////////////////////////////////////////////////////////
	
	
	private HashSet<Integer> generateStaticIndexes() {
		HashSet<Integer> idxSet = new HashSet<>(staticNum);
		switch (symmetry) {
			case SYMMETRY_DIAGCCW:
				symmetryFuncBase( idxSet, new SymmetryObjDiagCCW() );
				break;
			case SYMMETRY_DIAGCW:
				symmetryFuncBase( idxSet, new SymmetryObjDiagCW() );
				break;
			case SYMMETRY_VERT:
				symmetryFuncBase( idxSet, new SymmetryObjVert() );
				break;
			case SYMMETRY_HORZ:
				symmetryFuncBase( idxSet, new SymmetryObjHorz() );
				break;
			case SYMMETRY_NONE:
			default:
				symmetryFuncBase( idxSet, null );
				break;
		}
		return idxSet;
	}
	
	
	private void symmetryFuncBase( HashSet<Integer> idxSet, SymmetryObj symmetryObj ) {
		Random rand = new Random(seed);
		int r;
		
		int idx = 0;
		int k = 0;
		int max = gridX*gridY;
		
		while (k < staticNum) {
			r = rand.nextInt(max/2);
			idx = (idx + max + r) % max;
			while (idxSet.contains(idx)) {
				System.out.println( "reroll: " + idx );
				idx = (idx + max + r) % max;
			}
			System.out.printf( "k:%d -> idx:%d\n", k, idx );
			idxSet.add(idx);
			k++;
			if (symmetryObj != null) {
				int midx = symmetryObj.symmetryFunc( idx );
				if (idxSet.add(midx)) { k++; }
			}
		}
	}
	private interface SymmetryObj {
		abstract public int symmetryFunc( int idx );
	}
	private class SymmetryObjHorz implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			int x0 = idx % gridX;
			int x1 = gridX-1 - x0;
			int y = idx / gridX;
			int midx = y*gridX + x1;
			return midx;
		}
	}
	private class SymmetryObjVert implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			int x = idx % gridX;
			int y0 = idx / gridX;
			int y1 = gridY-1 - y0;
			int midx = y1*gridX + x;
			return midx;
		}
	}
	private class SymmetryObjDiagCW implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			int x0 = idx % gridX;
			int y0 = idx / gridX;
			int x1 = y0;
			int y1 = x0;
			int midx = y1*gridX + x1;
			return midx;
		}
	}
	private class SymmetryObjDiagCCW implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			return gridX*gridY - 1 - idx;
		}
	}
	
	
	////////////////////////////////////////////////////////////////
	//	emt
	////////////////////////////////////////////////////////////////
	
	
	public int[][] toArray( String input ) {
		int[][] array = new int[gridY][gridX];
		
		int max = gridX*gridY;
		int x, y;
		for (int i = 0; i < max; i++) {
			char c = (char) (input.charAt(i) - '0');
			x = i % gridX;
			y = i / gridX;
			array[y][x] = c;
		}
		
		return array;
	}
	
	
	////////////////////////////////////////////////////////////////
	//	prants
	////////////////////////////////////////////////////////////////
	
	
	@Override
	public String toString() {
		return puzzleString;
	}
	
	
	public String toStringSolution() {
		return gridString;
	}
	
	
	public String printGrid( boolean solved, Character empty ) {
		StringBuilder sb = new StringBuilder();
		
		int[][] sudokuGrid = toArray(solved ? gridString : puzzleString);
		
		int c;
		for (int y = 0; y < gridY; y++) {
			for (int x = 0; x < gridX; x++) {
				c = sudokuGrid[y][x];
				
				if (c == '0' || c == 0) {
					sb.append( empty );
				} else {
					sb.append( c );
					
				}
				if (x > 0 && (x+1) % blockX == 0 && (x+1) < gridX) {
					sb.append( " | " );
				}
			}
			sb.append( "\n" );
			if (y > 0 && (y+1) % blockX == 0 && (y+1) < gridY) {
				sb.append( "----+-----+----\n" );
			}
		}
		return sb.toString();
	}
	public String printGrid( boolean solved ) {
		return printGrid( solved, '.' );
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	
	public static void main(String[] args) {
		SudokuGenerator perse = new SudokuGenerator(
			9,9,3,3, 1, 25, SYMMETRY_DIAGCCW
		);
		System.out.println( perse.printGrid(true) );
		System.out.println( perse.printGrid(false) );
	}
}
