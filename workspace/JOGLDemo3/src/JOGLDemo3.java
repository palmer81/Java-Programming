//Listing 3. JOGLDemo3.java

// JOGLDemo3.java

// This demo renders a smoothly shaded triangle and a flat shaded square. The
// scene is animated with the triangle and square rotating around the Y axis.
// Because polygon antialiasing is used to minimize jagged edges, depth buffer 
// testing must not be enabled -- it's performed manually by determining which
// geometric object to draw first based on the angle of rotation.

import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import javax.swing.*;

import com.sun.opengl.util.*;

public class JOGLDemo3 extends JFrame {
	final static int WIDTH = 300;
	final static int HEIGHT = 300;

	Animator animator;

	public JOGLDemo3() {
		super("JOGLDemo #3");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				new Thread() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});

		GLCapabilities caps = new GLCapabilities();
		caps.setAlphaBits(8);

		SceneRenderer sr = new SceneRenderer(caps);
		sr.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		getContentPane().add(sr);

		pack();
		setVisible(true);

		animator = new Animator(sr);
		// animator.setRunAsFastAsPossible (true);
		animator.start();
	}

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				new JOGLDemo3();
			}
		};
		EventQueue.invokeLater(r);
	}
}

class SceneRenderer extends GLCanvas implements GLEventListener {
	// The constants below identify the number of units between the viewpoint
	// and the near and far clipping planes.

	private final static double NEAR = 0.5; // Z values < NEAR are clipped
	private final static double FAR = 4.0; // Z values > FAR are clipped

	private GLU glu = new GLU();

	// Degrees to rotate.

	private float rotAngle;

	SceneRenderer(GLCapabilities caps) {
		super(caps);
		addGLEventListener(this);
	}

	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		// Enable polygon antialiasing.

		gl.glBlendFunc(GL.GL_SRC_ALPHA_SATURATE, GL.GL_ONE);
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_POLYGON_SMOOTH);
		gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
	}

	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		// Clear the drawing surface to the background color, which defaults to
		// black.

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Reset the modelview matrix.

		gl.glLoadIdentity();

		// The camera is currently positioned at the (0, 0, 0) origin, its lens
		// is pointing along the negative Z axis (0, 0, -1) into the screen, and
		// its orientation up-vector is (0, 1, 0). The following call positions
		// the camera at (0, 0, 2), points the lens towards the origin, and
		// keeps the same up-vector.

		glu.gluLookAt(0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		// Rotate the scene rotAngle degrees around the Y axis.

		gl.glRotatef(rotAngle, 1.0f, 1.0f, 1.0f);

		// Render the scene, manually performing depth buffer testing by
		// determining what gets drawn first.

		if (rotAngle < 158.0 || rotAngle > 202.0) {
			drawTriangle(gl);
			drawSquare(gl);
		} else {
			// Triangle is partially eclipsed by the square. Make sure that
			// square appears on top.

			drawSquare(gl);
			drawTriangle(gl);
		}

		// Increment the rotation angle by 0.5 degrees, making sure that it
		// doesn't exceed 360 degrees.

		rotAngle += 0.5f;
		rotAngle %= 360.0f;
	}

	void drawSquare(GL gl) {
		// Color the square red via flat shading, in contrast to the triangle's
		// smooth shading.

		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL.GL_FLAT);

		// Render a square one unit behind the origin along the negative Z axis.

		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(-0.25, 0.25, -1.0);
		gl.glVertex3d(-0.25, -0.25, -1.0);
		gl.glVertex3d(0.25, -0.25, -1.0);
		gl.glVertex3d(0.25, 0.25, -1.0);
		gl.glEnd();
	}

	void drawTriangle(GL gl) {
		// Make sure that smooth shading is enabled. This type of shading is
		// the default the first time display() is invoked. Because flat shading
		// is enabled later on, it becomes the new default. Without the
		// following method call, the triangle would also be flat shaded on
		// subsequent display() method invocations.

		gl.glShadeModel(GL.GL_SMOOTH);

		// Render a triangle one unit in front of the origin along the positive
		// Z axis.

		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // red at the top
		gl.glVertex3d(0.0, 1.0, 1.0);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // green at the lower left
		gl.glVertex3d(-1.0, -1.0, 1.0);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // blue at the lower right
		gl.glVertex3d(1.0, -1.0, 1.0);
		gl.glEnd();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// We don't need to invoke gl.glViewport(x, y, width, height) because
		// this is done automatically by JOGL prior to invoking reshape().

		// Because the modelview matrix is currently in effect, we need to
		// switch to the projection matrix before we can establish a perspective
		// view.

		gl.glMatrixMode(GL.GL_PROJECTION);

		// Reset the projection matrix.

		gl.glLoadIdentity();

		// Establish a perspective view in which any drawing in front of the
		// NEAR clipping plane or behind the FAR clipping plane is discarded.
		// The frustum is assigned the same aspect ratio as the viewport, to
		// prevent distortion.

		float aspect = (float) width / (float) height;
		gl.glFrustum(-aspect, aspect, -1.0f, 1.0f, NEAR, FAR);

		// From now on, we'll work with the modelview matrix.

		gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}
}
