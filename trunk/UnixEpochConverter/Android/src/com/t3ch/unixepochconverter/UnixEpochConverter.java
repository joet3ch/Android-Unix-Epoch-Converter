package com.t3ch.unixepochconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
//import android.util.Log;


public class UnixEpochConverter extends Activity implements OnClickListener {
	//private static final String TAG = "UnixEpochConverter";
	private Button tounixButton;
	private Button fromunixButton;
	private Button aboutButton;
	private Button exitButton;
	private Intent toUnix;
	private Intent fromUnix;
	private Intent about;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        eventHandlers();
 
    }

	private void findViews() {
		tounixButton = (Button) findViewById(R.id.tounix_button);
		fromunixButton = (Button) findViewById(R.id.fromunix_button);
		aboutButton = (Button) findViewById(R.id.about_button);
		exitButton = (Button) findViewById(R.id.exit_button);
	}

	private void eventHandlers() {
		tounixButton.setOnClickListener(this);
		fromunixButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.tounix_button:
			toUnix = new Intent(this, ToUnix.class);
			startActivity(toUnix);
			break;
		case R.id.fromunix_button:
			fromUnix = new Intent(this, FromUnix.class);
			startActivity(fromUnix);
			break;
		case R.id.about_button:
			about = new Intent(this, About.class);
			startActivity(about);
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}
	
}