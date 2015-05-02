package my.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentEX extends Activity {
	private String strNumber;
	private Button btnDial;
	private EditText phoneNumber;
	/** Called when the Activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnDial = (Button)findViewById(R.id.btnDial);
		phoneNumber = (EditText)findViewById(R.id.phoneNumber); 	
		
		btnDial.setOnClickListener(new Button.OnClickListener()
		{
		        @Override
		        public void onClick(View v)
		       {
		                strNumber = phoneNumber.getText().toString();

		                /*建立Intent */
		                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + strNumber));

		                /*启动活动 */
		                startActivity(intent);
		         }
		});
	}
}