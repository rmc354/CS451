package checkers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.ws.Response;

import network.Login;

public class Board extends JPanel implements ActionListener, Serializable, Runnable{
	
    private static final int BOARD_SIDE = 800;
    private static final int SQ_SIZE = 100;
    
    private static final Color DARK_BOARD_COLOR = new Color(100, 50, 0);
    private static final Color LIGHT_BOARD_COLOR = new Color(245, 222, 179);
    
    boolean pieceClicked = false;
    static boolean yourTurn;
    static boolean controlsRed;
    boolean lastMoveWasJump = false;
    
    private Piece[][] pieces;
    
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    protected static Login l = new Login();
    private static String server;
    private static int PORT;
    private PrintStream ps;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Thread runner = null;
    
    public Board(boolean controlsRed, boolean goesFirst, String server, int PORT) throws UnknownHostException, IOException {
    	yourTurn = goesFirst;
    	this.controlsRed = controlsRed;
    	Board.server = server;
    	Board.PORT = PORT;
    	socket = new Socket(server, PORT);
    	//os = new ObjectOutputStream(socket.getOutputStream());
    	//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	//out = new PrintWriter(socket.getOutputStream(), true);
    	//is = new ObjectInputStream(socket.getInputStream());
    	//ps = new PrintStream(socket.getOutputStream(), true);
		OutputStream oos = socket.getOutputStream();
		os = new ObjectOutputStream(oos);
		InputStream iis = socket.getInputStream();
		is = new ObjectInputStream(iis);
		this.start();
    	
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
    
    public void start()
    {
    	runner = new Thread(this);
    	runner.start();
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
        placePieces();
    }
    
    private void placePieces() {
    	System.out.println("placed");

    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			if (pieces[x][y] != null) {
    				try {
    					String pieceName = "p" + x + "," + y;
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
    			}
    		}
    	}
    }
    
    private void removeExistingButton(String regex) {
    	for (Component c : this.getComponents()) {
    		if (c.getName().matches(regex)) {
    			this.remove(c);
    		}
    	}
    }
    
    private boolean existsButtonThatMatches(String regex) {
    	for (Component c : this.getComponents()) {
    		if (c.getName().matches(regex)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void initializeGui(Board board) throws UnknownHostException, IOException {
    	System.out.println("WORKS");
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
    			}else {
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
    
    private void jumpAgain(String piece) throws IOException {
    	
    	String[] pos = piece.split(",");
    	int x = Integer.parseInt(pos[0]);
    	int y = Integer.parseInt(pos[1]);
		pieceClicked = true;
		lastMoveWasJump = true;
		
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
    	
    	if (!existsButtonThatMatches("^j.*")) {
    		//controlsRed = !controlsRed;//comment out this line and uncomment the following once networking is implemented
        	//yourTurn = false;
        	endTurn();
        	return;
	    }
    	else {
    		//controlsRed = !controlsRed;//comment out this line and uncomment the following once networking is implemented
    		endTurn();
    	}
    }
    private void clickPiece(String piece) throws IOException {

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
    
    private void makeMove(String move, boolean isJumpMove) throws IOException {
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
    
    private void endTurn() throws IOException {
    	if(lastMoveWasJump) {
    		pieceClicked = false;
    		lastMoveWasJump = false;
    		return;
    	}
    	pieceClicked = false;
    	controlsRed = !controlsRed;//comment out this line and uncomment the following once networking is implemented
    	//yourTurn = false;
    	System.out.println("end turn\n"+controlsRed+" lastMoveWasJump: "+lastMoveWasJump);
    	//System.out.println(is.available());
    	repaint();

    	// no clue what should go here
    }
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	System.out.println(command);
    	String response;

				//p = (Piece[][]) is.readObject();
				//p = (Piece[][]) is.readObject();
				//System.out.println(p);
		    	if (command.startsWith("p")) {
		    		// in format "p(x),(y)"
		    		try {
						clickPiece(command.replace('p', ' ').trim());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	} else if (command.startsWith("m")) {
		    		// in format "m(currentX),(currentY)>(newX),(newY)"
		    		try {
						makeMove(command.replace('m', ' ').trim(), false);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					


		    	} else if (command.startsWith("j")) {
		    		// in format "j(currentX),(currentY)>(newX),(newY)"
		    		try {
						makeMove(command.replace('j', ' ').trim(), true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
    		
    	}


    
    
    public void play() throws IOException
    {
    	String response;
    	
    	while(true)
    	{
    		response = in.readLine();
    		System.out.println(response);
    		if(response.startsWith("VALIDM"))
    		{
    			//b.actionPerformed();
    			repaint();
    		}
    		else if (response.startsWith("VALIDP"))
    		{
    			
    		}
    		//out.println(response);
    		//System.out.println(response);
    	}
    	
    }
    
    public void log() throws IOException, ClassNotFoundException
    {
    		//pieces = piece;
//    		if(is.available()!=0)
//    		{
//    			pieces = (Piece[][]) is.readObject();
//    			repaint();
//    		}
    	
    	
//    	response = in.readLine();
//
//    	if(response.startsWith("PASS"))
//    	{
//    		
//    		l.cPass = response;
//    		l.confirmPass();
//    		System.out.println("WORKS");
//    		if(response.equals("VALIDM"))
//    		{
//    			
//    		}
//    		//play();
//    	}
//    	else if(response.startsWith("p"))
//    	{
//    		System.out.println(response);
//    		clickPiece(response);
//    	}
//    	else if (response.startsWith("m"))
//    	{
//    		makeMove(response, false);
//    	}
//    	else if (response.startsWith("j"))
//    	{
//    		makeMove(response, true);
//    	}
    	
    	
//    	while(true)
//    	{
//    		response = in.readLine();
//    		if(response.startsWith("PASS"))
//    		{
//    			
//    		}
//    		out.println(response);
//    		//System.out.println(response);
//    	}
    }
    
    public void playAgain()
    {
    	
    }
    public synchronized void run()
    {
    	while(true)
    	{
    		System.out.println("hello");
    		Piece[][] pieces;
			try {
				System.out.println(this.pieces);
				pieces = (Piece[][]) is.readObject();
	    		this.pieces = pieces;
	    		repaint();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    }

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		l.Screen();
		System.out.println(server);
		//Board.initializeGui();
	}
}