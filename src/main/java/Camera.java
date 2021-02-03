public class Camera {
    private static double horizontalAngle = 45.0D;
    private static double verticalAngle = 45.0D;
    private static double lenToCenter = 75.0D;
    private static double xView;
    private static double yView;
    private static double zView;

    public Camera() {
    }

    public static double getHorizontalAngle() {
        return horizontalAngle;
    }

    public static void setHorizontalAngle(double horizontalAngle) {
        Camera.horizontalAngle = horizontalAngle;
    }

    public static double getVerticalAngle() {
        return verticalAngle;
    }

    public static void setVerticalAngle(double verticalAngle) {
        Camera.verticalAngle = verticalAngle;
    }

    public static double getLenToCenter() {
        return lenToCenter;
    }

    public static void setLenToCenter(double lenToCenter) {
        Camera.lenToCenter = lenToCenter;
    }

    public static double getxView() {
        return xView;
    }

    public static void setxView(double xView) {
        Camera.xView = xView;
    }

    public static double getyView() {
        return yView;
    }

    public static void setyView(double yView) {
        Camera.yView = yView;
    }

    public static double getzView() {
        return zView;
    }

    public static void setzView(double zView) {
        Camera.zView = zView;
    }

    public static void calcView() {
        setxView((double) (getLenToCenter() * Math.sin(Math.toRadians(getVerticalAngle())) * Math.cos(Math.toRadians(getHorizontalAngle()))));
        setyView((double) (getLenToCenter() * Math.cos(Math.toRadians(getVerticalAngle()))));
        setzView((double) (getLenToCenter() * Math.sin(Math.toRadians(getVerticalAngle())) * Math.sin(Math.toRadians(getHorizontalAngle()))));
    }
}
