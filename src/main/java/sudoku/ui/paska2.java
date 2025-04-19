package sudoku.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class paska2 {
	
	static int ass = 0;
	static final String DEFAULTICONPATH = "icons/default.png";
	
	public static boolean showPalette = true;
	public static boolean showTimer = true;
	
	////////////////////////////////////////////////////////////////
	//	ass butte timer shitte?
	////////////////////////////////////////////////////////////////
	
	static String statTimerText = "00:00:00";
	static boolean statTimerRun = false;
	static ActionListener statTimerUpdate;
	static javax.swing.Timer statTimer = new javax.swing.Timer(
		400,
		statTimerUpdate
	);
	
	////////////////////////////////////////////////////////////////
	//	soemthing
	////////////////////////////////////////////////////////////////
	
	private static ArrayList<Pair<String,Icon>> toolbarButtons = new ArrayList<>();
	
	private static void registerToolbarButton( String name, String path ) {
		Icon icon = null;
		try {
			icon = new ImageIcon( path );
			if (icon.getIconHeight() <= 0 || icon.getIconWidth() <= 0) { throw new FileNotFoundException(); }
			System.out.printf( "registered button: %s: [%dx%d] \"%s\"\n",
				name, icon.getIconWidth(), icon.getIconHeight(), path
			);
		} catch (Exception e) {
			System.out.println( "failed to create icon with path: \""+path+"\"\n\t" + e );
			icon = new ImageIcon( DEFAULTICONPATH );
		}
		toolbarButtons.add(new Pair<>(name, icon));
	}
	private static void registerToolbarSeparator() {
		toolbarButtons.add(new Pair<>("separator",null));
	}
	
	
	////////////////////////////////////////////////////////////////
	//	event handlers
	////////////////////////////////////////////////////////////////
	
	public static class EH_menuGamePref implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			System.out.println( "SETTINGES BUTTON HASE BEENE PRESSEDE "+(++ass)+" TIMESE" );
		}
	}
	public static class EH_menuGameExit implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			System.out.println( "menuGameExit" );
			System.exit(0);
		}
	}
	public static class EH_menuViewPalette implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showPalette = !showPalette;
			System.out.printf( "showPalette " );
			if (showPalette)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	public static class EH_menuViewTimer implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			showTimer = !showTimer;
			System.out.printf( "showTimer " );
			if (showTimer)
				{ System.out.println( "on" ); }
			else{ System.out.println( "off" ); }
		}
	}
	
	////////////////////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		Runnable ass = new Runnable() {
			@Override
			public void run() {
				
				////////////////////////////////////////////////////////////////
				//	ass butt
				////////////////////////////////////////////////////////////////
				
				registerToolbarButton( "New Game", "icons/newgame.png" );
				registerToolbarButton( "Restart", "icons/restart.png" );
				registerToolbarSeparator();
				registerToolbarButton( "Undo", "icons/undo.png" );
				registerToolbarButton( "Redo", "icons/redo.png" );
				registerToolbarButton( "Hint", "icons/hint.png" );
				registerToolbarButton( "Check", "icons/check.png" );
				registerToolbarSeparator();
				registerToolbarButton( "Settings", "icons/settings.png" );
				registerToolbarButton( "Help", "icons/help.png" );
				
				////////////////////////////////////////////////////////////////
				//	frame
				////////////////////////////////////////////////////////////////
				
				JFrame frame = new JFrame( "ass title" );
					frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					frame.setSize( 480, 320 );
				Container frameCont = frame.getContentPane();
				
				////////////////////////////////////////////////////////////////
				//	statusbar
				////////////////////////////////////////////////////////////////
				
				JPanel statusBar = new JPanel();
					statusBar.add(new JLabel( "ass status bar" ));
					statusBar.setBackground(new Color( 0xCCCCCC ));
					statusBar.setBorder(new BevelBorder(
						BevelBorder.LOWERED,
						new Color( 0x999999 ),
						new Color( 0x333333 )
					));
				frameCont.add( statusBar, BorderLayout.PAGE_END );
				
				////////////////////////////////////////////////////////////////
				//	menubar
				////////////////////////////////////////////////////////////////
				
				////////////////////////////////
				//	G A M E
				////////////////////////////////
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
				
				////////////////////////////////
				//	V I E W
				////////////////////////////////
				JMenu menuView = new JMenu( "View" );
					menuView.setMnemonic('V');
					JMenuItem menuViewButt = new JMenuItem( "Butt" );
						menuViewButt.setMnemonic('B');
					menuView.add( menuViewButt );
					JCheckBoxMenuItem menuViewPalette = new JCheckBoxMenuItem( "Show palette" );
						menuViewPalette.setState(showPalette);
						menuViewPalette.setMnemonic('p');
						menuViewPalette.addActionListener(new EH_menuViewPalette());
					menuView.add( menuViewPalette );
					JCheckBoxMenuItem menuViewTimer = new JCheckBoxMenuItem( "Show timer" );
						menuViewTimer.setState(showTimer);
						menuViewTimer.setMnemonic('t');
						menuViewTimer.addActionListener(new EH_menuViewTimer());
					menuView.add( menuViewTimer );
				
				////////////////////////////////
				//	H E L P
				////////////////////////////////
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
				JMenuBar menuBar = new JMenuBar();
					menuBar.add( menuGame );
					menuBar.add( menuView );
					menuBar.add( menuHelp );
				frame.setJMenuBar( menuBar );
				
				////////////////////////////////////////////////////////////////
				//	toolbar?
				////////////////////////////////////////////////////////////////
				
				JPanel toolbar = new JPanel(
					new FlowLayout( FlowLayout.LEADING, 16, 8 )
				);
					toolbar.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
					toolbar.setBackground(new Color( 0xcccccc ));
					toolbar.setSize( frame.getWidth(), 128 );
					for (int i = 0; i < toolbarButtons.size(); i++) {
						try {
							Pair<String,Icon> btnPair = toolbarButtons.get(i);
							if (btnPair.getVal() != null) {
								JButton btn = new JButton( btnPair.getKey(), btnPair.getVal() );
									//btn.setPreferredSize(new Dimension(128, 80));
								toolbar.add( btn );
							} else {
								// vitu häkki
								JPanel sep = new JPanel();
									sep.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
									sep.setPreferredSize(new Dimension(2,48));
								toolbar.add( sep );
							}
						} catch (Exception e) {
							System.out.println( "adding button failed:\n\t" + e );
						}
					}
				frame.add( toolbar, BorderLayout.PAGE_START );
				
				////////////////////////////////////////////////////////////////
				//	palette?
				////////////////////////////////////////////////////////////////
				
				JPanel palette = new JPanel();
				{
					palette.setBackground(new Color( 0x008080 ));
					GridLayout gridl = new GridLayout(9, 0, 0, 0);
					palette.setLayout( gridl );
					for (int i = 1; i <= 9; i++) {
						JButton palbtn = new JButton( i + "" );
							palbtn.setFont(new Font("Liberation Mono", Font.BOLD, 48));
						palette.add( palbtn );
					}
				}
				frame.add( palette, BorderLayout.LINE_START );
				
				////////////////////////////////////////////////////////////////
				//	grid?
				////////////////////////////////////////////////////////////////
				
				JPanel gridBase = new JPanel();
				{
					gridBase.setBackground(new Color( 0x990099 ));
					gridBase.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
					GridLayout gl0 = new GridLayout( 3, 3, 4, 4 );
					gridBase.setLayout( gl0 );
					for (int u = 0; u < 9; u++) {
						JPanel gridBlock = new JPanel();
							gridBlock.setBackground(new Color( 0x009900 ));
							gridBlock.setBorder(new EtchedBorder(EtchedBorder.RAISED));
							GridLayout gl1 = new GridLayout( 3, 3, 0, 0 );
							gridBlock.setLayout( gl1 );
							for (int i = 0; i < 9; i++) {
								JButton cellbtn = new JButton(""+(i+1 + u*9));
									cellbtn.setBackground(new Color( 0xffffff ));
									cellbtn.setSize(48,48);
								gridBlock.add( cellbtn );
							}
						gridBase.add( gridBlock );
					}
				}
				frame.add( gridBase, BorderLayout.CENTER );
				
				////////////////////////////////////////////////////////////////
				//	timer ? and some other shit?
				////////////////////////////////////////////////////////////////
				
				JPanel stats = new JPanel();
				{
					stats.setBackground(new Color( 0x00ff00 ));
					JLabel text = new JLabel();
						text.setFont(new Font( "Liberation Mono", Font.BOLD, 32 ));
						text.setText( statTimerText );
					stats.add( text );
				}
				frame.add( stats, BorderLayout.LINE_END );
				
				////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////
				
				frame.pack(); // ass
				frame.setVisible( true );
			}
		};
		SwingUtilities.invokeLater( ass );
	}
}
