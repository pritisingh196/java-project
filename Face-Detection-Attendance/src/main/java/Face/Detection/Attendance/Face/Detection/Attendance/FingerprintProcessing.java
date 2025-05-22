package Face.Detection.Attendance.Face.Detection.Attendance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FingerprintProcessing {
    // Connection details for the fingerprints database
    private static final String DB_URL_FINGERPRINTS = "jdbc:mysql://localhost:3306/fingerprints";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Load the MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ MySQL JDBC Driver not found!");
        }
    }

    // Method to establish a connection to the fingerprints database
    public static Connection getFingerprintConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL_FINGERPRINTS, USER, PASSWORD);
            System.out.println("✅ Connected to the fingerprints database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Database connection to fingerprints failed!");
        }
        return conn;
    }

    // Method to save fingerprint details in the fingerprints database
    public void saveFingerprint(String name, String imagePath) {
        String sql = "INSERT INTO fingerprint_records (name, image_path) VALUES (?, ?)";
        try (Connection connection = getFingerprintConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            System.out.println("Executing SQL: " + sql);
            System.out.println("Name: " + name);
            System.out.println("Image Path: " + imagePath);

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

    // Main method to run the program
    public static void main(String[] args) {
        // Create an instance of FingerprintProcessing
        FingerprintProcessing processor = new FingerprintProcessing();

        // Example: Save a fingerprint record
        String name = "priti";
      
        
        String imagePath = "C:\\Users\\HP\\Downloads\\face-detection-attendence\\Face-Detection-Attendance\\fingerprintinput"; // Update with the actual image path
        processor.saveFingerprint(name, imagePath);
    }
}