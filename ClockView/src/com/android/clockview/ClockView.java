package com.android.clockview;

import java.util.Calendar;   
import java.util.TimeZone;   
  
import android.R.integer;
import android.app.Activity;   
import android.content.Context;   
import android.graphics.Bitmap;   
import android.graphics.BitmapFactory;   
import android.graphics.Canvas;   
import android.graphics.Color;   
import android.graphics.Matrix;   
import android.graphics.Paint;   
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;   
import android.os.Handler;   
import android.util.Log;
import android.view.View;   
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;   
  
public class ClockView extends Activity {   
    
    private static String TAG = "ClockView";    
  
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    
    private QAnalogClock clock1;        
    
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        LinearLayout lLayout = new LinearLayout(this);
        lLayout.setLayoutParams(new LinearLayout.LayoutParams(FP, FP));
        lLayout.setOrientation(LinearLayout.VERTICAL);
        
        clock1 = new QAnalogClock(this,"GMT+8:00");    
        lLayout.addView(clock1, new LinearLayout.LayoutParams(WC,WC));          

        setContentView(lLayout);        
        
    }   
    /*
     * Customized AnalogClock View
     * 1)Draw dial by bitmap
     * 2)Draw hour/minute/second by BitmapDrawable
     */
    
    class QAnalogClock extends View {   
        //Original bitmap of dial,hour,minute,second
        Bitmap mBmpDial;  
        Bitmap mBmpHour;   
        Bitmap mBmpMinute;   
        Bitmap mBmpSecond;
        
        //Drawable of dial,hour,minute,second
        BitmapDrawable bmdHour;
        BitmapDrawable bmdMinute;
        BitmapDrawable bmdSecond;
        BitmapDrawable bmdDial;
        
        Paint mPaint;//Paint draw on canvas
        
        Handler tickHandler;        
        
        int mWidth;   //Dial width
        int mHeigh;   //Dial height
        int mTempWidth;   //Hour/Minute/Second width
        int mTempHeigh;   //Hour/Minute/Second height
        int centerX ; //Hour/Minute/Second picture center x  
        int centerY ; //Hour/Minute/Second picture center y
        
        int availableWidth = 100;//Available width of the dial
        int availableHeight = 100;//Available height of the dial
        
        private String sTimeZoneString;
        
        public QAnalogClock(Context context,String sTime_Zone) {   
            super(context);   
            sTimeZoneString = sTime_Zone;
            
            mBmpHour = BitmapFactory.decodeResource(getResources(), R.drawable.clockdroid2_hour);   
            bmdHour = new BitmapDrawable(mBmpHour);
            
            mBmpMinute = BitmapFactory.decodeResource(getResources(), R.drawable.clockdroid2_minute);  
            bmdMinute = new BitmapDrawable(mBmpMinute);
            
            mBmpSecond = BitmapFactory.decodeResource(getResources(), R.drawable.clockdroid2_minute);  
            bmdSecond = new BitmapDrawable(mBmpSecond);
            
            mBmpDial = BitmapFactory.decodeResource(getResources(), R.drawable.clockdroid2_dial); 
            bmdDial = new BitmapDrawable(mBmpDial);
            mWidth = mBmpDial.getWidth();
            mHeigh = mBmpDial.getHeight();
            centerX= availableWidth/2;
            centerY = availableHeight/2;
            
            mPaint = new Paint();   
            mPaint.setColor(Color.BLUE);           
            run();//Begin to send update event ,how to sync?
        }      
  

  
       public void run() {   
           tickHandler = new Handler();   
           tickHandler.post(tickRunnable);    
       }   
 
        
        private Runnable tickRunnable = new Runnable() {   
            public void run() {   
                postInvalidate(); 
                tickHandler.postDelayed(tickRunnable, 1000);   
            }   
        };   
 
        
        protected void onDraw(Canvas canvas) {   
            super.onDraw(canvas);      
  
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(sTimeZoneString));   
            int hour = cal.get(Calendar.HOUR);   
            int minute = cal.get(Calendar.MINUTE);   
            int second = cal.get(Calendar.SECOND);
            float hourRotate =  hour * 30.0f + minute / 60.0f * 30.0f;   
            float minuteRotate = minute * 6.0f;   
            float secondRotate = second * 6.0f;
            
         //   Log.d(TAG, "hour is "+hour+" minute is "+minute);
         //   Log.d(TAG, "hourRotate is "+hourRotate+" minuteRotate is "+minuteRotate+" secondRotate is "+secondRotate);
            boolean scaled = false;
            
            if (availableWidth < mWidth || availableHeight < mHeigh) {
                scaled = true;
                float scale = Math.min((float) availableWidth / (float) mWidth,
                                       (float) availableHeight / (float) mHeigh);
                canvas.save();
                canvas.scale(scale, scale, centerX, centerY);
            }
            
          //  Log.d(TAG,"Canvas's height is "+canvas.getHeight() +" width is "+canvas.getWidth());
           
            //Draw dial on view
            bmdDial.setBounds(centerX-(mWidth/2),centerY-(mHeigh/2),centerX+(mWidth/2),centerY+(mHeigh/2));            
            bmdDial.draw(canvas);
            
            //Draw hour on view
            mTempWidth = bmdHour.getIntrinsicWidth();
            mTempHeigh = bmdHour.getIntrinsicHeight();
            canvas.save();//Save non rotated canvas
            canvas.rotate(hourRotate,  centerX, centerY);       
            bmdHour.setBounds(centerX-(mTempWidth/2),centerY-(mTempHeigh/2),centerX+(mTempWidth/2),centerY+(mTempHeigh/2));            
            bmdHour.draw(canvas);   
            
            canvas.restore(); //restore canvas to last saved state       
            
            //Draw Minute on view
            mTempWidth = bmdMinute.getIntrinsicWidth();
            mTempHeigh = bmdMinute.getIntrinsicHeight();
            canvas.save();
            canvas.rotate(minuteRotate, centerX, centerY);        
            bmdMinute.setBounds(centerX-(mTempWidth/2),centerY-(mTempHeigh/2),centerX+(mTempWidth/2),centerY+(mTempHeigh/2));        
            bmdMinute.draw(canvas);  
            
             canvas.restore();   
             
            //Draw Second on view
            mTempWidth = bmdSecond.getIntrinsicWidth();
            mTempHeigh = bmdSecond.getIntrinsicHeight();                    
            canvas.rotate(secondRotate,  centerX, centerY);      
            bmdSecond.setBounds(centerX-(mTempWidth/2),centerY-(mTempHeigh/2),centerX+(mTempWidth/2),centerY+(mTempHeigh/2));            
            bmdSecond.draw(canvas); 
            
            if (scaled)
            {
                canvas.restore();
            }
       }   
    }   
}  
