package CHAT;

import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer {

	static int number;
	private ServerSocket ss = null;
	private DataInputStream dis = null;
	List<Client> clients = new ArrayList<Client>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new ChatServer().start();
	}
	public void start()
	{
		try {
			ss = new ServerSocket(8888);
		} catch (BindException e) {
			System.out.println("端口已被占用");
			System.out.println("请退出相关程序，并重启看服务端");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			boolean started = false;
			started = true;

			while (started) {
				Socket s = ss.accept();
				Client c = new Client(s);
				number++;
				new Thread(c).start();
				clients.add(c);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			try{
				ss.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public class Client implements Runnable {
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean connected = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				connected = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*服务端给客户发信息*/
		public void send(String str)throws IOException
		{
			dos.writeUTF(str);
		}
		public void run() {
			Client c = null;
			try {
				while (connected) {
					String msg = dis.readUTF(); 
					//System.out.println("client" + number + ":" + msg);
					for(int i=0; i<clients.size();i++)
					{
						c = clients.get(i);
							c.send(msg);
						
					}
				}
			} catch(SocketException e)
			{
				clients.remove(this);
				System.out.println("一名客户端退出");
			}
			catch (EOFException e) {
				System.out.println("客户端已关闭");
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					if (dis != null) {
						dis.close();
					}
					if(dos != null)
					{
						dos.close();
					}
					if(s != null)
					{
						s.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		}
	}
}
