package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
	
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    protected static Login l = new Login();
    
    public Client(String server, int PORT) throws UnknownHostException, IOException
    {
    	socket = new Socket(server, PORT);
    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void play() throws IOException
    {
    	String response;
    	
    	while(true)
    	{
    		response = in.readLine();
    		if(response.startsWith("PASS"))
    		{
    			System.out.println("True");
    		}
    		out.println(response);
    		//System.out.println(response);
    	}
    	
    }
    
    public void log() throws IOException
    {
    	String response;
    	
    	response = in.readLine();
    	if(l.user=='h')
    	{
    	}
    	else if(response.startsWith("PASS"))
    	{
    		
    		l.cPass = response;
    		l.confirmPass();
    	}
    	
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

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		l.Screen();
	}

}
