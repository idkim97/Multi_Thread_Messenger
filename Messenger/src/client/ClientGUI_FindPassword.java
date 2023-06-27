package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ClientGUI_FindPassword extends JFrame
{

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtEmail;
	private JTextField idField;
	private JTextField emailField;
	private JButton btnFindPassword;
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String id = "";
	private String email = "";
	
	public ClientGUI_FindPassword(Socket socket, Scanner in, PrintWriter out)
	{
		this.socket = socket;
		this.in = in;
		this.out = out;
		
		/*GUI*/
		setTitle("Find Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 328, 169);
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
		
		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setToolTipText("Enter the email");
		txtEmail.setEditable(false);
		txtEmail.setText("Email:");
		txtEmail.setBounds(12, 64, 76, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		idField = new JTextField();
		idField.setToolTipText("Enter the ID");
		idField.setBounds(103, 33, 183, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setToolTipText("Enter the email");
		emailField.setColumns(10);
		emailField.setBounds(103, 64, 183, 21);
		contentPane.add(emailField);
		
		btnFindPassword = new JButton("Find Password");
		btnFindPassword.setBounds(91, 97, 137, 23);
		contentPane.add(btnFindPassword);
		
		/*비밀번호 찾기 버튼 이벤트
		 * idField와 emailField의 값을 읽어온 뒤
		 * FINDPW라는 문자열과 id, email 값을 합쳐서 서버로 보냄*/
		btnFindPassword.addActionListener(e -> {
			id = idField.getText();
			email = emailField.getText();
			out.println("FINDPW " + id + " " + email);
			setVisible(false);
		});
	}
}
