
package sudoku;

//import java.util.ArrayList;

public class Sudoku {
	
	
	private int gridX;
	private int gridY;
	private int blockX;
	private int blockY;
	
	private String gridString;
	private int[][] sudokuGrid;
	
	private Coord[] staticIndexes;
	//private ArrayList<Coord> staticIndexes = new ArrayList<>();
	
	/**
		overheaad : D
	**/
	public class Coord{
		public int x, y, i;
		public Coord( int x, int y ) {
			this.x = x;
			this.y = y;
			this.i = y*gridX + x;
		}
		public Coord( int i ) {
			this.x = i % gridX;
			this.y = i / gridX;
			this.i = i;
		}
		@Override
		public String toString() {
			return String.format( "[%d,%d: index:%d]", x, y, i );
		}
	}
	/*
	// facilitate undo/redo?
	public class Move{
		public Coord xy;
		public int nold;
		public int nnew;
		public Move( int num, int old, int x, int y ) {
			xy = new Coord( x, y );
			nold = old;
			nnew = num;
		}
		public Move( int num, int old, int i ) {
			xy = new Coord( i );
			nold = old;
			nnew = num;
		}
		@Override
		public String toString() {
			return String.format( "%s: %d -> %d", xy, nold, nnew );
		}
	}
	*/
	
	
	////////////////////////////////////////////////////////////////
	//	constructores
	////////////////////////////////////////////////////////////////
	
	
	public Sudoku( String inp, int gridx, int gridy, int blockx, int blocky ) {
		gridX = gridx;
		gridY = gridy;
		blockX = blockx;
		blockY = blocky;
		staticIndexes = new Coord[gridX*gridY];
		gridString = parseInputString( inp );
		sudokuGrid = parseInput( gridx, gridy );
	}
	
	
	////////////////////////////////////////////////////////////////
	//	methodes
	////////////////////////////////////////////////////////////////
	
	
	public int getGridX() { return gridX; }
	public int getGridY() { return gridY; }
	public int getBlockX() { return blockX; }
	public int getBlockY() { return blockY; }
	
	
	public String getGridString() { return gridString; }
	
	
	private boolean sanityCheck( int x, int y) {
		return
			x >= 0 && x < gridX &&
			y >= 0 && y < gridY
		;
	}
	private boolean sanityCheck( int i ) {
		return i >= 0 && i < gridX*gridY;
	}
	
	
	public int[] getRow( int y ) {
		return sudokuGrid[y];
	}
	
	
	public int[] getCol( int x ) {
		int[] col = new int[gridY];
		for (int y = 0; y < gridY; y++) {
			col[y] = sudokuGrid[y][x];
		}
		return col;
	}
	
	
	public int[][] getBlock2( int x, int y ) {
		int[][] block = new int[blockY][blockX];
		// integer truncatingk :  D
		x /= blockX; x *= blockX;
		y /= blockY; y *= blockY;
		for (int iy = 0; iy < blockX; iy++) {
			for (int ix = 0; ix < blockX; ix++) {
				block[iy][ix] = sudokuGrid[y + iy][x + ix];
			}
		}
		return block;
	}
	public int[] getBlock1( int x, int y ) {
		int[] block = new int[blockY*blockX];
		x /= blockX; x *= blockX;
		y /= blockY; y *= blockY;
		int i = 0;
		for (int iy = 0; iy < blockX; iy++) {
			for (int ix = 0; ix < blockX; ix++) {
				block[i++] = sudokuGrid[y + iy][x + ix];
			}
		}
		return block;
	}
	
	
	public int getCell( int x, int y ) {
		int cell = -1;
		if (sanityCheck( x, y )) {
			cell = sudokuGrid[y][x];
		}
		return cell;
	}
	public int getCell( int i ) {
		int cell = -1;
		if (sanityCheck( i )) {
			cell = sudokuGrid[i / gridX][i % gridY];
		}
		return cell;
	}
	
	
	////////////////////////////////////////////////////////////////
	//	konversionges
	////////////////////////////////////////////////////////////////
	
	
	/**
		ääh blööh spdlfpdlfpsdflp
	**/
	private String parseInputString( String input ) {
		if (input != null) {
			// remove whitespace (or try to at least)
			input = input.replaceAll( "[\s]", "" );
			input = input.replace( "\n", "" ); // jeses
			input = input.replace( "\t", "" ); // kristus
			input = input.replace( " ", "" );
			
			// keep digits 0-9, change anything else to 0
			input = input.replaceAll( "[^0-9]", "0" );
		}
		return input;
	}
	
	
	private int[][] parseInput( String input, int x, int y ) {
		int[][] grid = null;
		
		if (!input.equals(gridString)) {
			input = parseInputString( input );
		}
		
		if (input != null) {
			
			if (x > 0 && y > 0 && input.length() == x*y) {
				int gx = 0;
				int gy = 0;
				grid = new int[y][x];
				for (int i = 0; i < y*x; i++) {
					int c = input.charAt(i) - '0'; // '0':48
					if (c != '0' && c != 0) {
						//staticIndexes.add(new Coord(i));
						staticIndexes[i] = new Coord(i);
					}
					grid[gy][gx] = c;
					gx++;
					if (gx >= x) { gy++; gx = 0; }
				}
			} else {
				System.out.printf( "input was not kosher\n\tlen:%d != %d:x*y\n",
					input.length(), x*y
				);
			}
		}
		return grid;
	}
	private int[][] parseInput( int x, int y ) {
		return parseInput( gridString, x, y );
	}
	
	
	public int coord2index( int x, int y ) {
		return y*gridY + x;
	}
	
	
	////////////////////////////////////////////////////////////////
	//	checks
	////////////////////////////////////////////////////////////////
	
	
	private boolean checkFunc( int[] array, int max ) {
		int mask = 0;
		int bit;
		for (int i = 0; i < max; i++) {
			int c = array[i]; //int c = (int) (array[i] - '0');
			if (c != 0) {
				bit = (1 << (c-1));
				if ((mask & bit) > 0) {
					return false;
				} else {
					mask ^= bit;
				}
			}
		}
		return true;
	}
	
	
	public boolean checkRow( int y ) {
		int[] row = getRow( y );
		return checkFunc( row, gridX );
	}
	public boolean checkCol( int x ) {
		int[] col = getCol( x );
		return checkFunc( col, gridY );
	}
	public boolean checkBlock( int x, int y ) {
		int[] block = getBlock1( x, y );
		return checkFunc( block, gridX );
	}
	
	
	public boolean checkCell( int x, int y ) {
		return
			checkRow( y ) &&
			checkCol( x ) &&
			checkBlock( x, y )
		;
	}
	
	
	/**
		check if given cell is static
	**/
	public boolean checkStatic( int x, int y ) {
		return staticIndexes[coord2index(x, y)] != null;
	}
	public boolean checkStatic( int i ) {
		return staticIndexes[i] != null;
	}
	
	
	public boolean checkGrid() {
		int x, y;
		for (int i = 0; i < gridX*gridY; i++) {
			if (!checkStatic(i)) {
				y = i / gridX;
				x = i % gridX;
				if (!checkCell(x, y)) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	////////////////////////////////////////////////////////////////
	//	inserts, deletes -> set cell
	////////////////////////////////////////////////////////////////
	
	
	/**
		set the value of a given cell if its not static and
		the coords pass a sanity check
		<br>
		setting the value to 0 effectively deletes a value
		@return true if operation succeeded, false otherwise
	**/
	public boolean setCell( int num, int x, int y ) {
		if (!sanityCheck( x, y ) || checkStatic( x, y )) {
			return false;
		} else {
			sudokuGrid[y][x] = num;
		}
		return true;
	}
	/**
		set the value of a given cell if its not static
		and the index passes a sanity check
		<br>
		setting the value to 0 effectively deletes a value
		@return true if operation succeeded, false otherwise
	**/
	public boolean setCell( int num, int i ) {
		if (!sanityCheck( i ) || checkStatic( i )) {
			return false;
		} else {
			sudokuGrid[i / gridX][i % gridX] = num;
		}
		return true;
	}
	
	
	////////////////////////////////////////////////////////////////
	//	prants
	////////////////////////////////////////////////////////////////
	
	
	@Override
	public String toString() {
		String out = String.format( "grid %dx%d, numbers %d, blocksize %dx%d, %s",
			gridX, gridY, gridX*gridY, blockX, blockY, getGridString()
		);
		return out;
	}
	
	
	public String printGrid( Character empty ) {
		StringBuilder sb = new StringBuilder();
		
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
	public String printGrid() {
		return printGrid( '.' );
	}
	
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	
	public static void main(String[] args) {
		//String butt = "745896132928431756361725849519683427274159368836247591652378914483912675197564283";
		String butt = """
			1.. .56 ...
			... 7.9 .2.
			..9 1.. 4..
			
			.3. ... .9.
			..4 .9. 2..
			.9. ... .6.
			
			..2 ..5 9..
			.4. 9.8 ...
			... 31. ..5
		""";
		Sudoku ass = new Sudoku( butt, 9, 9, 3, 3 );
		Coord xy;
		ass.checkGrid();
		for (int i = 0; i < 81; i++) {
			xy = ass.new Coord(i);
			System.out.printf( "%s: %d %s\n",
				xy,
				ass.getCell(i),
				(ass.checkStatic(i) ? "(static)" : "")
			);
			ass.setCell( 0, i );
		}
		System.out.println( ass.printGrid() );
	}
	
}
