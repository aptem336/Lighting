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

        glcanvas.addGLEventListener(new GLEventListener() {
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
            }

            public void init(GLAutoDrawable glautodrawable) {
            }

            public void dispose(GLAutoDrawable glautodrawable) {
            }

            public void display(GLAutoDrawable glautodrawable) {
                final com.jogamp.opengl.GL2 gl = glautodrawable.getGL().getGL2();
                gl.glBegin (GL2.GL_LINES);
                gl.glVertex3f(0.50f,-0.50f,0);
                gl.glVertex3f(-0.50f,0.50f,0);
                gl.glEnd();
            }
        });
    }
}