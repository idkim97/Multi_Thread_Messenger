package server;

import java.sql.*;

public class ServerDB
{
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/messenger";
	private final String user = "root";
	private final String password = "root";
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	ServerDB()
	{
		connectDB();
	}
	
	/*DB�� ���� ����*/
	public void connectDB()
	{
		try
		{
			Class.forName(driver);	// driver Ŭ���� �ε��� DriverManger�� ���
			con = DriverManager.getConnection(url, user, password); // DB�� �����ϴ� ��ü
			stmt = con.createStatement(); // SQL�� ������ ���� ��ü
			System.out.println(con);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*Ŭ���̾�Ʈ���� �α��� ��û�� ���� ��, select���� �̿��Ͽ� id�� ã��
	 * �ش� id�� ��й�ȣ�� ��ġ�� ��, true�� ��ȯ*/
	public boolean login(ServerDB DB, String id, String password) throws SQLException
	{
		String search = "select id, password from user where id = '" + id + "';";
		System.out.println("login�޼ҵ忡 �Ѿ�� id : " + id);
		System.out.println("login�޼ҵ忡 �Ѿ�� pw : " + password);
		ResultSet rs = stmt.executeQuery(search);	// ������ ������ ResultSetŸ������ ����
		if (rs.next())
		{
			if (password.equals(rs.getString("password")))
			{
				System.out.println("��й�ȣ DB�� ��ġ");
				return true;
			}
			else
			{
				System.out.println("��й�ȣ DB�� ����ġ");
				return false;
			}
		}
		System.out.println("�Ѵ� �ƴϰ� ���� �׳� ��������");
		return false;
	}
	
	/*Ŭ���̾�Ʈ���� ��й�ȣ ã�� ��û�� ���� ��, select���� �̿��Ͽ� id�� ã��
	 * �ش� id�� �̸����� ��ġ�� ��, ��й�ȣ�� ��ȯ*/
	public String findPw(ServerDB DB, String id, String email) throws SQLException
	{
		String search = "select id, password, email from user where id = '" + id + "';";
		ResultSet rs = stmt.executeQuery(search);
		if (rs.next())
		{
			if (email.equals(rs.getString("email")))
			{
				return rs.getString("password");
			}
			else
			{
				return "";
			}
		}
		return "";
	}
	
	/*Ŭ���̾�Ʈ���� ȸ������ ��û�� ���� ��, ���ڿ��� �Ľ��Ͽ� �� �׸��� ã�� �� insert���� �̿��Ͽ� DB�� ����*/
	public void register(ServerDB DB, String register) throws SQLException
	{
		String[] registerArray = register.split(" ");
		String[] title = {"id","password","nickname","name","email","birth",
							"phoneNumber","homepage","additional"};
		for(int i=0;i<title.length;i++){
			title[i] = registerArray[i+1];
		}

		String sql = "insert into user(id, password, nickName, name, email, birth, phoneNumber, homepage, additional) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = DB.con.prepareStatement(sql);

		for(int i=0;i<title.length;i++){
			pstmt.setString(i+1,title[i]);
		}
		
		int res = pstmt.executeUpdate();
	}
	
	public String findFriend(ServerDB DB, String id) throws SQLException
	{
		String search = "select id, friend from user where id = '" + id + "';";
		ResultSet rs = stmt.executeQuery(search);
		if (rs.next())
		{
			return rs.getString("friend");
		}
		return "";
	}
	
	public String Search(ServerDB DB, String id) throws SQLException
	{
		String search = "select id from user where id = '" + id + "';";
		ResultSet rs = stmt.executeQuery(search);
		if (rs.next())
		{
			return rs.getString("id");
		}
		return "";
	}
	
	public void AddFriend(ServerDB DB, String id, String addId) throws SQLException
	{
		
		String sql = "update user set friend = ? where id = ?";
		String friendList = findFriend(DB, id);
		friendList += " " + addId + " ";
		PreparedStatement pstmt = DB.con.prepareStatement(sql);
		
		pstmt.setString(1, friendList);
		pstmt.setString(2, id);
		
		int res = pstmt.executeUpdate();
	}
	
	public void changeNick(ServerDB DB, String id, String nick) throws SQLException
	{
		String sql = "update user set nickName = ? where id = ?";
		PreparedStatement pstmt = DB.con.prepareStatement(sql);
		
		pstmt.setString(1, nick);
		pstmt.setString(2, id);
		
		int res = pstmt.executeUpdate();
	}
}
