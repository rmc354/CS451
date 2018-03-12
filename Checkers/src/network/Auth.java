package network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import checkers.Board;
import checkers.Piece;

public class Auth extends Thread implements Serializable{
	private Socket socket;
	private String user;
	private String pass = "test";
	BufferedReader in;
	PrintWriter out;
    private PrintStream ps;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    
    
	public Auth(Socket socket) throws IOException
	{
		this.socket = socket;
		storePass();
		System.out.println(pass);
		
		try {
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//out = new PrintWriter(socket.getOutputStream(), true);
	    	//ps = new PrintStream(socket.getOutputStream(), true);
	    	//is = new ObjectInputStream(socket.getInputStream());
			OutputStream oos = socket.getOutputStream();
			os = new ObjectOutputStream(oos);
			InputStream iis = socket.getInputStream();
			is = new ObjectInputStream(iis);
			//os.writeObject("PASS " + pass);
			//os.writeObject("Waiting for opponent");
		}
		catch (IOException e)
		{
			System.out.println("Player has left the session");
		}
	}
	public void setPass(String pass)
	{
		this.pass = pass;
	}
	public String getPass()
	{
		return pass;
	}
	public void storePass() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("src/pass.txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        pass = sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public void run()
	{
		try {
			System.out.println("Player logged in");
			while(true) {
				Piece[][] piece = (Piece[][]) is.readObject();
				//Piece[][] piece = (Piece[][]) is.readObject();
				//System.out.println(response);
				//System.out.println(piece);
				System.out.println(piece);
				if(piece.toString().startsWith("["))
				{
					os.writeObject(piece);
				}


			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				socket.close();
			}
			catch(IOException e)
			{
			}
		}
	}
}
