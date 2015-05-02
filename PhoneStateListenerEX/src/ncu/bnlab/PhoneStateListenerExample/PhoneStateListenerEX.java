package ncu.bnlab.PhoneStateListenerExample;
import android.app.Activity;
import android.content .Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
public class PhoneStateListenerEX extends Activity
{
protected void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
TelephonyManager telMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
telMgr.listen(new PhoneCallListener(), PhoneStateListener.LISTEN_CALL_STATE);
}
public class PhoneCallListener extends PhoneStateListener
{
public void onCallStateChanged(int state, String incomingNumber)
{
super.onCallStateChanged(state, incomingNumber);
switch (state)
{
case TelephonyManager.CALL_STATE_IDLE:
Context context1 = getApplicationContext();
CharSequence text1 = "闲置狀态";
int duration1 = Toast.LENGTH_LONG; // 设定停留长短
Toast.makeText(context1, text1, duration1).show();
break;
case TelephonyManager.CALL_STATE_OFFHOOK:
Context context2 = getApplicationContext();
CharSequence text2 = "通话中...";
int duration2 = Toast.LENGTH_LONG; // 设定停留长短
Toast.makeText(context2, text2, duration2).show();
break;
case TelephonyManager.CALL_STATE_RINGING:
Context context3 = getApplicationContext();
CharSequence text3 = "响鈴中...";
int duration3 = Toast.LENGTH_LONG; // 设定停留长短
Toast.makeText(context3, text3, duration3).show();
break;
default:
break;
}
}
}
}
