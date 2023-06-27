package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ClientGUI_Search extends JFrame
{

	private JPanel contentPane;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private JTextField textField;
	private String who = "";
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	int searchPanelWidth = 450;
	int searchPanelHeight = 200;
	int searchPanelX = 100;
	int searchPanelY = 100;

	public ClientGUI_Search(Socket socket, Scanner in, PrintWriter out)
	{
		this.socket = socket;
		this.in = in;
		this.out = out;

		setTitle("사용자 검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(searchPanelX, searchPanelY, searchPanelWidth, searchPanelHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 10, 294, 32);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(e -> {
			String name = textField.getText();
			out.println("SEARCH " + name);
			}
		);
		btnNewButton.setBounds(textField.getX() + textField.getWidth() + 10, textField.getY(), 97, 28);
		contentPane.add(btnNewButton);


		
		btnNewButton_1 = new JButton("친구등록");
		btnNewButton_1.addActionListener(e -> {
			String who = textField.getText();
//			out.println("ADDFRIEND " + "temp"+ " " + who);
			out.println("ADDFRIEND " + ClientGUI_Main.getId() + " " + who);
			JOptionPane.showMessageDialog(null, "사용자 " + who + "을(를) 친구목록에 추가했습니다.");
		});
		btnNewButton_1.setBounds(textField.getX(), textField.getY() + textField.getHeight() + 60, 138, 32);
		contentPane.add(btnNewButton_1);



		btnNewButton_2 = new JButton("정보보기");
		btnNewButton_2.addActionListener(e -> {
			String who = textField.getText();
			out.println("SHOWINFO " + who);
		});
		btnNewButton_2.setBounds(btnNewButton_1.getX() + btnNewButton_1.getWidth() + btnNewButton.getWidth() + 10, btnNewButton_1.getY(), 155, 32);
		contentPane.add(btnNewButton_2);
		
		
		
		
	}
}
