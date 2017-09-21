package TCPDocument;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
	private Socket client;
	private ServerSocket serverSocket;
	private DataInputStream dis;
	private FileOutputStream output;
	private boolean started = false;

	public static void main(String[] args) {
		new TCPServer().link();
	}
	
	public void link() {
		try {
			serverSocket = new ServerSocket(8888);
			System.out.println("创建服务器成功,等待连接");
			client = serverSocket.accept();
			System.out.println("客户连接成功");
			started = true;
			receive();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String Document() {
		Scanner in = new Scanner(System.in);
		System.out.print("请输入文件路径:");
		String pathName = in.nextLine();
		return pathName;
	}

	public void receive() {
		String pathName = Document();
		File file = new File(pathName);

		try {
			dis = new DataInputStream(client.getInputStream());
			
			if (!file.exists()) {
				
				if (file.createNewFile()) {
					output = new FileOutputStream(file);
						while (started) {
							byte[] data = new byte[1024];
							String str = dis.readUTF();	
							data = str.getBytes();
							output.write(data);
							//System.out.println(str);
						}
				}
			} else {
				System.out.println("复制路径已存在相同文件名");
				System.out.println("传输失败");
			}
			System.out.println("传输完毕");
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	public void disconnect() {
		try {
			if (output != null) {
				output.close();
			}
			if(dis != null)
			{
				dis.close();
			}
			if(client != null)
			{
				client.close();
			}
			if(serverSocket != null)
			{
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
