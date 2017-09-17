package TCP;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {

	public static void main(String[] args)throws Exception
	{
		Scanner in = new Scanner(System.in);
		System.out.print("请输入要绑定的端口号:");
		int number = in.nextInt();
		ServerSocket serverSocket = new ServerSocket(number);
		System.out.println("创建服务器成功,等待连接");
		Socket client;
		while(true)
		{
			client = serverSocket.accept();
			DataInputStream dis = new DataInputStream(client.getInputStream());
			String inputStr = "";
			while (inputStr != "再见") {
				inputStr = dis.readUTF();
				System.out.println(inputStr);
			}
			client.close();
		}
	}
}
