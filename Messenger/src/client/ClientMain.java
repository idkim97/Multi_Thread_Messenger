package client;

import javax.swing.JFrame;
import java.io.IOException;

public class ClientMain
{
	/*���� �޼ҵ�*/
	public static void main(String[] args) throws Exception
	{
		/* ConfigRead.java�� ���� ���� Ip, Port �о����*/
		ConfigRead readServerInfo = new ConfigRead();
		readServerInfo.readFile();
		String serverAddress = readServerInfo.getIp();
		int serverPort = readServerInfo.getPort();
		
		/* ����� 1�� ���� ������ ���� �� UI */
		ClientGUI_Main client = new ClientGUI_Main(serverAddress, serverPort);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setVisible(true);
		Thread clientThread1 = new Thread(() -> {
			try{
				client.run();
			} catch (IOException e){
				e.printStackTrace();
			}
		});
		clientThread1.start();


		/* ����� 2�� ���� ������ ���� �� UI */
		ClientGUI_Main client2 = new ClientGUI_Main(serverAddress, serverPort);
		client2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client2.setVisible(true);
		Thread clientThread2 = new Thread(() -> {
			try{
				client2.run();
			} catch (IOException e){
				e.printStackTrace();
			}
		});
		clientThread2.start();
	}
}