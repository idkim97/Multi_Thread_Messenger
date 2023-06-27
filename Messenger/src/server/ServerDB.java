package server;

import java.sql.*;

public class ServerDB
{
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/messenger";
	private final String user = "root";
	private final String password = "root";
	private Connection con = null;	// DB와 연결을 하는 객체. 연결 설정 및 종료
	private Statement stmt = null;	// SQL문 실행하기 위한 객체
	private ResultSet rs = null;	// 쿼리 결과로 얻은 데이터 객체
	
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

		String sql = "insert into user(id, password, nickName, name, email, birth, phoneNumber, homepage, additional, friend) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, 'admin')";
		
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
		try {
			String friendList = findFriend(DB, id);
			friendList += " " + addId + " ";
			PreparedStatement pstmt = DB.con.prepareStatement(sql);

			pstmt.setString(1, friendList);
			pstmt.setString(2, id);

			int res = pstmt.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void changeNick(ServerDB DB, String id, String nick) throws SQLException
	{
		String sql = "update user set nickName = ? where id = ?";
		PreparedStatement pstmt = DB.con.prepareStatement(sql);
		
		pstmt.setString(1, nick);
		pstmt.setString(2, id);
		
		int res = pstmt.executeUpdate();
	}

	// DB에 저장되어 있는 데이터가 존재하는지
	// 한개도 없으면 true
	// 한개라도 존재하면 false를 return 한다.
	// 최초 회원가입자라면 admin 사용자를 DB에 먼저 넣어주고
	// friend를 만들어주기위함
	public boolean isEmpty(){
		String sql = "SELECT COUNT(*) FROM user";

		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				int count = rs.getInt(1);

				if (count == 0) return true;
				else return false;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}

		return false;
	}

	public void setAdmin() throws SQLException {
		String sql = "insert into user(id, password, nickName, name, email, birth, phoneNumber, homepage, additional, friend) "
				+ "values('admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin')";
		try {
			stmt.executeUpdate(sql);
			System.out.println("admin 유저가 DB에 추가되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
