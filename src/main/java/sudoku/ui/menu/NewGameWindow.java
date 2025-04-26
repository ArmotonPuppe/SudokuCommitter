package sudoku.ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.game.Difficulty;

public class NewGameWindow extends JDialog implements ActionListener {
    JComboBox<Difficulty> difficultyComboBox = new JComboBox<>( Difficulty.values() );
    Integer[] sizes = {4, 9, 16};
    JComboBox<Integer> sizeComboBox = new JComboBox<>(sizes);
    Integer size;
    Difficulty difficulty;
    public NewGameWindow(JFrame parent) {
        super(parent, "New game", true);
        setLayout(new BorderLayout());
        add(difficultyComboBox, BorderLayout.NORTH);
        difficultyComboBox.addActionListener(new ComboBoxListener());
        sizeComboBox.addActionListener(new ComboBoxListener());
        add(sizeComboBox, BorderLayout.LINE_START);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setMnemonic('S');
        startGameButton.addActionListener(this);
        JButton cancelButton = new JButton("Cancel");

        add(startGameButton, BorderLayout.WEST);
        add(cancelButton, BorderLayout.EAST);

        startGameButton.addActionListener(this);
        pack();
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JComboBox<Difficulty> getDifficultyComboBox() {
        return difficultyComboBox;
    }

    public JComboBox<Integer> getSizeComboBox() {
        return sizeComboBox;
    }
    class ComboBoxListener implements ActionListener {
        @Override
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == sizeComboBox) {
                size = (Integer) sizeComboBox.getSelectedItem();
                System.out.println("Selected size: " + size);
            }else if (source == difficultyComboBox) {
                difficulty = (Difficulty) difficultyComboBox.getSelectedItem();
            }
        }
    }
}
