import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Lighting implements GLEventListener {
    private static boolean cullFace = true;
    private static Area area;

    public Lighting() {
    }

    public static void main(String[] args) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AreaConfig.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            AreaConfig areaConfig = (AreaConfig) unmarshaller.unmarshal(Lighting.class.getClassLoader().getResourceAsStream("AreaConfig.xml"));
            area = new Area(areaConfig);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Lighting());
        canvas.setBounds(0, 0, frame.getWidth(), frame.getHeight() - 30);
        canvas.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    Camera.setHorizontalAngle(Camera.getHorizontalAngle() - 2.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    Camera.setHorizontalAngle(Camera.getHorizontalAngle() + 2.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    Camera.setVerticalAngle(Camera.getVerticalAngle() + 2.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    Camera.setVerticalAngle(Camera.getVerticalAngle() - 2.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    Camera.setLenToCenter(Camera.getLenToCenter() + 1.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_E) {
                    Camera.setLenToCenter(Camera.getLenToCenter() - 1.0D);
                    Camera.calcView();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Excel.createTable(area.getAreaPositions(), area.getAreaValues(), area.getInsideLamp());
                } else if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
                    cullFace = !cullFace;
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        frame.add(canvas);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_LIGHT0);

        gl.glFrontFace(GL2.GL_CCW);
        gl.glCullFace(GL2.GL_FRONT);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glAlphaFunc(GL2.GL_GREATER, 0);
        gl.glEnable(GL2.GL_BLEND);
        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Camera.calcView();
        area.build();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        double h = (double) width / (double) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);
        glu.gluPerspective(45.0D, h, 0.5D, 1000.0D);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        GLUT glut = new GLUT();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(Camera.getxView(), Camera.getyView(), Camera.getzView(), 0.0D, 0.0D, 0.0D, 0.0D, 0.5D, 0.0D);
        if (cullFace) {
            gl.glEnable(GL2.GL_CULL_FACE);
            gl.glLightiv(GL2.GL_LIGHT0, GL2.GL_POSITION, new int[]{0, 1, 0, 0}, 0);
        } else {
            gl.glDisable(GL2.GL_CULL_FACE);
            gl.glLightiv(GL2.GL_LIGHT0, GL2.GL_POSITION, new int[]{0, -1, 0, 0}, 0);
        }
        area.draw(drawable);
        gl.glWindowPos2i(drawable.getSurfaceWidth() - 300, drawable.getSurfaceHeight() - 20);
        glut.glutBitmapString(8, "w,s,a,d,q,e - camera");
        gl.glWindowPos2i(drawable.getSurfaceWidth() - 300, drawable.getSurfaceHeight() - 40);
        glut.glutBitmapString(8, "enter - export to excel");
        gl.glWindowPos2i(drawable.getSurfaceWidth() - 300, drawable.getSurfaceHeight() - 60);
        glut.glutBitmapString(8, "caps lock - toggle cull front faces");
        gl.glFlush();
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
    }
}
