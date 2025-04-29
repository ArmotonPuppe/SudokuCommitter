
package sudoku;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.Enumeration;

public class gridpainttest {
	
	static int gridx = 9;
	static int gridy = 9;
	static int blockx = 3;
	static int blocky = 3;
	
	static final BevelBorder btnBevelRaised = new BevelBorder(BevelBorder.RAISED);
	static final BevelBorder btnBevelLowered = new BevelBorder(BevelBorder.LOWERED);
	static final Color btnColorRaised = new Color( 0xccffff );
	static final Color btnColorLowered = new Color( 0x99ff99 );
	
	static final Font fontLarge = new Font( "Liberation Mono", Font.BOLD, 32 );
	static final Font fontMedium = new Font( "Liberation Mono", Font.BOLD, 24 );
	
	static final JFrame frame = new JFrame();
	
	static JSpinner spinnergx, spinnergy, spinnerbx, spinnerby;
	static ButtonGroup palButtonGroup;
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private static String paletteSelected( ButtonGroup group ) {
		for (Enumeration<AbstractButton> pButtons = group.getElements(); pButtons.hasMoreElements();) {
			AbstractButton button = pButtons.nextElement();
			if (button.isSelected()) { return button.getActionCommand(); }
		}
		return null;
	}
	private static void paletteDeselect( ButtonGroup group, AbstractButton ignoreButton ) {
		for (Enumeration<AbstractButton> pButtons = group.getElements(); pButtons.hasMoreElements();) {
			AbstractButton button = pButtons.nextElement();
			styleButton(
				button.equals(ignoreButton) ? null : button,
				false
			);
		}
		if (ignoreButton == null) { group.clearSelection(); }
	}
	
	static void styleButton( AbstractButton btn, boolean pressed ) {
		if (btn == null) { return; }
		
		if (pressed) {
			btn.setBorder( btnBevelLowered );
			btn.setBackground( btnColorLowered );
		} else {
			btn.setBorder( btnBevelRaised );
			btn.setBackground( btnColorRaised );
		}
	}
	
	static class GridPaintListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {
			AbstractButton source = (AbstractButton) e.getSource();
			Character key = e.getKeyChar();
			
			System.out.printf( "%s: ↓ (\"%s\",%s)\n",
				source.getName(), key, e.getKeyCode()
			);
			
			if (gridx < 10) {
				if (key > '0' && key <= ('0'+gridx)) {
					source.setText( key.toString() );
				}
			} else {
				Character keyUp = Character.toUpperCase(key);
				if (keyUp >= 'A' && keyUp <= ('A'+gridx-1)) {
					source.setText( keyUp.toString() );
				}
			}
			
