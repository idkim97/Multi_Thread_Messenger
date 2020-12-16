package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class User
{
	 public static HashMap<String, PrintWriter> participants = new HashMap<>();
	 public static HashMap<String, Socket> participants_socket = new HashMap<>(); 
	 public static HashMap<String, String> login_time = new HashMap<>(); 
	 public static HashMap<String, String> logout_time = new HashMap<>();
	 public static HashMap<String, String> status_message = new HashMap<>(); 
	 
	 public static void add_participants(String id, PrintWriter out)
	 {
		 participants.put(id, out);
	 }
	 
	 public static void add_socket(String id, Socket socket)
	 {
		 participants_socket.put(id, socket);
	 }
	 
	 public static void add_loginTime(String id, String loginTime)
	 {
		 login_time.put(id, loginTime);
	 }
	 
	 public static void add_logoutTime(String id, String logoutTime)
	 {
		 logout_time.put(id, logoutTime);
	 }
	 
	 public static void add_statusMessage(String id, String statusMessage)
	 {
		 status_message.put(id, statusMessage);
	 }
	 
		//해쉬맵에서 value로 key를 찾기 위한 메소드
		public static Object getKey(HashMap<String, PrintWriter>in, Object value)
		{
			for (Object o : in.keySet())
			{
				if (in.get(o).equals(value))
				{
					return o;
				}
			}
			return null;
		}
		
	public static boolean isOnline(HashMap<String, PrintWriter>map, String id)
	{
		if (map.containsKey(id))
		{
			return true;
		}
		return false;
	}
		
}
