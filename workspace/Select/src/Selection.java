/*
 * Lesson03.java
 *
 * Created on July 14, 2003, 12:35 PM
 */

import java.awt.*;
import java.awt.event.*;
import java.nio.IntBuffer;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;

/**
 * Port of the NeHe OpenGL Tutorial to Java using the Jogl
 * interface to OpenGL. Jogl can be obtained at http://jogl.dev.java.net/
 * 
 * @author Kevin Duling (jattier@hotmail.com)
 */
public class Selection {
	static Animator animator = null;

	static class Renderer implements GLEventListener, KeyListener, MouseListener {
		private static final int BUFSIZE = 12; // numberOfObjects*nameStackMaxDepth*4
		private Point pickPoint = new Point();
		private int width, height;
		static int HEAD, BODY, WING_L, WING_R;
		float nx, ny;

		/**
		 * Called by the drawable to initiate OpenGL rendering by the client.
		 * After all GLEventListeners have been notified of a display event, the
		 * drawable will swap its buffers if necessary.
		 * 
		 * @param drawable
		 *            The GLAutoDrawable object.
		 */
		public void display(GLAutoDrawable drawable) {
			GL gl = drawable.getGL();
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			gl.glLoadIdentity();

			if (pickPoint.getX() != 0.0 && pickPoint.getY() != 0.0) {
				pickRects(gl);
				pickPoint = new Point(0, 0);
			}
			displayBug(gl, 5, 1f, 1f, 0f);
			drawRects(gl);
			gl.glFlush();
		}

		/**
		 * prints the names of what was hit and which was closest
		 * 
		 * @param hits number of objects hit
		 * @param buffer select buffer
		 */
		private void processHits(int hits, int buffer[]) {
			int names, ptr = 0;
			int minZ = Integer.MAX_VALUE, minName = -1;
			int z;

			System.out.print("hit: ");
			for (int i = 0; i < hits; i++) {
				names = buffer[ptr];
				ptr++;
				z = buffer[ptr];
				ptr += 2;
				for (int j = 0; j < names; j++) {
					System.out.print(buffer[ptr] + " ");
					if (z < minZ) {
						minZ = z;
						minName = buffer[ptr];
					}
					ptr++;
				}
			}
			System.out.println("\nmin hit: " + minName);
		}

		/**
		 * draw three rectangles at different depths with different names
		 * 
		 * @param gl gl object
		 */
		private void drawRects(GL gl) {
			gl.glLoadName(1);
			gl.glBegin(GL.GL_QUADS);
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex3f(-2.0f, -1.0f, -5.0f);
			gl.glVertex3f(-2.0f, 1.0f, -5.0f);
			gl.glVertex3f(1.0f, 1.0f, -5.0f);
			gl.glVertex3f(1.0f, -1.0f, -5.0f);
			gl.glEnd();

			gl.glLoadName(2);
			gl.glBegin(GL.GL_QUADS);
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, -1.0f, -6.0f);
			gl.glVertex3f(0.0f, 2.0f, -6.0f);
			gl.glVertex3f(2.0f, 2.0f, -6.0f);
			gl.glVertex3f(2.0f, -1.0f, -6.0f);
			gl.glEnd();
			
			gl.glLoadName(3);
			gl.glBegin(GL.GL_QUADS);
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex3f(-1.0f, -2.0f, -7.0f);
			gl.glVertex3f(-1.0f, 0.0f, -7.0f);
			gl.glVertex3f(2.0f, 0.0f, -7.0f);
			gl.glVertex3f(2.0f, -2.0f, -7.0f);
			gl.glEnd();
			
			gl.glLoadName(4);
			gl.glBegin(GL.GL_TRIANGLES);
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(-2.25f, -3.5f, -4.0f);
			gl.glVertex3f(0.0f, -1.5f, -4.0f);
			gl.glVertex3f(0.0f, 0.0f, -4.0f);
			gl.glEnd();
		}
		
