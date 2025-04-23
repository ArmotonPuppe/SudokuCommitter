package sudoku.ui.menu;

import sudoku.ui.paska3;

import javax.swing.*;

public class MenuView extends JMenu {
    private boolean showPalette = true;
    private boolean showStats = true;
    private boolean showToolBar = true;
    private boolean showStatusBar = true;

    public MenuView() {
        super("View");
        setMnemonic('V');
        JCheckBoxMenuItem menuViewPalette = new JCheckBoxMenuItem("    palette");
        menuViewPalette.setState(showPalette);
        menuViewPalette.setMnemonic('p');
        menuViewPalette.addActionListener(new paska3.EH_menuViewPalette());
        add(menuViewPalette);

        JCheckBoxMenuItem menuViewStats = new JCheckBoxMenuItem("    stats");
        menuViewStats.setState(showStats);
        menuViewStats.setMnemonic('s');
        menuViewStats.addActionListener(new paska3.EH_menuViewStats());
        add(menuViewStats);

        JCheckBoxMenuItem menuViewStatusBar = new JCheckBoxMenuItem("    statusbar");
        menuViewStatusBar.setState(showStatusBar);
        menuViewStatusBar.setMnemonic('t');
        menuViewStatusBar.addActionListener(new paska3.EH_menuViewStatusBar());
        add(menuViewStatusBar);

        JCheckBoxMenuItem menuViewToolBar = new JCheckBoxMenuItem("    toolbar");
        menuViewToolBar.setState(showToolBar);
        menuViewToolBar.setMnemonic('o');
        menuViewToolBar.addActionListener(new paska3.EH_menuViewToolBar());
        add(menuViewToolBar);
    }
}
