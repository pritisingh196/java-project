package Face.Detection.Attendance.Face.Detection.Attendance;

import org.opencv.core.*;
	import org.opencv.imgcodecs.Imgcodecs;
	import org.opencv.imgproc.Imgproc;
	import org.opencv.objdetect.CascadeClassifier;

	public class FaceDetection {
	    static {
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    }

	    public static void detectFaces(String imagePath, String outputPath) {
	        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
	        Mat image = Imgcodecs.imread(imagePath);

	        MatOfRect faceDetections = new MatOfRect();
	        faceDetector.detectMultiScale(image, faceDetections);

	        System.out.println("Detected " + faceDetections.toArray().length + " faces.");

	        for (Rect rect : faceDetections.toArray()) {
	            Imgproc.rectangle(image, 
	                new Point(rect.x, rect.y), 
	                new Point(rect.x + rect.width, rect.y + rect.height), 
	                new Scalar(0, 255, 0));
	        }

	        Imgcodecs.imwrite(outputPath, image);
	    }
	}


