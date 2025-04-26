package sudoku.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import sudoku.ui.menu.*;
import sudoku.game.Game;

public class paska3 {
	
	// base window frame?
	static JFrame frame;
		static final String WINDOWTITLE = "ass window title";
	
	// stuff we probably won't hide
	static JMenuBar menuBar;
	static GridBase gridBase; // main game field
	
	// TODO paska
	static int blockx = 3;
	static int blocky = 3;
	static ArrayList<JPanel> gridBlocks; 
	
	// stuff that could be hidden by the user
	static JPanel statusBar;
	static JPanel toolBar; // button shortcuts for shit
	static JPanel palette;
	static JPanel stats; // timer? and other shit?
	
	static boolean showStatusBar = true;
	static boolean showToolBar = true;
	static boolean showPalette = true;
	static boolean showStats = true;
    static Game game = new Game();
	
	////////////////////////////////////////////////////////////////
	//	toolbar buttons?
	////////////////////////////////////////////////////////////////
	
	static final String DEFAULTICONPATH = "icons/default.png";
	
	// add pairs of toolbar button names and (Image)Icon objects
	// pull them from the (ordered) list in a for loop and display?s
	private static ArrayList<Pair<String,Icon>> toolBarButtons = new ArrayList<>();
	
	private static void registerToolBarButton( String name, String path ) {
		Icon icon = null;
		try {
			icon = new ImageIcon( path );
			if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
				throw new Exception( "image width or height 0 or less" );
			}
			System.out.printf( "registered toolbar button: %s [%d,%d] \"%s\"\n",
				name, icon.getIconWidth(), icon.getIconHeight(), path
			);
		} catch (Exception e) {
			System.out.println( "failed to create icon from path: " + path + "\n\t" + e );
			icon = new ImageIcon( DEFAULTICONPATH );
		}
		toolBarButtons.add(new Pair<>( name, icon ));
	}
	private static void registerToolBarSeparator() {
		toolBarButtons.add(new Pair<>( "separator", null ));
	}
	
	////////////////////////////////////////////////////////////////
	//	evemt hanleres ?!
	////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////
	//	menu item actions
	static int butte = 0;
	public static class EH_menuGamePref implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			System.out.println( "SETTINGES BUTTON HASE BEENE PRESSEDE "+(++butte)+" TIMESE" );
		}
	}
	public static class EH_menuGameExit implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			System.out.println( "menuGameExit" );
			System.exit(0);
		}
	}
	////////////////////////////////////////////////////////////////
	//	view -> show various things
	public static class EH_menuViewStatusBar implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showStatusBar = !showStatusBar;
			statusBar.setVisible( showStatusBar );
			System.out.printf( "showStatusBar " );
			if (showStatusBar)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	public static class EH_menuViewToolBar implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showToolBar = !showToolBar;
			toolBar.setVisible( showToolBar );
			System.out.printf( "showToolBar " );
			if (showToolBar)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	public static class EH_menuViewPalette implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showPalette = !showPalette;
			palette.setVisible( showPalette );
			System.out.printf( "showPalette " );
			if (showPalette)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	public static class EH_menuViewStats implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showStats = !showStats;
			stats.setVisible( showStats );
			System.out.printf( "showStats " );
			if (showStats)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {

		Runnable ass = new Runnable() {
			@Override
			public void run() {
				
				////////////////////////////////////////////////////////////////
				//	frame
				////////////////////////////////////////////////////////////////
				
				frame = new JFrame( WINDOWTITLE );
					frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					frame.setSize( 480, 320 );
				Container frameCont = frame.getContentPane();
				
				////////////////////////////////////////////////////////////////
				//	menubar
				////////////////////////////////////////////////////////////////

				menuBar = new JMenuBar();
					menuBar.add( new MenuGame(frame) );
					menuBar.add( new MenuView() );
					menuBar.add( new MenuHelp() );
				frame.setJMenuBar( menuBar );
				
				////////////////////////////////////////////////////////////////
				//	statusbar
				////////////////////////////////////////////////////////////////
				
				statusBar = new JPanel();
					statusBar.add(new JLabel( "ass status bar" ));
					statusBar.setBackground(new Color( 0xCCCCCC ));
					statusBar.setBorder(new BevelBorder(
						BevelBorder.LOWERED,
						new Color( 0x999999 ),
						new Color( 0x333333 )
					));
				frameCont.add( statusBar, BorderLayout.PAGE_END );
				
				////////////////////////////////////////////////////////////////
				//	toolbar
				////////////////////////////////////////////////////////////////
				
				registerToolBarButton( "New Game", "icons/newgame.png" );
				registerToolBarButton( "Restart", "icons/restart.png" );
				registerToolBarSeparator();
				registerToolBarButton( "Undo", "icons/undo.png" );
				registerToolBarButton( "Redo", "icons/redo.png" );
				registerToolBarButton( "Hint", "icons/hint.png" );
				registerToolBarButton( "Check", "icons/check.png" );
				registerToolBarSeparator();
				registerToolBarButton( "Settings", "icons/settings.png" );
				registerToolBarButton( "Help", "icons/help.png" );
				
				toolBar = new JPanel();
				{
					toolBar.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
					toolBar.setBackground(new Color( 0xcccccc ));
					toolBar.setSize( frame.getWidth(), 128 );
					for (int i = 0; i < toolBarButtons.size(); i++) {
						Pair<String,Icon> btnpair = toolBarButtons.get(i);
						try {
							if (btnpair.getVal() != null) {
								JButton btn = new JButton( btnpair.getKey(), btnpair.getVal() );
								toolBar.add( btn );
							} else {
								// vitu häkki
								JPanel sep = new JPanel();
									sep.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
									sep.setPreferredSize(new Dimension(2, 48));
								toolBar.add( sep );
							}
						} catch (Exception e) {
							System.out.printf( "failed to add button to toolbar: %s: %s\n\t%s",
								btnpair.getKey(), btnpair.getVal(), e
							);
						}
					}
				}
				frame.add( toolBar, BorderLayout.PAGE_START );
				
				////////////////////////////////////////////////////////////////
				//	palette
				////////////////////////////////////////////////////////////////
				
				palette = new JPanel();
				{
				}
				frameCont.add( palette, BorderLayout.LINE_START );
				
				////////////////////////////////////////////////////////////////
				//	stats
				////////////////////////////////////////////////////////////////
				
				stats = new JPanel();
				{
				}
				frameCont.add( stats, BorderLayout.LINE_END );
				
				////////////////////////////////////////////////////////////////
				//	grid
				////////////////////////////////////////////////////////////////
				//en tiedä miksi sulla oli tää gridBlocks muuttuja täällä kun et tuossa loopissa laittanut
				//siihen mitään, mutta otin sen kuitenki talteen se on kans tuolla gridbase luokassa
				/// ////////////////////////////////////////////////////////////
				gridBase = new GridBase(game.getPuzzle().getGrid());
				gridBlocks = gridBase.getGridBlocks();

				frameCont.add( gridBase, BorderLayout.CENTER );
				
				////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////
				
				frame.pack();
				frame.setVisible( true );
			}
		};
		SwingUtilities.invokeLater(ass);
	}
}
