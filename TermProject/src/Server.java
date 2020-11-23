import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

	private static HashSet<String> names = new HashSet<String>();

	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	private static Hashtable<String, PrintWriter> n =new Hashtable<String, PrintWriter>();

 
	public static void main(String[] args) throws Exception{
		System.out.println("The chat server is running.");
		ExecutorService pool = Executors.newFixedThreadPool(50); // 50개의 클라이언트 수용가능
		ServerSocket listener = new ServerSocket(9997);
		try {
			while (true) {
				pool.execute(new Handler(listener.accept())); // 쓰레드를 통해 여러개의 클라이언트 동시지원
			}
		} finally {
			listener.close();
		}
	}

	private static class Handler extends Thread{
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;


		public Handler(Socket socket) {
			this.socket = socket;
		}


		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				while (true) {
					out.println("SUBMITNAME");
					name = in.readLine();
					n.put(name,out); //해쉬테이블에 값을 넣어줌.
					writers.add(out);
					for (PrintWriter writer : writers) {
						writer.println("MESSAGE " + name + " has joined");
					} // 클라이언트가 들어올때마다 브로드캐스트



					out.println("NAMEACCEPTED" + name);
					writers.add(out);

					if (name == null) {
						return;
					}
					synchronized (names) { // 동기화
						if (!names.contains(name)) {
							names.add(name);
							break;
						}
					}
				}

				String tempname="";
				PrintWriter whisper=null;


				while (true) {
					String input = in.readLine();
					if (input == null) {
						return;
					}

					if (input.toLowerCase().startsWith("/quit")) {
						return; // quit하면 클라이언트 연결종료.
					}
					else if(input.startsWith("/w")) // 귓속말기능 /w 사람 + 할말
					{
						int start = input.indexOf(" ") + 1;
						int end = input.indexOf(" ", start);
						tempname=input.substring(start,end);
						String msg = input.substring(end+1);


						if(n.containsKey(tempname)) // 받는사람 텍스트에리어에 표시
							whisper=(PrintWriter) n.get(tempname);
						whisper.println("MESSAGE " + "[귓속말] " +  name + ": " + msg);

						if(n.containsKey(name)) //보내는사람 텍스트 에리어에 표시
							whisper=(PrintWriter) n.get(name);
						whisper.println("MESSAGE " + "[귓속말] " +  name + ": " + msg);
					}
					else
					{

						for (PrintWriter writer : writers) {
							writer.println("MESSAGE " + name + ": " + input);
						} // 모든사람에게 텍스트표시
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {

				writers.add(out);
				for (PrintWriter writer : writers) {
					writer.println("MESSAGE " + name + " has left");
				} // 클라이언트가 종료했을때 브로드캐스트 해줌
				if (name != null) {
					names.remove(name);
					n.remove(name,out);
				}
				if (out != null) {
					writers.remove(out);
				}

				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}



