package ncu.bnlab.CameraExample;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
public class CameraEX extends Activity
{
private static final String TAG = "Camera";
public Camera camera;
public Preview preview;
public Button buttonClick;
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
preview = new Preview(this);
( (FrameLayout) findViewById(R.id.wPreview) ).addView(preview);
buttonClick = (Button) findViewById(R.id.btnClick);
buttonClick.setOnClickListener( new OnClickListener()
{
public void onClick(View v)
{
preview.camera.takePicture(shutterCallback, rawCallback,
jpegCallback);
}
});
}
ShutterCallback shutterCallback = new ShutterCallback()
{
public void onShutter()
{
}
};
PictureCallback rawCallback = new PictureCallback()
{
public void onPictureTaken(byte[] data, Camera camera)
{
}
};
PictureCallback jpegCallback = new PictureCallback()
{
public void onPictureTaken(byte[] data, Camera camera)
{
FileOutputStream outStream = null;
try
{
outStream = new
FileOutputStream(String.format("/sdcard/%d.jpg",
System.currentTimeMillis()));
outStream.write(data);
outStream.close();
}
catch (FileNotFoundException e)
{
e.printStackTrace();
}
catch (IOException e)
{
e.printStackTrace();
}
}
};
}