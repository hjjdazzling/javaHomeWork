package TCPDocument;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

	private Socket client;
	private DataOutputStream dos;
	private FileInputStream input;

	public static void main(String[] args) {
		new TCPClient().connect();
	}
	
	public void connect() {
		try {
			client = new Socket("localhost", 8888);
			dos = new DataOutputStream(client.getOutputStream());
			send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String Document() {	
		 Scanner in = new Scanner(System.in); System.out.print("请输入文件路径:"); 
		 String pathName = in.nextLine();
		 return pathName;
	}

	public void send() {
		try {
			String pathName = Document();
			File file = new File(pathName);
			int i = 1;

			if (file.exists()) {
				input = new FileInputStream(file);
				do {
					byte[] data = new byte[1024];
					i = input.read(data);
					String str = new String(data);
					dos.writeUTF(str);
					//System.out.println(str);
					dos.flush();
				} while (i > 0);
			} else {
				System.out.println("目标文件不存在");
				send();
			}
			System.out.println("传输完毕");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void disconnect() {
		try {
			if (dos != null) {
				dos.close();
			}
			if (client != null) {
				client.close();
			}
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
