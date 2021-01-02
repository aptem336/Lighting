package graphic;

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

        glcanvas.addGLEventListener(new GLEventListener() {
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                GL2 gl2 = glautodrawable.getGL().getGL2();
                gl2.glMatrixMode(GL2.GL_PROJECTION);
                gl2.glLoadIdentity();

                GLU glu = new GLU();
                glu.gluOrtho2D(0.0f, width, 0.0f, height);

                gl2.glMatrixMode(GL2.GL_MODELVIEW);
                gl2.glLoadIdentity();

                gl2.glViewport(0, 0, width, height);
            }

            public void init(GLAutoDrawable glautodrawable) {
            }

            public void dispose(GLAutoDrawable glautodrawable) {
            }

            public void display(GLAutoDrawable glautodrawable) {
                GL2 gl2 = glautodrawable.getGL().getGL2();
                int width = glautodrawable.getSurfaceWidth();
                int height = glautodrawable.getSurfaceHeight();
                gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

                gl2.glLoadIdentity();
                gl2.glBegin(GL2ES3.GL_QUADS);
                gl2.glColor3f(1, 0, 0);
                gl2.glVertex2f(0, 0);
                gl2.glColor3f(0, 1, 0);
                gl2.glVertex2f(width, 0);
                gl2.glColor3f(0, 0, 1);
                gl2.glVertex2f(width / 2f, height);
                gl2.glColor3f(0, 0, 1);
                gl2.glVertex2f(width / 2f, height);
                gl2.glEnd();
            }
        });
        final Frame frame = new Frame(Constants.APP_NAME);
        frame.add(glcanvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                frame.remove(glcanvas);
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setSize(Constants.WIDTH, Constants.HEIGHT);
        frame.setVisible(true);
    }
}