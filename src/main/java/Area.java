import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Area {
    public static void main(String[] args) {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        final GLCanvas glcanvas = new GLCanvas(glcapabilities);
        final Frame frame = new Frame(AreaConstants.APP_NAME);
        frame.add(glcanvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                frame.remove(glcanvas);
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setSize(AreaConstants.WIDTH, AreaConstants.HEIGHT);
        frame.setVisible(true);

        glcanvas.addGLEventListener(new GLEventListener() {
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                final GL2 gl = glautodrawable.getGL().getGL2();
                gl.glViewport(0, 0, width, height);
                gl.glMatrixMode(GL2.GL_PROJECTION);
                gl.glLoadIdentity();
                final GLU glu = new GLU();
                final float h = (float) width / (float) height;
                glu.gluPerspective(45.0d, h, 0.5d, 100.0d);
                glu.gluLookAt(50.0d, 50.0d, 50.0d, 0d, 0d, 0d, 0d, 0.5d, 0d);
                gl.glMatrixMode(GL2.GL_MODELVIEW);
                gl.glLoadIdentity();
            }

            public void init(GLAutoDrawable glautodrawable) {
                final GL2 gl = glautodrawable.getGL().getGL2();
                gl.glEnable(GL2.GL_LIGHTING);
                gl.glEnable(GL2.GL_LIGHT0);
                gl.glLightiv(GL2.GL_LIGHT0, GL2.GL_POSITION, new int[]{1, 1, 1, 0}, 0);
                gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
            }

            public void dispose(GLAutoDrawable glautodrawable) {
            }

            public void display(GLAutoDrawable glautodrawable) {

            }
//            private void drawGrid(GLAutoDrawable glautodrawable) {
//                final GL2 gl = glautodrawable.getGL().getGL2();
//                gl.glColor3f(0.0f, 0.0f, 0.0f);
//                gl.glBegin(GL.GL_LINES);
//                for (int width = 0; width <= AreaConfig.getWidth(); width += AreaConfig.getStep()) {
//                    for (int height = 0; height <= AreaConfig.getHeight(); height += AreaConfig.getStep()) {
//                        gl.glVertex3f(width, height, 0.0f);
//                        gl.glVertex3f(width, height, AreaConfig.getDepth());
//                    }
//                }
//                for (int width = 0; width <= AreaConfig.getWidth(); width += AreaConfig.getStep()) {
//                    for (int depth = 0; depth <= AreaConfig.getHeight(); depth += AreaConfig.getStep()) {
//                        gl.glVertex3f(width, 0.0f, depth);
//                        gl.glVertex3f(width, AreaConfig.getHeight(), depth);
//                    }
//                }
//                for (int height = 0; height <= AreaConfig.getWidth(); height += AreaConfig.getStep()) {
//                    for (int depth = 0; depth <= AreaConfig.getHeight(); depth += AreaConfig.getStep()) {
//                        gl.glVertex3f(0.0f, height, depth);
//                        gl.glVertex3f(AreaConfig.getWidth(), height, depth);
//                    }
//                }
//                gl.glEnd();
//            }
        });
    }
}