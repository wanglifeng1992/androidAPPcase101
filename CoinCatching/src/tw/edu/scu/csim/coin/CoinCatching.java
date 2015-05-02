package tw.edu.scu.csim.coin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CoinCatching extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		Button startBtn = (Button) findViewById(R.id.StartButton);
		Button introBtn = (Button) findViewById(R.id.InstructionButton);
		Button exitBtn = (Button) findViewById(R.id.ExitButton);

		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CoinCatching.this, Game.class);
				startActivity(intent); // �����µ� Activity
			}
		});

		introBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CoinCatching.this, Instruction.class);
				startActivity(intent); // �����µ� Activity
			}
		});

		exitBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				CoinCatching.this.finish(); // �ر�Ŀǰ Activity
			}
		});
	}
}