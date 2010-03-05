package com.t3ch.unixepochconverter;

/****************************************************************
 * Copyright 2010 Joe Nicosia <joe@t3ch.com>  
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ****************************************************************/

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ToUnix extends Activity {
	//private static final String TAG = "UnixEpochConverter";
	private DatePicker datePicker;
	private TimePicker timePicker;
	//private EditText inputTime;
	private TextView output_unix;
	private Button convertButton;
	private Long convertedTime;
	//private Long finalTime;
	private TextView output_log;
	private Integer day;
	private Integer month;
	private Integer year;
	private Integer hour;
	private Integer minute;
	private Date epoch;
	private String convertedString;
	@SuppressWarnings("unused")
	private String monthString;
	private String logString = "";
	private String currentLogString = "";
	private Long gmtTime;
	private Integer gmtOffset;
	private String tzName;
	private String gmtString;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tounix);
        findViews();
        eventHandlers();
        
    	if (savedInstanceState != null) {
    		restoreMe(savedInstanceState);
    	}
    }
		
	private void findViews() {
		datePicker = (DatePicker) findViewById(R.id.date_picker);
		timePicker = (TimePicker) findViewById(R.id.time_picker);
		convertButton = (Button) findViewById(R.id.convert_button);
		output_log = (TextView) findViewById(R.id.output_log);
		output_unix = (TextView) findViewById(R.id.output_unix);
	}

	private void eventHandlers() {
		convertButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				convertFromHumanToUnix();
			}
		});
	}
	
    private void log(String string) {
    	output_log.append(string + "\n");
    }

	private void convertFromHumanToUnix() {
		day = (Integer) datePicker.getDayOfMonth();
		month = (Integer) datePicker.getMonth();
		year = (Integer) datePicker.getYear();
		hour = (Integer) timePicker.getCurrentHour();
		minute = (Integer) timePicker.getCurrentMinute();
		month = month+1;
		if (month < 10) {
			monthString = month.toString();
			monthString = "0" + month;
		} else {
			monthString = month.toString();
		}

		try {
			// epoch = new java.text.SimpleDateFormat ("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00");
			epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(month + "/" + day + "/" + year + " " + hour + ":" + minute + ":00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		convertedTime = (Long) epoch.getTime();
		convertedTime = convertedTime/1000;
		convertedString = (String) convertedTime.toString();
		
		TimeZone tz = TimeZone.getDefault();
		gmtOffset = tz.getOffset(convertedTime) / 1000;
		gmtTime = convertedTime + gmtOffset;
		
		Date currentDate = new java.util.Date();
		Boolean dstValue = tz.inDaylightTime(currentDate);
				
		tzName = tz.getDisplayName(dstValue, 0);

		convertedString = convertedString + " [" + tzName + "]";
		gmtString = gmtTime.toString() + " [GMT]";
		output_unix.setText(convertedString + "\n" + gmtString);
		
		logString = logString + "\n" + month + "/" + day + "/" + year + " " + hour + ":" + minute + ":00" + " = " + convertedString;
		logString = logString + "\n" + month + "/" + day + "/" + year + " " + hour + ":" + minute + ":00" + " = " + gmtString;
		log(logString);
		currentLogString = currentLogString + logString;
		logString = "";
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("CURRENTLOGSTRING", currentLogString);
		outState.putString("CONVERTEDSTRING", convertedString);
		outState.putString("GMTSTRING", gmtString);
	}
	
	private void restoreMe(Bundle state) {
		if (state != null) {
			currentLogString = state.getString("CURRENTLOGSTRING");
			convertedString = state.getString("CONVERTEDSTRING");
			gmtString = state.getString("GMTSTRING");
			if (convertedString == null || gmtString == null) {
				
			} else {
				output_unix.setText(convertedString + "\n" + gmtString);
				log(currentLogString);
			}
		}
	}
}
