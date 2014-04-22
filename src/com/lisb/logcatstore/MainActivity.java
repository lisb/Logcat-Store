package com.lisb.logcatstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lisb.logcatstore.R.id;
import com.lisb.logcatstore.R.layout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_main);

		final TextView textView = (TextView) findViewById(id.text);
		textView.setText("LogcatStoreService started!\nLog stored at "
				+ LogcatStoreService.getOutputFilePath(this));

		startService(new Intent(this, LogcatStoreService.class));
	}

}
