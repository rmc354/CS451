package network;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import checkers.Board;

public class Login implements ActionListener{

	private JFrame frame = new JFrame("Login");
	
    private JPanel panel = new JPanel();
    //private JLabel l = new JLabel("label");
    private JPanel jPanel = new JPanel();
	public String cPass;
	public char user;
	
	public Login()
	{

	}
	
	public void Screen()
	{
		JLabel check = new JLabel("Checkers");
	    JButton host = new JButton("Host Game");
	    host.setActionCommand("host");
	    host.addActionListener(this);
	    JButton join = new JButton("Join Game");
	    join.setActionCommand("join");
	    join.addActionListener((ActionListener) this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        panel.setLayout(new GridBagLayout());
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        check.setText("Checkers");
        frame.add(panel);
        panel.add(check);
        panel.add(host); 
        panel.add(join);
	}
	
	public void HostGame() throws IOException, ClassNotFoundException
	{
		user = 'h';
		Server s = new Server();
		JFrame hFrame = new JFrame("Host Game"); 
		hFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hFrame.setSize(600, 600);
        hFrame.setVisible(true);
        hFrame.setResizable(false);
        String ip = JOptionPane.showInputDialog(null, "Enter IP Address", JOptionPane.QUESTION_MESSAGE);
        String port = JOptionPane.showInputDialog(null, "Enter Port Number", JOptionPane.QUESTION_MESSAGE);
        //String pass = JOptionPane.showInputDialog(null, "Set Server Password", JOptionPane.QUESTION_MESSAGE);
        //sendPass(pass);
        frame.setVisible(false);
        hFrame.setVisible(false);
        //cPass = s.auth.getPass();
        //Board board = new Board(true, true, ip, Integer.parseInt(port));
        //board.initializeGui(board);
        //board.log();
		
		
	}
	
	public void sendPass(String pass) throws FileNotFoundException, UnsupportedEncodingException
	{
			PrintWriter r = new PrintWriter("src/pass.txt", "UTF-8");
			r.println(pass);
			r.close();			
	}
	
	public void JoinGame() throws UnknownHostException, IOException, ClassNotFoundException
	{

	    JButton j = new JButton("Join");

	    JFrame jFrame = new JFrame("Join Game");
	    j.setActionCommand("j");
	    j.addActionListener((ActionListener) this);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
	    jFrame.add(jPanel);
	    jPanel.add(j);
	    frame.setVisible(false);
        jFrame.setVisible(false);
	    String ip = JOptionPane.showInputDialog(null, "Enter IP Address", JOptionPane.QUESTION_MESSAGE);
	    String port = JOptionPane.showInputDialog(null, "Enter Port Number", JOptionPane.QUESTION_MESSAGE);
        //Board board = new Board(true, true, ip, Integer.parseInt(port));
        //board.initializeGui(board);
        //board.log();
	    //confirmPass();
	   // System.out.println(auth.getPass());
	   // System.out.println(auth.getPort());
	}
	
	public void confirmPass() throws UnknownHostException, IOException
	{
		String[] nPass;
        String splitPass;
        String pass = JOptionPane.showInputDialog(null, "Set Server Password", JOptionPane.QUESTION_MESSAGE);
        System.out.println(cPass);
        if(!pass.equals("PASS "));
        {
         nPass = cPass.split(" ");
         splitPass = nPass[1];
        }
        if (pass.equals(splitPass))
        {
        	System.out.println("True");
        }
        else
        {
        	confirmPass();
        }
	    

	}
	
	public void actionPerformed(ActionEvent e)
	{
		if("host".equals(e.getActionCommand()))
		{
			try {
				HostGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.setVisible(false);
		}
		else if("join".equals(e.getActionCommand()))
		{
			try {
				JoinGame();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.setVisible(false);
		}
		else if ("j".equals(e.getActionCommand()))
		{
			jPanel.setVisible(false);
		}
	}

}
