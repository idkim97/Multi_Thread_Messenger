package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigRead
{
	private String ip;
	private String portS;
	private int port;
	
	ConfigRead() // 기본 생성자
	{
		ip = "localhost";
		portS = "59001";
		port = 59001;
	}
	
	public void readFile()
	{
		try
		{
			String filename = "config.txt";
			
			Scanner scanner = new Scanner(new File(filename));
			//첫줄은 ip주소, 두번째줄은 포트 넘버로 사용한다
			
			ip = scanner.next();
			portS = scanner.next();
			port = Integer.parseInt(portS);

		}
		catch(FileNotFoundException e) // 파일이 없을 경우 기본값 사용
		{
			ip = "localhost";
			portS = "59001";
			port = 59001;
		}
	}
	
	// ClientMain 에서 ip를 사용하기 위한 getter
	String getIp()
	{
		return ip; 
	}
	
	// ClientMain 에서 port를 사용하기 위한 getter
	int getPort()
	{
		return port; 
	}

}
