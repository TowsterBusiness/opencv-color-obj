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

        Scalar upper = new Scalar(140, 144, 250);
        Scalar lower = new Scalar(85, 84, 60);

        Mat color = Mat.zeros(300, 300, CvType.CV_8UC3);
        color.setTo(lower);
        Imgproc.cvtColor(color, color, Imgproc.COLOR_HSV2BGR);
        imageCodecs.imwrite("lower.png", color);
        color.setTo(upper);
        Imgproc.cvtColor(color, color, Imgproc.COLOR_HSV2BGR);
        imageCodecs.imwrite("upper.png", color);

        Mat rangeMat = new Mat();
        Core.inRange(img, lower, upper, rangeMat);

        imageCodecs.imwrite("range.png", rangeMat);
    }
}