import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Patient {
	private final static String DB_ADDRESS = "jdbc:mysql://localhost:3306/mydb";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "";
	
	public int patient_id;
	public String first_name;
	public String last_name;
	public String sin;
	public Date patient_registeration_date;
	public int default_dr_id;
	public int health_indication_id;
	
	public Patient() {
		this.patient_id = 0;
		this.first_name = null;
		this.last_name = null;
		this.sin = null;
		this.patient_registeration_date = null;
		this.default_dr_id = 0;
		this.health_indication_id = 0;
	}
	
	public static Patient getAllPatientDetails(int patient_id) {
		Patient thisPatient = new Patient();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT * FROM tbl_patient_info WHERE patient_id = ?";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, patient_id);
			ResultSet result = statement.executeQuery();
			result.next();
			thisPatient.patient_id = result.getInt("patient_id");
			thisPatient.first_name = result.getString("first_name");
			thisPatient.last_name = result.getString("last_name");
			thisPatient.sin = result.getString("sin");
			thisPatient.patient_registeration_date = result.getDate("patient_registeration_date");
			thisPatient.default_dr_id = result.getInt("default_dr_id");
			thisPatient.health_indication_id = result.getInt("health_indication_id");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return thisPatient;
	}
	
	public static List<Patient> getAllPatient1(String firstApptDateTime, String lastApptDateTime) {
		List<Patient> listOfPatient = new ArrayList<Patient>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT * FROM tbl_patient_info WHERE patient_id IN (SELECT patient_id FROM tbl_appointment WHERE appt_date_time >= ? AND appt_date_time <= ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setTimestamp(1, Timestamp.valueOf(firstApptDateTime));
			statement.setTimestamp(2, Timestamp.valueOf(lastApptDateTime));
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Patient thisPatient = new Patient();
				thisPatient.patient_id = result.getInt("patient_id");
				thisPatient.first_name = result.getString("first_name");
				thisPatient.last_name = result.getString("last_name");
				thisPatient.sin = result.getString("sin");
				thisPatient.patient_registeration_date = result.getDate("patient_registeration_date");
				thisPatient.default_dr_id = result.getInt("default_dr_id");
				thisPatient.health_indication_id = result.getInt("health_indication_id");
				listOfPatient.add(thisPatient);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return listOfPatient;
	}
	
	public static List<Patient> getAllPatient2(String firstArrivalTime, String lastArrivalTime) {
		List<Patient> listOfPatient = new ArrayList<Patient>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "SELECT * FROM tbl_patient_info WHERE patient_id IN (SELECT patient_id FROM tbl_patient_visit WHERE arrival_time >= ? AND arrival_time <= ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setTimestamp(1, Timestamp.valueOf(firstArrivalTime));
			statement.setTimestamp(2, Timestamp.valueOf(lastArrivalTime));
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Patient thisPatient = new Patient();
				thisPatient.patient_id = result.getInt("patient_id");
				thisPatient.first_name = result.getString("first_name");
				thisPatient.last_name = result.getString("last_name");
				thisPatient.sin = result.getString("sin");
				thisPatient.patient_registeration_date = result.getDate("patient_registeration_date");
				thisPatient.default_dr_id = result.getInt("default_dr_id");
				thisPatient.health_indication_id = result.getInt("health_indication_id");
				listOfPatient.add(thisPatient);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return listOfPatient;
	}
	
	public static int insertPatient(Patient p) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_patient_info VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, p.patient_id);
			statement.setString(2, p.first_name);
			statement.setString(3, p.last_name);
			statement.setString(4, p.sin);
			statement.setDate(5, p.patient_registeration_date);
			statement.setInt(6, p.default_dr_id);
			statement.setInt(7, p.health_indication_id);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void printListOfPatient(List<Patient> listOfPatient) {
		Iterator<Patient> patientIterator = listOfPatient.iterator();
		while (patientIterator.hasNext()) {
			Patient p = patientIterator.next();
			System.out.println(p.patient_id + " " + p.first_name + " " + p.last_name + " " + p.sin + " " + p.patient_registeration_date.toString() + " " + p.default_dr_id + " " + p.health_indication_id);
		}
	}
	
	// This is left here for testing purpose
	public static void main(String[] args) throws SQLException {
		
		// If an error occurs either you are trying to reinsert the same user twice
//		Patient newPatient = new Patient();
//		newPatient.patient_id = 0;
//		newPatient.first_name = "New";
//		newPatient.last_name = "User";
//		newPatient.sin = "000";
//		newPatient.patient_registeration_date = Date.valueOf("2014-03-29");
//		newPatient.default_dr_id = 1234;
//		newPatient.health_indication_id = 0;
//		insertPatient(newPatient);
		
		// Need to insert appointment with same patient id using Appointment.java first to test this
//		List<Patient> pList = getAllPatient1("2014-03-28 00:00:00", "2014-03-31 00:00:00");
//		printListOfPatient(pList);
		
		// Need to insert appointment and then visit with same patient id  and appointment id using Appointment.java and Visit.java first to test this
//		List<Patient> pList = getAllPatient2("2014-03-28 00:00:00", "2014-03-31 00:00:00");
//		printListOfPatient(pList);
		
//		Patient p = getAllPatientDetails(0);
//		System.out.println(p.patient_id + " " + p.first_name + " " + p.last_name + " " + p.sin + " " + p.patient_registeration_date.toString() + " " + p.default_dr_id + " " + p.health_indication_id);
	}
}
