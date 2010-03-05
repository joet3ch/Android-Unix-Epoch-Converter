package com.t3ch.unixepochconverter;

//import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.util.Log;

public class FromUnix extends Activity {
	//private static final String TAG = "UnixEpochConverter";
	private EditText inputTime;
	private TextView outputTime;
	private Button convertButton;
	private String convertedTime;
	private Long finalTime;
	private TextView output;
	private String logString = "";
	private String currentLogString = "";
	private Long gmtTime;
	private Integer gmtOffset;
	private String tzName;
	private String gmtString;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fromunix);
        findViews();
        eventHandlers();

        if (savedInstanceState != null) {
    		restoreMe(savedInstanceState);
    	}
    }

	private void findViews() {
		inputTime = (EditText) findViewById(R.id.input_time);
		outputTime = (TextView) findViewById(R.id.output_time);
		convertButton = (Button) findViewById(R.id.convert_button);
		output = (TextView) findViewById(R.id.output);
	}

	private void eventHandlers() {
		convertButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				convertFromUnixToHuman();
			}
		});
	}
	
    private void log(String string) {
    	output.append(string + "\n");
    }

	private void convertFromUnixToHuman() {
		try {
			TimeZone tz = TimeZone.getDefault();
			Date currentDate = new java.util.Date();
			Boolean dstValue = tz.inDaylightTime(currentDate);
			tzName = tz.getDisplayName(dstValue, 0);

			finalTime = Long.parseLong(inputTime.getText().toString());			
			gmtOffset = tz.getOffset(finalTime);
			finalTime = finalTime*1000;
			convertedTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(finalTime));

			gmtTime = finalTime - gmtOffset;
			gmtString = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(gmtTime));

			
			convertedTime = convertedTime + " [" + tzName + "]";
			gmtString = gmtString + " [GMT]";
			
			outputTime.setText(convertedTime + "\n" + gmtString);

			logString = logString + inputTime.getText().toString() + " = " + convertedTime + "\n";
			logString = logString + inputTime.getText().toString() + " = " + gmtString +"\n";
			log(logString);
			currentLogString = currentLogString + logString;
			logString = "";
			
			//Log.d(TAG, "input time: " + inputTime.getText().toString() + ", converted time: " + convertedTime);
			
		} catch(Exception e) {
			outputTime.setText("No input detected");
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("CURRENTLOGSTRING", currentLogString);
		outState.putString("CONVERTEDTIME", convertedTime);
		outState.putString("GMTSTRING", gmtString);
	}
	
	private void restoreMe(Bundle state) {
		if (state != null) {
			currentLogString = state.getString("CURRENTLOGSTRING");
			convertedTime = state.getString("CONVERTEDTIME");
			gmtString = state.getString("GMTSTRING");
			if (convertedTime == null || gmtString == null) {
				
			} else {
				outputTime.setText(convertedTime + "\n" + gmtString);
				log(currentLogString);
			}
		}
	}
}
