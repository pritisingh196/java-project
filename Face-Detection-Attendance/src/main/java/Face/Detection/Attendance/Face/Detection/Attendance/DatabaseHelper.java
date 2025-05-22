package Face.Detection.Attendance.Face.Detection.Attendance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {
    // Change database name, user, and password as required
    private static final String DB_URL = "jdbc:mysql://localhost:3306/face_detector";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Static block to load the MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ MySQL JDBC Driver not found!");
        }
    }

    // Returns a connection to the database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Database connection failed!");
        }
        return conn;
    }

    // Saves face details (name and image path) into the database
    public void saveFace(String name, String imagePath) {
        String sql = "INSERT INTO faces (name, image_path) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, imagePath);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Face details saved successfully in the database.");
            } else {
                System.out.println("❌ Failed to save face details.");
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL Exception: " + e.getMessage());
        }
    }
    

    // Saves fingerprint details (name and image path) into the database
    public void saveFingerprint(String name, String imagePath) {
        String sql = "INSERT INTO fingerprints (name, image_path) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, imagePath);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Fingerprint details saved successfully in the database.");
            } else {
                System.out.println("❌ Failed to save fingerprint details.");
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL Exception: " + e.getMessage());
        }
    }
}
