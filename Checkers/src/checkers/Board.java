package checkers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Board extends JPanel implements ActionListener{
	
    private static final int BOARD_SIDE = 800;
    private static final int SQ_SIZE = 100;
    
    private static final Color DARK_BOARD_COLOR = new Color(100, 50, 0);
    private static final Color LIGHT_BOARD_COLOR = new Color(245, 222, 179);
    
    private Piece[][] pieces;
    
    Board() {
    	pieces = new Piece[8][8];
    	for (int x = 0; x < 8; x++) {
    		// Fill in every dark space in the top 3 rows
    		for (int y = 0; y < 3; y++) {
    			if (x % 2 == 0 && y % 2 == 1 || x % 2 == 1 && y % 2 == 0) {
    				pieces[x][y] = new Piece(false);
    			}
    		}
    		// Fill in every dark space in the bottom 3 rows
    		for (int y = 7; y > 4; y--) {
    			if (x % 2 == 0 && y % 2 == 1 || x % 2 == 1 && y % 2 == 0) {
    				pieces[x][y] = new Piece(true);
    			}
    		}
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean dark = true;
        for (int i = 0; i < BOARD_SIDE; i += SQ_SIZE) {
            if (dark) {
            	dark = false;
            } else {
            	dark = true;
            }
            for (int j = 0; j < BOARD_SIDE; j += SQ_SIZE) {
                if (dark) {
                    g.setColor(DARK_BOARD_COLOR);
                    g.fillRect(j, i, SQ_SIZE, SQ_SIZE);
                    dark = false;
                } else {
                    g.setColor(LIGHT_BOARD_COLOR);
                    g.fillRect(j, i, SQ_SIZE, SQ_SIZE);
                    dark = true;
                }
            }
        }
        for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			if (pieces[x][y] != null) {
    				try {
    					removeExistingButton(x + "," + y);
    					String imageSource = "";
    					if (pieces[x][y].isRed && !pieces[x][y].isKinged) {
    						imageSource = "RedPiece.gif";
    					} else if (!pieces[x][y].isRed && !pieces[x][y].isKinged) {
    						imageSource = "BlackPiece.gif";
    					}
    					// TODO implement king pieces
    					
    					Image img = ImageIO.read(getClass().getResource(imageSource));
    					JButton button = new JButton();
	    				button.setName(x + "," + y);
    					button.setActionCommand(x + "," + y);
	    				button.setLayout(null);
	    				button.setBounds(x * SQ_SIZE + 2, y * SQ_SIZE + 2, SQ_SIZE - 4, SQ_SIZE - 4);
	    				button.setIcon(new ImageIcon(img));
	    				button.setBackground(DARK_BOARD_COLOR);
	    				button.setOpaque(true);
	    				button.setBorderPainted(false);
	    				button.addActionListener(this);
	    				add(button);
    				} catch (IOException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}
    }
    
    private void removeExistingButton(String pos) {
    	for (Component c : this.getComponents()) {
    		if (c.getName().equals(pos)) {
    			this.remove(c);
    		}
    	}
    }

    public void initializeGui() {
        JFrame frame = new JFrame();
        frame.add(new Board());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_SIDE, BOARD_SIDE);
        frame.setLocationByPlatform(true);
        frame.setTitle("Checkers");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public Dimension getPreferredSize() {
        return new Dimension(BOARD_SIDE, BOARD_SIDE);
    }

    public void setPieces(Piece[][] pieces) {
    	this.pieces = pieces;
    }
    
    public void actionPerformed(ActionEvent e) {
    	System.out.println(e.getActionCommand());
    }
}