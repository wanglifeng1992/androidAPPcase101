/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package tw.edu.scu.csim.dice3d;

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
	int rotatecountcontrol=0;
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
	private static final int MANU05 = 4;
	private static final int MANU06 = 5;
	private static final int MANU07 = 6;
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
		Toast.makeText(this, "请看菜单选项", Toast.LENGTH_LONG).show();
   }

   @Override
   protected void onPause() {
       super.onPause();
        view.onPause();
                 
	
   }
   @Override
   protected void onDestroy() {
       super.onDestroy();
    
       GLRenderer.GLCubeL.remove(0);
   		
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
		
		menu.add(GALLERY, MANU04, Menu.NONE, "增加旋转次数").setShortcut('3', 's')
		;
		menu.add(GALLERY, MANU05, Menu.NONE, "减少旋转次数").setShortcut('4', 's')
		;
		menu.add(GALLERY, MANU06, Menu.NONE, "转法2").setShortcut('5', 's')
		;

		return true;
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		Toast.makeText(this, "下好请离手!", Toast.LENGTH_SHORT).show();
			//Dialogs.setTitle("下好离手");
			//Dialogs.show();
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			//Dialogs.dismiss();
			thread.run();
          
			
       }
		return true;}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getGroupId()) {
			case GALLERY:
				String itemid = Integer.toString(item.getItemId());
				String title = item.getTitle().toString();
				if (item.getItemId() == 0) {// 游戏说明
					Toast.makeText(this, "旋转" + (rotatecountcontrol+1) + "次-勿连点", Toast.LENGTH_SHORT).show();
					// thread.run();

				}
				if (item.getItemId() == 1) {// 游戏结束

					thread2control = false;
					Dice3D.this.finish();
				}
				if (item.getItemId() == 3) {// 徵家
					if (rotatecountcontrol==8)
					{
						Toast.makeText(this, "不可多於8次", Toast.LENGTH_SHORT).show();
					}
					else
					{
					Toast.makeText(this, "萤幕旋转次数加1"+"目前旋转次数为"+ (rotatecountcontrol+2), Toast.LENGTH_SHORT).show();  
					rotatecountcontrol = rotatecountcontrol + 1;
					}
				}
				if (item.getItemId() == 4) {// 减少
					if (rotatecountcontrol==0)
					{
						Toast.makeText(this, "不可小於1次", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(this, "萤幕旋转次数减1"+"目前旋转次数为"+ (rotatecountcontrol), Toast.LENGTH_SHORT).show();
						rotatecountcontrol = rotatecountcontrol - 1;
					}
				}
				if (item.getItemId() == 5) {// 减少
					thread.run();
				}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	////////////////////////判断点萤幕////////////////////////////

	

   private float mPreviousX;
   private float mPreviousY;
   private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
///////////////////////////////////旋转1///////////////////////////////////////////////////
   Thread thread = new Thread() {
		@Override
		public void run() {
			try {
				roote = false;
				while (rotatecount <= rotatecountcontrol) {

					myRandomX = new Random();
					R3 = (int) Math.abs(myRandomX.nextInt() % 7);	// 选择转动方式
					if (R3 == 0) {
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
						//thread.run();

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
						//thread.run();
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
						//thread.run();

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
						//thread.run();

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

						//thread.run();
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
						//thread.run();
					}
				} 
				
				rotatecount=0;
				FFF();
				Dialogs.dismiss();
				Dialogs.setTitle("旋转结束-"+number+"点");
				threadH.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	};
	// /////////////////////////////////旋转2///////////////////////////////////////////////////
	Thread thread2= new Thread() {
		@Override
		public void run() {
			try {
				myRandomX = new Random();
				R3 = (int) Math.abs(myRandomX.nextInt() % 7);
				roote = false;
				while (rotatecount < R3) {
					
					GLRenderer.GLCubeL.get(i).shapeX = 15;
					GLRenderer.GLCubeL.get(i).shapeY = 0;
					while (X < 6) {

						GLRenderer.GLCubeL.get(i).RussiaX += GLRenderer.GLCubeL
								.get(i).shapeX;
						GLRenderer.GLCubeL.get(i).RussiaY += GLRenderer.GLCubeL
								.get(i).shapeY;
						thread2.sleep(100);
						X++;
					}
					X=0;
					rotatecount++;
				} 
				
				rotatecount=0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	};
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
		threadH.sleep(3000);
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