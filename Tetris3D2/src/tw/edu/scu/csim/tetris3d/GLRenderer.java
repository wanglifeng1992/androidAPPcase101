/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/


package tw.edu.scu.csim.tetris3d;



import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

//import com.example.android.gps.HelloItemizedOverlay;

//import com.example.android.apis.graphics.kube.Cube;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

class GLRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = "GLRenderer";
	static Context context;
	RussiaCube cube = new RussiaCube();
	static ArrayList<RussiaCube> GLCubeL = new ArrayList<RussiaCube>();
	private long startTime;
	private long fpsStartTime;
	private long numFrames;
	static float dddX;
	static float dddY;
	static boolean control = false;
	RussiaCube GLCubeX = new RussiaCube();

	GLRenderer(Context context) {
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		boolean SEE_THRU = true;

		startTime = System.currentTimeMillis();
		fpsStartTime = startTime;
		numFrames = 0;
		
		float[] lightPosition6 = { 1000000.0f, 0f, 0f, 1f };
		float[] lightPosition5 = { 0f, 1000000.0f, 0f, 1f };
		float[] lightPosition4 = { 0f, 0f, 1000000.0f, 1f };
		
		float[] lightPosition3 = { 1000000.0f, 0f, 1000000.0f, 1f };
		float[] lightPosition2 = { 1000000.0f, 1000000.0f, 0f, 1f };
		float[] lightPosition = { 0f, 1000000.0f, 1000000.0f, 1f };
		// Define the lighting
		float lightAmbient[] = new float[] { 1f, 1f, 1f, 0.5f };
		float lightDiffuse[] = new float[] { 1f, 1f, 1.0f, 0.5f };

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glEnable(GL10.GL_LIGHT2);
		gl.glEnable(GL10.GL_LIGHT3);
		gl.glEnable(GL10.GL_LIGHT4);
		gl.glEnable(GL10.GL_LIGHT5);
		gl.glEnable(GL10.GL_LIGHT6);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);

		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition2, 0);
		
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_POSITION, lightPosition3, 0);
		
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_POSITION, lightPosition4, 0);
		
		gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT4, GL10.GL_POSITION, lightPosition5, 0);
		
		gl.glLightfv(GL10.GL_LIGHT5, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT5, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT5, GL10.GL_POSITION, lightPosition6, 0);
		// 从两个方向打光lightPosition lightPosition2

		// What is the cube made of?
		float matAmbient[] = new float[] { 1, 1, 1, 1 };
		float matDiffuse[] = new float[] { 1, 1, 1, 1 };
		float matAmbient2[] = new float[] { 0.1f, 0.1f, 0.1f, 0.1f };
		float matDiffuse2[] = new float[] { 0.5f, 0.5f, 0.5f, 0.5f };

		// 设定位置
		// 设定环境光系数
		// 设定散设系数

		// Set up any OpenGL options we need
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		if (SEE_THRU) {

		}

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float ratio = (float) width / height;
		GLU.gluPerspective(gl, 45.0f, ratio, 1, 100f);

	}

	public void onDrawFrame(GL10 gl) {

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		for (int i = 0; i < GLCubeL.size(); i++) {
			GLCubeL.get(i).draw(gl);
		}

	}

}