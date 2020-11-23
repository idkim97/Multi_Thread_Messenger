import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener {
	
	// 채팅방 GUI 변수
	private JPanel contentPane = new JPanel(); // 프레임을 제외한 나머지 변수
	private JButton Invite = new JButton("친구초대"); // 친구초대 버튼
	private JButton Exit = new JButton("나가기");		//나가기 버튼
	private JTextArea ChatView = new JTextArea(); // 채팅방 부분
	private JTextArea Chatting = new JTextArea(); // 채팅 입력부분
	private JLabel ChatName = new JLabel("채팅방 이름"); // 채팅방 이름
	private JFrame frame = new JFrame(); //프레임
	
//	private Socket socket;
//	private String ip="";
//	private int port;
//	private InputStream is;
//	private OutputStream os;
//	private DataInputStream dis;
//	private DataOutputStream dos;
//	
	
// 	  그외에 변수
	String serverAddress;
    Scanner in;
    PrintWriter out;
    String names[] = new String[100];
    int i=0;

	// 생성자 메소드
	Client(){
		init();		// 채팅방 화면 구성 메소드
		start();	// 리스너 버튼  
	}
	
	private void start() {
		Invite.addActionListener(this); // 친구초대버튼 리스터
		Exit.addActionListener(this);   // 채팅방 나가기 버튼 리스너
	}
	
	// 채팅방 화면 구성 메소드
	private void init() {

				setTitle("채팅방 이름");

				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 452, 532);
				
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				
				
				//친구초대 GUI
				Invite.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				Invite.setBounds(217, 0, 109, 53);
				contentPane.add(Invite);
				
				
				//채팅방 나가기 GUI
				Exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				Exit.setBounds(325, 0, 109, 53);
				contentPane.add(Exit);
				
				
				//채팅창 GUI
				ChatView.setBounds(0, 53, 434, 374);
				contentPane.add(ChatView);
				
				
				//채팅 입력 GUI
				Chatting.setBounds(0, 432, 434, 53);
				contentPane.add(Chatting);
				
				
				//채팅방 이름 GUI
				ChatName.setHorizontalAlignment(SwingConstants.CENTER);
				ChatName.setBounds(0, 0, 221, 53);
				contentPane.add(ChatName);
				
				this.setVisible(true); // 화면에 보이게하면 true
	}

//	private void Network() {
//		try {
//			socket = new Socket(ip,port);
//			if(socket!=null) { // 소켓이 정상적으로 연결된 경우
//				Connection();
//			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	private void Connection() { // 서버와 연결하는 부분
//		try {
//		is = socket.getInputStream();
//		dis = new DataInputStream(is);
//		
//		os = socket.getOutputStream();
//		dos = new DataOutputStream(os);
//		}
//		catch(IOException e) {
//			
//		}
//	}
	
	// 리스너 버튼 확인
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Invite) {
			System.out.println("친구초대버튼 클릭");
		}
		else if(e.getSource()==Exit) {
			System.out.println("채팅방 나가기 버튼 클릭");
		}
	}
	
	// 서버에 메세지를 보내는 부분
//	private void send_message(String str) {
//		try {
//			dos.writeUTF(str);	//문자열을 받아서 전달
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	

	
	private void run(int port) throws IOException {
        try {
           Socket socket = new Socket(serverAddress, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            
           
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("SUBMITNAME")) {
                    out.println(getName());
                } else if (line.startsWith("NAMEACCEPTED")) {
                    names[i] = line.substring(12);
                    frame.setTitle("Chatter - " + line.substring(12));
                    ChatView.setEditable(true);
                    i++;
  
                    
                } else if (line.startsWith("MESSAGE")) {
                    Chatting.append(line.substring(8) + "\n");
                }
            }  
        }
       finally {
    	   frame.setVisible(false);
    	   frame.dispose();
       }
       
    }
	
	public static void main(String[] args) {
		new Client();
		String st=null; // 아이피주소
	    int port=0; // 포트번호
	    
	    try {
	    	File file = new File("C:\\work");
	        //내 컴퓨터에 저장되있는 serverinfo.dat 읽기  

	        Scanner input = new Scanner(file);
	    	 while(input.hasNext())
	         {
	            st=input.nextLine();
	            port=input.nextInt();
	         }
	    }
	    catch(FileNotFoundException e) // 만약 파일이 찾아지지않을시 기본적으로 연결 
	    {
	       st = "127.0.0.1";
	       port = 9999;
	       System.out.println(e.getMessage());
	    }
		
	}
}
