import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class User {
	private final static String DB_ADDRESS = "jdbc:mysql://localhost:3306/mydb";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "";
	
	public int user_id;
	public String user_name;
	public String first_name;
	public String last_name;
	public String login_password;
	public String phone_number;
	public Timestamp creation_date;
	public String user_type;
	
	public User() {
		this.user_id = 0;
		this.user_name = null;
		this.first_name = null;
		this.last_name = null;
		this.login_password = null;
		this.phone_number = null;
		this.creation_date = null;
		this.user_type = null;
	}
	
	public static int insertUser(User user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, user.user_id);
			statement.setString(2, user.user_name);
			statement.setString(3, user.first_name);
			statement.setString(4, user.last_name);
			statement.setString(5, user.login_password);
			statement.setString(6, user.phone_number);
			statement.setTimestamp(7, user.creation_date);
			statement.setString(8, user.user_type);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int verifyUser(String user_name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT user_type FROM tbl_user WHERE user_name = ?";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setString(1, user_name);
			ResultSet result = statement.executeQuery();
			result.next();
			return Integer.parseInt(result.getString("user_type"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// This is left here for testing purpose
	public static void main(String[] args) throws SQLException {
		
		// If an error occurs either you are trying to reinsert the same user twice or you have an invalid user type
		User newUser = new User();
		newUser.user_id = 0;
		newUser.user_name = "New user";
		newUser.first_name = "New";
		newUser.last_name = "User";
		newUser.login_password = "000";
		newUser.phone_number = "000000000";
		newUser.creation_date = Timestamp.valueOf("2014-03-29 00:00:00");
		newUser.user_type = "4";
		insertUser(newUser);
		
		// If this prints 4 then both methods are working
		System.out.println(verifyUser("New user"));
	}
}
