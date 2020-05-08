package rcv1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Auth extends Thread{
	private Socket socket;
	private String user;
	private String pass = "test";
	BufferedReader in;
	PrintWriter out;
	public Auth(Socket socket, String user) throws IOException
	{
		this.socket = socket;
		this.user = user;
		storePass();
		System.out.println(pass);
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("PASS " + pass);
			out.println("Waiting for opponent");
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
				String response = in.readLine();
				//System.out.println(response);
				out.println(response);


			}
			
		} catch (IOException e) {
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
