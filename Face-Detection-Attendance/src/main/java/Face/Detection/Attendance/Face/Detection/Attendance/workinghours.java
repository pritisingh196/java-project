package Face.Detection.Attendance.Face.Detection.Attendance;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class workinghours {

    // Update the connection URL to use the separate schema (workinghours)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workinghours";
    private static final String DB_USER = "root";      // your MySQL username
    private static final String DB_PASSWORD = "root";  // your MySQL password

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection using the separate schema
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Connected to the database (schema: workinghours) successfully.");

            // (Optional) Create the attendance table if it does not exist
            try (Statement stmt = conn.createStatement()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS attendance ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "employee_name VARCHAR(255) NOT NULL, "
                        + "punch_in DATETIME, "
                        + "punch_out DATETIME, "
                        + "work_duration TIME"
                        + ")";
                stmt.executeUpdate(createTableSQL);
            }

            String employeeName = "priti";

            // 1. Punch In: Insert record with current timestamp as punch_in
            LocalDateTime punchInTime = LocalDateTime.now();
            String insertSQL = "INSERT INTO attendance (employee_name, punch_in) VALUES (?, ?)";
            PreparedStatement pstmtInsert = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, employeeName);
            pstmtInsert.setTimestamp(2, Timestamp.valueOf(punchInTime));
            pstmtInsert.executeUpdate();

            // Retrieve the generated record id
            ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
            int recordId = 0;
            if (generatedKeys.next()) {
                recordId = generatedKeys.getInt(1);
            }
            System.out.println("Punched in at: " + punchInTime);

            // Simulate work duration (e.g., sleep for 5 seconds)
            Thread.sleep(5000);

            // 2. Punch Out: Update the record with punch_out time and calculate duration
            LocalDateTime punchOutTime = LocalDateTime.now();
            System.out.println("Punched out at: " + punchOutTime);

            // Calculate duration between punchInTime and punchOutTime
            Duration duration = Duration.between(punchInTime, punchOutTime);
            long totalSeconds = duration.getSeconds();
            // Format duration as HH:MM:SS
            String formattedDuration = String.format("%02d:%02d:%02d",
                    totalSeconds / 3600, (totalSeconds % 3600) / 60, totalSeconds % 60);

            String updateSQL = "UPDATE attendance SET punch_out = ?, work_duration = ? WHERE id = ?";
            PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL);
            pstmtUpdate.setTimestamp(1, Timestamp.valueOf(punchOutTime));
            pstmtUpdate.setString(2, formattedDuration);
            pstmtUpdate.setInt(3, recordId);
            pstmtUpdate.executeUpdate();

            System.out.println("Work duration: " + formattedDuration);

            // 3. Query and display the record
            String selectSQL = "SELECT * FROM attendance WHERE id = ?";
            PreparedStatement pstmtSelect = conn.prepareStatement(selectSQL);
            pstmtSelect.setInt(1, recordId);
            ResultSet rs = pstmtSelect.executeQuery();
            while (rs.next()) {
                System.out.println("Record ID: " + rs.getInt("id"));
                System.out.println("Employee: " + rs.getString("employee_name"));
                System.out.println("Punch In: " + rs.getTimestamp("punch_in"));
                System.out.println("Punch Out: " + rs.getTimestamp("punch_out"));
                System.out.println("Work Duration: " + rs.getString("work_duration"));
            }
            rs.close();
            pstmtSelect.close();
            pstmtUpdate.close();
            pstmtInsert.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close connection if open
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
}