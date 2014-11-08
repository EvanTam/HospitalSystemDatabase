import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


public class Visit {
	private final static String DB_ADDRESS = "jdbc:mysql://localhost:3306/mydb";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "";
	
	public int appt_id;
	public int dr_id;
	public int patient_id;
	public String prescribtion;
	public String treatment_schedule;
	public Timestamp arrival_time;
	public Timestamp leave_time;
	
	public Visit() {
		this.appt_id = 0;
		this.dr_id = 0;
		this.patient_id = 0;
		this.prescribtion = null;
		this.treatment_schedule = null;
		this.arrival_time = null;
		this.leave_time = null;
	}
	
	public static int insertVisit(Visit appt) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_patient_visit VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, appt.appt_id);
			statement.setInt(2, appt.dr_id);
			statement.setInt(3, appt.patient_id);
			statement.setString(4, appt.prescribtion);
			statement.setString(5, appt.treatment_schedule);
			statement.setTimestamp(6, appt.arrival_time);
			statement.setTimestamp(7, appt.leave_time);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// This is left here for testing purpose
	public static void main(String[] args) {
		Visit newVisit = new Visit();
		newVisit.appt_id = 5;
		newVisit.dr_id = 0;
		newVisit.patient_id = 6;
		newVisit.prescribtion = "sugar";
		newVisit.treatment_schedule = "more sugar";
		newVisit.arrival_time = Timestamp.valueOf("2014-03-29 00:01:00");
		newVisit.leave_time = Timestamp.valueOf("2014-03-29 00:02:00");
		System.out.println(insertVisit(newVisit));
	}
}
