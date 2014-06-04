package cz.krajcovic.whistle;

import android.app.Activity;
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

//import com.google.ads.AdView;

public class RandomActivity extends AdMobActivity {

	private static final String TAG = "RandomActivity";

	private EditText minText;
	private EditText maxText;

	private Button startButton;
	private Button stopButton;

	private Chronometer chronometer;

	TrainingTask trainingTask;

	PowerManager.WakeLock wl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_random);
		this.setContentView(R.layout.activity_random);

		PowerManager pm = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		this.wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, TAG);

		this.minText = (EditText) this.findViewById(R.id.editTextMin);
		this.maxText = (EditText) this.findViewById(R.id.editTextMax);
		this.startButton = (Button) this.findViewById(R.id.buttonStart);
		this.stopButton = (Button) this.findViewById(R.id.buttonStop);
		this.stopButton.setEnabled(false);

		this.chronometer = (Chronometer) this.findViewById(R.id.chronometer);

		this.minText.setText(Integer.toString(1));
		this.maxText.setText(Integer.toString(10));

		this.startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int min, max;
				try {
					min = RandomActivity.this.minText.getText().toString()
							.equals("") ? 0 : Integer
							.parseInt(RandomActivity.this.minText.getText()
									.toString());
					max = RandomActivity.this.maxText.getText().equals("") ? 0
							: Integer.parseInt(RandomActivity.this.maxText
									.getText().toString());

					if (min >= max) {
						Toast.makeText(
								RandomActivity.this.getApplicationContext(),
								"Max have to bigger than min.",
								Toast.LENGTH_SHORT).show();
						return;
					}

				}

				catch (NumberFormatException ex) {
					Log.e(TAG, ex.getMessage());
					Toast.makeText(RandomActivity.this.getApplicationContext(),
							"Invalid min or max values.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				TrainingParams params = new TrainingParams(
						TrainingActivities.Random);
				params.setActivity((Activity) v.getContext());
				params.setMin(min);
				params.setMax(max);

				RandomActivity.this.trainingTask = (TrainingTask) new TrainingTask()
						.execute(params);
				RandomActivity.this.startButton.setEnabled(false);
				RandomActivity.this.stopButton.setEnabled(true);
				RandomActivity.this.chronometer.setBase(SystemClock
						.elapsedRealtime());
				RandomActivity.this.chronometer.start();
				RandomActivity.this.wl.acquire();
				// refreshAdMob();
			}
		});

		this.stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				RandomActivity.this.StopTask();
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
