package tw.edu.scu.csim.coin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Instruction extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.instruction);

		Button backBtn = (Button) findViewById(R.id.BackButton);

		backBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Instruction.this.finish(); // �ر�Ŀǰ Activity
			}
		});
	}
}