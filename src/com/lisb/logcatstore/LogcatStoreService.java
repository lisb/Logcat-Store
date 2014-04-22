package com.lisb.logcatstore;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

@SuppressLint("WorldReadableFiles")
public class LogcatStoreService extends IntentService {

	public static final String LOGFILE_NAME = "logcat.log";

	private static final String THREAD_NAME = "LogcatStore";
	private static final String LOG_TAG = LogcatStoreService.class
			.getSimpleName();
	// バッファを大きくすると端末が落ちた際に大きく情報が欠落するので小さめに設定。
	private static final int IO_BUFFER_SIZE = 256;

	public LogcatStoreService() {
		super(THREAD_NAME);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(LOG_TAG, "LogcatStore started.");

		// jelly bean 以降は su しないとこのアプリのログしか取れないので注意。

		final List<String> commandLine = new ArrayList<String>();
		commandLine.add("logcat");
		commandLine.add("-v");
		commandLine.add("threadtime");

		final byte[] bytes = new byte[IO_BUFFER_SIZE];
		InputStream in = null;
		OutputStream out = null;
		try {
			final Process process = Runtime.getRuntime().exec(
					commandLine.toArray(new String[0]));
			in = process.getInputStream();
			out = openFileOutputStream();
			int count;
			while ((count = in.read(bytes)) != -1) {
				out.write(bytes, 0, count);
				out.flush();
			}
			Log.i(LOG_TAG, "LogcatStore finished.");
		} catch (IOException e) {
			Log.e(LOG_TAG, "LogcatStore error.", e);
		} finally {
			closeSilently(in);
			closeSilently(out);
		}
	}

	@SuppressWarnings("deprecation")
	private OutputStream openFileOutputStream() throws IOException {
		return openFileOutput(LOGFILE_NAME, MODE_WORLD_READABLE);
	}

	public static String getOutputFilePath(final Context context) {
		// return new File(context.getExternalFilesDir(null), LOGFILE_NAME);
		return new File(context.getFilesDir(), LOGFILE_NAME).getAbsolutePath();
	}

	private void closeSilently(final Closeable c) {
		if (c == null) {
			return;
		}

		try {
			c.close();
		} catch (IOException e) {
		}
	}

}
