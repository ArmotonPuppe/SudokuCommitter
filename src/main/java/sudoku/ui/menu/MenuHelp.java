package sudoku.ui.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuHelp extends JMenu {
    public MenuHelp() {
        super( "Help" );
        setMnemonic('H');
        JMenuItem menuHelpHelp = new JMenuItem( "Help" );
        menuHelpHelp.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)
        );
        add( menuHelpHelp );
        addSeparator();
        JMenuItem menuHelpAbout = new JMenuItem( "About" );
        add( menuHelpAbout );
    }
}
