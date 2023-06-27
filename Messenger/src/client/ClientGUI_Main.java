package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public class ClientGUI_Main extends JFrame
{
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtPassword;
	private JTextField idField;
	private JPasswordField passwordField;
	
	private String serverAddress;
	private int serverPort;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private static String id = "";
	private static String lastSenderId="";
	private String password = "";
	
	
	public void run() throws IOException
	{
		try
		{
			socket = new Socket(serverAddress, serverPort); // 불러온 서버주소, port로 연결 요청
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

			
            JFrame frame = new JFrame("전체 채팅");
    		JTextField textField = new JTextField(50);
    		JTextArea messageArea = new JTextArea(16, 50);
    		JPanel contentPane;
    		frame.getContentPane().add(textField, BorderLayout.SOUTH);
    		frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
    	    frame.pack();


			// 채팅 입력시 MESSAGE id textField.getText() 개행
    	    textField.addActionListener(e -> {
				out.println("MESSAGE " + id + " " + textField.getText() + "\n");
				messageArea.append(textField.getText() + "\n");
				textField.setText("");
			});
    	    
    	    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setVisible(false);





            JFrame frame1 = new JFrame("개인채팅");
    		JTextField textField1 = new JTextField(50);
    		JTextArea messageArea1 = new JTextArea(16, 50);
    		JPanel contentPane1;
    		frame1.getContentPane().add(textField1, BorderLayout.SOUTH);
    		frame1.getContentPane().add(new JScrollPane(messageArea1), BorderLayout.CENTER);
    	    frame1.pack();


    	    textField1.addActionListener(e -> {
				String with = frame1.getName();
				String[] name = with.split(" ");
				// 1대1채팅시 채팅입력하면 CHAT2 + id + name[1] + 입력한메세지
				out.println("CHAT2 " + id + " " + name[1] + " " + textField1.getText());
				messageArea1.append(textField1.getText() + "\n");
				textField1.setText("");
			});
    	    frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame1.setVisible(false);










            while (in.hasNextLine())
            {
            	String line = in.nextLine();
				System.out.println(line);
            	
            	/*CONNECTED로 시작하는 문자열을 받으면
            	 * 서버와 연결되었다는 다이얼로그를 띄운 뒤
            	 * CONNECTED와 클라이언트의 IP주소를 합쳐서 문자열로 보냄*/
            	if (line.startsWith("CONNECTED"))
            	{
            		JOptionPane.showMessageDialog(null, "Connected with server");
            		out.println("CONNECETED " + InetAddress.getLocalHost().getHostAddress());
            	}
            	
            	/*LOGINOK로 시작하는 문자열을 받으면
            	 * 로그인 성공 + 클라이언트의 ID를 다이얼로그로 띄움
            	 * 실패하였을 시는 아이디 또는 비밀번호를 다시 확인하라는 다이얼로그를 띄움*/
            	if (line.startsWith("LOGINOK"))
            	{
            		String[] getFriend = line.split(" ");
            		ClientGUI_List2 list = new ClientGUI_List2(getFriend, socket, in, out);
            		list.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            		list.setVisible(true);
            		setVisible(false);
            		
            	}
            	else if (line.startsWith("LOGINFAIL"))
            	{
            		JOptionPane.showMessageDialog(null, "Login Failed. Check Id or Password ");
            	}
            	
            	/*FINDPWOK로 시작하는 문자열을 받으면
            	 * 서버에서 받아온 문자열을 파싱하여 
            	 * 비밀번호 찾기 성공이라는 문자열과 비밀번호를 다이얼로그로 띄움
            	 * 실패시는 아이디나 이메일을 확인하라는 다이얼로그를 띄움*/
            	if (line.startsWith("FINDPWOK"))
            	{
            		String[] getPW = line.split(" ");
            		String myPW = getPW[1];
            		try
            		{
						myPW = SimpleCrypto.decrypt(myPW);
					}
            		catch (Exception e)
            		{
						e.printStackTrace();
					}
            		JOptionPane.showMessageDialog(null, "Find Password Succeced. Password: " + myPW);
            	}
            	else if (line.startsWith("FINDPWFAIL"))
            	{
            		JOptionPane.showMessageDialog(null, "Find Password Failed. Check Id or Email ");
            	}
            	
            	if (line.startsWith("SEARCHRESULT"))
            	{

            		String[] who = line.split(" ");
            		String who1 = who[1];
            		JOptionPane.showMessageDialog(null, "사용자 " + who1 + "을(를) 찾았습니다.");
            	}
            	
            	if (line.startsWith("INFOS"))
            	{
            		String[] infos = line.split(" ");
            		String status = infos[1];
            		String time = infos[2] + " " + infos[3];
            		String isOnline = infos[4];
            		JOptionPane.showMessageDialog(null, "상태메세지 : " + status + "\n" + "최근 접속시간 : " + time + "\n" + "상태 : " + isOnline);
            	}


            	if (line.startsWith("ENTERALL"))
            	{
            		 frame.setVisible(true);
            	}
				
				// 전체 채팅방에 접속해서 메세지 뿌릴때
				if (line.startsWith("MESSAGEFROM")) {
					String[] input = line.split(" ");
					String senderId = input[1];
					StringBuffer message = new StringBuffer();
					for (int i = 2; i < input.length; i++) {
						message.append(input[i]).append(" ");
					}
					System.out.println(message);


					messageArea.append("From " + senderId + " :" + message + "\n");



				}
            	
            	if (line.startsWith("OPEN"))
            	{
            		String[] messages = line.split(" ");
            		String id1 = messages[1];
            		String id2 = messages[2];
            		int result = JOptionPane.showConfirmDialog(null, id1 + "와 채팅 하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
            		
            		if (result == JOptionPane.YES_OPTION)
            		{
            			frame1.setName("With " + id1);
            			frame1.setVisible(true);
            			messageArea1.append("Chat with " + id1 + "\n");
            			out.println("CHATOK " + id1 + " " + id2);
            		}
            		else
            		{
            			out.println("CHATNO "+ id1 + " " + id2);
            		}
            	}
            	
            	if (line.startsWith("FOPENF"))
            	{
            		String[] messages = line.split(" ");
            		String id1 = messages[1];
            		frame1.setName("With " + id1);
            		frame1.setVisible(true);
            		messageArea1.append("Waiting for " + id1 + "\n");
            	}
            	
            	if (line.startsWith("CHAT2NO") || line.startsWith("CHAT2OK"))
            	{
            		String[] input = line.split(" ");
            		String id1 = input[1];
            		String id2 = input[2];
            		String stat = input[3];
            		
            		if (line.startsWith("CHAT2NO"))
            		{
            			messageArea1.append(id1 + id2 + " Chat room rejected");
            		}
            		if (line.startsWith("CHAT2OK"))
            		{
            			messageArea1.append(id2 + " Entered" + "\n");
            		}
            	}
            	
            	if (line.startsWith("CHAT2WITH"))
            	{
					// 여기서 이렇게 처리하면 CHAT2WITH이랑 id는 잘 가져오지만
					// 뒤에 메세지도 split처리되어서 잘려서 안들어가잖아
					// 앞에 두개만 split으로 자르고
					// 뒤에 문자열은 for문돌려서 문자열 끝까지 가져와야돼
					String[] input = line.split(" ");
					String from = input[1];
					StringBuffer message = new StringBuffer();
					for (int i = 2; i < input.length; i++) {
						message.append(input[i]).append(" ");
					}
					System.out.println(message);
					messageArea1.append("From " + from + " : " + message.toString() + "\n");
            	}
            }
		}
		finally
		{
			setVisible(false);
			dispose();
		}
	}





	// 실행시 가장 처음 뜨는 UI
	// 로그인 화면
	public ClientGUI_Main(String serverAddress, int serverPort)
	{
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

		setTitle("Messenger-Client-Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtId = new JTextField();
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setToolTipText("Enter the ID");
		txtId.setEditable(false);
		txtId.setText("ID:");
		txtId.setBounds(12, 33, 76, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setToolTipText("Enter the Password");
		txtPassword.setEditable(false);
		txtPassword.setText("Password:");
		txtPassword.setBounds(12, 64, 76, 21);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		idField = new JTextField();
		idField.setToolTipText("Enter the ID");
		idField.setBounds(103, 33, 183, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Enter the Password");
		passwordField.setEchoChar('*');
		passwordField.setBounds(103, 64, 183, 21);
		contentPane.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(285, 95, 70, 31);
		contentPane.add(loginButton);
		
		JButton findPasswordButton = new JButton("Forgot your password?");
		findPasswordButton.setBounds(12, 143, 170, 23);
		contentPane.add(findPasswordButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(258, 143, 97, 23);
		contentPane.add(registerButton);
		
		/*로그인 버튼 이벤트
		 * idField와 passwordField에 입력된 값을 읽어와서
		 * LOGIN이라는 문자열과 함께 서버로 보냄*/
		
		loginButton.addActionListener(e -> {
			id = idField.getText();
			password = String.valueOf(passwordField.getPassword());
			try {
				// 회원가입할때 암호화한 비밀번호와 일치하는지 확인하기 위해 같은방식으로 암호화
				password = SimpleCrypto.encrypt(password);	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 서버에 LOGIN + id + password 형식으로 데이터 전달
			out.println("LOGIN " + id + " " + password);
		});


		
		/*비밀번호 찾기 버튼 이벤트
		 * 비밀번호 찾기 GUI를 띄움 - ClientGUI_FindPassword.java*/
		findPasswordButton.addActionListener(e -> {
			ClientGUI_FindPassword findPW = new ClientGUI_FindPassword(socket, in, out);
			findPW.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			findPW.setVisible(true);
		});
		
		/*회원가입 버튼 이벤트
		 * 회원가입 GUI를 띄움 - ClientGUI_Register.java*/
		registerButton.addActionListener(e -> {
			ClientGUI_Register register = new ClientGUI_Register(socket, in, out);
			register.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			register.setVisible(true);
		});
	}
	public static String getId()
	{
		return id;
	}
}
