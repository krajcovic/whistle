package cz.krajcovic.whistle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class PeriodActivity extends AdMobActivity {

	private static final String TAG = "RandomActivity";

	private EditText periodText;

	private Button startButton;
	private Button stopButton;

	private Chronometer chronometer;

	TrainingTask trainingTask;

	PowerManager.WakeLock wl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.activity_period);

		this.setContentView(R.layout.activity_period);

		PowerManager pm = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		this.wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, TAG);

		this.periodText = (EditText) this.findViewById(R.id.editTextPeriod);

		this.startButton = (Button) this.findViewById(R.id.buttonStart);
		this.stopButton = (Button) this.findViewById(R.id.buttonStop);
		this.stopButton.setEnabled(false);

		this.chronometer = (Chronometer) this.findViewById(R.id.chronometer);

		this.periodText.setText(Integer.toString(20));

		this.startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int period;
				try {
					period = PeriodActivity.this.periodText.getText()
							.toString().equals("") ? 0 : Integer
							.parseInt(PeriodActivity.this.periodText.getText()
									.toString());

				}

				catch (NumberFormatException ex) {
					Log.e(TAG, ex.getMessage());
					Toast.makeText(PeriodActivity.this.getApplicationContext(),
							"Invalid min or max values.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				TrainingParams params = new TrainingParams(
						TrainingActivities.Period);
				params.setActivity((Activity) v.getContext());
				params.setPeriod(period);

				PeriodActivity.this.trainingTask = (TrainingTask) new TrainingTask()
						.execute(params);
				PeriodActivity.this.startButton.setEnabled(false);
				PeriodActivity.this.stopButton.setEnabled(true);
				PeriodActivity.this.chronometer.setBase(SystemClock
						.elapsedRealtime());
				PeriodActivity.this.chronometer.start();
				PeriodActivity.this.wl.acquire();
				// refreshAdMob();
			}
		});

		this.stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PeriodActivity.this.StopTask();
			}
		});

//		this.refreshAdMob();
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
