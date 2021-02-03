class Triangle {
    private double[] triangleVertexA;
    private double[] triangleVertexB;
    private double[] triangleVertexC;
    private double[] triangleNormal;

    Triangle() {
    }

    public double[] getTriangleVertexA() {
        return this.triangleVertexA;
    }

    public void setTriangleVertexA(double[] triangleVertexA) {
        this.triangleVertexA = triangleVertexA;
    }

    public double[] getTriangleVertexB() {
        return this.triangleVertexB;
    }

    public void setTriangleVertexB(double[] triangleVertexB) {
        this.triangleVertexB = triangleVertexB;
    }

    public double[] getTriangleVertexC() {
        return this.triangleVertexC;
    }

    public void setTriangleVertexC(double[] triangleVertexC) {
        this.triangleVertexC = triangleVertexC;
    }

    public double[] getTriangleNormal() {
        return this.triangleNormal;
    }

    public void setTriangleNormal(double[] triangleNormal) {
        this.triangleNormal = triangleNormal;
    }
}
