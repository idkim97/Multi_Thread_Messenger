import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Test1");
					System.out.println("Test1");
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(335, 170, 73, 24);
		panel.add(btnNewButton);
		
		JButton btnClickHereTo = new JButton("Click here to register");
		btnClickHereTo.setBounds(229, 206, 179, 27);
		panel.add(btnClickHereTo);
		
		JTextArea txtrUserName = new JTextArea();
		txtrUserName.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtrUserName.setBackground(SystemColor.control);
		txtrUserName.setText("User name :");
		txtrUserName.setBounds(34, 81, 94, 24);
		panel.add(txtrUserName);
		
		JTextArea txtrPassword = new JTextArea();
		txtrPassword.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtrPassword.setBackground(SystemColor.control);
		txtrPassword.setText("Password  :");
		txtrPassword.setBounds(34, 116, 94, 24);
		panel.add(txtrPassword);
		
		JTextArea txtrForgotYourPassword = new JTextArea();
		txtrForgotYourPassword.setBackground(SystemColor.control);
		txtrForgotYourPassword.setText("Forgot your password?");
		txtrForgotYourPassword.setBounds(14, 208, 164, 24);
		panel.add(txtrForgotYourPassword);
		
		textField = new JTextField();
		textField.setBounds(142, 80, 164, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(142, 115, 164, 24);
		panel.add(textField_1);
		textField_1.setColumns(10);
	}
}
