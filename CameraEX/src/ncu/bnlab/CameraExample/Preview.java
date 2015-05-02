package ncu.bnlab.CameraExample;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
class Preview extends SurfaceView implements SurfaceHolder.Callback
{
private static final String TAG = "PreviewWindow";
SurfaceHolder mHolder;
public Camera camera;
Preview(Context context)
{
super(context);
mHolder = getHolder();
mHolder.addCallback(this);
mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
}
public void surfaceCreated(SurfaceHolder holder)
{
camera = Camera.open();
try
{
camera.setPreviewDisplay(holder);
camera.setPreviewCallback(new PreviewCallback()
{
public void onPreviewFrame(byte[] data, Camera arg1)
{
FileOutputStream outStream = null;
try
{
outStream = new
FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
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
Preview.this.invalidate();
}
});
}
catch (IOException e)
{
e.printStackTrace();
}
}
public void surfaceDestroyed(SurfaceHolder holder)
{
camera.stopPreview();
camera = null;
}
public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
{
Camera.Parameters parameters = camera.getParameters();
parameters.setPreviewSize(320, 240);
camera.setParameters(parameters);
camera.startPreview();
}
}