import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "areaConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class AreaConfig {
    private double width;
    private double height;
    private double depth;
    private double step;
    private double[] boundaryValues;
    private List<Lamp> lamps = new ArrayList<>();

    public AreaConfig() {
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return this.depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getStep() {
        return this.step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double[] getBoundaryValues() {
        return this.boundaryValues;
    }

    public void setBoundaryValues(double[] boundaryValues) {
        this.boundaryValues = boundaryValues;
    }

    public List<Lamp> getLamps() {
        return this.lamps;
    }

    public void setLamps(List<Lamp> lamps) {
        this.lamps = lamps;
    }
}
