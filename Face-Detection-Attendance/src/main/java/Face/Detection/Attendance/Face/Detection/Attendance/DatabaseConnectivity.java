package Face.Detection.Attendance.Face.Detection.Attendance;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;

	public class DatabaseConnectivity {
	    private static final String URL = "jdbc:mysql://localhost:3306/face detector";
	    private static final String USER = "root";
	    private static final String PASSWORD = "root";

	    public static Connection getConnection() throws Exception {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    public static void saveFace(String name, String imagePath) {
	        try (Connection conn = getConnection()) {
	            String query = "INSERT INTO detected_faces (name, image_path) VALUES (?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, name);
	            stmt.setString(2, imagePath);
	            stmt.executeUpdate();
	            System.out.println("Face saved to database.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}



