package client;

import javax.swing.JFrame;

public class ClientMain
{
	/*메인 메소드*/
	public static void main(String[] args) throws Exception
	{
		/* ConfigRead.java로 부터 서버 Ip, Port 읽어오기*/
		ConfigRead readServerInfo = new ConfigRead();
		readServerInfo.readFile();
		String serverAddress = readServerInfo.getIp();
		int serverPort = readServerInfo.getPort();
		
		/*로그인창 GUI 실행*/
		ClientGUI_Main client = new ClientGUI_Main(serverAddress, serverPort);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setVisible(true);
		client.run();
	}
}