package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import java.awt.Font;

public class ClientGUI_List2 extends JFrame
{
//	Weather weather = new Weather();
	private JPanel contentPane;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private DefaultMutableTreeNode selectedNode;
	JTree tree = new JTree();
	public ClientGUI_List2(String[] friendList, Socket socket, Scanner in, PrintWriter out) throws IOException
	{
		this.socket = socket;
		this.in = in;
		this.out = out;
		String id = ClientGUI_Main.getId();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("\uC0AC\uC6A9\uC790 \uAC80\uC0C9");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientGUI_Search se = new ClientGUI_Search(socket, in, out);
				se.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				se.setVisible(true);
			}
		});
		btnNewButton.setBounds(0, 0, 126, 55);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\uC815\uBCF4\uBCC0\uACBD");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientGUI_ChangeInfo se1= new ClientGUI_ChangeInfo(socket, in, out);
				se1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				se1.setVisible(true);
			}
		});
		button.setBounds(129, 0, 94, 55);
		contentPane.add(button);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(411, 0, 21, 600);
		contentPane.add(scrollBar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 119, 388, 449);
		
		contentPane.add(scrollPane);
		
		
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("친구목록") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("전체");
					for (int i = 1; i < friendList.length; i++)
					{
						node_1.add(new DefaultMutableTreeNode(friendList[i]));
					}
					add(node_1);
				}
			}
		));
		tree.setFont(new Font("굴림", Font.PLAIN, 15));
		tree.setBackground(SystemColor.control);
		scrollPane.setViewportView(tree);
		tree.addMouseListener(getMouseListener());
		
		
		JPopupMenu popupMenu = new JPopupMenu();
	      addPopup(tree, popupMenu);
	      
	      JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open Chat");
	      mntmNewMenuItem_1.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		String chatWith = String.valueOf(selectedNode);
	      		out.println("CHATWITH " + id + " " + chatWith);
	      	}
	      });
	      popupMenu.add(mntmNewMenuItem_1);
	      
	      JMenuItem mntmNewMenuItem = new JMenuItem("Info");
	      mntmNewMenuItem.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		String info = String.valueOf(selectedNode);
	      		out.println("SHOWINFO " + info);
	      	}
	      });
	      popupMenu.add(mntmNewMenuItem);
	     
	      
	      JTextArea textArea_1 = new JTextArea();
	      textArea_1.setBounds(17, 570, 388, 18);
//	      textArea_1.append(Weather.getWeather());
	      contentPane.add(textArea_1);
	      
	      JButton button_1 = new JButton("\uC804\uCCB4 \uCC44\uD305\uBC29 \uC785\uC7A5");
	      button_1.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		out.println("ENTERALL ");
	      	}
	      });
	      button_1.setBounds(225, 0, 132, 55);
	      contentPane.add(button_1);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
	      component.addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	               showMenu(e);
	            }
	         }
	         public void mouseReleased(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	               showMenu(e);
	            }
	         }
	         private void showMenu(MouseEvent e) {
	            popup.show(e.getComponent(), e.getX(), e.getY());
	         }
	      });
	   }
	
	private MouseListener getMouseListener() {
	      return new MouseAdapter() {         @Override
	          public void mousePressed(MouseEvent arg0) {
	            if(arg0.getButton() == MouseEvent.BUTTON3){
	               TreePath pathForLocation = tree.getPathForLocation(arg0.getPoint().x, arg0.getPoint().y);
	               if(pathForLocation != null){
	                  selectedNode = (DefaultMutableTreeNode) pathForLocation.getLastPathComponent();
	               } else{
	                  selectedNode = null;

	               }

	            }
	            super.mousePressed(arg0);
	         }
	      };
	   }



}
