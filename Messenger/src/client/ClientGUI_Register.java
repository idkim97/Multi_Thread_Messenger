package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;


/* 회원가입을 위한 UI 띄우고 서버에 정보 전달 
*  "REGISTER " + id + " " + password + " " + nickName + " " +
	name + " " + email + " " + birth + " " + phoneNumber + " " +
	homepage + " " + additional 의 형태로 데이터 전달해줌
	* */
public class ClientGUI_Register extends JFrame
{

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtPassword;
	private JTextField txtNickName;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtBirth;
	private JTextField txtPhoneNumber;
	private JTextField txtHomepage;
	private JTextField idField;
	private JTextField passwordField;
	private JTextField txtAdditional;
	private JTextField nickNameField;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField birthField;
	private JTextField phoneNumberField;
	private JTextField homepageField;
	private JTextField additionalField;
	private JButton btnRegister;
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String id = "";
	private String password = "";
	private String nickName = "";
	private String name = "";
	private String email = "";
	private String birth = "";
	private String phoneNumber = "";
	private String homepage = "";
	private String additional = "";

	// 회원가입(Register) UI 띄우기
	public ClientGUI_Register(Socket socket, Scanner in, PrintWriter out)
	{
		this.socket = socket;
		this.in = in;
		this.out = out;
		

		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setToolTipText("Enter the ID");
		txtId.setText("ID(*)");
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setColumns(10);
		txtId.setBounds(12, 29, 116, 21);
		contentPane.add(txtId);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password(*)");
		txtPassword.setEditable(false);
		txtPassword.setToolTipText("Enter the Password");
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setColumns(10);
		txtPassword.setBounds(12, 60, 116, 21);
		contentPane.add(txtPassword);
		
		txtNickName = new JTextField();
		txtNickName.setEditable(false);
		txtNickName.setToolTipText("Enter the Nickname");
		txtNickName.setText("Nickname(*)");
		txtNickName.setHorizontalAlignment(SwingConstants.CENTER);
		txtNickName.setColumns(10);
		txtNickName.setBounds(12, 91, 116, 21);
		contentPane.add(txtNickName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setToolTipText("Enter the Name");
		txtName.setText("Name(*)");
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setColumns(10);
		txtName.setBounds(12, 122, 116, 21);
		contentPane.add(txtName);
		
		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setToolTipText("Enter the Email");
		txtEmail.setText("E-mail(*)");
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setColumns(10);
		txtEmail.setBounds(12, 153, 116, 21);
		contentPane.add(txtEmail);
		
		txtBirth = new JTextField();
		txtBirth.setEditable(false);
		txtBirth.setToolTipText("Enter the Birth");
		txtBirth.setText("Birth (YYMMDD)(*)");
		txtBirth.setHorizontalAlignment(SwingConstants.CENTER);
		txtBirth.setColumns(10);
		txtBirth.setBounds(12, 184, 116, 21);
		contentPane.add(txtBirth);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setEditable(false);
		txtPhoneNumber.setToolTipText("Enter the Phone number");
		txtPhoneNumber.setText("Phone number");
		txtPhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(12, 215, 116, 21);
		contentPane.add(txtPhoneNumber);
		
		txtHomepage = new JTextField();
		txtHomepage.setEditable(false);
		txtHomepage.setToolTipText("Enter the Homepage");
		txtHomepage.setText("Homepage");
		txtHomepage.setHorizontalAlignment(SwingConstants.CENTER);
		txtHomepage.setColumns(10);
		txtHomepage.setBounds(12, 246, 116, 21);
		contentPane.add(txtHomepage);
		
		txtAdditional = new JTextField();
		txtAdditional.setToolTipText("Enter the Additional info");
		txtAdditional.setText("Additional");
		txtAdditional.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdditional.setEditable(false);
		txtAdditional.setColumns(10);
		txtAdditional.setBounds(12, 277, 116, 21);
		contentPane.add(txtAdditional);




		/* 정보 입력 텍스트필드*/
		idField = new JTextField();
		idField.setToolTipText("Enter the ID");
		idField.setBounds(169, 29, 239, 21);
		contentPane.add(idField);
		idField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setToolTipText("Enter the Password");
		passwordField.setColumns(10);
		passwordField.setBounds(169, 60, 239, 21);
		contentPane.add(passwordField);
		
		nickNameField = new JTextField();
		nickNameField.setToolTipText("Enter the Nickname");
		nickNameField.setColumns(10);
		nickNameField.setBounds(169, 91, 239, 21);
		contentPane.add(nickNameField);
		
		nameField = new JTextField();
		nameField.setToolTipText("Enter the Name");
		nameField.setColumns(10);
		nameField.setBounds(169, 122, 239, 21);
		contentPane.add(nameField);
		
		emailField = new JTextField();
		emailField.setToolTipText("Enter the Email");
		emailField.setColumns(10);
		emailField.setBounds(169, 153, 239, 21);
		contentPane.add(emailField);
		
		birthField = new JTextField();
		birthField.setToolTipText("Enter the Birth");
		birthField.setColumns(10);
		birthField.setBounds(169, 184, 239, 21);
		contentPane.add(birthField);
		
		phoneNumberField = new JTextField();
		phoneNumberField.setToolTipText("Enter the Phone number");
		phoneNumberField.setColumns(10);
		phoneNumberField.setBounds(169, 215, 239, 21);
		contentPane.add(phoneNumberField);
		
		homepageField = new JTextField();
		homepageField.setToolTipText("Enter the Homepage");
		homepageField.setColumns(10);
		homepageField.setBounds(169, 246, 239, 21);
		contentPane.add(homepageField);
		
		additionalField = new JTextField();
		additionalField.setToolTipText("Enter the Additional info");
		additionalField.setColumns(10);
		additionalField.setBounds(169, 277, 239, 21);
		contentPane.add(additionalField);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(147, 317, 138, 23);
		contentPane.add(btnRegister);
		
		/*회원가입 버튼 이벤트
		 * 각각 Field로 부터 입력된 값을 받아온 뒤
		 * REGISTER 라는 문자열과 함께 입력값을 보냄
		 * 필수 입력 항목이 빈칸일 경우, 다이얼로그를 띄움*/
		btnRegister.addActionListener(e -> {
			id = idField.getText();
			password = passwordField.getText();
			nickName = nickNameField.getText();
			name = nameField.getText();
			email = emailField.getText();
			birth = birthField.getText();
			phoneNumber = phoneNumberField.getText();
			homepage = homepageField.getText();
			additional = additionalField.getText();
			try {
				// 회원가입시 패스워드 암호화해서 password변수에 담아줌
				password = SimpleCrypto.encrypt(password);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (isBlank(id, password, nickName, name, email, birth)) {
				JOptionPane.showMessageDialog(null, "id, password, nickname, name, email, birth is necessary");
			} else {
				out.println("REGISTER " + id + " " + password + " " + nickName + " " +
						name + " " + email + " " + birth + " " + phoneNumber + " " +
						homepage + " " + additional);
			}

			// 회원가입이 완료되었다는 창을 띄우고 회원가입 창을 종료
			JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
			dispose(); // 회원가입 창을 종료
		});
	}
	
	/*필수 입력 정보 빈칸인지 체크, true이면 빈칸*/
	public boolean isBlank(String id, String password, String nickName, String name, String email, String birth)
	{
		return id.isEmpty() || password.isEmpty() || nickName.isEmpty() || name.isEmpty() || email.isEmpty() || birth.isEmpty();
	}
}