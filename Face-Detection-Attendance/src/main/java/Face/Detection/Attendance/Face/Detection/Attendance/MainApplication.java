package Face.Detection.Attendance.Face.Detection.Attendance;

import java.util.Scanner;

public class MainApplication {
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name for the detected face(s): ");
        String name = scanner.nextLine();
        String inputImagePath = "C:\\Users\\HP\\Downloads\\Face-Detection-Attendance\\inputimg.jpg";
        String outputImagePath = "C:\\Users\\HP\\Downloads\\Face-Detection-Attendance\\outputimg.jpg";

        // Start face detection
        System.out.println("INFO: Starting face detection...");
        FaceDetector.detectFaces(inputImagePath, outputImagePath);
        System.out.println("INFO: Face detection completed. Processed image saved at: " + outputImagePath);

        // Save face details to database
        System.out.println("INFO: Saving face details to the database...");
        DatabaseHelper databaseHelper = new DatabaseHelper();
		databaseHelper.saveFace(name, outputImagePath);
        System.out.println("INFO: Face details saved successfully.");

        System.out.println("Face detection and database save completed successfully!");
        System.out.println("INFO: Application execution completed.");

        scanner.close();
    }
}


class DatabaseHelper1 {
    public void saveFace(String name, String imagePath) {
      
        System.out.println("Saving face for " + name + " with image at " + imagePath);
       
    }
}

class FaceDetector1 {
    public void detectFaces(String inputImagePath, String outputImagePath) {
        System.out.println("Detecting faces in " + inputImagePath + " and saving to " + outputImagePath);
      
    }
}