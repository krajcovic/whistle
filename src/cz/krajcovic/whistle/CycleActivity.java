package cz.krajcovic.whistle;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class CycleActivity extends AdMobActivity {
	private static final String TAG = "RandomActivity";

	private EditText activeText;
	private EditText restText;

	private Button startButton;
	private Button stopButton;

	private Chronometer chronometer;

	TrainingTask trainingTask;

	KeyguardManager mKeyGuardManager;

	PowerManager.WakeLock wl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_cycle);
		this.setContentView(R.layout.activity_cycle);

		PowerManager pm = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		this.wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, TAG);
		this.activeText = (EditText) this.findViewById(R.id.editTextActive);
		this.restText = (EditText) this.findViewById(R.id.editTextRest);
		this.startButton = (Button) this.findViewById(R.id.buttonStart);
		this.stopButton = (Button) this.findViewById(R.id.buttonStop);
		this.stopButton.setEnabled(false);

		this.chronometer = (Chronometer) this.findViewById(R.id.chronometer);

		this.activeText.setText(Integer.toString(30));
		this.restText.setText(Integer.toString(5));

		this.startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int active, rest;
				try {
					active = CycleActivity.this.activeText.getText().toString()
							.equals("") ? 0 : Integer
							.parseInt(CycleActivity.this.activeText.getText()
									.toString());
					rest = CycleActivity.this.restText.getText().equals("") ? 0
							: Integer.parseInt(CycleActivity.this.restText
									.getText().toString());
				}

				catch (NumberFormatException ex) {
					Log.e(TAG, ex.getMessage());
					Toast.makeText(CycleActivity.this.getApplicationContext(),
							"Invalid min or max values.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				TrainingParams params = new TrainingParams(
						TrainingActivities.Cycle);
				params.setActivity((Activity) v.getContext());
				params.setActive(active);
				params.setRest(rest);

				CycleActivity.this.trainingTask = (TrainingTask) new TrainingTask()
						.execute(params);
				CycleActivity.this.startButton.setEnabled(false);
				CycleActivity.this.stopButton.setEnabled(true);
				CycleActivity.this.chronometer.setBase(SystemClock
						.elapsedRealtime());
				CycleActivity.this.chronometer.start();
				CycleActivity.this.wl.acquire();
				// refreshAdMob();
			}
		});

		this.stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				CycleActivity.this.StopTask();
			}

		});

//		this.refreshAdMob();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_whistle, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.StopTask();
	}

	private void StopTask() {
		if (this.trainingTask != null) {
			this.chronometer.stop();
			this.trainingTask.cancel(true);
			this.startButton.setEnabled(true);
			this.stopButton.setEnabled(false);
			this.wl.release();
		}
	}
}
