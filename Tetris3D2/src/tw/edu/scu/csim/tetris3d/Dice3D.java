/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package tw.edu.scu.csim.tetris3d;

import java.util.Random;


import android.app.Activity;
import android.app.Dialog;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class Dice3D extends Activity {
	GLView view;
	Random myRandomX = new Random();
	int X = 0;
	int Y = 0;
	int Z = 0;
	int R1 = 0;
	int R2 = 0;
	int R3 = 0;
	int shapeX = 15;
	int shapeY = 15;
	int rotatecount;
	int number;
	int counttouch;
	int count;
	int Vcount = 0;
	int Vcount2 = 0;
	int Vcount3 = 0;
	int ddd = 0;
	boolean thread2control= true;
	boolean roote = true;
	boolean translate1 = false;
	boolean translate2 = false;
	boolean DS = true;
	boolean DS2 = false;
	public boolean dddB = false;
	private SensorManager senorManager;
	 int i = 0;
	static int[] Xi;
	int threadtraslatC=0;
	static int[][] arr2 = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, };
	private static final int GALLERY = 0;
	private static final int MANU01 = 0;
	private static final int MANU02 = 1;
	private static final int MANU03 = 2;
	private static final int MANU04 = 3;
	Random myRandomX2 = new Random();
	Dialog Dialogs;
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      view = new GLView(this);
      setContentView(view);
      i=0;
      Dialogs = new Dialog(Dice3D.this);
      RussiaCube a = new RussiaCube();
		GLRenderer.GLCubeL.add(a);
		GLRenderer.GLCubeL.get(i).setplace();
		thread.start();
   }

   @Override
   protected void onPause() {
       super.onPause();
        view.onPause();
        thread.stop();
   		thread2.stop();
   		threadH.stop();         
	
   }
   @Override
   protected void onDestroy() {
       super.onDestroy();
    
       thread2control=false;
       
       	for (int j = 15; j > 1; j--) {
   			for (int i = 12; i > 1; i--) {
   				arr2[i][j] = 0;

   			}
   		}
   		for (int i = 0; i <= GLRenderer.GLCubeL.size()+1; i++) {

   			GLRenderer.GLCubeL.remove(i);

   		}
   		
   		thread.interrupt();
   		thread2.interrupt();
   		threadH.interrupt(); 
   		//threadX.interrupt();
   		i = 0;
      
		
   }
   @Override
   protected void onResume() {
       super.onResume();
       view.onResume();
      
   }
