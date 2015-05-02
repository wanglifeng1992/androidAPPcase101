/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package tw.edu.scu.csim.tetris3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

class RussiaCube {
	float RussiaX = 0;
	float RussiaY = 0;
	int translate = 1;
	int translate2 = 1;
	float MoveX;
	float MoveY = 6f;
	float distance = -20f;
	float shapeX;
	float shapeY;
	int place1X;
	int place1Y;
	boolean Xtrue = true;

	int place1XS;
	int place1YS;

	public void setplace() {

		place1X = 7;
		place1Y = 2;

	}

	public void draw(GL10 gl) {

		RussiaCubeX cubeX = new RussiaCubeX();

		cubeX.draw(gl);

	}

	class RussiaCubeX {

		GLCubeX cube2 = new GLCubeX();

		public void draw(GL10 gl) {

			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(MoveX, MoveY, distance);
			gl.glRotatef(RussiaX, 0, 1, 0);
			gl.glRotatef(RussiaY, 1, 0, 0);

			cube2.draw(gl);

		}

		class GLCubeX {
			// rivate Context context;
			GLCube cube1 = new GLCube();
			GLCube2 cube2 = new GLCube2();
			GLCube3 cube3 = new GLCube3();
			GLCube4 cube4 = new GLCube4();
			GLCube5 cube5 = new GLCube5();
			GLCube6 cube6 = new GLCube6();

			public void draw(GL10 gl) {
				cube1.loadTexture(gl, GLRenderer.context, R.drawable.d1);
				cube1.draw(gl);
				cube6.loadTexture(gl, GLRenderer.context, R.drawable.d6);
				cube6.draw(gl);
				cube3.loadTexture(gl, GLRenderer.context, R.drawable.d3);
				cube3.draw(gl);
				cube4.loadTexture(gl, GLRenderer.context, R.drawable.d4);
				cube4.draw(gl);
				cube2.loadTexture(gl, GLRenderer.context, R.drawable.d2);
				cube2.draw(gl);
				cube5.loadTexture(gl, GLRenderer.context, R.drawable.d5);
				cube5.draw(gl);
			}

			class GLCube {
				private final IntBuffer mVertexBuffer;

				private final IntBuffer mTextureBuffer;

				public GLCube() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {
							// FRONT
							-half, -half, half, half, -half, half, -half, half,
							half, half, half, half,

					};

					int texCoords[] = {
							// FRONT
							0, one, one, one, 0, 0, one, 0,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer = vbb.asIntBuffer();
					mVertexBuffer.put(vertices);
					mVertexBuffer.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer = tbb.asIntBuffer();
					mTextureBuffer.put(texCoords);
					mTextureBuffer.position(0);

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);

					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);

					gl.glColor4f(1, 1, 1, 0.6f);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d1);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

			class GLCube2 {
				private final IntBuffer mVertexBuffer2;

				private final IntBuffer mTextureBuffer2;

				public GLCube2() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {

							// BACK
							-half, -half, -half, -half, half, -half, half,
							-half, -half, half, half, -half,

					};

					int texCoords[] = {

							// BACK
							one, one, one, 0, 0, one, 0, 0,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer2 = vbb.asIntBuffer();
					mVertexBuffer2.put(vertices);
					mVertexBuffer2.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer2 = tbb.asIntBuffer();
					mTextureBuffer2.put(texCoords);
					mTextureBuffer2.position(0);

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer2);

					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer2);

					gl.glColor4f(1, 1, 1, 0.6f);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d2);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

			class GLCube3 {
				private final IntBuffer mVertexBuffer;

				private final IntBuffer mTextureBuffer;

				public GLCube3() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {
							// LEFT
							-half, -half, half, -half, half, half, -half,
							-half, -half, -half, half, -half,

					};

					int texCoords[] = {

							// LEFT
							one, one, one, 0, 0, one, 0, 0,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer = vbb.asIntBuffer();
					mVertexBuffer.put(vertices);
					mVertexBuffer.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer = tbb.asIntBuffer();
					mTextureBuffer.put(texCoords);
					mTextureBuffer.position(0);

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);

					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);

					gl.glColor4f(1, 1, 1, 0.6f);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d3);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

			class GLCube4 {
				private final IntBuffer mVertexBuffer;
				private final IntBuffer mTextureBuffer;

				public GLCube4() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {

					half, -half, -half, half, half, -half, half, -half, half,
							half, half, half,

					};

					int texCoords[] = {

					one, one, one, 0, 0, one, 0, 0,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer = vbb.asIntBuffer();
					mVertexBuffer.put(vertices);
					mVertexBuffer.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer = tbb.asIntBuffer();
					mTextureBuffer.put(texCoords);
					mTextureBuffer.position(0);

					// ...

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);

					gl.glColor4f(1, 1, 1, 0.6f);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d4);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

			class GLCube5 {
				private final IntBuffer mVertexBuffer;
				private final IntBuffer mTextureBuffer;

				public GLCube5() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {

							// TOP
							-half, half, half, half, half, half, -half, half,
							-half, half, half, -half,

					};

					int texCoords[] = {

							// TOP
							one, 0, 0, 0, one, one, 0, one,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer = vbb.asIntBuffer();
					mVertexBuffer.put(vertices);
					mVertexBuffer.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer = tbb.asIntBuffer();
					mTextureBuffer.put(texCoords);
					mTextureBuffer.position(0);

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);

					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);

					gl.glColor4f(1, 1, 1, 0.6f);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d5);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

			class GLCube6 {
				private final IntBuffer mVertexBuffer;

				private final IntBuffer mTextureBuffer;

				public GLCube6() {

					int one = 65536;
					int half = one / 2;
					int vertices[] = {

							// BOTTOM
							-half, -half, half, -half, -half, -half, half,
							-half, half, half, -half, -half,

					};

					int texCoords[] = {

							// BOTTOM
							0, 0, 0, one, one, 0, one, one,

					};

					ByteBuffer vbb = ByteBuffer
							.allocateDirect(vertices.length * 4);
					vbb.order(ByteOrder.nativeOrder());
					mVertexBuffer = vbb.asIntBuffer();
					mVertexBuffer.put(vertices);
					mVertexBuffer.position(0);

					// ...
					ByteBuffer tbb = ByteBuffer
							.allocateDirect(texCoords.length * 4);
					tbb.order(ByteOrder.nativeOrder());
					mTextureBuffer = tbb.asIntBuffer();
					mTextureBuffer.put(texCoords);
					mTextureBuffer.position(0);

				}

				public void draw(GL10 gl) {
					gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);

					gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);

					gl.glColor4f(1, 1, 1, 1);
					gl.glNormal3f(0, 0, 1);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				}

				void loadTexture(GL10 gl, Context context, int resource) {
					Bitmap bmp = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.d6);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
					gl.glTexParameterx(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					bmp.recycle();
				}

			}

		}

	}

}