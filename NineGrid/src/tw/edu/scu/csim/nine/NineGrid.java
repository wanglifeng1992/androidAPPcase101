package tw.edu.scu.csim.nine;



import java.util.List;
import java.util.Random;

import nine.com.R;

//import example.game.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.widget.Toast;

public class NineGrid extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView view;
	//Random myRandomX = new Random();
	//Random myRandomX = new Random();
	Bitmap bitmap;
	Bitmap bitmap1;
	Bitmap bitmap2;
	Bitmap bitmap3;
	Bitmap bitmap4;
	Bitmap bitmap5;
	Bitmap bitmap6;
	Bitmap bitmap7;
	Bitmap bitmap8;
	int loop=0;
	int swap=0;
	int temp=0;
	int							intRandomN;
	int							count			= 0;
	int							count2			= 0;
	int							countG			= 0;

	int[]						RandomArray		= { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	int[]						RandomArray2	= { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private int[]				preX			= { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

												};
	private int[]				preY			= { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

												};

	private int[]				FORMX			= { 0, 100, 200, 0, 100, 200, 0, 100, 200, 0, 0 };
	private int[]				FORMY			= { 0, 0, 0, 100, 100, 100, 200, 200, 200, 0, 0 };

	/** Called when the activity is first created. */
	private static final int GALLERY = 0;
	private static final int MANU01 = 0;
	private static final int MANU02 = 1;
	private static final int MANU03 = 2;
	private static final int MANU04 = 3;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(GALLERY, MANU01, Menu.NONE, "重新开始")
    	.setShortcut('0', 'k');
    	menu.add(GALLERY, MANU02, Menu.NONE, "作弊")
    	.setShortcut('1', 's');
    	menu.add(GALLERY, MANU03, Menu.NONE, "说明")
    	.setShortcut('2', 's');
    	
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getGroupId()) {
    	case GALLERY:
    		String itemid = Integer.toString(item.getItemId());
    		String title = item.getTitle().toString();
    		
    		if (item.getItemId()==0)
    		{
    			Random myRandomN = new Random();
    			while (loop<50)
    			{
    				
    				swap= Math.abs(myRandomN.nextInt()%8);
    				temp=RandomArray[0];
    				RandomArray[0]=RandomArray[swap];
    				RandomArray[swap]=temp;
    				
    				
    				
    				loop++;
    			}
    			loop=0;
    			swap=0;
    			temp=0;
    			count=0;
    			while(count<8)
    			{
    				preX[count]=FORMX[RandomArray[count]];
    				preY[count]=FORMY[RandomArray[count]];
    				count++;
    			}
    			
			count=0;
			//MySurfaceView.this.threadX.run();
    		}
    		if (item.getItemId()==1)
    		{
    			RandomArray[0]=0;
    			RandomArray[1]=1;
    			RandomArray[2]=2;
    			RandomArray[3]=3;
    			RandomArray[4]=4;
    			RandomArray[5]=5;
    			RandomArray[6]=6;
    			RandomArray[7]=7;
    			count=0;
            	while(count<9)
            	{
            		   preX[count]=FORMX[RandomArray[count]];
            		   preY[count]=FORMY[RandomArray[count]];
            		  
            		   count++;
            	} 
            	
    		}
    		if (item.getItemId()==2)
    		{
    			
    			Toast.makeText(this, "请依123换行456换行78顺序排列", Toast.LENGTH_LONG).show();
    			
    		}
    		
			return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    private void showAlertDialog(String message) {
    	new AlertDialog.Builder(this)
    	.setTitle("选择选目")
    	.setMessage(message)
    	.setPositiveButton("关闭", null)
    	.show();
    }
	
	
	
    @Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		Random myRandomN = new Random();
		while (loop<50)
		{
			
			swap= Math.abs(myRandomN.nextInt()%8);
			temp=RandomArray[0];
			RandomArray[0]=RandomArray[swap];
			RandomArray[swap]=temp;
			
			
			
			loop++;
		}
		loop=0;
		swap=0;
		temp=0;
	while(count<8)
	{
		    RandomArray2[count]=RandomArray[count];
		    preX[count]=FORMX[RandomArray[count]];
		    preY[count]=FORMY[RandomArray[count]];
		count++;
	}
	
	
	Toast.makeText(this, "请看menu", Toast.LENGTH_LONG).show();
		
		view = new MySurfaceView(this);
		setContentView(view);
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Thread.interrupted();
	}
	Thread mainLoop;
	//延伸SurfaceView类别创建MySurfaceView且执行Runnable
	public class MySurfaceView extends SurfaceView implements Runnable {
		
		
		//private SensorManager senorManager;
		boolean u, d, l, r;
		int left, top;
		Paint paint = new Paint();
		long time;
		int tx, ty;
		private float imageX = 0f;
        private float imageY = 0f;
		//实作MySurfaceView处理
        
       	
		public MySurfaceView(Context context) {
			super(context);
			setFocusable(true); //使用Key Event，setFocusable设成true，可以聚焦
			requestFocus(); //要求聚焦，没有聚焦的话，会收不到正确的Key Event
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
			bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.one);
			bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.two);
			bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.three);
			bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.four);
			bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.five);
			bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.six);
			bitmap7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.seven);
			bitmap8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.eight);
			paint.setColor(Color.YELLOW);
			paint.setTextSize(12);
			tx = bitmap.getWidth();
			ty = bitmap.getHeight() / 2;
			mainLoop = new Thread(this);
			mainLoop.start();
			
		}
		public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                imageX = event.getX();
                imageY = event.getY();
                count=0;
            	Log.d("count1<count RandomArray[0]", ""+RandomArray[0]);
            	Log.d("count1<count RandomArray[1]", ""+RandomArray[1]);
            	Log.d("count1<count RandomArray[2]", ""+RandomArray[2]);
            	Log.d("count1<count RandomArray[3]", ""+RandomArray[3]);
            	Log.d("count1<count RandomArray[4]", ""+RandomArray[4]);
            	Log.d("count1<count RandomArray[5]", ""+RandomArray[5]);
            	Log.d("count1<count RandomArray[6]", ""+RandomArray[6]);
            	Log.d("count1<count RandomArray[7]", ""+RandomArray[7]);
            	Log.d("count1<count RandomArray[7]", ""+RandomArray[8]);
            	
           if(imageX<100)
           {
        	   if(imageY<100)
               {
        		   if ( RandomArray[8]==1)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==0)
                		   {
                			   RandomArray[count]=1;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=0;
        		   Log.d("00000X", ""+RandomArray[0]);
        		  //andomArray[0]
        		   }
        		   if ( RandomArray[8]==3)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==0)
                		   {
                			   RandomArray[count]=3;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=0;
        		   Log.d("00000Y", ""+RandomArray[0]);
        		   }
        		   Log.d("00000", ""+RandomArray[0]);
               }
        	   
           }
           if(imageX>100&&imageX<200)
           {
        	   if(imageY<100)
               {
        		   if ( RandomArray[8]==0)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==1)
                		   {
                			   RandomArray[count]=0;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=1;
        		   Log.d("10000X", ""+RandomArray[1]);
        		   }
        		   if ( RandomArray[8]==2)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==1)
                		   {
                			   RandomArray[count]=2;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=1;
        		   Log.d("10000Y", ""+RandomArray[1]);
        		   }
        		   if ( RandomArray[8]==4)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==1)
                		   {
                			   RandomArray[count]=4;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=1;
        		   Log.d("10000Z", ""+RandomArray[1]);
        		   }
        		   Log.d("10000", ""+RandomArray[1]);
               }
        	   
           }
           if(imageX>200&&imageX<300)
           {
        	   if(imageY<100)
               {   
        		   if ( RandomArray[8]==1)
    		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==2)
                		   {
                			   RandomArray[count]=1;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=2;
        		   Log.d("20000X", ""+RandomArray[2]);
    		   }
        		   if ( RandomArray[8]==5)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==2)
                		   {
                			   RandomArray[count]=5;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=2;
        		   Log.d("20000Y", ""+RandomArray[2]);
        		   }
        		   Log.d("20000", ""+RandomArray[2]);
        		
               }
        	   
           }
           if(imageX<100)
           {
        	   if(imageY>100&&imageY<200)
               {
        		   if ( RandomArray[8]==0)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==3)
                		   {
                			   RandomArray[count]=0;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=3;
        		   Log.d("30000X", ""+RandomArray[3]);
        		   }
        		   if ( RandomArray[8]==4)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==3)
                		   {
                			   RandomArray[count]=4;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=3;
        		   Log.d("30000Y", ""+RandomArray[3]);
        		   }
        		   if ( RandomArray[8]==6)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==3)
                		   {
                			   RandomArray[count]=6;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=3;
        		   Log.d("30000Z", ""+RandomArray[3]);
        		   }
        		   Log.d("30000", ""+RandomArray[3]);
               }
        	   
           }
           if(imageX>100&&imageX<200)
           {
        	   if(imageY>100&&imageY<200)
               {
        		   if ( RandomArray[8]==1)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==4)
                		   {
                			   RandomArray[count]=1;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=4;
        		   Log.d("40000X", ""+RandomArray[4]);
        		   }
        		   if ( RandomArray[8]==3)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==4)
                		   {
                			   RandomArray[count]=3;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=4;
        		   Log.d("40000Y", ""+RandomArray[4]);
        		   }
        		   if ( RandomArray[8]==5)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==4)
                		   {
                			   RandomArray[count]=5;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=4;
        		   Log.d("40000Z", ""+RandomArray[4]);
        		   }
        		   if ( RandomArray[8]==7)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==4)
                		   {
                			   RandomArray[count]=7;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=4;
        		   Log.d("40000S", ""+RandomArray[4]);
        		
        		   }       		   Log.d("40000", ""+RandomArray[4]);
               }
        	   
           }
           if(imageX>200&&imageX<300)
           {
        	   if(imageY>100&&imageY<200)
               {    if ( RandomArray[8]==2)
               {
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==5)
            		   {
            			   RandomArray[count]=2;
            		   }
            			   count++;
            		   
            	   }
    			   count=0;
               RandomArray[8]=5;
               Log.d("50000X", ""+RandomArray[5]);
               }
               if ( RandomArray[8]==4)
               {
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==5)
            		   {
            			   RandomArray[count]=4;
            		   }
            			   count++;
            		   
            	   }
    			   count=0;
               RandomArray[8]=5;
               Log.d("50000Y", ""+RandomArray[5]);
               }
               if ( RandomArray[8]==8)
               {
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==5)
            		   {
            			   RandomArray[count]=8;
            		   }
            			   count++;
            		   
            	   }
    			   count=0;
               RandomArray[8]=5;
               Log.d("50000Z", ""+RandomArray[5]);
               }
               Log.d("50000", ""+RandomArray[5]);
            	   
               }
        	   
           }
           if(imageX<100)
           {
        	   if(imageY>200)
               {
        		   if ( RandomArray[8]==3)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==6)
                		   {
                			   RandomArray[count]=3;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=6;
        		   Log.d("60000X", ""+RandomArray[6]);
        		   }
        		   if ( RandomArray[8]==7)
        		   {
        			   while (count<9)
                	   {
                		   if(RandomArray[count]==6)
                		   {
                			   RandomArray[count]=7;
                		   }
                			   count++;
                		   
                	   }
        			   count=0;
        		   RandomArray[8]=6;
        		   Log.d("60000Y", ""+RandomArray[6]);
        		   }
        		   Log.d("60000", ""+RandomArray[6]);
               }
        	   
           }
           if(imageX>100)
           {
        	   if(imageX<200)
        	   {
        	   if(imageY>200)
               {   
        	   if ( RandomArray[8]==4)
               {
        		   while (count<9)
            	   {
            		   if(RandomArray[count]==7)
            		   {
            			   RandomArray[count]=4;
            		   }
            			   count++;
            		   
            	   }
        		   count=0;
               RandomArray[8]=7;
               Log.d("70000X", ""+RandomArray[7]);
               }
               if ( RandomArray[8]==6)
               {
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==7)
            		   {
            			   RandomArray[count]=6;
            		   }
            			   count++;
            		   
            	   }
            	   count=0;
               RandomArray[8]=7;
               Log.d("70000Y", ""+RandomArray[7]);
               }
               if ( RandomArray[8]==8)
               {
            	 //  Log.d("00000", ""+RandomArray[7]);
               //RandomArray[8]=   RandomArray[7];
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==7)
            		   {
            			   RandomArray[count]=8;
            		   }
            			   count++;
            		   
            	   }
            	   count=0;
               RandomArray[8]=7;
               Log.d("70000Z", ""+RandomArray[7]);
               }
               Log.d("70000", ""+RandomArray[7]);
               }
        	   }
           }
           if(imageX>200&&imageX<300)
           {
        	   if(imageY>200)
               {      if ( RandomArray[8]==5)
               {
            	  //andomArray[8]=9;
        		 //  RandomArray[5]= RandomArray[8];
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==8)
            		   {
            			   RandomArray[count] =5;
            		   }
            			   count++;
            		   
            	   }
            	   count=0;
               RandomArray[8]=8;
               Log.d("80000X", ""+RandomArray[8]);
               }
               if ( RandomArray[8]==7)
               {
            	  // RandomArray[7]= RandomArray[8];
            	   while (count<9)
            	   {
            		   if(RandomArray[count]==8)
            		   {
            			   RandomArray[count] =7;
            		   }
            			   count++;
            		   
            	   }
            	   count=0;
               RandomArray[8]=8;
               Log.d("80000Y", ""+RandomArray[8]);
               }
               Log.d("80000", ""+RandomArray[8]);
               }
        	   
           }
               // gettt(imageX,imageY);
            }
        	
        	
        	Log.d("count3<count RandomArray[0]", ""+RandomArray[0]);
        	Log.d("count3<count RandomArray[1]", ""+RandomArray[1]);
        	Log.d("count3<count RandomArray[2]", ""+RandomArray[2]);
        	Log.d("count3<count RandomArray[3]", ""+RandomArray[3]);
        	Log.d("count3<count RandomArray[4]", ""+RandomArray[4]);
        	Log.d("count3<count RandomArray[5]", ""+RandomArray[5]);
        	Log.d("count3<count RandomArray[6]", ""+RandomArray[6]);
        	Log.d("count3<count RandomArray[7]", ""+RandomArray[7]);
        	Log.d("count3<count RandomArray[8]", ""+RandomArray[8]);
        	
        	count=0;
        	while(count<9)
        	{
        		   preX[count]=FORMX[RandomArray[count]];
        		   preY[count]=FORMY[RandomArray[count]];
        		   
        		count++;
        	}
        		invalidate(); 

            return true;
        }
		
		//提起键时的处理
	
		
		//不断执行中，呼叫doDraw()
		void doDraw() {
			Canvas canvas = getHolder().lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.BLACK);
				
				canvas.drawBitmap(bitmap1, preX[0], preY[0], null);
				canvas.drawBitmap(bitmap2, preX[1], preY[1], null);
				canvas.drawBitmap(bitmap3, preX[2], preY[2], null);
				canvas.drawBitmap(bitmap4, preX[3], preY[3], null);
				canvas.drawBitmap(bitmap5, preX[4], preY[4], null);
				canvas.drawBitmap(bitmap6, preX[5], preY[5], null);
				canvas.drawBitmap(bitmap7, preX[6], preY[6], null);
				canvas.drawBitmap(bitmap8, preX[7], preY[7], null);
	         if(RandomArray[0]==0&&RandomArray[1]==1&&RandomArray[2]==2&&RandomArray[3]==3&&RandomArray[4]==4&&RandomArray[5]==5&&RandomArray[6]==6&&RandomArray[7]==7)
	         {  
	             paint.setTextSize(72);
	        	 canvas.drawText("胜利",100,150, paint);	 
	        
	         }
	         
				getHolder().unlockCanvasAndPost(canvas);
			
			}
		}
		public void run() {
			while (true) {
				doDraw();
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	}