import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Area {
    private final AreaConfig areaConfig;
    private final List<MetaBall> metaBalls = new ArrayList<>();
    private double[][][][] areaPositions;
    private double[][][] areaValues;
    private boolean[][][] insideLamp;

    Area(AreaConfig areaConfig) {
        this.areaConfig = areaConfig;
    }

    private static double[] interpolate(double[] positionA, double[] positionB, double valueA, double valueB, double boundaryValue) {
        double[] position = new double[3];
        if (Math.abs(boundaryValue - valueA) < 5.0E-15D) {
            return positionA;
        } else if (Math.abs(boundaryValue - valueB) < 5.0E-15D) {
            return positionB;
        } else if (Math.abs(valueA - valueB) < 5.0E-15D) {
            return positionA;
        } else {
            double value = (boundaryValue - valueA) / (valueB - valueA);
            position[0] = positionA[0] + value * (positionB[0] - positionA[0]);
            position[1] = positionA[1] + value * (positionB[1] - positionA[1]);
            position[2] = positionA[2] + value * (positionB[2] - positionA[2]);
            return position;
        }
    }

    public FloatBuffer toWindowCoords(GLAutoDrawable drawable, float x, float y, float z) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        FloatBuffer modelView = FloatBuffer.allocate(16);
        FloatBuffer projection = FloatBuffer.allocate(16);
        IntBuffer viewport = IntBuffer.allocate(4);

        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelView);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

        FloatBuffer outputCoords = FloatBuffer.allocate(3);

        glu.gluProject(x, y, z, modelView, projection, viewport, outputCoords);
        outputCoords.put(2, viewport.get(3) - outputCoords.get(2));

        return outputCoords;
    }

    public void draw(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        for (int i = 0; i < this.metaBalls.size(); ++i) {
            MetaBall metaBall = this.metaBalls.get(i);
            gl.glColor3d(metaBall.getRed(), metaBall.getGreen(), metaBall.getBlue());
            gl.glWindowPos2i(20, (metaBalls.size() - i) * 20);
            glut.glutBitmapString(8, metaBall.getAbsoluteBoundaryValue() + "");
            for (Triangle triangle : metaBall.getTriangles()) {
                double[] triangleNormal = triangle.getTriangleNormal();
                double[] triangleVertexA = triangle.getTriangleVertexA();
                double[] triangleVertexB = triangle.getTriangleVertexB();
                double[] triangleVertexC = triangle.getTriangleVertexC();
                gl.glPushMatrix();
                gl.glNormal3d(triangleNormal[0], triangleNormal[1], triangleNormal[2]);
                gl.glBegin(GL2.GL_TRIANGLES);
                gl.glVertex3d(triangleVertexA[0], triangleVertexA[1], triangleVertexA[2]);
                gl.glVertex3d(triangleVertexB[0], triangleVertexB[1], triangleVertexB[2]);
                gl.glVertex3d(triangleVertexC[0], triangleVertexC[1], triangleVertexC[2]);
                gl.glEnd();
                gl.glPopMatrix();
            }
        }
        gl.glColor3d(1.0D, 0.0D, 0.0D);
        for (Lamp lamp : this.areaConfig.getLamps()) {
            gl.glPushMatrix();
            gl.glTranslated(lamp.getX(), lamp.getY(), lamp.getZ());
            glut.glutWireSphere(lamp.getR(), 15, 15);
            gl.glPopMatrix();
        }
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glColor4d(0.3D, 0.3D, 0.3D, 0.5D);
        gl.glPushMatrix();
        gl.glTranslated(this.areaConfig.getWidth() / 4.0D, this.areaConfig.getHeight() / 4.0D, this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(this.areaConfig.getWidth() / 4.0D, this.areaConfig.getHeight() / 4.0D, -this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(this.areaConfig.getWidth() / 4.0D, -this.areaConfig.getHeight() / 4.0D, this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(this.areaConfig.getWidth() / 4.0D, -this.areaConfig.getHeight() / 4.0D, -this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-this.areaConfig.getWidth() / 4.0D, this.areaConfig.getHeight() / 4.0D, this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-this.areaConfig.getWidth() / 4.0D, this.areaConfig.getHeight() / 4.0D, -this.areaConfig.getDepth() / 4.0D);
        gl.glScaled(this.areaConfig.getWidth() / 2.0D, this.areaConfig.getHeight() / 2.0D, this.areaConfig.getDepth() / 2.0D);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-this.areaConfig.getWidth() / 4.0F, -this.areaConfig.getHeight() / 4.0F, this.areaConfig.getDepth() / 4.0F);
        gl.glScaled(this.areaConfig.getWidth() / 2.0F, this.areaConfig.getHeight() / 2.0F, this.areaConfig.getDepth() / 2.0F);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-this.areaConfig.getWidth() / 4.0F, -this.areaConfig.getHeight() / 4.0F, -this.areaConfig.getDepth() / 4.0F);
        gl.glScaled(this.areaConfig.getWidth() / 2.0F, this.areaConfig.getHeight() / 2.0F, this.areaConfig.getDepth() / 2.0F);
        glut.glutWireCube(1.0F);
        gl.glPopMatrix();

        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glBegin(GL2.GL_LINES);
        gl.glLineWidth(5.0F);
        gl.glColor3d(1.0D, 1.0D, 1.0D);

        gl.glVertex3d(-areaConfig.getWidth() / 2.0D, -areaConfig.getHeight() / 2.0D, -areaConfig.getDepth() / 2.0D);
        gl.glVertex3d(areaConfig.getWidth() / 1.5D, -areaConfig.getHeight() / 2.0D, -areaConfig.getDepth() / 2.0D);
        gl.glVertex3d(-areaConfig.getWidth() / 2.0D, -areaConfig.getHeight() / 2.0D, -areaConfig.getDepth() / 2.0D);
        gl.glVertex3d(-areaConfig.getWidth() / 2.0D, areaConfig.getHeight() / 1.5D, -areaConfig.getDepth() / 2.0D);
        gl.glVertex3d(-areaConfig.getWidth() / 2.0D, -areaConfig.getHeight() / 2.0D, -areaConfig.getDepth() / 2.0D);
        gl.glVertex3d(-areaConfig.getWidth() / 2.0D, -areaConfig.getHeight() / 2.0D, areaConfig.getDepth() / 1.5D);

        gl.glEnd();

        for (float x = (float) -areaConfig.getWidth() / 2.0F; x <= areaConfig.getWidth() / 2.0F; x += 1.0F) {
            gl.glWindowPos2fv(toWindowCoords(drawable, x, (float) -areaConfig.getHeight() / 2.0F, (float) -areaConfig.getDepth() / 2.0F));
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "   " + x);
        }
        for (float y = (float) -areaConfig.getHeight() / 2.0F; y <= areaConfig.getHeight() / 2.0F; y += 1.0F) {
            gl.glWindowPos2fv(toWindowCoords(drawable, (float) -areaConfig.getWidth() / 2.0F, y, (float) -areaConfig.getDepth() / 2.0F));
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "   " + y);
        }
        for (float z = (float) -areaConfig.getDepth() / 2.0F; z <= areaConfig.getDepth() / 2.0F; z += 1.0F) {
            gl.glWindowPos2fv(toWindowCoords(drawable, (float) -areaConfig.getWidth() / 2.0F, (float) -areaConfig.getHeight() / 2.0F, z));
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "    " + z);
        }
        gl.glWindowPos2fv(toWindowCoords(drawable, (float) areaConfig.getWidth() / 1.5F, (float) -areaConfig.getHeight() / 2.0F, (float) -areaConfig.getDepth() / 2.0F));
        glut.glutBitmapString(8, " x");
        gl.glWindowPos2fv(toWindowCoords(drawable, (float) -areaConfig.getWidth() / 2.0F, (float) (areaConfig.getHeight() / 1.5F), (float) -areaConfig.getDepth() / 2.0F));
        glut.glutBitmapString(8, " y");
        gl.glWindowPos2fv(toWindowCoords(drawable, (float) -areaConfig.getWidth() / 2.0F, (float) -areaConfig.getHeight() / 2.0F, (float) (areaConfig.getDepth() / 1.5F)));
        glut.glutBitmapString(8, " z");
    }

    public double[][][][] getAreaPositions() {
        return areaPositions;
    }

    public double[][][] getAreaValues() {
        return areaValues;
    }

    public boolean[][][] getInsideLamp() {
        return insideLamp;
    }

    public void build() {
        int xStepCount = (int) (this.areaConfig.getWidth() / this.areaConfig.getStep());
        int yStepCount = (int) (this.areaConfig.getHeight() / this.areaConfig.getStep());
        int zStepCount = (int) (this.areaConfig.getDepth() / this.areaConfig.getStep());
        areaPositions = new double[xStepCount + 1][yStepCount + 1][zStepCount + 1][3];
        areaValues = new double[xStepCount + 1][yStepCount + 1][zStepCount + 1];
        insideLamp = new boolean[xStepCount + 1][yStepCount + 1][zStepCount + 1];
        double maxValue = -Double.MAX_VALUE;
        for (int i = 0; i < xStepCount + 1; ++i) {
            for (int j = 0; j < yStepCount + 1; ++j) {
                for (int k = 0; k < zStepCount + 1; ++k) {
                    double[] position = new double[]{(double) i * this.areaConfig.getStep() - this.areaConfig.getWidth() / 2.0F, (double) j * this.areaConfig.getStep() - this.areaConfig.getHeight() / 2.0F, (double) k * this.areaConfig.getStep() - this.areaConfig.getDepth() / 2.0F};
                    areaPositions[i][j][k] = position;
                    double value = 0.0D;
                    for (Lamp lamp : this.areaConfig.getLamps()) {
                        double lengthToLamp = Math.sqrt(Math.pow(position[0] - lamp.getX(), 2.0D) + Math.pow(position[1] - lamp.getY(), 2.0D) + Math.pow(position[2] - lamp.getZ(), 2.0D));
                        if (lengthToLamp < lamp.getR()) {
                            insideLamp[i][j][k] = true;
                        }
                        for (Term term : lamp.getTerms()){
                            value += term.getCoefficient() * Math.pow(lengthToLamp, term.getPower());
                        }
                    }
                    areaValues[i][j][k] = value;
                    if (!insideLamp[i][j][k]) {
                        maxValue = Math.max(value, maxValue);
                    } /*else {
                        areaValues[i][j][k] = Double.MAX_VALUE;
                    }*/
                }
            }
        }
        for (double boundaryValue : this.areaConfig.getBoundaryValues()) {
            MetaBall metaBall = new MetaBall();
            metaBall.setRelativeBoundaryValue(boundaryValue);
            boundaryValue = boundaryValue * maxValue;
            metaBall.setAbsoluteBoundaryValue(boundaryValue);
            for (int i = 0; i < xStepCount; i += 1) {
                for (int j = 0; j < yStepCount; j += 1) {
                    for (int k = 0; k < zStepCount; k += 1) {
                        double[] cubeValues = new double[]{
                                areaValues[i][j][k],
                                areaValues[i + 1][j][k],
                                areaValues[i + 1][j][k + 1],
                                areaValues[i][j][k + 1],
                                areaValues[i][j + 1][k],
                                areaValues[i + 1][j + 1][k],
                                areaValues[i + 1][j + 1][k + 1],
                                areaValues[i][j + 1][k + 1]};
                        int cubeIndex = 0;
                        if (cubeValues[0] > boundaryValue) {
                            cubeIndex |= 1;
                        }
                        if (cubeValues[1] > boundaryValue) {
                            cubeIndex |= 2;
                        }
                        if (cubeValues[2] > boundaryValue) {
                            cubeIndex |= 4;
                        }
                        if (cubeValues[3] > boundaryValue) {
                            cubeIndex |= 8;
                        }
                        if (cubeValues[4] > boundaryValue) {
                            cubeIndex |= 16;
                        }
                        if (cubeValues[5] > boundaryValue) {
                            cubeIndex |= 32;
                        }
                        if (cubeValues[6] > boundaryValue) {
                            cubeIndex |= 64;
                        }
                        if (cubeValues[7] > boundaryValue) {
                            cubeIndex |= 128;
                        }
                        int edge = Tables.EDGES[cubeIndex];
                        if (edge != 0) {
                            double[][] cubePositions = new double[][]{
                                    areaPositions[i][j][k],
                                    areaPositions[i + 1][j][k],
                                    areaPositions[i + 1][j][k + 1],
                                    areaPositions[i][j][k + 1],
                                    areaPositions[i][j + 1][k],
                                    areaPositions[i + 1][j + 1][k],
                                    areaPositions[i + 1][j + 1][k + 1],
                                    areaPositions[i][j + 1][k + 1]};
                            double[][] vertexList = new double[12][3];
                            if ((edge & 1) > 0) {
                                vertexList[0] = interpolate(cubePositions[0], cubePositions[1], cubeValues[0], cubeValues[1], boundaryValue);
                            }
                            if ((edge & 2) > 0) {
                                vertexList[1] = interpolate(cubePositions[1], cubePositions[2], cubeValues[1], cubeValues[2], boundaryValue);
                            }
                            if ((edge & 4) > 0) {
                                vertexList[2] = interpolate(cubePositions[2], cubePositions[3], cubeValues[2], cubeValues[3], boundaryValue);
                            }
                            if ((edge & 8) > 0) {
                                vertexList[3] = interpolate(cubePositions[3], cubePositions[0], cubeValues[3], cubeValues[0], boundaryValue);
                            }
                            if ((edge & 16) > 0) {
                                vertexList[4] = interpolate(cubePositions[4], cubePositions[5], cubeValues[4], cubeValues[5], boundaryValue);
                            }
                            if ((edge & 32) > 0) {
                                vertexList[5] = interpolate(cubePositions[5], cubePositions[6], cubeValues[5], cubeValues[6], boundaryValue);
                            }
                            if ((edge & 64) > 0) {
                                vertexList[6] = interpolate(cubePositions[6], cubePositions[7], cubeValues[6], cubeValues[7], boundaryValue);
                            }
                            if ((edge & 128) > 0) {
                                vertexList[7] = interpolate(cubePositions[7], cubePositions[4], cubeValues[7], cubeValues[4], boundaryValue);
                            }
                            if ((edge & 256) > 0) {
                                vertexList[8] = interpolate(cubePositions[0], cubePositions[4], cubeValues[0], cubeValues[4], boundaryValue);
                            }
                            if ((edge & 512) > 0) {
                                vertexList[9] = interpolate(cubePositions[1], cubePositions[5], cubeValues[1], cubeValues[5], boundaryValue);
                            }
                            if ((edge & 1024) > 0) {
                                vertexList[10] = interpolate(cubePositions[2], cubePositions[6], cubeValues[2], cubeValues[6], boundaryValue);
                            }
                            if ((edge & 2048) > 0) {
                                vertexList[11] = interpolate(cubePositions[3], cubePositions[7], cubeValues[3], cubeValues[7], boundaryValue);
                            }
                            for (int triangleIndex = 0; Tables.TRIANGLES[cubeIndex][triangleIndex] != -1; triangleIndex += 3) {
                                double[] triangleVertexA = vertexList[Tables.TRIANGLES[cubeIndex][triangleIndex]];
                                double[] triangleVertexB = vertexList[Tables.TRIANGLES[cubeIndex][triangleIndex + 1]];
                                double[] triangleVertexC = vertexList[Tables.TRIANGLES[cubeIndex][triangleIndex + 2]];
                                double[] triangleNormal = new double[]{(triangleVertexB[1] - triangleVertexA[1]) * (triangleVertexC[2] - triangleVertexA[2]) - (triangleVertexC[1] - triangleVertexA[1]) * (triangleVertexB[2] - triangleVertexA[2]), (triangleVertexB[0] - triangleVertexA[0]) * (triangleVertexC[2] - triangleVertexA[2]) - (triangleVertexC[0] - triangleVertexA[0]) * (triangleVertexB[2] - triangleVertexA[2]), (triangleVertexB[0] - triangleVertexA[0]) * (triangleVertexC[1] - triangleVertexA[1]) - (triangleVertexC[0] - triangleVertexA[0]) * (triangleVertexB[1] - triangleVertexA[1])};
                                Triangle triangle = new Triangle();
                                triangle.setTriangleNormal(triangleNormal);
                                triangle.setTriangleVertexA(triangleVertexA);
                                triangle.setTriangleVertexB(triangleVertexB);
                                triangle.setTriangleVertexC(triangleVertexC);
                                metaBall.getTriangles().add(triangle);
                            }
                        }
                    }
                }
            }
            this.metaBalls.add(metaBall);
        }
    }
}
