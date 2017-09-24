package 红包;

import java.net.*;
import java.io.*;

public class Client {
	private Socket s = null;
	private DataInputStream dis = null;
	
	public static void main(String[] args)
	{
		new Client().connect();
	}
	public void connect()
	{
		try {
			s = new Socket("localhost",8888);
			System.out.println("连接成功");
			dis = new DataInputStream(s.getInputStream());
			String msg = dis.readUTF();
			System.out.println(msg);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			disconnect();
		}
	}
	public void disconnect()
	{
		try {
			if(dis != null)
			{
				dis.close();
			}
			if(s != null)
			{
				s.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
