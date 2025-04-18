
import com.sun.jdi.event.StepEvent;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class paska3 {
	
	// base window frame?
	static JFrame frame;
		static final String WINDOWTITLE = "ass window title";
	
	// stuff we probably wont hide
	static JMenuBar menuBar;
	static JPanel gridBase; // main game field
	
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
				
				// GAME
				JMenu menuGame = new JMenu( "Game" );
					menuGame.setMnemonic('G');
					
					JMenuItem menuGameNew = new JMenuItem( "New Game" );
						menuGameNew.setMnemonic('N');
					menuGame.add( menuGameNew );
					
					JMenuItem menuGameRestart = new JMenuItem( "Restart Game" );
						menuGameRestart.setMnemonic('R');
					menuGame.add( menuGameRestart );
					
					menuGame.addSeparator();
					JMenuItem menuGamePref = new JMenuItem( "Preferences" );
						menuGamePref.setMnemonic('P');
						menuGamePref.addActionListener(new EH_menuGamePref());
					menuGame.add( menuGamePref );
					
					menuGame.addSeparator();
					JMenuItem menuGameExit = new JMenuItem( "Exit" );
						menuGameExit.setMnemonic('x');
						menuGameExit.addActionListener(new EH_menuGameExit());
					menuGame.add( menuGameExit );
				
				// VIEW
				JMenu menuView = new JMenu( "View" );
					menuView.setMnemonic('V');
					JCheckBoxMenuItem menuViewPalette = new JCheckBoxMenuItem( "    palette" );
						menuViewPalette.setState(showPalette);
						menuViewPalette.setMnemonic('p');
						menuViewPalette.addActionListener(new EH_menuViewPalette());
					menuView.add( menuViewPalette );
					
					JCheckBoxMenuItem menuViewStats = new JCheckBoxMenuItem( "    stats" );
						menuViewStats.setState(showStats);
						menuViewStats.setMnemonic('s');
						menuViewStats.addActionListener(new EH_menuViewStats());
					menuView.add( menuViewStats );
					
					JCheckBoxMenuItem menuViewStatusBar = new JCheckBoxMenuItem( "    statusbar" );
						menuViewStatusBar.setState(showStatusBar);
						menuViewStatusBar.setMnemonic('t');
						menuViewStatusBar.addActionListener(new EH_menuViewStatusBar());
					menuView.add( menuViewStatusBar );
					
					JCheckBoxMenuItem menuViewToolBar = new JCheckBoxMenuItem( "    toolbar" );
						menuViewToolBar.setState(showToolBar);
						menuViewToolBar.setMnemonic('o');
						menuViewToolBar.addActionListener(new EH_menuViewToolBar());
					menuView.add( menuViewToolBar );
				
				// HELP
				JMenu menuHelp = new JMenu( "Help" );
					menuHelp.setMnemonic('H');
					
					JMenuItem menuHelpHelp = new JMenuItem( "Help" );
						menuHelpHelp.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)
						);
					menuHelp.add( menuHelpHelp );
					
					menuHelp.addSeparator();
					JMenuItem menuHelpAbout = new JMenuItem( "About" );
					menuHelp.add( menuHelpAbout );
				
				////////////////////////////////
				menuBar = new JMenuBar();
					menuBar.add( menuGame );
					menuBar.add( menuView );
					menuBar.add( menuHelp );
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
				
				gridBase = new JPanel();
				{
					/* TODO ruudut järjestykseen
					
						nyt ne on
						00 01 02 | 09 10 11 | 18 19 20
						03 04 05 | 12 13 14 | 21 22 23
						06 07 08 | 15 16 17 | 24 25 26
						...
						
						ja ne pitäs olla
						00 01 02 | 03 04 05 | 06 07 08
						09 10 11 | 12 13 14 | 15 16 17
						18 19 20 | 21 22 23 | 24 25 26
						...
						
						pitää tehä gridit ja layoutit eka
						sitten lisätä ruutu(napit) sinne tänne
						vaikea generalisoida? emt
						jos tietää block dimensiot niin
						voi hypätä seuraavaan
						
						block0		block1		block2
						x = 0-2		x = 3-5		x = 6-8
						y = 0-2		y = 0-2		y = 0-2
						...
						block6		block7		block8
						x = 0-2		x = 3-5		x = 6-8
						y = 6-8		y = 6-8		y = 6-8
					*/
					blockx = 4;
					blocky = 4;
					int xy = blockx * blocky;
					gridBlocks = new ArrayList<>( xy );
					gridBase.setBackground(new Color( 0x990099 ));
					gridBase.setBorder(new EtchedBorder( EtchedBorder.LOWERED ));
					gridBase.setLayout(new GridLayout( blockx, blocky, 4, 4 ));
					JPanel gridBlock;
					
					for (int u = 0; u < xy; u++) {
						gridBlock = new JPanel();
							gridBlock.setBackground(new Color(  ));
							gridBlock.setBorder(new EtchedBorder( EtchedBorder.RAISED ));
							gridBlock.setLayout(new GridLayout( blocky, blockx, 0, 0 ));
							/*
							for (int i = 0; i < xy; i++) {
								JButton cellbtn = new JButton( "" + (i+1 + u*xy) );
									cellbtn.setBackground(new Color( 0xffffff ));
									cellbtn.setPreferredSize(new Dimension(32, 32));
								gridBlock.add( cellbtn );
							}
							*/
						gridBase.add( gridBlock );
					}
				}
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
