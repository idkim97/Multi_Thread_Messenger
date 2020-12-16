package client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * A multithreaded chat room server. When a client connects the server requests a screen
 * name by sending the client the text "SUBMITNAME", and keeps requesting a name until
 * a unique one is received. After a client submits a unique name, the server acknowledges
 * with "NAMEACCEPTED". Then all messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name. The broadcast messages are prefixed
 * with "MESSAGE".
 *
 * This is just a teaching example so it can be enhanced in many ways, e.g., better
 * logging. Another is to accept a lot of fun commands, like Slack.
 */
public class ChatServer {

	// All client names, so we can check for duplicates upon registration.
	private static Set<String> names = new HashSet<>();

	// The set of all the print writers for all the clients, used for broadcast.
	private static Set<PrintWriter> writers = new HashSet<>();
	
	// whisper 기능을 간단하게 구현하기 위한 hash table.
    private static Hashtable<String, PrintWriter> n =new Hashtable<String, PrintWriter>(); 
    static int userNum=0;// A variable which can save the number of current user(client). 
	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running...");
		ExecutorService pool = Executors.newFixedThreadPool(500); //maximum of client is 500
		try (ServerSocket listener = new ServerSocket(8888)) {
			while (true) {
				pool.execute(new Handler(listener.accept()));
				userNum++; // when client connect to server, userNum +1 ! 
			}
		}
	}

	/**
	 * The client handler task.
	 */
	private static class Handler implements Runnable {
		private String name;
		private Socket socket;
		private Scanner in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket. All the interesting
		 * work is done in the run method. Remember the constructor is called from the
		 * server's main method, so this has to be as short as possible.
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Services this thread's client by repeatedly requesting a screen name until a
		 * unique one has been submitted, then acknowledges the name and registers the
		 * output stream for the client in a global set, then repeatedly gets inputs and
		 * broadcasts them.
		 */
		public void run() {
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
				String ipaddr = socket.getInetAddress().toString(); //서버에 접속한 클라이언트의 주소 확인용

				// Keep requesting a name until we get a unique one.
				  while (true) {
	                    out.println("SUBMITNAME");
	                    name = in.nextLine();
	                    n.put(name,out); //해쉬테이블에 값을 넣어줌.
	                    writers.add(out);
	                    for (PrintWriter writer : writers) {
	                        writer.println("MESSAGE " + name + " has joined");
	                    } // 클라이언트가 들어올때마다 브로드캐스트
				  
				// Now that a successful name has been chosen, add the socket's print writer
				// to the set of all writers so this client can receive broadcast messages.
				// But BEFORE THAT, let everyone else know that the new person has joined!
				out.println("NAMEACCEPTED " + name);
				System.out.println(name);
				
				for (PrintWriter writer : writers) { //새로운 유저가 들어올때, Broadcast, 갱신된 유저 수, 유저 명을 보내준다.
					writer.println("MESSAGE " +clock()+ name + " has joined");
					writer.println("SERVERINFO " + userNum+"명");
					writer.println("USERINFO "+ names);
				} 
				
				writers.add(out); //처음 유저가 들어올때 해당 유저를 writer Set에 추가하고, userNum, userInfo등을 준다.
				out.println("SERVERINFO " + userNum+"명");
				out.println("USERINFO "+ names);
				
				String tempname="";
            	PrintWriter whisper=null; //whisper 기능 추가를 위한 PW
            	
            	int index;
				// Accept messages from this client and broadcast them.
				while (true) {
					String input = in.nextLine();
				/*	if(input.startsWith("EXIT")) {
                        writers.add(out);
        					userNum--; //Decreasing number to 1.
        					names.remove(name); //names hashSet 에서 해당 name 제거
        					n.remove(name,out); //n hashTable에서 해당 name 제거
        					for (PrintWriter writer : writers) { //해당 client가 나간다는 방송과 함께 갱신된 정보를 client들에게 제공
        						writer.println("MESSAGE " + clock()+ name + " has left");
        						writer.println("SERVERINFO " + userNum+"명");
        						writer.println("USERINFO "+ names);
        					}
                            socket.close();                        
                    }*/
					if(input.startsWith("/")){
							if(input.startsWith("/quit")) { //나가기 기능
								return;
							}
							if(input.startsWith("/w ") ||input.startsWith("/W ")) { //귓속말 기능
								index = input.indexOf('w');
								if(index == 1) {
								int start = input.indexOf(" ") + 1;
								int end = input.indexOf(" ", start);
								if(end == -1)// ex) /wasdasdad 이런식으로 끝날때
								{
									out.println("MESSAGE [도움말] 귓속말 기능은 \"/w [사용자이름] [할 말]\" 입니다.");
								}
								else {
								tempname=input.substring(start,end);
								String msg = input.substring(end+1);
								if(!tempname.equals(name)) {// 귓속말을 보내는 상대가 달라야 기능
									if(n.containsKey(tempname)) { // 받는사람 텍스트에리어에 표시 
										whisper=(PrintWriter)n.get(tempname);
										whisper.println("MESSAGE "+ clock() + "[귓속말] " +"["+  name+"]"+"님으로부터 :" + msg);
										if(n.containsKey(name)) { //보내는사람 텍스트에리어에 표시
											whisper=(PrintWriter)n.get(name);
											whisper.println("MESSAGE " + clock()+ "[귓속말] " +  "["+tempname+"]"+"님에게 :" + msg );
										}
									}
									else { // 해당사용자가 없을시
										out.println("MESSAGE "+ clock()+"[알림]["+tempname +"] 해당 사용자는 없습니다.");
									}
								}else { // 자기자신에게 귓속말을 보냈을시
									out.println("MESSAGE "+"[도움말] 자기자신에게 귓속말을 보낼 수 없습니다.");
								}	
								}
							}
							}
							else {//   /로 시작하는데 두번째 character가 'w'가 아닐 경우.
									out.println("MESSAGE [도움말] 귓속말 기능은 \"/w [사용자이름] [할 말]\" 입니다.");
							}
						}
                    else //   '/'로 시작하지 않는 경우 메세지로 간주한다.
                    {
                    	if(!input.startsWith("EXIT"))
                    	for (PrintWriter writer : writers) {
                    		writer.println("MESSAGE "+ clock() +"["+ name+"]" + ": " + input);
					}
                    }
				}
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
				if (out != null) {
					writers.remove(out); // out
				}
				if (name != null) { //when client out server.
					System.out.println(clock()+name + " is leaving");
					userNum--; //Decreasing number to 1.
					names.remove(name); //names hashSet 에서 해당 name 제거
					n.remove(name,out); //n hashTable에서 해당 name 제거
					for (PrintWriter writer : writers) { //해당 client가 나간다는 방송과 함께 갱신된 정보를 client들에게 제공
						writer.println("MESSAGE " + clock()+ name + " has left");
						writer.println("SERVERINFO " + userNum+"명");
						writer.println("USERINFO "+ names);
					}
				}
				try { socket.close(); } catch (IOException e) {}
			}
		}
	}
 /*현재 시각을 알기위해 선언*/
	static String clock() {
		Date dtime = new Date();
		String time = new SimpleDateFormat("[HH:mm:ss]").format(dtime);
		return time;
	}
}