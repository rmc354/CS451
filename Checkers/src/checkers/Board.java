package checkers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.ws.Response;

public class Board extends JPanel implements ActionListener, Serializable{
	
    private String version = "1.01";
	
    private static final int BOARD_SIDE = 400;
    private static final int SQ_SIZE = 50;
    
    private static final Color DARK_BOARD_COLOR = new Color(100, 50, 0);
    private static final Color LIGHT_BOARD_COLOR = new Color(245, 222, 179);
    
    boolean pieceClicked = false;
    static boolean yourTurn;
    static boolean controlsRed;
    boolean lastMoveWasJump = false;
    
    private Piece[][] pieces;
    
    private JLabel versionText;
    private JLabel turnText;
    
    public Board(boolean controlsRed, boolean goesFirst) {
    	yourTurn = goesFirst;
    	this.controlsRed = controlsRed;
    	
    	this.setLayout(null);
    	
    	versionText = new JLabel();
        versionText.setBounds(0, 810, 200, 40);
        versionText.setText("Version : " + version);
        add(versionText);
        
        turnText = new JLabel();
        turnText.setBounds(200, 810, 600, 40);
        String color = controlsRed ? "Red" : "Black";
        String turn = yourTurn ? "" : "not ";
        turnText.setText("You are " + color + "   Currently " + turn + "your turn");
        turnText.setFont(new Font(turnText.getName(), Font.PLAIN, 20));
        add(turnText);
    	
    	pieces = new Piece[8][8];
    	for (int x = 0; x < 8; x++) {
    		// Fill in every dark space in the top 3 rows
    		for (int y = 0; y < 3; y++) {
    			if (x % 2 == 0 && y % 2 == 1 || x % 2 == 1 && y % 2 == 0) {
    				pieces[x][y] = new Piece(false,x,y);
    			}
    		}
    		// Fill in every dark space in the bottom 3 rows
    		for (int y = 7; y > 4; y--) {
    			if (x % 2 == 0 && y % 2 == 1 || x % 2 == 1 && y % 2 == 0) {
    				pieces[x][y] = new Piece(true,x,y);
    			}
    		}
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	
    	String color = controlsRed ? "Red" : "Black";
        String turn = yourTurn ? "" : "not ";
        turnText.setText("You are " + color + "   Currently " + turn + "your turn");
    	
        super.paintComponent(g);
        boolean dark = true;
        for (int i = 0; i < SQ_SIZE * 8; i += SQ_SIZE) {
            if (dark) {
            	dark = false;
            } else {
            	dark = true;
            }
            for (int j = 0; j < SQ_SIZE * 8; j += SQ_SIZE) {
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
        
        placePieces();
    }
    
    private void placePieces() {
    	System.out.println("placed");

    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			if (pieces[x][y] != null) {
    				try {
    					String pieceName = "p" + x + "," + y;
    					System.out.print(x + "," + y + " - ");
    					removeExistingButton(pieceName);
    					String imageSource = pieces[x][y].getImageSource();
    					
    					Image img = ImageIO.read(getClass().getResource(imageSource));
    					JButton button = new JButton();
	    				button.setName(pieceName);
    					button.setActionCommand(pieceName);
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
    			} else {
    				String pieceName = "p" + x + "," + y;
					removeExistingButton(pieceName);
    			}
    		}
    	}
    }
    
    private void removeExistingButton(String regex) {
    	for (Component c : this.getComponents()) {
    		try {
				if (c.getName().matches(regex)) {
					this.remove(c);
				}
    		} catch (NullPointerException e) {
    			
    		}
    	}
    }
    
    private boolean existsButtonThatMatches(String regex) {
    	for (Component c : this.getComponents()) {
    		if (c.getName() != null && c.getName().matches(regex)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void initializeGui(Board board) throws UnknownHostException, IOException {        
        JFrame frame = new JFrame();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_SIDE, BOARD_SIDE);
        frame.setLocationByPlatform(true);
        frame.setTitle("Checkers");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        System.out.println(pieces);
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_SIDE, BOARD_SIDE);
    }
    
    public void setPieces(Piece[][] pieces) {
    	this.pieces = pieces;
    }
    
    private void createPiece(int x, int y, String imageSource, String pieceName, Color color) {
    	try {
			removeExistingButton(pieceName);
			
			Image img = ImageIO.read(getClass().getResource(imageSource));
			JButton button = new JButton();
			button.setName(pieceName);
			button.setActionCommand(pieceName);
			button.setLayout(null);
			button.setBounds(x * SQ_SIZE + 2, y * SQ_SIZE + 2, SQ_SIZE - 4, SQ_SIZE - 4);
			button.setIcon(new ImageIcon(img));
			button.setBackground(color);
			button.setOpaque(true);
			button.setBorderPainted(false);
			button.addActionListener(this);
			add(button);
			repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void createMovePiece (int x, int y, int moveX, int moveY, boolean isLeftMove) {
    	if (moveX >= 0 && moveX < 8 && moveY >= 0 && moveY < 8) {
    		if (pieces[moveX][moveY] == null ) {
    			if(!lastMoveWasJump) {
	    			String pieceName = "m" + x + "," + y + ">" + moveX + "," + moveY;
	    			// used in the actionlistener to determine what piece was clicked and what to do
	    			
	    			createPiece(moveX, moveY, "MovePiece.gif", pieceName, DARK_BOARD_COLOR);
	    		}
    		} else {
    			System.out.println(moveX + "," + moveY + " is NOT empty");
    			if (pieces[moveX][moveY]!=null && pieces[moveX][moveY].isRed() != pieces[x][y].isRed()) { 
    				// different colors
    				
    				if (isLeftMove) {
    					int[] moves = pieces[x][y].getLeftMove(moveX, moveY);
    					int jumpMoveX = moves[0];
						int jumpMoveY = moves[1];
						if (jumpMoveX >= 0 && jumpMoveX < 8 && jumpMoveY >= 0 && jumpMoveY < 8) {
	    					if (((pieces[x][y].isRed() && moveY > y) || (pieces[x][y].isBlack() && moveY > y)) && moves.length > 2) {
	    						// moving opposite left (king only)
	    						jumpMoveX = moves[2];
	    						jumpMoveY = moves[3];
	    					}
	    					if (pieces[jumpMoveX][jumpMoveY] == null) {
	    		    			String pieceName = "j" + x + "," + y + ">" + jumpMoveX + "," + jumpMoveY;
	    		    			createPiece(jumpMoveX, jumpMoveY, "MovePiece.gif", pieceName, DARK_BOARD_COLOR);
							}
						}
    				} else {
    					int[] moves = pieces[x][y].getRightMove(moveX, moveY);
    					int jumpMoveX = moves[0];
						int jumpMoveY = moves[1];
						if (jumpMoveX >= 0 && jumpMoveX < 8 && jumpMoveY >= 0 && jumpMoveY < 8) {
	    					if (((pieces[x][y].isRed() && moveY > y) || (pieces[x][y].isBlack() && moveY > y)) && moves.length > 2) {
	    						// moving opposite right (king only)
	    						jumpMoveX = moves[2];
	    						jumpMoveY = moves[3];
	    					}
	    					if (pieces[jumpMoveX][jumpMoveY] == null) {
	    		    			String pieceName = "j" + x + "," + y + ">" + jumpMoveX + "," + jumpMoveY;
	    		    			createPiece(jumpMoveX, jumpMoveY, "MovePiece.gif", pieceName, DARK_BOARD_COLOR);
							}
						}
    				}
    			}
    		}
    	}
    }
    
    private void jumpAgain(String piece) {
    	
    	String[] pos = piece.split(",");
    	int x = Integer.parseInt(pos[0]);
    	int y = Integer.parseInt(pos[1]);
		pieceClicked = true;
		lastMoveWasJump=false;
		
    	int[] leftMoves = pieces[x][y].getLeftMove(x, y);
    	createMovePiece(x, y, leftMoves[0], leftMoves[1], true);
    	if (leftMoves.length > 2) {
        	createMovePiece(x, y, leftMoves[2], leftMoves[3], true);
    	}
    	
    	int[] rightMoves = pieces[x][y].getRightMove(x, y);
    	createMovePiece(x, y, rightMoves[0], rightMoves[1], false);
    	if (rightMoves.length > 2) {
        	createMovePiece(x, y, rightMoves[2], rightMoves[3], false);
    	}
    	if (!existsButtonThatMatches("^m.*")) {
    		System.out.println("no moves");
        	endTurn();
        	return;
    	}
    	if (!existsButtonThatMatches("^j.*")) {
    		System.out.println("no moves");
        	endTurn();
        	return;
    	}
    	endTurn();
    }
    private void clickPiece(String piece) {

    	String[] pos = piece.split(",");
    	int x = Integer.parseInt(pos[0]);
    	int y = Integer.parseInt(pos[1]);
    	
    	//if (true) {	// comment this and uncomment next line to run for real
    	if (controlsRed == pieces[x][y].isRed() && yourTurn && !pieceClicked && !lastMoveWasJump) {
    		// only click correct color pieces
    		pieceClicked = true;
	    	int[] leftMoves = pieces[x][y].getLeftMove(x, y);
	    	createMovePiece(x, y, leftMoves[0], leftMoves[1], true);
	    	if (leftMoves.length > 2) {
	        	createMovePiece(x, y, leftMoves[2], leftMoves[3], true);
	    	}
	    	
	    	int[] rightMoves = pieces[x][y].getRightMove(x, y);
	    	createMovePiece(x, y, rightMoves[0], rightMoves[1], false);
	    	if (rightMoves.length > 2) {
	        	createMovePiece(x, y, rightMoves[2], rightMoves[3], false);
	    	}
	    	if (!existsButtonThatMatches("^m.*")) {
	    		System.out.println("no moves");
	        	endTurn();
	        	return;
	    	}
    	}
    }
    
    private void makeMove(String move, boolean isJumpMove) {
    	pieceClicked = false;

    	String[] positions = move.split(">");
    	String[] start = positions[0].split(",");
    	String[] moveTo = positions[1].split(",");
    	int startX = Integer.parseInt(start[0]);
    	int startY = Integer.parseInt(start[1]);
    	int moveX = Integer.parseInt(moveTo[0]);
    	int moveY = Integer.parseInt(moveTo[1]);

    	System.out.println("move " + startX + "," + startY + " to " + moveX + "," + moveY);
    	pieces[moveX][moveY] = pieces[startX][startY];
    	pieces[moveX][moveY].setPosition(moveX, moveY);
    	pieces[startX][startY] = null;
    	
    	if (isJumpMove) {
	    	int jumpOverX = (moveX + startX) / 2;
	    	int jumpOverY = (moveY + startY) / 2;
	    	pieces[jumpOverX][jumpOverY] = null;
	    	removeExistingButton("p" + jumpOverX + "," + jumpOverY);
    	}
    	
    	removeExistingButton("p" + startX + "," + startY);
    	removeExistingButton("^m.*");
    	removeExistingButton("^j.*");
    	
    	if ((pieces[moveX][moveY].isRed() && moveY == 0) || (pieces[moveX][moveY].isBlack() && moveY == 7)) {
    		pieces[moveX][moveY].king();
        	repaint();
        	endTurn();
        	return;
    	}
    	else if (isJumpMove) {
    		repaint();
    		lastMoveWasJump = true;
    		jumpAgain(moveX+","+moveY);
    		return;
    	}
    	else {
        	repaint();
        	endTurn();
        	return;
    	}

    }
    
    private void endTurn() {

    	pieceClicked = false;
    	yourTurn = false;
    	System.out.println("end turn\n"+controlsRed+" lastMoveWasJump: "+lastMoveWasJump);
    	repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	System.out.println(command);

    	if (command.startsWith("p")) {
    		// in format "p(x),(y)"
			clickPiece(command.replace('p', ' ').trim());
    	} else if (command.startsWith("m")) {
    		// in format "m(currentX),(currentY)>(newX),(newY)"
			makeMove(command.replace('m', ' ').trim(), false);
    	} else if (command.startsWith("j")) {
    		// in format "j(currentX),(currentY)>(newX),(newY)"
			makeMove(command.replace('j', ' ').trim(), true);
    	}
    }

	public boolean getControlsRed() {
		return controlsRed;
	}
    
    public boolean getYourTurn() {
    	return yourTurn;
    }
    
    public void yourTurn() {
    	yourTurn = true;
    }
    
    public Piece[][] getPieces() {
    	return pieces;
    }
    
    public boolean gameOver() {
    	boolean redNoMoves = false;
    	boolean blackNoMoves = false;
    	boolean hasRedPiece = false;
    	boolean hasBlackPiece = false;
    	for (Piece[] row : pieces) {
    		for (Piece piece : row) {
    			if (piece != null) {
    				if (piece.isRed()) {
    					hasRedPiece = true;
    				} else {
    					hasBlackPiece = true;
    				}
    				if (!piece.hasValidMove(pieces)) {
    					if (piece.isRed()) {
    						redNoMoves = false;
    					} else {
    						blackNoMoves = false;
    					}
    				}
    			}
    		}
			if (redNoMoves && blackNoMoves) {
	    		System.out.println("Its a tie");
	    		return true;
	    	} else if (redNoMoves || !hasRedPiece) {
	    		System.out.println("Black wins");
	    		return true;
	    	} else if (blackNoMoves || !hasBlackPiece) {
	    		System.out.println("Red wins");
	    		return true;
	    	}
    	}
    	return false;
    }

	public void refresh() {
		repaint();
    	System.out.println("refreshed");
	}
    
}