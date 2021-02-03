import javax.xml.bind.annotation.XmlType;

@XmlType(name = "term")
public class Term {
    private double coefficient;
    private double power;

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
