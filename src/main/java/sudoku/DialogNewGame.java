package sudoku;

import java.util.Random;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class DialogNewGame {
	
	// typerä häkki : D en jaksa tai keksi miten pädätä paskaa niin ettei ne vekslaa eestaas
	// nyt ilman ikonia se skaalaa paskat fillin takia minne sattuu
	// tyhjällä ikonilla ei mutta semmone laiska ratkaisu
	private final ImageIcon NOICON = new ImageIcon( "icons/tinynothing.png" ); 
	private final ImageIcon ICON_TINYWARN = new ImageIcon( "icons/tinywarning.png" );
	private final ImageIcon ICON_TINYERR = new ImageIcon( "icons/tinyerror.png" );
	
	private final JDialog DIALOG;
	private final JFrame FRAME;
	private final String TITLE;
	
	private String[] COMBOBOX_CHOICES;
	private int COMBOBOX_INDEX;
	
	private int DIFFMIN;
	private int DIFFMAX;
	private int DIFFDEF;
	
	private boolean gameRunning;
	private boolean seedRandom = true;
	private int errorflags = 0;
	
	// RETURN VALUES ?!
	private boolean ok = false;
	private Integer gidx;
	private Integer diff;
	private Integer seed;
	
	// grid choice
	protected JComboBox<String> gridComboBox;
	// diff slider
	protected JLabel diffLabel;
	protected JLabel diffIcon;
	protected JSlider diffSlider;
	protected JTextField diffTextField;
	// seed
	protected JLabel seedLabel;
	protected JLabel seedIcon;
	protected JButton seedButton;
	protected JTextField seedTextField;
	protected JCheckBox seedCheckBox;
	// ok & kankel
	protected JButton dialogOK;
	protected JButton dialogCANCEL;
	
	////////////////////////////////////////////////////////////////
	//	constructores
	////////////////////////////////////////////////////////////////
	
	
	/**
		@frame window/parent frame pointer
		@gridChoices combo box choices
		@gridChoicesIndex default selection for combo box
		@diffRange indexes: 0=minimum, 1=maximum and 2=default
	**/
	public DialogNewGame(
		JFrame frame,
		String title,
		String[] gridChoices,
		int[] diffRange,
		Integer gridIndex,
		Integer diff,
		Integer seed,
		boolean gameRunning
	) {
		FRAME = frame;
		TITLE = title;
		
		COMBOBOX_CHOICES = gridChoices;
		COMBOBOX_INDEX = (gridIndex == null)
			? 2
			: gridIndex
		;
		this.gidx = COMBOBOX_INDEX;
		
		DIFFMIN = diffRange[0];
		DIFFMAX = diffRange[1];
		DIFFDEF = diffRange[2];
		this.diff = (diff == null)
			? DIFFDEF
			: inputClamp( diff, DIFFMIN, DIFFMAX ) // ei oo null saatana
		;
		
		seedRandom = (seed == null);
		if (seedRandom) {
			randomSeed();
		} else {
			this.seed = seed;
		}
		
		this.gameRunning = gameRunning;
		DIALOG = build();
	}
	
	
	////////////////////////////////////////////////////////////////
	//	methodes
	////////////////////////////////////////////////////////////////
	
	
	private void setErrorIcon( JLabel iconElement, int errorbit, boolean set ) {
		if (iconElement != null) {
			iconElement.setIcon( set ? ICON_TINYERR : NOICON );
		}
		if (set) {
			errorflags |= errorbit;
		} else {
			errorflags &= ~errorbit;
		}
	}
	private void setErrorOK( JButton okElement ) {
		if (okElement != null) {
			okElement.setEnabled( errorflags == 0 );
		}
	}
	
	
	private Integer textFieldSanityCheck( String inp ) {
		Integer val;
		try {
			val = Integer.valueOf( inp );
		} catch (NumberFormatException e) {
			//System.out.printf( "sanitycheck: failed to parse String:\"%s\"\n", inp );
			val = null;
		}
		return val;
	}
	private Integer inputClamp( Integer in, int min, int max ) {
		return (in > max ? max : (in < min ? min : in));
	}
	
	////////////////////////////////////////////////////////////////
	
	/*
	private void toggleSeedElements( boolean set ) {
		seedCheckBox.setSelected( set );
		System.out.println( "isSelected:"+seedCheckBox.isSelected() );
		seedTextField.setEditable( !seedCheckBox.isSelected() );
		seedTextField.setEditable( true );
		System.out.println( "seedRandom:"+seedRandom );
		if (seedRandom) {
			randomSeed();
			writeSeed();
			
			seedTextField.setBackground(new Color( 0xcccccc ));
			seedTextField.setForeground(new Color( 0x666666 ));
		} else {
			seedTextField.setBackground(new Color( 0xffffff ));
			seedTextField.setForeground(new Color( 0x000000 ));
		}
	}
	*/
	private void randomSeed() {
		Random rand = new Random(System.currentTimeMillis());
		seed = rand.nextInt(Integer.MAX_VALUE);
	}
	private void writeSeed() {
		seedTextField.setText( seed.toString() );
	}
	
	////////////////////////////////////////////////////////////////
	
	
	public void setVisible( boolean yes ) {
		DIALOG.setVisible(yes);
	}
	private JDialog build() {
		JDialog dialog = new JDialog( FRAME, TITLE, JDialog.ModalityType.APPLICATION_MODAL );
			dialog.setLayout(new BorderLayout( 0, 16 ));
			JLabel description = new JLabel(
				"start a new game with the given parameters",
				JLabel.CENTER
			);
			dialog.add( description, BorderLayout.PAGE_START );
			
			JPanel dialogPanel = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
					c.anchor = GridBagConstraints.LINE_START;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.insets = new Insets( 8, 8, 8, 8 );
					c.gridwidth = 1;
				
				////////////////////////////////////////////////////////////////
				//	GRID SIZE SELECTOR, COMBO BOX
				////////////////////////////////////////////////////////////////
				c.gridy = 0;
				c.gridx = 0;
				c.weightx = 4;
				JLabel gridLabel = new JLabel(
					"grid size",
					JLabel.TRAILING
				);
				dialogPanel.add( gridLabel, c );
				
				c.gridx = 3;
				c.ipadx = 40;
				c.weightx = 8;
				gridComboBox = new JComboBox<>(COMBOBOX_CHOICES);
					gridComboBox.addActionListener(
						(ActionEvent e) -> { gidx = gridComboBox.getSelectedIndex(); }
					);
					gridComboBox.setSelectedIndex( COMBOBOX_INDEX );
					gridComboBox.setFont(SudokuFonts.MEDIUM);
				dialogPanel.add( gridComboBox, c );
				
				////////////////////////////////////////////////////////////////
				//	DIFFICULTY SLIDER AND TEXT FIELD
				////////////////////////////////////////////////////////////////
				c.gridy++;
				c.ipadx = 0;
				c.gridx = 0;
				c.weightx = 4;
				diffLabel = new JLabel(
					"amount of starting numbers on grid",
					JLabel.TRAILING
				);
				dialogPanel.add( diffLabel, c );
				
				//*
				c.gridx = 1;
				c.ipadx = 10;
				c.weightx = 1;
				diffIcon = new JLabel( NOICON );
				dialogPanel.add( diffIcon, c );
				//*/
				
				c.gridx = 2;
				c.weightx = 2;
				diffTextField = new JTextField( 4 );
					diffTextField.addActionListener(new DiffTextFieldListener());
					diffTextField.getDocument().addDocumentListener(new DiffTextFieldChangeListener());
					diffTextField.setText( "" + diff );
					diffTextField.setHorizontalAlignment(JTextField.TRAILING);
					diffTextField.setFont(SudokuFonts.MEDIUM);
				dialogPanel.add( diffTextField, c );
				
				c.gridx = 3;
				c.ipadx = 40;
				c.weightx = 8;
				diffSlider = new JSlider(JSlider.HORIZONTAL, DIFFMIN, DIFFMAX, diff);
					diffSlider.addChangeListener(new DiffSliderListener());
					diffSlider.setMajorTickSpacing( 10 );
					diffSlider.setMinorTickSpacing( 2 );
					diffSlider.setPaintLabels( true );
					diffSlider.setPaintTicks( true );
					diffSlider.setFont(SudokuFonts.SMALL);
				dialogPanel.add( diffSlider, c );
				
				////////////////////////////////////////////////////////////////
				//	RANDOM SEED AND SHIT
				////////////////////////////////////////////////////////////////
				c.gridy++;
				c.ipadx = 0;
				c.gridx = 0;
				c.weightx = 4;
				seedLabel = new JLabel(
					"random generator seed",
					JLabel.TRAILING
				);
				dialogPanel.add( seedLabel, c );
				
				//*
				c.gridx = 1;
				c.weightx = 1;
				seedIcon = new JLabel( NOICON );
				dialogPanel.add( seedIcon, c );
				//*/
				
				c.gridx = 2;
				c.weightx = 2;
				seedButton = new JButton( "randomize" );
					seedButton.addActionListener(new SeedButtonListener());
					seedButton.setFont(SudokuFonts.SMALL);
				dialogPanel.add( seedButton, c );
				
				c.gridx = 3;
				c.weightx = 8;
				seedTextField = new JTextField( 11 );
					seedTextField.addActionListener(new SeedTextFieldListener());
					seedTextField.getDocument().addDocumentListener(
						new SeedTextFieldChangeListener()
					);
					seedTextField.setText( seed.toString() );
					seedTextField.setFont(SudokuFonts.MEDIUM);
					seedTextField.setHorizontalAlignment(JTextField.TRAILING);
				dialogPanel.add( seedTextField, c );
				
				/*
				c.gridx = 4;
				c.weightx = 1;
				seedCheckBox = new JCheckBox( "random" );
					//seedCheckBox.setSelected(seedRandom);
					seedCheckBox.addActionListener(new SeedCheckBoxListener());
					seedCheckBox.setFont(SudokuFonts.SMALL);
				dialogPanel.add( seedCheckBox, c );
				toggleSeedElements( seedRandom );
				*/
				////////////////////////////////////////////////////////////////
			dialog.add( dialogPanel );
			
			
			////////////////////////////////////////////////////////////////
			// OK AND KANKEL BUTTONES
			////////////////////////////////////////////////////////////////
			JPanel dialogButtonPanel = new JPanel(new GridBagLayout());
				c = new GridBagConstraints();
					c.anchor = GridBagConstraints.LINE_END;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.insets = new Insets( 5, 25, 5, 5 );
					c.gridwidth = 1;
				
				c.gridy = 0;
				
				c.gridx = 0;
				c.weightx = 0;
				dialogOK = new JButton( "  OK  " );
					if (gameRunning) { dialogOK.setIcon(ICON_TINYWARN); } // he he
					dialogOK.addActionListener(
						(ActionEvent e) -> {
							ok = true;
							if (gameRunning) {
								int ret = JOptionPane.showConfirmDialog(
									DIALOG,
									"Start a new game and remove all progress from existing game?",
									"Start New Game",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE
								);
								ok = (ret == JOptionPane.OK_OPTION);
							}
							setVisible(!ok);
						}
					);
				dialogButtonPanel.add( dialogOK, c );
				
				c.gridx++;
				dialogCANCEL = new JButton( "Cancel" );
					dialogCANCEL.addActionListener(
						(ActionEvent e) -> { ok = false; setVisible(false); }
					);
				dialogButtonPanel.add( dialogCANCEL, c );
				////////////////////////////////////////////////////////////////
			dialog.add( dialogButtonPanel, BorderLayout.PAGE_END );
			
		dialog.pack();
		dialog.setSize(
			dialog.getWidth() + 64,
			dialog.getHeight()
		);
		dialog.setLocation(
			FRAME.getX() + FRAME.getWidth()/2 - dialog.getWidth()/2,
			FRAME.getY() + FRAME.getWidth()/2 - dialog.getHeight()/2
		);
		
		return dialog;
	}
	
	
	public boolean getOK() { return ok; }
	public Integer getGidx() { return gidx; }
	public Integer getDiff() { return diff; }
	public Integer getSeed() { return seed; }
	
	
	////////////////////////////////////////////////////////////////
	//	event handleres and other shit
	////////////////////////////////////////////////////////////////
	
	////////////////////////////////
	//	DIFF FIELDS
	////////////////////////////////
	private class DiffTextFieldListener implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			try {
				diff = Integer.valueOf(diffTextField.getText());
				diff = inputClamp( diff, DIFFMIN, DIFFMAX );
				System.out.println( "diff:"+diff );
				diffTextField.setText( "" + diff );
				diffSlider.setValue( diff );
			} catch (NumberFormatException err) {
				System.out.println(
					this.getClass().getName() +
					":\n\terror getting TextField value\n\t" +
					err
				);
				diff = DIFFDEF;
				diffSlider.setValue( diff );
				diffTextField.setText( "" + diff );
			}
		}
	}
	private class DiffTextFieldChangeListener implements DocumentListener {
		@Override public void insertUpdate( DocumentEvent e ) { changedUpdate( e ); }
		@Override public void removeUpdate( DocumentEvent e ) { changedUpdate( e ); }
		@Override
		public void changedUpdate( DocumentEvent e ) {
			/*
			Integer check = textFieldSanityCheck( diffTextField.getText() );
			if (diffLabel != null && dialogOK != null) {
				if (check == null) {
					errorflags |= 1;
					diffIcon.setIcon( ICON_TINYERR );
					dialogOK.setEnabled( false );
				} else {
					errorflags &= 2;
					diffIcon.setIcon( NOICON );
					if (errorflags == 0) {
						dialogOK.setEnabled( true );
					}
				}
			}
			*/
			//System.out.println( "diff changed : " + diffTextField.getText() );
			Integer check = textFieldSanityCheck( diffTextField.getText() );
			setErrorIcon( diffIcon, 1, check == null );
			setErrorOK(dialogOK);
			if (check != null) {
				diff = inputClamp( check, DIFFMIN, DIFFMAX );
			}
			// näistä ei voi muuttaa slideria tai tekstikenttää ___
			// "attempt to mutate in notification"
			// ehkä infinit loup check
		}
	}
	private class DiffSliderListener implements ChangeListener {
		@Override
		public void stateChanged( ChangeEvent e ) {
			diff = diffSlider.getValue();
			diffTextField.setText( "" + diff );
		}
	}
	
	////////////////////////////////
	//	SEED FIELDS
	////////////////////////////////
	
	private class SeedButtonListener implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			randomSeed();
			writeSeed();
		}
	}
	private class SeedTextFieldListener implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			try {
				seed = Integer.valueOf(seedTextField.getText());
				seed = inputClamp( seed, 0, Integer.MAX_VALUE );
				System.out.println( "seed:"+seed );
				writeSeed();
			} catch (NumberFormatException err) {
				System.out.println(
					this.getClass().getName() +
					":\n\terror getting TextField value\n\t" +
					err 
				);
				randomSeed();
				writeSeed();
			}
		}
	}
	private class SeedTextFieldChangeListener implements DocumentListener {
		@Override public void insertUpdate( DocumentEvent e ) { changedUpdate( e ); }
		@Override public void removeUpdate( DocumentEvent e ) { changedUpdate( e ); }
		@Override
		public void changedUpdate( DocumentEvent e ) {
			/*
			Integer check = textFieldSanityCheck( seedTextField.getText() );
			if (seedLabel != null && dialogOK != null) {
				if (check == null) {
					errorflags |= 2;
					seedIcon.setIcon( ICON_TINYERR );
					dialogOK.setEnabled( false );
				} else {
					errorflags &= 1;
					seedIcon.setIcon( NOICON );
					if (errorflags == 0) {
						dialogOK.setEnabled( true );
					}
				}
			}
			*/
			//System.out.println( "seed changed : " + seedTextField.getText() );
			Integer check = textFieldSanityCheck( seedTextField.getText() );
			setErrorIcon( seedIcon, 2, check == null );
			setErrorOK(dialogOK);
			if (check != null) {
				seed = inputClamp( check, 0, Integer.MAX_VALUE);
			}
		}
	}
	/*
	private class SeedCheckBoxListener implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent e ) {
			seedRandom = seedCheckBox.isSelected();
			toggleSeedElements( seedRandom );
		}
	}
	*/
	
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	static Integer gidx2 = null;
	static Integer diff2 = null;
	static Integer seed2 = null;
	
	public static void main(String[] args) {
		
		String[] GRIDCHOICES = { "4x4 grid", "6x6 grid", "9x9 grid", "16x16 grid" };
		int GRIDCHOICEINDEX = 2;
		int[] DIFFS = { 20, 80, 25 }; // min, max, default
		
		//Integer grid, diff, seed;
		
		class ButtonListener implements ActionListener {
			JFrame frame;
			public ButtonListener( JFrame frame ) { this.frame = frame; }
			@Override
			public void actionPerformed( ActionEvent e ) {
				DialogNewGame dng = new DialogNewGame(
					frame, "New Game",
					GRIDCHOICES, DIFFS,
					gidx2,
					diff2,
					seed2,
					true
				);
				dng.setVisible( true );
				System.out.println( dng.getOK() );
				if (dng.getOK()) {
					gidx2 = dng.getGidx();
					diff2 = dng.getDiff();
					seed2 = dng.getSeed();
					System.out.printf(
						"\nGridSelection[%d]: %s\nStaticNum: %d\nSeed: %d\n",
						gidx2,
						GRIDCHOICES[gidx2],
						diff2,
						seed2
					);
				}
			}
			//public Integer getGidx() { return gidx2; }
			//public Integer getDiff() { return diff2; }
			//public Integer getSeed() { return seed2; }
		}
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame( "DialogNewGame test" );
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container frameCont = frame.getContentPane();
				frame.setBounds( 256, 256, 256, 256 );
				
				ButtonListener bl = new ButtonListener( frame );
				
				JButton button = new JButton( "dialog" );
					button.addActionListener(bl);
				frameCont.add( button );
				
				//System.out.println( "ASS " + bl.getGidx() );
				//System.out.println( "ASS " + bl.getDiff() );
				//System.out.println( "ASS " + bl.getSeed() );
				System.out.println( "ASS " + gidx2 );
				System.out.println( "ASS " + diff2 );
				System.out.println( "ASS " + seed2 );
				
				//frame.pack();
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(run);
	}
}