			if (
					key == '0' // both 0 and numpad 0
				||	key == 127 // delete
				||	key == 8 // backspace
			) {
				source.setText( "" );
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private static JPanel buildSpinners( int gx, int gy, int bx, int by ) {
		JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			selectPanel.setBackground(new Color( 0xff99cc ));
			
			selectPanel.add(new JLabel( "grid X" ));
			spinnergx = new JSpinner(new SpinnerNumberModel(
				gx, 4, 26, 1
			));
			selectPanel.add( spinnergx );
			
			selectPanel.add(new JLabel( "grid Y" ));
			spinnergy = new JSpinner(new SpinnerNumberModel(
				gy, 4, 26, 1
			));
			selectPanel.add( spinnergy );
			
			selectPanel.add(new JLabel( "block X" ));
			spinnerbx = new JSpinner(new SpinnerNumberModel(
				bx, 2, 6, 1
			));
			selectPanel.add( spinnerbx );
			
			selectPanel.add(new JLabel( "block Y" ));
			spinnerby = new JSpinner(new SpinnerNumberModel(
				by, 2, 6, 1
			));
			selectPanel.add( spinnerby );
			
			JButton selectButton = new JButton( "make it so" );
				selectButton.setIcon(new ImageIcon("icons/makeitso.png"));
				selectButton.addActionListener((ActionEvent ae) -> {
					gridx = (int) spinnergx.getValue();
					gridy = (int) spinnergy.getValue();
					blockx = (int) spinnerbx.getValue();
					blocky = (int) spinnerby.getValue();
					
					System.out.printf( "gx:%d, gy:%d, bx:%d, by:%d\n",
						gridx, gridy, blockx, blocky
					);
					
					frame.getContentPane().removeAll();
					huhhahhei();
					frame.getContentPane().validate();
				});
			selectPanel.add( selectButton );
		return selectPanel;
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private static JPanel buildPalette( int num, ButtonGroup group ) {
		Dimension btnSize = new Dimension( 32, 32 );
		JPanel pal = new JPanel();
			pal.setLayout(new GridLayout(0,1,0,0));
			for (int i = 0; i < num; i++) {
				JToggleButton palBtn = new JToggleButton();
				if (num < 10) {
					palBtn.setText( "" + (i+1) );
				} else {
					palBtn.setText( Character.toString('A'+i) );
				}
				palBtn.setFont( fontMedium );
				palBtn.setSize(btnSize);
				styleButton( palBtn, false );
				
				palBtn.addActionListener((ActionEvent ae) -> {
					AbstractButton btn = (AbstractButton) ae.getSource();
					if (btn.getBackground().equals(btnColorRaised)) {
						styleButton( btn, true );
						paletteDeselect( group, btn );
					} else {
						styleButton( btn, false );
						paletteDeselect( group, null );
					}
				});
				
				pal.add( palBtn );
				group.add( palBtn );
			}
			pal.setPreferredSize(new Dimension(48, num*32));
			pal.setBackground(new Color( 0xff0000 ));
		return pal;
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private static JPanel buildGrid(
		int gridx,
		int gridy,
		int blockx,
		int blocky,
		ButtonGroup palGroup
	) {
		JPanel gridPanel = new JPanel(new BorderLayout( 16, 16 ));
			gridPanel.setBackground(new Color( 0x99ccff ));
			gridPanel.setBorder(
				new MatteBorder(new Insets(16,16,16,16),
				new Color( 0xffcc99 )
			));
			JPanel gridBase = new JPanel();
			{
				class FillerPanel extends JPanel {
					public FillerPanel( Color color ) {
						this.setBackground( color );
					}
				}
				Color gridBaseColor = new Color( 0x99ffcc );
				gridBase.setBackground( gridBaseColor );
				gridBase.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
					c.anchor = GridBagConstraints.FIRST_LINE_START;
					c.fill = GridBagConstraints.BOTH;
					c.gridwidth = 1;
					c.gridheight = 1;
				
				c.gridx = 0;
				c.gridy = 0;
				for (int y = 0; y < gridy; y++) {
					
					if (y > 0 && y % blocky == 0) {
						c.gridwidth = GridBagConstraints.REMAINDER;
						gridBase.add(new FillerPanel(gridBaseColor), c);
						c.gridy++;
					}
					
					for (int x = 0; x < gridx; x++) {
						c.gridwidth = 1;
						if (x > 0 && x % blockx == 0) {
							gridBase.add(new FillerPanel(gridBaseColor), c);
							c.gridx++;
						}
						JButton gbtn = new JButton();
							//gbtn.setText( "" + (y+1) +","+ (x+1) );
							//gbtn.setText( "" + (y*GRIDX + x) );
							gbtn.setFont( fontLarge );
							gbtn.setText( "XX" );
							gbtn.setPreferredSize(gbtn.getPreferredSize());
							gbtn.setText( "" );
							gbtn.addKeyListener(new GridPaintListener());
							gbtn.addActionListener((ActionEvent ae) -> {
								String ps = paletteSelected(palGroup);
								if (ps != null) {
									if (gbtn.getText().equals(ps)) {
										gbtn.setText( "" );
									} else {
										gbtn.setText( ps );
									}
								}
							});
						gridBase.add( gbtn, c );
						c.gridx++;
					}
					c.gridy++;
					c.gridx = 0;
				}
			}
			gridPanel.add( gridBase, BorderLayout.CENTER );
		return gridPanel;
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private static void huhhahhei() {
		Container frameCont = frame.getContentPane();
		
		frameCont.add(
			buildSpinners( gridx, gridy, blockx, blocky ),
			BorderLayout.PAGE_END
		);
		
		palButtonGroup = new ButtonGroup();
		frameCont.add(
			buildPalette( gridx, palButtonGroup ),
			BorderLayout.LINE_START
		);
		
		frameCont.add(
			buildGrid( gridx, gridy, blockx, blocky, palButtonGroup ),
			BorderLayout.CENTER
		);
		
		frame.pack();
		
		int scrw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int scrh = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int winw = frame.getWidth();
		int winh = frame.getHeight();
		int winx = scrw/2 - winw/2;
		int winy = scrh/2 - winh/2;
		frame.setLocation( winx, winy );
		System.out.printf( "x:%d, y:%d, w:%d, h:%d\n", winx, winy, winw, winh );
	}
	
	private static final Runnable run = () -> {
		frame.setTitle( "grid paint test" );
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		huhhahhei();
		
		frame.pack();
		frame.setVisible(true);
	};
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(run);
	}
	
}
