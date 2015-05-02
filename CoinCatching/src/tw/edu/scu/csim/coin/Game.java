package tw.edu.scu.csim.coin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Game extends Activity implements SensorEventListener {
	private int				screenWidth, screenHeight;				// 萤幕长宽
	private int				level		= 10;						// 硬币数
																	// Random
																	// ran = new
																	// Random();
	private int				speed		= 10;						// 落下速度
	private int				score		= 0;						// 分数
	private int				time		= 30000;					// 游戏时间(1000=1秒)
	private Canvas			canvas;
	private ArrayList<Coin>	coins		= new ArrayList<Coin>();
	private MyCountDown		cdTimer;								// 倒数计时器
	private String			remainingTime;							// 剩馀时间
	Thread					mainLoop;
	CoinMove				coinMove;								// 执行落下动作
	private boolean			terminate	= false;					// 处理Thread结束
	Bitmap					box;
	private int				boxLeft;
	private int				boxTop;
	private int             count = 1;

	private SensorManager	senorManager;

	/* 画面绘出与触碰事件 */
	class MySurfaceView extends SurfaceView implements Runnable {
		BitmapDrawable	background;
		Paint			paint	= new Paint();

		public MySurfaceView(Context context) {
			super(context);

			/* 取得萤幕长宽 */
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;

			Log.i("w", String.valueOf(screenWidth));
			Log.i("h", String.valueOf(screenHeight));

			/* 设定背景图 */
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grid1);
			background = new BitmapDrawable(context.getResources(), bitmap);
			background.setBounds(0, 0, screenWidth, screenHeight);

			/* 设定箱子 */
			box = BitmapFactory.decodeResource(getResources(), R.drawable.pooman2);
			boxLeft = (screenWidth - box.getWidth()) / 2;
			boxTop = screenHeight - box.getHeight()/3*2;

			paint.setColor(Color.BLACK);
			paint.setTextSize(18);

			initilizeGame(context); // 随机重置图片位置

			mainLoop = new Thread(this);
			mainLoop.start();
		}

		/* 画面绘出 */
		void doDraw() {
			canvas = getHolder().lockCanvas();
			if (canvas != null) {
				background.draw(canvas);

				for (int i = 0; i < coins.size(); i++) {
					Bitmap bitmap = coins.get(i).bitmap;
					int left = coins.get(i).left;
					int top = coins.get(i).top;

					canvas.drawBitmap(bitmap, left, top, null);
				}

				String s = "收获：" + String.valueOf(score);
				canvas.drawText(s, 5, 40, paint); // 画出分数
				canvas.drawText(remainingTime, 5, 20, paint); // 画出剩馀秒数
				canvas.drawBitmap(box, boxLeft, boxTop, null);
				if(count==0)
					canvas.drawARGB(255, 255, 255, 255);
				getHolder().unlockCanvasAndPost(canvas);
				
			}
		}

		public void run() {
			while (!terminate) {
				doDraw();
			}
		}
	}

	/* 控制硬币下降速度 */
	class CoinMove extends Thread {
		void doMove() {
			Random r = new Random();
			for (int i = 0; i < coins.size(); i++) {
				int t = 1;
				// for(int t=1;t<=2;t++){
				coins.get(i).top += 4 * t;
				coins.get(i).left += coins.get(i).aa * t;// 下降幅度
				// }
				int coinRange = coins.get(i).top + coins.get(i).bitmap.getHeight();

				if (coinRange < boxTop) {
					// 不做事
				} else if (coins.get(i).top > screenHeight) {
					coins.set(i, ResetCoin()); // 重置跑出画面的硬币
				} else if (coinRange > boxTop) {
					int coinWidth = coins.get(i).left + coins.get(i).bitmap.getWidth();

					// 若硬币掉到箱子内则得分
					if (coins.get(i).left > boxLeft && coinWidth < boxLeft + box.getWidth()) {
						score += coins.get(i).dollar; // 加分
						coins.set(i, ResetCoin());
					}
				}
			}

			try {
				Thread.sleep(20); // 限制位置更新速率
			} catch (Exception ex) {
			}
		}

		public void run() {
			while (!terminate) {
				doMove();
			}
		}
	}

	/* 倒数计时器 */
	public class MyCountDown extends CountDownTimer {
		Context	context;

		public MyCountDown(long millisInFuture, long countDownInterval, Context context) {
			super(millisInFuture, countDownInterval);
			this.context = context;
		}

		@Override
		public void onFinish() {
			remainingTime = "剩馀时间: 0 秒";
            count=0;
			Button button = new Button(context);
			button.setText("确定");
			final Dialog dialog = new Dialog(context);
			dialog.setTitle("恭喜你得到 " + score + "元！");
			dialog.setContentView(button);
			dialog.show();
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
					Game.this.finish();
				}
			});
		}

		@Override
		public void onTick(long millisUntilFinished) {
			remainingTime = "剩馀时间: " + (millisUntilFinished / 1000 + 1) + " 秒";
		}
	}

	/* 将图重新放在画面上方随机位置 */
	private void initilizeGame(Context context) {
		coins.clear();

		for (int i = 0; i < level; i++) {
			Coin coin = ResetCoin();
			coins.add(coin);
		}

		score = 0; // 重置分数

		cdTimer = new MyCountDown(time, 500, context); // 倒数计时器
		cdTimer.start();

		coinMove = new CoinMove(); // 处理硬币移动的Thread
		coinMove.start();
	}

	/* 随机产生一个硬币 */
	private Coin ResetCoin() {
		Random r = new Random();

		Bitmap bitmap;
		int dollar = 10;
		int j = r.nextInt(100);
		if (j < 70) {
			// 10元
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin10);
		} else if (j < 90) {
			// 20元
			dollar = 20;
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin20);
		} else {
			// 50元
			dollar = 50;
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin50);
		}
		// int top = bitmap.getHeight();
		// int left = bitmap.getWidth();
		// int left = r.nextInt(screenWidth - bitmap.getWidth());
		int left = (int) (0 - bitmap.getWidth() - r.nextInt(screenWidth) * 1.5);
		int top = 0 - bitmap.getHeight() - r.nextInt(screenHeight) * 2;
		int a = 0;
		Coin coin = new Coin(bitmap, dollar, left, top, a);

		return coin;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		senorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		setContentView(new MySurfaceView(this));
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Sensor> sensors = senorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			senorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		terminate = true;
		mainLoop.interrupt();
		coinMove.interrupt();
		cdTimer.cancel();
	}

	@Override
	protected void onPause() {
		super.onPause();
		senorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.values[1] > 0&&event.values[1] > 5) {
			if(boxLeft>0)
			boxLeft = boxLeft - 5;
		}
		 if (event.values[1] > 5&&event.values[1] > 15)
		{
			 if(boxLeft>0)
			boxLeft = boxLeft - 5;
		}
		 if (event.values[1] > 10&&event.values[1] > 20)
		{
			 if(boxLeft>0)
			boxLeft = boxLeft - 5;
		}
		
		
		if (event.values[1] < 0&&event.values[1] < -5) {
			
			if(boxLeft+box.getWidth()<screenWidth)
			boxLeft = boxLeft + 5;
		}
		 if (event.values[1] < -5&&event.values[1] < -15)
		{
			 if(boxLeft+box.getWidth()<screenWidth)
			boxLeft = boxLeft + 5;
		}
		 if (event.values[1] < -10&&event.values[1] < -20)
		{
			 if(boxLeft+box.getWidth()<screenWidth)
			boxLeft = boxLeft + 5;
		}
		
	}
}