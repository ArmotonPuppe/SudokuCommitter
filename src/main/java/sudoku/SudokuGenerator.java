
package sudoku;

import java.util.Random;
import java.util.HashSet;

public class SudokuGenerator {
	
	static final int SYMMETRY_NONE = 0;
	static final int SYMMETRY_VERT = 1;
	static final int SYMMETRY_HORZ = 2;
	static final int SYMMETRY_DIAG1 = 3;
	static final int SYMMETRY_POINT = 4;
	
	private int gridX;
	private int gridY;
	private int blockX;
	private int blockY;
	private int max;
	
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
		
		max = gridX * gridY;
		
		this.staticNum =
			(staticNum < 0)
				? 0
				: (staticNum > max)
					? max
					: staticNum
		;
		
		
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
			case SYMMETRY_POINT:
				symmetryFuncBase( idxSet, new SymmetryObjPoint() );
				break;
			case SYMMETRY_DIAG1:
				symmetryFuncBase( idxSet, new SymmetryObjDiag1() );
				break;
			case SYMMETRY_HORZ:
				symmetryFuncBase( idxSet, new SymmetryObjHorz() );
				break;
			case SYMMETRY_VERT:
				symmetryFuncBase( idxSet, new SymmetryObjVert() );
				break;
			case SYMMETRY_NONE:
			default:
				symmetryFuncBase( idxSet, null );
				break;
		}
		return idxSet;
	}
	
	
	// dumpstere but here for posterity
	private void symmetryFuncBase0( HashSet<Integer> idxSet, SymmetryObj symmetryObj ) {
		Random rand = new Random(seed);
		int r;
		
		int idx = 0;
		int k = 0;
		int reroll = 0;
		int rerollmax = 100;
		
		while (k < staticNum) {
			r = rand.nextInt(max/2);
			idx = (idx + max + r) % max;
			while (idxSet.contains(idx) && reroll < rerollmax) {
				System.out.printf( "reroll%d/%d: %d\n", reroll, rerollmax, idx );
				idx = (idx + max + r) % max;
				reroll++;
			}
			reroll = 0;
			System.out.printf( "k:%d -> idx:%d\n", k, idx );
			idxSet.add(idx);
			k++;
			if (symmetryObj != null) {
				int midx = symmetryObj.symmetryFunc( idx );
				if (idxSet.add(midx)) { k++; }
			}
		}
	}
	private void symmetryFuncBase( HashSet<Integer> idxSet, SymmetryObj symmetryObj ) {
		Random rand = new Random(seed);
		int r;
		
		// pull inderxes from an array instead of just rolling them forever potentially
		
		// make an array that is in order
		Integer[] free = new Integer[max];
		for (int i = 0; i < max; i++) { free[i] = i; }
		
		// shuffle array kind of
		for (int i = max - 1; i >= 0; i--) {
			r = rand.nextInt(max);
			if (i != r) {
				Integer temp = free[i];
				free[i] = free[r];
				free[r] = temp;
			}
		}
		
		int k = 0;
		int idx = 0;
		int freeIdx;
		boolean abort = false;
		while (k < staticNum) {
			freeIdx = free[idx++];
			System.out.println( freeIdx );
			while (idxSet.contains(freeIdx)) {
				freeIdx = free[idx++];
				System.out.println( "reroll: " + freeIdx );
				if (idx >= max) { abort = true; break; }
			}
			if (abort) { break; }
			idxSet.add(freeIdx);
			k++;
			if (symmetryObj != null) {
				int midx = symmetryObj.symmetryFunc(freeIdx);
				if (idxSet.add(midx)) { k++; }
			}
		}
	}
	private interface SymmetryObj {
		abstract public int symmetryFunc( int idx );
	}
	/*
		01|23	01|10
		45|67	45|54
		89|ab	89|98
		cd|ef	cd|dc
		
		vertical mirror axis, mirrored by X coordinate
	*/
	private class SymmetryObjVert implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			int x0 = idx % gridX;
			int x1 = gridX-1 - x0;
			int y = idx / gridX;
			int midx = y*gridX + x1;
			return midx;
		}
	}
	/*
		0123	0123
		4567	4567
		----	----
		89ab	4567
		cdef	0123
		
		horizontal mirror axis, mirrored by Y coordinate
	*/
	private class SymmetryObjHorz implements SymmetryObj {
		@Override
		public int symmetryFunc( int idx ) {
			int x = idx % gridX;
			int y0 = idx / gridX;
			int y1 = gridY-1 - y0;
			int midx = y1*gridX + x;
			return midx;
		}
	}
	/*
		\123	\123
		4\67	1\67
		89\b	26\b
		cde\	37b\
		
		mirrored by diagonal axis from NW -> SE, \
	*/
	private class SymmetryObjDiag1 implements SymmetryObj {
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
	/**
		-->3	0123
		4567	4567
		89ab	7654
		c<--	3210
		
		mirorred by index, point symmetry
	**/
	private class SymmetryObjPoint implements SymmetryObj {
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
		
		int x, y;
		for (int i = 0; i < max; i++) {
			char c = (char) (input.charAt(i) - '0');
			x = i % gridX;
			y = i / gridX;
			array[y][x] = c;
		}
		
		return array;
	}
	public String arrayToString( int[][] array ) {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < gridY; y++) {
			for (int x = 0; x < gridX; x++) {
				sb.append( array[y][x] );
			}
		}
		
		return sb.toString();
	}
	
	
	public void reverseGrid() {
		gridString = new StringBuilder(gridString).reverse().toString();
		puzzleString = new StringBuilder(puzzleString).reverse().toString();
	}
	
	
	public int[][] flipArray( int[][] array, boolean horz, boolean vert ) {
		int[][] out = new int[gridY][gridX];
		
		int xf, yf;
		for (int y = 0; y < gridY; y++) {
			for (int x = 0; x < gridX; x++) {
				xf = (horz) ? gridX-1 - x : x;
				yf = (vert) ? gridY-1 - y : y;
				out[y][x] = array[yf][xf];
			}
		}
		
		return out;
	}
	
	
	public String flipGridString( String inp, boolean horz, boolean vert ) {
		int[][] grid = toArray(inp);
		grid = flipArray( grid, horz, vert );
		return arrayToString(grid);
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
			9,9,
			3,3,
			1,
			25,
			SYMMETRY_NONE
		);
		System.out.println( perse.printGrid(true) );
		System.out.println( perse.printGrid(false) );
	}
}
