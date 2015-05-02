package ncu.bnlab.TelephonyManagerExample;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
public class TelephonyManagerEX extends Activity
{
private TextView phoneInfo;
private TelephonyManager telMgr;
private StringBuilder strBdr;
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
phoneInfo = (TextView)findViewById(R.id.phoneInfo);
telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
strBdr = new StringBuilder();
strBdr.append("\n 设备编号(IMEI) = " + telMgr.getDeviceId());
strBdr.append("\n 设备软件版本 = " + telMgr.getDeviceSoftwareVersion());
strBdr.append("\n 线路1 号码 = " + telMgr.getLine1Number());
strBdr.append("\n 服务业者所在国码 = " + telMgr.getNetworkCountryIso());
strBdr.append("\n 服务业者 = " + telMgr.getNetworkOperator());
strBdr.append("\n 服务业者名称 = " + telMgr.getNetworkOperatorName());
strBdr.append("\n 网路類型 = " + telMgr.getNetworkType());
strBdr.append("\n 手机類型 = " + telMgr.getPhoneType());
strBdr.append("\n Sim卡所在国码 = " + telMgr.getSimOperatorName());
strBdr.append("\n Sim卡营运商 = " + telMgr.getSimOperator());
strBdr.append("\n Sim卡营运商名称 = " + telMgr.getSimOperatorName());
strBdr.append("\n Sim卡序号 = " + telMgr.getSimSerialNumber());
strBdr.append("\n Sim卡狀态 = " + telMgr.getSimState());
strBdr.append("\n 订阅者编号(IMSI) = " + telMgr.getSubscriberId());
strBdr.append("\n 音效邮件号码 = " + telMgr.getVoiceMailNumber());
phoneInfo.setText(strBdr.toString());
}
}
