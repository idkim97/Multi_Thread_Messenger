package client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * A simple Swing-based client for the chat server. Graphically it is a frame with a text
 * field for entering messages and a textarea to see the whole dialog.
 *
 * The client follows the following Chat Protocol. When the server sends "SUBMITNAME" the
 * client replies with the desired screen name. The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are already in use. When the
 * server sends a line beginning with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all chatters connected to the
 * server. When the server sends a line beginning with "MESSAGE" then all characters
 * following this string should be displayed in its message area.
 */
public class ChatClient {
	/* Variable */
    String serverAddress;
    Scanner in;
    static PrintWriter out;  
    String names[] = new String[100];
    int clientNum;
    String temp;
    String output;
    private static String[] values;
    /* GUI */
    static JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(30);
    JTextArea messageArea = new JTextArea(10, 30);
    JButton sendButton = new JButton("Send");
    JLabel lblNewLabel = new JLabel("");
	JList <String>list = new JList<String>((new AbstractListModel() {
		String[] values = new String[] {"username"};
		public int getSize() {
			return values.length;
		}
		public Object getElementAt(int index) {
			return values[index];
		}
	}));
    
    /**
     * Constructs the client by laying out the GUI and registering a listener with the
     * textfield so that pressing Return in the listener sends the textfield contents
     * to the server. Note however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED message from
     * the server.
     */
    public ChatClient(String serverAddress) {
        this.serverAddress = serverAddress;
        /*GUI SETTING*/
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(sendButton,BorderLayout.EAST);
        frame.getContentPane().add(list,BorderLayout.WEST);
        frame.getContentPane().add(lblNewLabel,BorderLayout.NORTH);
        list.setBackground(Color.BLACK);
        list.setForeground(new Color(0, 128, 0));
        list.setFont(new Font("돋움", Font.PLAIN, 15));
        textField.setBackground(Color.BLACK);
        textField.setForeground(new Color(0, 128, 0));
        textField.setFont(new Font("돋움", Font.PLAIN, 15));
        lblNewLabel.setBackground(Color.BLACK);
        lblNewLabel.setFont(new Font("돋움", Font.PLAIN, 13));      
		messageArea.setBackground(Color.BLACK);
		messageArea.setForeground(new Color(0, 128, 0));
		messageArea.setFont(new Font("돋움", Font.PLAIN, 15));
        frame.pack();
 
        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
        
        // Send on click button then clear to prepare for next message
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              // whisper.hide();
               out.println(textField.getText());
               textField.setText("");        
             }
        });
           
    }
    //first display(닉네임 설정 메세지)
    private String getName() {    	
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.OK_CANCEL_OPTION
        );
    }

    void run(int port, String id) throws IOException {
        try {
           Socket socket = new Socket(serverAddress, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
                      System.out.println(id);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("SUBMITNAME")) { //SUBMITNAME으로 시작하면 GETNAME을 보낸다
                    out.println(id);
                } else if (line.startsWith("NAMEACCEPTED")) { //NameAccepted 라는 String을 받으면 실행
                    this.frame.setTitle("Chatter - World Server " + line.substring(12));
                    textField.setEditable(true);
                }else if(line.startsWith("SERVERINFO")) { //serverinfo 메세지 수신시 접속자 수 가져옴.
                	this.lblNewLabel.setText("접속자 수:"+line.substring(11));
                }else if(line.startsWith("USERINFO")) { //userinfo 메세지 수신시 유저 이름 가져옴.
                	values = line.substring(10,line.length()-1).split(", ");
                	list.setListData(values);
                }else if (line.startsWith("MESSAGE")) { //message 메세지 수신시 채팅 내용 가져옴
                    messageArea.append(line.substring(8) + "\n"); // messageArea라는 textfield에 출력해줌!
                }
            }
        } finally {
            frame.setVisible(false); 
            frame.dispose();
        }
    }
}