// ///////////////////////MANU设定//////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(GALLERY, MANU01, Menu.NONE, "说明").setShortcut('0', 'k')
				.setIcon(R.drawable.coin20);
		menu.add(GALLERY, MANU02, Menu.NONE, "结束").setShortcut('1', 's')
				.setIcon(R.drawable.coin50);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getGroupId()) {
		case GALLERY:
			String itemid = Integer.toString(item.getItemId());
			String title = item.getTitle().toString();
			if (item.getItemId() == 0) {//游戏说明
				Toast.makeText(this, "将点数放置於底下之行如点数和之值之个位数为1则加分",
						Toast.LENGTH_LONG).show();
				
			}
			if (item.getItemId() == 1) {//游戏结束

				thread2control=false;
                Dice3D.this.finish();
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	////////////////////////判断点萤幕////////////////////////////
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_UP:
			thread.stop();
			// 左右移动
			if (x > (GLRenderer.GLCubeL.get(i).place1X) * 25) {
				if (arr2[GLRenderer.GLCubeL.get(i).place1X + 1][GLRenderer.GLCubeL
						.get(i).place1Y] == 0) {

					GLRenderer.GLCubeL.get(i).place1X++;
					GLRenderer.GLCubeL.get(i).MoveX = GLRenderer.GLCubeL.get(i).MoveX + 1f;

				}

			}

			else if (x < (GLRenderer.GLCubeL.get(i).place1X - 1) * 25) {

				if (arr2[GLRenderer.GLCubeL.get(i).place1X - 1][GLRenderer.GLCubeL
						.get(i).place1Y] == 0) {
					GLRenderer.GLCubeL.get(i).place1X--;
					GLRenderer.GLCubeL.get(i).MoveX = GLRenderer.GLCubeL.get(i).MoveX - 1f;

				}
			} else {// 如点在上面则旋转
			    
				threadtraslatC=1;	
				thread.run();
			}

		}

		return true;
	}
	/////////////////////
	Thread thread2 = new Thread() {
		@Override
		public void run() {
			try {
				counttouch = 0;
				thread2control=true;
				while (thread2control) {
					if (arr2[GLRenderer.GLCubeL.get(i).place1X][GLRenderer.GLCubeL
							.get(i).place1Y + 1] == 0) {
						thread2.sleep(500);
						GLRenderer.GLCubeL.get(i).place1Y++;
						GLRenderer.GLCubeL.get(i).MoveY = GLRenderer.GLCubeL
								.get(i).MoveY - 1f;
					}
					if (arr2[GLRenderer.GLCubeL.get(i).place1X][GLRenderer.GLCubeL
							.get(i).place1Y + 1] != 0) {

						counttouch++;

						if (counttouch == 3) {
							arr2[GLRenderer.GLCubeL.get(i).place1X][GLRenderer.GLCubeL
									.get(i).place1Y] = number;
							
							for (int j = 15; j > 1; j--) {
								for (int i = 12; i > 1; i--) {
									if (Dice3D.arr2[i][3] != 0) {
										Dialogs.setTitle("游戏结束-共获得"+Vcount3+"分");
										threadH.run();
										Dice3D.this.finish();

									}
									Vcount = Vcount + Dice3D.arr2[i][j];
									if (Dice3D.arr2[i][j] == 0) {
										Vcount2++;
									}
								}
								if(j==GLRenderer.GLCubeL
										.get(i).place1Y)
								{	
								Dialogs.setTitle("此行为"+Vcount+"分");
								threadH.run();// 启动对话框
								}
								if (Vcount2 == 0) {
									if (Vcount % 10 == 1||Vcount % 10 == 3||Vcount % 10 == 5||Vcount % 10 == 7||Vcount % 10 == 9) {

									
										Vcount3 = Vcount3 + Vcount;
										Dialogs.setTitle("获得"+Vcount+"分-目前总共获得"+Vcount3+"分");
										threadH.run();// 启动对话框
										Vcount = 0;

									} else {
										Dialogs.setTitle("游戏失败-获得"+Vcount3+"分");
										threadH.run();
									
									}
								}
								GLRenderer.control = false;
								Vcount2 = 0;
								Vcount = 0;
							}
							counttouch = 0;
							GLRenderer.GLCubeL.add(new RussiaCube());
							i++;
							GLRenderer.GLCubeL.get(i).setplace();
							thread.run();
						}

					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	};

   private float mPreviousX;
   private float mPreviousY;
   private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
// /////////////////////////////////旋转1///////////////////////////////////////////////////
   Thread thread = new Thread() {
		@Override
		public void run() {
			try {
				roote = false;
				if (rotatecount == 0) {

					myRandomX = new Random();
					R3 = (int) Math.abs(myRandomX.nextInt() % 7);	// 选择转动方式
					if (R3 == 0) {
						roote = true;
						
						FFF();
						thread.stop();
						translate1 = true;

					}
					if (R3 == 1) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = 15;
						GLRenderer.GLCubeL.get(i).shapeY = 0;
						while (X < 6) {

							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
						}
						X = 0;
						thread.run();

					}
					if (R3 == 2) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = 0;
						GLRenderer.GLCubeL.get(i).shapeY = 15;
						while (X < 6) {

							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
							// GLRenderer.dddY += 15;
						}
						X = 0;
						thread.run();
					}
					if (R3 == 3) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = 15;
						GLRenderer.GLCubeL.get(i).shapeY = 15;
						while (X < 6) {

							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
						}
						X = 0;
						thread.run();

					}
					if (R3 == 4) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = -15;
						GLRenderer.GLCubeL.get(i).shapeY = 0;
						while (X < 6) {

							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
						}
						X = 0;
						thread.run();

					}
					if (R3 == 5) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = 0;
						GLRenderer.GLCubeL.get(i).shapeY = -15;
						while (X < 6) {

							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
						}
						X = 0;

						thread.run();
					}
					if (R3 == 6) {
						rotatecount++;
						GLRenderer.GLCubeL.get(i).shapeX = -15;
						GLRenderer.GLCubeL.get(i).shapeY = -15;
						while (X < 6) {
							GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
									.get(i).shapeX;
							GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
									.get(i).shapeY;
							Thread.sleep(100);
							X++;
						}
						X = 0;
						thread.run();
					}
				} else {
					roote = true;
					//threadH.run();
					Log.d("place3XS", "" + GLRenderer.GLCubeL.get(i).RussiaX
							% 360);
					Log.d("place3YS", "" + GLRenderer.GLCubeL.get(i).RussiaY
							% 360);
					rotatecount = 0;
					FFF();
					thread.stop();
					if (threadtraslatC==0)
					{	
					thread2.run();
					}
					threadtraslatC=0;
					translate1 = true;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	};
	// /////////////////////////////////旋转2///////////////////////////////////////////////////

	// ///////////////////////DIALOG THREAD 版//////////////////////////////////
	/* 由於THOAST在THREAD中无法使用因此我用DIALOG修改一个类似THOAST的东西 */
	Thread threadH = new Thread() {
		@Override
		public void run() {
			try {
				doStep1();/////////Dialogs.show();
				doStep2();/////////Dialogs.dismiss();
				threadH.stop();
				// Thread.sleep(3000);
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
			}
		}
	};

	private void doStep1() throws InterruptedException {
		Message msg = new Message();
		msg.what = 1;
		uiMessageHandler.sendMessage(msg);
	}

	private void doStep2() throws InterruptedException {
		threadH.sleep(1000);
		Message msg = new Message();
		msg.what = 2;
		uiMessageHandler.sendMessage(msg);
	}

	Handler uiMessageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (count == 0) {
				count++;
			}
			switch (msg.what) {
			case 1:
				Dialogs.show();
				break;
			case 2:
				Dialogs.dismiss();
				threadH.interrupt(); 
				DS = false;
				break;
			case 3:
				break;
			}

			super.handleMessage(msg);
		}
	};
	
//////////////////计算点数///////////////	
			
	void FFF() {
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 0
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 1;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 1;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 1;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 0
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 2;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 2;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 2;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 0
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 5;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 5;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 5;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 5;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 0
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 6;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 6;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 180
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 6;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 0
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == -90) {
			number = 6;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 3;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 3;
		}

		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == -90) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == -90
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 0) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 90) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 180) {
			number = 4;
		}
		if (GLRenderer.GLCubeL.get(i).RussiaX % 360 == 270
				&& GLRenderer.GLCubeL.get(i).RussiaY % 360 == 270) {
			number = 4;
		}

	}
		
	
	
	
}