package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import checkers.Board;
import checkers.Piece;

public class Client {
    
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
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		l.Screen();
		System.out.println(server);
		//Board.initializeGui();
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
				//System.out.println(this.pieces);
				pieces = (Piece[][]) is.readObject();
	    		//this.pieces = pieces;
	    		//repaint();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
    			//repaint();
    		}
    		else if (response.startsWith("VALIDP"))
    		{
    			
    		}
    		//out.println(response);
    		//System.out.println(response);
    	}
    	
    }
    
    public void start()
    {
    	runner = new Thread();
    	runner.start();
    }
}
