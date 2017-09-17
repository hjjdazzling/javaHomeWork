package TCP;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class TCPClient {


	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.print("请输入要连接的端口号:");
		int number = in.nextInt();
		Socket client = new Socket("localhost",number);
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		String str = "";
		while(str != "再见")
		{
		System.out.println("请输入要发送的信息");
		Scanner input = new Scanner(System.in);
		str = input.nextLine();
		dos.writeUTF(str);
		}
		client.close();
	}

}
