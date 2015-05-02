package com.tsinghua.hellodialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HellodialogActivity extends Activity {
    /** Called when the activity is first created. */
    
    private static final int PROGRESS_DIALOG = 0;
    private ProgressDialog mProgressDlg = null;
    private ProgressThread mProgressThd = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      // Setup the button that starts the progress dialog
      Button btnProgress = (Button) findViewById(R.id.progressBtn);
      btnProgress.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          showDialog(PROGRESS_DIALOG);
        }
      });
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {

      switch (id) {
      case PROGRESS_DIALOG:
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDlg.setTitle("Loading ¼ÓÔØ...");
        mProgressDlg.setMessage("µÈ >>> Please wait for a while...");
                          mProgressThd = new ProgressThread(handler);
                          mProgressThd.start();
        return mProgressDlg;
      default:
        return null;
      }
    }

    // Define the Handler that receives messages from the thread and update the
    // progress
    private final Handler handler = new Handler() {
      public void handleMessage(Message msg) {
        int total = msg.getData().getInt("total");
        mProgressDlg.setProgress(total);
        if (total >= 100) {
          dismissDialog(PROGRESS_DIALOG);
          mProgressThd.setState(ProgressThread.STATE_DONE);
        }
      }
    };

    /** Nested class that performs progress calculations (counting) */
    private class ProgressThread extends Thread {
      Handler mHandler = null;
      private final static int STATE_DONE = 0;
      private final static int STATE_RUNNING = 1;
      private int mState = 0;

      ProgressThread(Handler h) {
        this.mHandler = h;
      }

      public void run() {
        setState(STATE_RUNNING);
        int total = 0;
        while (STATE_RUNNING == mState) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            Log.e("ERROR", "Thread Interrupted");
          }
          Message msg = mHandler.obtainMessage();
          Bundle b = new Bundle();
          b.putInt("total", total);
          msg.setData(b);
          mHandler.sendMessage(msg);
          total++;
        }
      }

      /**
       * sets the current state for the thread, used to stop the thread
       */
      public void setState(int state) {
        mState = state;
      }
    }

}