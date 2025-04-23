package sudoku.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridBase extends JPanel {
    JButton[][] grid;
    ArrayList<JPanel> gridBlocks;
    private int blockx = 3;
    private int blocky = 3;
    private int size = 9;
    public GridBase() {
        super();
        setBackground(new Color( 0x990099 ));
        setBorder(new EtchedBorder( EtchedBorder.LOWERED ));
        setLayout(new GridLayout( blockx, blocky, 4, 4 ));
        gridBlocks = new ArrayList<>(size);
        makeBlocks();
        makeButtons();
    }

    public void makeBlocks(){
        for(int i = 0; i<size; i++){
            JPanel gridBlock = new JPanel();
            gridBlock.setBackground(new Color(0));
            gridBlock.setBorder(new EtchedBorder( EtchedBorder.RAISED ));
            gridBlock.setLayout(new GridLayout( blocky, blockx, 0, 0 ));
            gridBlocks.add(gridBlock);
            add(gridBlock);
        }
    }

    public void makeButtons(){
        for (int i = 0; i < size*size; i++) {
            JButton button = new JButton("" + i);
            button.setBackground(new Color( 0xffffff ));
            button.setPreferredSize(new Dimension(32, 32));
            gridBlocks.get(getBlockIndex(i%size, i/size, blockx)).add(button);
        }
    }
    private int getBlockIndex(int x, int y, int boxSize){
        return (y/boxSize) * boxSize + x/boxSize;
    }

    public ArrayList<JPanel> getGridBlocks() {
        return gridBlocks;
    }
}
