//package client;
//
//import java.awt.Component;
//import java.awt.Font;
//import java.awt.SystemColor;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.JScrollBar;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.JTree;
//import javax.swing.border.EmptyBorder;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//
//import org.json.simple.parser.ParseException;
//
//public class List_GUI extends JFrame {
//
//	private JPanel contentPane;
//	Weather weather = new Weather();
//	private JTextField textField;
//	JPopupMenu popup;
//	JMenuItem open;
//    JMenuItem Info;
//	
//
//
//	public List_GUI() throws IOException, ParseException {
//		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 647);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		JButton btnNewButton = new JButton("\uCE5C\uAD6C\uCD94\uAC00");
//		btnNewButton.setBounds(0, 0, 94, 55);
//		contentPane.add(btnNewButton);
//		
//		JTextArea textArea = new JTextArea();
//		textArea.setBounds(77, 65, 320, 24);
//		contentPane.add(textArea);
//		
//		JLabel lblNewLabel = new JLabel("\uCE5C\uAD6C\uAC80\uC0C9");
//		lblNewLabel.setBounds(17, 67, 62, 18);
//		contentPane.add(lblNewLabel);
//		
//		JButton button = new JButton("\uC815\uBCF4\uBCC0\uACBD");
//		button.setBounds(96, 0, 94, 55);
//		contentPane.add(button);
//		
//		JScrollBar scrollBar = new JScrollBar();
//		scrollBar.setBounds(411, 0, 21, 600);
//		contentPane.add(scrollBar);
//		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(17, 119, 388, 396);
//		
//		contentPane.add(scrollPane);
//		
//		JTree tree = new JTree();
//		tree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode("친구목록") {
//				{
//					DefaultMutableTreeNode node_1;
//					node_1 = new DefaultMutableTreeNode("온라인");
////					if(ClientGUI_Main.set1.contains("kim")==true)
////						node_1.add(new DefaultMutableTreeNode(ClientGUI_Main.id));
////					if(ClientGUI_Main.set1.contains("lee")==true)
////						node_1.add(new DefaultMutableTreeNode(ClientGUI_Main.id));
////					if(ClientGUI_Main.set1.contains("choi")==true)
////						node_1.add(new DefaultMutableTreeNode(ClientGUI_Main.id));
////					if(ClientGUI_Main.set1.contains("park")==true)
////						node_1.add(new DefaultMutableTreeNode(ClientGUI_Main.id));
////
////					add(node_1);
////					node_1 = new DefaultMutableTreeNode("오프라인");
////					if(ClientGUI_Main.set1.contains("kim")==false)
////						node_1.add(new DefaultMutableTreeNode("kim"));
////					if(ClientGUI_Main.set1.contains("lee")==false)
////						node_1.add(new DefaultMutableTreeNode("lee"));
////					if(ClientGUI_Main.set1.contains("choi")==false)
////						node_1.add(new DefaultMutableTreeNode("choi"));
////					if(ClientGUI_Main.set1.contains("park")==false)
////						node_1.add(new DefaultMutableTreeNode("park"));
//				add(node_1);
//					
//					
//				}
//			}
//		));
//		tree.setFont(new Font("굴림", Font.PLAIN, 15));
//		tree.setBackground(SystemColor.control);
//		scrollPane.setViewportView(tree);
//		
//		JTextArea textArea_1 = new JTextArea();
//		textArea_1.setBounds(17, 527, 388, 61);
//		textArea_1.append(weather.getInfo());
//		contentPane.add(textArea_1);
//		
//		JButton button_1 = new JButton("\uCC44\uD305\uBC29\uC0DD\uC131\r\n");
//		button_1.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent arg0) {
//				String st = null; // 아이피주소
//				int port = 0; // 포트번호
//				try {
//					File file = new File("C:\\work\\serverinfo.dat");
//					// 내 컴퓨터에 저장되있는 serverinfo.dat 읽기
//
//					Scanner input = new Scanner(file);
//					while (input.hasNext()) {
//						st = input.nextLine();
//						port = input.nextInt();
//					}
//				} catch (FileNotFoundException e) // 만약 파일이 찾아지지않을시 기본적으로 연결
//				{
//					st = "127.0.0.1";
//					port = 8888;
//					System.out.println(e.getMessage());
//				}
//				// ChatClient client = new ChatClient(st);
//				// client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				ChatClient.frame.setVisible(true);
//				// client.run(port, ClientGUI_Main.id);
//			}
//		});
//		
//		addWindowListener(new WindowAdapter() {
//		public void windowClosing(WindowEvent evt) {
//			ChatClient.out.println("EXIT");
//			System.exit(0);
//	}});
//		
//		button_1.setBounds(192, 0, 103, 55);
//		contentPane.add(button_1);
//			
//		
//		
//	      JPopupMenu popupMenu = new JPopupMenu();
//	      addPopup(tree, popupMenu);
//	      
//	       open = new JMenuItem("Open Chat");
//	      popupMenu.add(open);
//	      
//	       Info = new JMenuItem("Info");
//	      popupMenu.add(Info);
//
//	      
//	      
//
//
//		
//	}
//	
//	private static void addPopup(Component component, final JPopupMenu popup) {
//	      component.addMouseListener(new MouseAdapter() {
//	         public void mousePressed(MouseEvent e) {
//	            if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
//	               popup.show(e.getComponent(), e.getX(), e.getY());
//	            }
//	         }
//	      /*   public void mouseReleased(MouseEvent e) {
//	            if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
//	               showMenu(e);
//	            }
//	         }
//	         private void showMenu(MouseEvent e) {
//	            popup.show(e.getComponent(), e.getX(), e.getY());
//	         }*/
//	      });
//	   }
//	
//	private ActionListener getChatActionListener() {
//		return new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if(open != null){
//					System.out.println("pressed");
//				
//				}
//			}
//		};
//	}
//
//	
//	
//
//}
