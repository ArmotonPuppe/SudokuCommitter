
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Enumeration;

public class gridpainttest {
	
	static final int GRIDX = 9;
	static final int GRIDY = 9;
	static final int BLOCKX = 3;
	static final int BLOCKY = 3;
	
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
	
	static final BevelBorder btnBevelRaised = new BevelBorder(BevelBorder.RAISED);
	static final BevelBorder btnBevelLowered = new BevelBorder(BevelBorder.LOWERED);
	static final Color btnColorRaised = new Color( 0xccffff );
	static final Color btnColorLowered = new Color( 0x99ff99 );
	
	static final Font fontLarge = new Font( "Liberation Mono", Font.BOLD, 32 );
	
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
			if (GRIDX < 10) {
				if (key >= '0' && key <= '9') {
					System.out.println( source.getActionCommand() + ": ↓" + key );
					source.setText( (key == '0') ? "" : key.toString() );
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
	
	static Runnable run = () -> {
		JFrame frame = new JFrame( "grid paint test" );
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container frameCont = frame.getContentPane();
		
		int scrw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int scrh = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int winw = 128 + (GRIDX + BLOCKX) * 64;
		int winh = 0 + (GRIDY + BLOCKY) * 64;
		int winx = scrw/2 - winw/2;
		int winy = scrh/2 - winh/2;
		frame.setBounds( winx, winy, winw, winh );
		System.out.printf( "x:%d, y:%d, w:%d, h:%d\n", winx, winy, winw, winh );
		
		
		// palette kinda
		ButtonGroup palButtonGroup = new ButtonGroup();
		JPanel pal = new JPanel();
			pal.setPreferredSize(new Dimension(winw/16, winh));
			pal.setLayout(new GridLayout(0,1,0,0));
			for (int i = 0; i < GRIDX; i++) {
				JToggleButton palBtn = new JToggleButton( "" + (i+1) );
				styleButton( palBtn, false );
				
				palBtn.addActionListener((ActionEvent ae) -> {
					AbstractButton btn = (AbstractButton) ae.getSource();
					if (btn.getBackground().equals(btnColorRaised)) {
						styleButton( btn, true );
						paletteDeselect( palButtonGroup, btn );
					} else {
						styleButton( btn, false );
						paletteDeselect( palButtonGroup, null );
					}
				});
				
				pal.add( palBtn );
				palButtonGroup.add( palBtn );
			}
			pal.setBackground(new Color( 0xff0000 ));
		frameCont.add( pal, BorderLayout.LINE_START );
		
		// grid
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
				for (int y = 0; y < GRIDY; y++) {
					
					if (y > 0 && y % BLOCKY == 0) {
						c.gridwidth = GridBagConstraints.REMAINDER;
						gridBase.add(new FillerPanel(gridBaseColor), c);
						c.gridy++;
					}
					
					for (int x = 0; x < GRIDX; x++) {
						c.gridwidth = 1;
						if (x > 0 && x % BLOCKX == 0) {
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
								String ps = paletteSelected(palButtonGroup);
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
		frameCont.add( gridPanel, BorderLayout.CENTER );
		
		frame.pack();
		frame.setVisible(true);
	};
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(run);
	}
	
}
