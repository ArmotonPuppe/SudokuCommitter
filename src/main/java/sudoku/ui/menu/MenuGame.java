package sudoku.ui.menu;

import sudoku.ui.paska3;

import javax.swing.*;

public class MenuGame extends JMenu {
    public MenuGame(JFrame parent) {
        super("Game");
        setMnemonic('G');

        JMenuItem menuGameNew = new JMenuItem( "New Game" );
        menuGameNew.setMnemonic('N');
        add(menuGameNew);
        menuGameNew.addActionListener(e -> {
            NewGameWindow window = new NewGameWindow(parent);
            window.setVisible(true);
        });
        JMenuItem menuGameRestart = new JMenuItem( "Restart Game" );
        menuGameRestart.setMnemonic('R');
        add(menuGameRestart);

        addSeparator();
        JMenuItem menuGamePref = new JMenuItem( "Preferences" );
        menuGamePref.setMnemonic('P');
        menuGamePref.addActionListener(new paska3.EH_menuGamePref());
        add(menuGamePref);

        addSeparator();
        JMenuItem menuGameExit = new JMenuItem( "Exit" );
        menuGameExit.setMnemonic('x');
        menuGameExit.addActionListener(new paska3.EH_menuGameExit());
        add(menuGameExit);
    }


}
