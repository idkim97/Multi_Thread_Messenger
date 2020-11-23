import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class ChatRoom extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom();
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
	
	// 채팅방 GUI
	public ChatRoom() {
		// 채팅창 이름
		setTitle("채팅방 이름");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("친구초대");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(217, 0, 109, 53);
		contentPane.add(button);
		
		JButton button_1 = new JButton("나가기");	// 나가기버튼
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(325, 0, 109, 53);
		contentPane.add(button_1);
		
		JTextArea textArea = new JTextArea(); // 채팅방 부분
		textArea.setBounds(0, 53, 434, 374);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea(); // 채팅 입력부분
		textArea_1.setBounds(0, 432, 434, 53);
		contentPane.add(textArea_1);
		
		JLabel label = new JLabel("채팅방 이름");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 221, 53);
		contentPane.add(label);
	}
}
