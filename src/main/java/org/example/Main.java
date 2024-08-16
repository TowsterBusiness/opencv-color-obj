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

        Mat rangeMat = new Mat();
        Core.inRange(img, lower, upper, rangeMat);

        Core.bitwise_not(rangeMat, rangeMat);

        SimpleBlobDetector blobDetector = SimpleBlobDetector.create();
        SimpleBlobDetector_Params blobDetectorParams = blobDetector.getParams();
        blobDetectorParams.set_filterByArea(true);
        blobDetectorParams.set_maxArea(10000);
        blobDetectorParams.set_minArea(50);
        blobDetectorParams.set_filterByCircularity(false);
        blobDetectorParams.set_filterByConvexity(true);
        blobDetectorParams.set_maxConvexity(1000);
        blobDetectorParams.set_minConvexity(1);
        blobDetectorParams.set_filterByInertia(false);

        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        blobDetector.detect(rangeMat, keypoints2);

        Mat coloredRangedMat = new Mat();
        Imgproc.cvtColor(rangeMat, coloredRangedMat, Imgproc.COLOR_GRAY2BGR);
        List<KeyPoint> kps = keypoints2.toList();
        for (KeyPoint kp : kps) {
            int kpRadius = (int) kp.size / 2;
            Imgproc.rectangle(coloredRangedMat, new Point(kp.pt.x - kpRadius, kp.pt.y - kpRadius),
                    new Point(kp.pt.x + kpRadius, kp.pt.y + kpRadius), new Scalar(250, 50, 50), 3);
        }
    }
}