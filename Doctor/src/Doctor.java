import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Doctor {
	private final static String DB_ADDRESS = "jdbc:mysql://localhost:3306/mydb";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "";
	
	public int dr_id;
	public String first_name;
	public String last_name;
	public String specaillity;
	public String office_no;
	public Date join_date;
	
	public Doctor() {
		this.dr_id = 0;
		this.first_name = null;
		this.last_name = null;
		this.specaillity = null;
		this.office_no = null;
		this.join_date = null;
	}
	
	public static List<Doctor> getAllDoctors() {
		List<Doctor> listOfDoctor = new ArrayList<Doctor>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT * FROM tbl_doctor";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Doctor thisDoctor = new Doctor();
				thisDoctor.dr_id = result.getInt("dr_id");
				thisDoctor.first_name = result.getString("first_name");
				thisDoctor.last_name = result.getString("last_name");
				thisDoctor.specaillity = result.getString("specaillity");
				thisDoctor.office_no = result.getString("office_no");
				thisDoctor.join_date = result.getDate("join_date");
				listOfDoctor.add(thisDoctor);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return listOfDoctor;
	}
	
	public static int grantPermission(int grantorId, int granteeId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_doctor_to_doctor_permission VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, grantorId);
			statement.setInt(2, granteeId);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static List<Doctor> getGrantedDocs(int grantorId) {
		List<Doctor> listOfDoctor = new ArrayList<Doctor>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT * FROM tbl_doctor WHERE dr_id IN (SELECT grantee_doctor FROM tbl_doctor_to_doctor_permission WHERE grantor_doctor = ?";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, grantorId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Doctor thisDoctor = new Doctor();
				thisDoctor.dr_id = result.getInt("dr_id");
				thisDoctor.first_name = result.getString("first_name");
				thisDoctor.last_name = result.getString("last_name");
				thisDoctor.specaillity = result.getString("specaillity");
				thisDoctor.office_no = result.getString("office_no");
				thisDoctor.join_date = result.getDate("join_date");
				listOfDoctor.add(thisDoctor);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return listOfDoctor;
	}
	
	public static int insertDoctor(Doctor d) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_doctor VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, d.dr_id);
			statement.setString(2, d.first_name);
			statement.setString(3, d.last_name);
			statement.setString(4, d.specaillity);
			statement.setString(5, d.office_no);
			statement.setDate(6, d.join_date);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void printListOfDoctor(List<Doctor> listOfDoctor) {
		Iterator<Doctor> doctorIterator = listOfDoctor.iterator();
		while (doctorIterator.hasNext()) {
			Doctor d = doctorIterator.next();
			System.out.println(d.dr_id + " " + d.first_name + " " + d.last_name + " " + d.specaillity + " " + d.office_no + " " + d.join_date.toString());
		}
	}
	
	// This is left here for testing purpose
	public static void main(String[] args) throws SQLException {
		// If an error occurs either you are trying to reinsert the same user twice
//		Doctor newDoctor = new Doctor();
//		newDoctor.dr_id = 0;
//		newDoctor.first_name = "New";
//		newDoctor.last_name = "Doctor";
//		newDoctor.specaillity = "0";
//		newDoctor.office_no = "000";
//		newDoctor.join_date = Date.valueOf("2014-03-29");
//		insertDoctor(newDoctor);
		
//		List<Doctor> dList =  getAllDoctors();
//		printListOfDoctor(dList);
		
		System.out.println(grantPermission(0, 1));
	}
}
