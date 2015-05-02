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
	private int				screenWidth, screenHeight;				// өĻ����
	private int				level		= 10;						// Ӳ����
																	// Random
																	// ran = new
																	// Random();
	private int				speed		= 10;						// �����ٶ�
	private int				score		= 0;						// ����
	private int				time		= 30000;					// ��Ϸʱ��(1000=1��)
	private Canvas			canvas;
	private ArrayList<Coin>	coins		= new ArrayList<Coin>();
	private MyCountDown		cdTimer;								// ������ʱ��
	private String			remainingTime;							// ʣ��ʱ��
	Thread					mainLoop;
	CoinMove				coinMove;								// ִ�����¶���
	private boolean			terminate	= false;					// ����Thread����
	Bitmap					box;
	private int				boxLeft;
	private int				boxTop;
	private int             count = 1;

	private SensorManager	senorManager;

	/* �������봥���¼� */
	class MySurfaceView extends SurfaceView implements Runnable {
		BitmapDrawable	background;
		Paint			paint	= new Paint();

		public MySurfaceView(Context context) {
			super(context);

			/* ȡ��өĻ���� */
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;

			Log.i("w", String.valueOf(screenWidth));
			Log.i("h", String.valueOf(screenHeight));

			/* �趨����ͼ */
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grid1);
			background = new BitmapDrawable(context.getResources(), bitmap);
			background.setBounds(0, 0, screenWidth, screenHeight);

			/* �趨���� */
			box = BitmapFactory.decodeResource(getResources(), R.drawable.pooman2);
			boxLeft = (screenWidth - box.getWidth()) / 2;
			boxTop = screenHeight - box.getHeight()/3*2;

			paint.setColor(Color.BLACK);
			paint.setTextSize(18);

			initilizeGame(context); // �������ͼƬλ��

			mainLoop = new Thread(this);
			mainLoop.start();
		}

		/* ������ */
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

				String s = "�ջ�" + String.valueOf(score);
				canvas.drawText(s, 5, 40, paint); // ��������
				canvas.drawText(remainingTime, 5, 20, paint); // ����ʣ������
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

	/* ����Ӳ���½��ٶ� */
	class CoinMove extends Thread {
		void doMove() {
			Random r = new Random();
			for (int i = 0; i < coins.size(); i++) {
				int t = 1;
				// for(int t=1;t<=2;t++){
				coins.get(i).top += 4 * t;
				coins.get(i).left += coins.get(i).aa * t;// �½�����
				// }
				int coinRange = coins.get(i).top + coins.get(i).bitmap.getHeight();

				if (coinRange < boxTop) {
					// ������
				} else if (coins.get(i).top > screenHeight) {
					coins.set(i, ResetCoin()); // �����ܳ������Ӳ��
				} else if (coinRange > boxTop) {
					int coinWidth = coins.get(i).left + coins.get(i).bitmap.getWidth();

					// ��Ӳ�ҵ�����������÷�
					if (coins.get(i).left > boxLeft && coinWidth < boxLeft + box.getWidth()) {
						score += coins.get(i).dollar; // �ӷ�
						coins.set(i, ResetCoin());
					}
				}
			}

			try {
				Thread.sleep(20); // ����λ�ø�������
			} catch (Exception ex) {
			}
		}

		public void run() {
			while (!terminate) {
				doMove();
			}
		}
	}

	/* ������ʱ�� */
	public class MyCountDown extends CountDownTimer {
		Context	context;

		public MyCountDown(long millisInFuture, long countDownInterval, Context context) {
			super(millisInFuture, countDownInterval);
			this.context = context;
		}

		@Override
		public void onFinish() {
			remainingTime = "ʣ��ʱ��: 0 ��";
            count=0;
			Button button = new Button(context);
			button.setText("ȷ��");
			final Dialog dialog = new Dialog(context);
			dialog.setTitle("��ϲ��õ� " + score + "Ԫ��");
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
			remainingTime = "ʣ��ʱ��: " + (millisUntilFinished / 1000 + 1) + " ��";
		}
	}

	/* ��ͼ���·��ڻ����Ϸ����λ�� */
	private void initilizeGame(Context context) {
		coins.clear();

		for (int i = 0; i < level; i++) {
			Coin coin = ResetCoin();
			coins.add(coin);
		}

		score = 0; // ���÷���

		cdTimer = new MyCountDown(time, 500, context); // ������ʱ��
		cdTimer.start();

		coinMove = new CoinMove(); // ����Ӳ���ƶ���Thread
		coinMove.start();
	}

	/* �������һ��Ӳ�� */
	private Coin ResetCoin() {
		Random r = new Random();

		Bitmap bitmap;
		int dollar = 10;
		int j = r.nextInt(100);
		if (j < 70) {
			// 10Ԫ
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin10);
		} else if (j < 90) {
			// 20Ԫ
			dollar = 20;
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin20);
		} else {
			// 50Ԫ
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