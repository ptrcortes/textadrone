package sample;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Detects faces in an image, draws boxes around them, and writes the results to
 * "faceDetection.png".
 *
 * @author Thomas Shucker
 */
public class DetectFaceDemo
{
	public void run()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		System.out.println("\nRunning DroneDetectFace");

		// Create a face detector from the cascade file in the resources
		// directory.

		File cascadeFile = new File("./resources/", "lbpcascade_frontalface.xml");
		CascadeClassifier faceDetector = new CascadeClassifier(cascadeFile.getAbsolutePath());
		System.out.println(faceDetector.empty());

		File[] folder = new File("./pictures/").listFiles();
		int len = 0;
		for (File f: folder)
		{
			File interestImage = new File(f.toString());
			Mat image = Highgui.imread(interestImage.getAbsolutePath());
			System.out.println(image.size());

			// Detect faces in the image.
			// MatOfRect is a special container class for Rect.
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(image, faceDetections);

			System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

			// Draw a bounding box around each face.
			for (Rect rect: faceDetections.toArray())
				Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

			if (faceDetections.toArray().length > 0)
			{
				// Save the visualized detection.
				String filename = "./pictures/ardrone" + len + ".png";
				System.out.println(String.format("Writing %s", filename));
				Highgui.imwrite(filename, image);
				len++;
			}
		}
	}
	
	public static void main(String[] args)
	{
		new DetectFaceDemo().run();
	}
}
