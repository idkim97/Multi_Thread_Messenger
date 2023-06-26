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
	
	/*DB와 최초 연결*/
	public void connectDB()
	{
		try
		{
			Class.forName(driver);	// driver 클래스 로딩후 DriverManger에 등록
			con = DriverManager.getConnection(url, user, password); // DB와 연결하는 객체
			stmt = con.createStatement(); // SQL문 실행을 위한 객체
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
	
	/*클라이언트에서 로그인 요청이 들어온 뒤, select문을 이용하여 id를 찾음
	 * 해당 id와 비밀번호가 일치할 시, true를 반환*/
	public boolean login(ServerDB DB, String id, String password) throws SQLException
	{
		String search = "select id, password from user where id = '" + id + "';";
		System.out.println("login메소드에 넘어온 id : " + id);
		System.out.println("login메소드에 넘어온 pw : " + password);
		ResultSet rs = stmt.executeQuery(search);	// 실행한 쿼리문 ResultSet타입으로 저장
		if (rs.next())
		{
			if (password.equals(rs.getString("password")))
			{
				System.out.println("비밀번호 DB랑 일치");
				return true;
			}
			else
			{
				System.out.println("비밀번호 DB랑 불일치");
				return false;
			}
		}
		System.out.println("둘다 아니고 지금 그냥 빠져나감");
		return false;
	}
	
	/*클라이언트에서 비밀번호 찾기 요청이 들어온 뒤, select문을 이용하여 id를 찾음
	 * 해당 id와 이메일이 일치할 시, 비밀번호를 반환*/
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
	
	/*클라이언트에서 회원가입 요청이 들어온 뒤, 문자열을 파싱하여 각 항목을 찾은 뒤 insert문을 이용하여 DB에 저장*/
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
