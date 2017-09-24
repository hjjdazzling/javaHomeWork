package 红包;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private ServerSocket ss = null;
	private Socket s = null;
	private boolean started = false;
	private static String[] money;
	private static int number;
	private int count = 0;

	public static void main(String[] args) {
		Server s = new Server();
		s.money();
		s.bind();
	}

	public void money()
	{
		Scanner in = new Scanner(System.in);
		System.out.println("请输入红包数");
		number = in.nextInt();
		System.out.println("请输入钱数");
		double m = in.nextDouble();
		money = new String[number];
		double h = m / number;
		/*平均主义*/
		for(int i=0; i<money.length; i++)
		{
			
			money[i] = String.valueOf(h); 
		}
	}
	public void bind() {
		try {
			ss = new ServerSocket(8888);
			started = true;
			while (started) {
				Socket s = ss.accept();
				Client c = new Client(s);
				new Thread(c).start();
			}
		} catch(BindException e)
		{
			System.out.println("端口已被占用");
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public class Client implements Runnable {
		private Socket s = null;
		private DataOutputStream dos = null;
		private DataInputStream dis = null;

		public Client(Socket s) {
			this.s = s;
			try {
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			Client c = null;
			try {
				if (count < money.length) {
					send(new String(money[count]));
					count++;
				} else {
					send("红包已被抢完");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		public void disconnect() {
			try {
				if (dis != null) {
					dis.close();
				}
				if (dos != null) {
					dos.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
