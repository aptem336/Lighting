import java.util.ArrayList;
import java.util.List;

class MetaBall {
    private List<Triangle> triangles = new ArrayList<>();
    private double red;
    private double green;
    private double blue;
    private double relativeBoundaryValue;
    private double absoluteBoundaryValue;

    MetaBall() {
    }

    public double getRelativeBoundaryValue() {
        return this.relativeBoundaryValue;
    }

    public void setRelativeBoundaryValue(double boundaryValue) {
        blue = 0.0D;
        if (boundaryValue > 0.5D) {
            red = 1.0D;
            green = (1.0D - boundaryValue) * 2.0D;
        } else {
            green = 1.0d;
            red = boundaryValue * 2.0D;
        }
    }

    public double getAbsoluteBoundaryValue() {
        return absoluteBoundaryValue;
    }

    public void setAbsoluteBoundaryValue(double absoluteBoundaryValue) {
        this.absoluteBoundaryValue = absoluteBoundaryValue;
    }

    public List<Triangle> getTriangles() {
        return this.triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public double getRed() {
        return this.red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return this.green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return this.blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

}
