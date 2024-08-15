package org.example;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadLocally();

        System.out.println(Core.getVersionString());

        VideoCapture videoCapture = new VideoCapture(1);

        // Read camera data
        Mat img = new Mat();
        videoCapture.read(img);

        Imgcodecs imageCodecs = new Imgcodecs();
        imageCodecs.imwrite("source.png", img);

        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HSV);

        imageCodecs.imwrite("hsv.png", img);

        Scalar upper = new Scalar(240, 184, 250);
        Scalar lower = new Scalar(15, 87, 90);

        Mat rangeMat = new Mat();
        Core.inRange(img, lower, upper, rangeMat);

        imageCodecs.imwrite("range.png", rangeMat);
    }
}