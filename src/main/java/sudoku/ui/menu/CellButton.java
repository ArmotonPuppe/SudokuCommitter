package sudoku.ui.menu;

import sudoku.board.Cell;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellButton extends JButton {
    int x;
    int y;

    public CellButton(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    public void changeView(int number) {
        setText(Integer.toString(number));
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
