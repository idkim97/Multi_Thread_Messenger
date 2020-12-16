package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI_ChangeInfo extends JFrame {

	private JPanel contentPane;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private JTextField textField;
	private JTextField textField_1;

	public ClientGUI_ChangeInfo(Socket socket, Scanner in, PrintWriter out)
	{
		this.socket = socket;
		this.in = in;
		this.out = out;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 137);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uD55C\uB9C8\uB514 : ");
		lblNewLabel.setBounds(24, 22, 59, 30);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(81, 27, 281, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\uB2C9\uB124\uC784 : ");
		lblNewLabel_1.setBounds(24, 62, 59, 30);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(81, 67, 281, 21);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("\uBCC0\uACBD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String status = textField.getText();
				String nickName = textField_1.getText();
				String id = ClientGUI_Main.getId();
				out.println("CHANGEINFO " + id + " "+ status + " " + nickName);
				JOptionPane.showMessageDialog(null, "Changed");
				setVisible(false);
			}
		});
		btnNewButton.setBounds(374, 40, 70, 23);
		contentPane.add(btnNewButton);
	}

}
