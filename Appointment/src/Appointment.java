import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


public class Appointment {
	private final static String DB_ADDRESS = "jdbc:mysql://localhost:3306/mydb";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "";
	
	public int appt_id;
	public int patient_id;
	public int dr_id;
	public Timestamp appt_register_date;
	public Timestamp appt_date_time;
	public String created_by;
	public int attended;
	public String appt_Type;
	
	public Appointment() {
		this.appt_id = 0;
		this.patient_id = 0;
		this.dr_id = 0;
		this.appt_register_date = null;
		this.appt_date_time = null;
		this.created_by = null;
		this.attended = 0;
		this.appt_Type = null;
	}
	
	public static int insertAppointment(Appointment appt) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWORD);
			String statementSQL = "INSERT INTO tbl_appointment VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(statementSQL);
			statement.setInt(1, appt.appt_id);
			statement.setInt(2, appt.patient_id);
			statement.setInt(3, appt.dr_id);
			statement.setTimestamp(4, appt.appt_register_date);
			statement.setTimestamp(5, appt.appt_date_time);
			statement.setString(6, appt.created_by);
			statement.setInt(7, appt.attended);
			statement.setString(8, appt.appt_Type);
			return statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// This is left here for testing purpose
	public static void main(String[] args) {
		Appointment newAppt = new Appointment();
		newAppt.appt_id = 5;
		newAppt.patient_id = 6;
		newAppt.dr_id = 0;
		newAppt.appt_register_date = Timestamp.valueOf("2014-03-29 00:00:00");
		newAppt.appt_date_time = Timestamp.valueOf("2014-03-30 00:00:00");
		newAppt.created_by = "Evan";
		newAppt.attended = 0;
		newAppt.appt_Type = "0";
		System.out.println(insertAppointment(newAppt));
	}
}