		/**
		 * @description Displays a bug at the point where the user clicked 
		 * @param bugId
		 * @param xPos
		 * @param yPos
		 * @param bugAngle
		 */
		public void displayBug (GL gl, int bugId, float xPos, float yPos, float bugAngle) {
			gl.glPushMatrix();
			gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);
			gl.glTranslatef(xPos, yPos, 0.0f);
			gl.glRotatef(bugAngle, 0.0f, 0.0f, 1.0f);
			gl.glLoadName(bugId);
			gl.glPushName(HEAD);
			gl.glCallList(HEAD);
			gl.glPopName();
			gl.glPushName(BODY);
			gl.glCallList(BODY);
			gl.glPopName();
			gl.glPushName(WING_L);
			gl.glCallList(WING_L);
			gl.glPopName();
			gl.glPushName(WING_R);
			gl.glCallList(WING_R);
			gl.glPopName();
			gl.glPopAttrib();
			gl.glPopMatrix();
		}

		/**
		 * sets up selection mode, name stack, and projection matrix for
		 * picking. Then the objects are drawn.
		 * 
		 * @param gl gl object
		 */
		private void pickRects(GL gl) {
			int[] selectBuf = new int[BUFSIZE];
			IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);
			int hits;
			int viewport[] = new int[4];

			gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

			gl.glSelectBuffer(BUFSIZE, selectBuffer);
			gl.glRenderMode(GL.GL_SELECT);

			gl.glInitNames();
			gl.glPushName(-1);

			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix();
			gl.glLoadIdentity();
			/* create 1x1 pixel picking region near cursor location */
			GLU glu = new GLU();
			glu.gluPickMatrix((double) pickPoint.x,
					(double) (viewport[3] - pickPoint.y), //
					1.0, 1.0, viewport, 0);
			glu.gluPerspective(45.0f, (double) width / (double) height, 4.0f,
					8.0f);

			drawRects(gl);
			gl.glPopMatrix();
			gl.glFlush();

			hits = gl.glRenderMode(GL.GL_RENDER);
			selectBuffer.get(selectBuf);
			if(hits > 0)
				processHits(hits, selectBuf);
			gl.glMatrixMode(GL.GL_MODELVIEW);
		}

		/**
		 * Called when the display mode has been changed. <B>!! CURRENTLY
		 * UNIMPLEMENTED IN JOGL !!</B>
		 * 
		 * @param drawable
		 *            The GLAutoDrawable object.
		 * @param modeChanged
		 *            Indicates if the video mode has changed.
		 * @param deviceChanged
		 *            Indicates if the video device has changed.
		 */
		public void displayChanged(GLAutoDrawable drawable,
				boolean modeChanged, boolean deviceChanged) {
		}

		/**
		 * Called by the drawable immediately after the OpenGL context is
		 * initialized for the first time. Can be used to perform one-time
		 * OpenGL initialization such as setup of lights and display lists.
		 * 
		 * @param drawable
		 *            The GLAutoDrawable object.
		 */
		public void init(GLAutoDrawable drawable) {
			final GL gl = drawable.getGL();
			gl.glShadeModel(GL.GL_SMOOTH);
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glClearDepth(1.0f);
			gl.glEnable(GL.GL_DEPTH_TEST);
			gl.glDepthFunc(GL.GL_LEQUAL);
			gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
			
			HEAD = gl.glGenLists(1);
			BODY = gl.glGenLists(2);
			WING_L = gl.glGenLists(3);
			WING_R = gl.glGenLists(4);

			gl.glNewList(HEAD, GL.GL_COMPILE);
			gl.glBegin(GL.GL_POLYGON);
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex3f(0.0f, 0.0f, -4.0f);
			gl.glVertex3f(0.5f, 0.25f, -4.0f);
			gl.glVertex3f(0.5f, 0.5f, -4.0f);
			gl.glVertex3f(-0.5f, 0.5f, -4.0f);
			gl.glVertex3f(-0.5f, 0.25f, -4.0f);
			gl.glEnd();
			gl.glEndList();

			gl.glNewList(BODY, GL.GL_COMPILE);
			gl.glBegin(GL.GL_POLYGON);
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex3f(0.0f, -3.0f, 0.0f);
			gl.glVertex3f(0.5f, -2.5f, 0.0f);
			gl.glVertex3f(0.5f, -0.5f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(-0.5f, -0.5f, 0.0f);
			gl.glVertex3f(-0.5f, -2.5f, 0.0f);
			gl.glEnd();
			gl.glEndList();

			gl.glNewList(WING_L, GL.GL_COMPILE);
			gl.glBegin(GL.GL_TRIANGLES);
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(-2.25f, -3.5f, 0.0f);
			gl.glVertex3f(0.0f, -1.5f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glEnd();
			gl.glEndList();

			gl.glNewList(WING_R, GL.GL_COMPILE);
			gl.glBegin(GL.GL_TRIANGLES);
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex3f(2.25f, -3.5f, 0.0f);
			gl.glVertex3f(0.0f, -1.5f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glEnd();
			gl.glEndList();
		}

		/**
		 * Called by the drawable during the first repaint after the component
		 * has been resized. The client can update the viewport and view volume
		 * of the window appropriately, for example by a call to
		 * GL.glViewport(int, int, int, int); note that for convenience the
		 * component has already called GL.glViewport(int, int, int, int)(x, y,
		 * width, height) when this method is called, so the client may not have
		 * to do anything in this method.
		 * 
		 * @param drawable
		 *            The GLAutoDrawable object.
		 * @param x
		 *            The X Coordinate of the viewport rectangle.
		 * @param y
		 *            The Y coordinate of the viewport rectanble.
		 * @param width
		 *            The new width of the window.
		 * @param height
		 *            The new height of the window.
		 */
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
				int height) {
			final GL gl = drawable.getGL();
			final GLU glu = new GLU();

			this.width = width;
			this.height = height;

			gl.setSwapInterval(1);

			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();

			glu.gluPerspective(45.0f, (double) width / (double) height, 4.0f,
					8.0f);

			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
		}

		/**
		 * Invoked when a key has been pressed. See the class description for
		 * {@link KeyEvent} for a definition of a key pressed event.
		 * 
		 * @param e
		 *            The KeyEvent.
		 */
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				animator.stop();
				System.exit(0);
			}
		}

		/**
		 * Invoked when a key has been released. See the class description for
		 * {@link KeyEvent} for a definition of a key released event.
		 * 
		 * @param e
		 *            The KeyEvent.
		 */
		public void keyReleased(KeyEvent e) {
		}

		/**
		 * Invoked when a key has been typed. See the class description for
		 * {@link KeyEvent} for a definition of a key typed event.
		 * 
		 * @param e
		 *            The KeyEvent.
		 */
		public void keyTyped(KeyEvent e) {
		}

		public void mouseClicked(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		/**
		 * Invoked when a mouse button is released,
		 * records the location of the event,
		 * which triggers a hit test
		 * 
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent event) {
			pickPoint = event.getPoint();
		}
	}

	/**
	 * Program's main entry point
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		Frame frame = new Frame("Selection");
		GLCanvas canvas = new GLCanvas();
		Renderer renderer = new Renderer();
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		frame.add(canvas);
		frame.setSize(640, 480);
		animator = new Animator(canvas);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop();
				System.exit(0);
			}
		});
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}
}
