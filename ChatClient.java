package CHAT;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.SocketException;
import java.io.*;

public class ChatClient extends Frame{
	
	TextField tf = new TextField();
	TextArea ta = new TextArea();
	private Socket s = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private boolean connected = false;
	Thread tRecv = new Thread(new RecvThread());
	
	public void lauchFrame()
	{
		this.setLocation(400,300);
		this.setSize(600,500);
		add(tf,BorderLayout.SOUTH);
		add(ta,BorderLayout.NORTH);
		pack();                   //消除空白部分
		//关闭窗口
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				disconnect();    //关闭窗口时回收资源
				System.exit(0);
			}
			
		});
		tf.addActionListener(new TFListner());
		connect();
		this.setVisible(true);
	}
	/*enter发送信息*/
	private class TFListner implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msg = tf.getText();
			//ta.setText(ta.getText() + "\n" + msg);
			tf.setText("");
			send(msg);
		}
		
	}
	/*连接到服务器*/
	public void connect()
	{
		try {
			s = new Socket("localHost", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			System.out.println("连接成功");
			connected = true;
			tRecv.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*发信息*/
	public void send(String msg)
	{
		try {
			dos.writeUTF(msg);
			dos.flush();
		}catch(SocketException e)
		{
			
		}catch(EOFException e)
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/*断开连接并回收资源*/
	public void disconnect()
	{
		try {
			dos.close();
			dis.close();
			s.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		/*try {
			connected = false;
			tRecv.join();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			try {
			dos.close();
			dis.close();
			s.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		*/
		
	}
	public class RecvThread implements Runnable{

		@Override
		public void run() {
			try {
				while (connected) {
					String str = dis.readUTF();
					//System.out.println(str);
					ta.setText(ta.getText() + "\n" +str);
				}
			}
			catch(EOFException e)
			{
				System.out.println("拜拜");
			}
			catch(SocketException e)
			{
				System.out.println("退出了");
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatClient().lauchFrame();
	}

}
