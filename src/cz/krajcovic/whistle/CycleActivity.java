package cz.krajcovic.whistle;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class CycleActivity extends Activity {
	private static final String TAG = "RandomActivity";

	private EditText activeText;
	private EditText restText;

	private Button startButton;
	private Button stopButton;

	private Chronometer chronometer;

	TrainingTask trainingTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cycle);

		activeText = (EditText) findViewById(R.id.editTextActive);
		restText = (EditText) findViewById(R.id.editTextRest);
		startButton = (Button) findViewById(R.id.buttonStart);
		stopButton = (Button) findViewById(R.id.buttonStop);
		stopButton.setEnabled(false);

		chronometer = (Chronometer) findViewById(R.id.chronometer);

		activeText.setText(Integer.toString(30));
		restText.setText(Integer.toString(5));

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int active, rest;
				try {
					active = activeText.getText().toString().equals("") ? 0
							: Integer.parseInt(activeText.getText().toString());
					rest = restText.getText().equals("") ? 0 : Integer
							.parseInt(restText.getText().toString());
				}

				catch (NumberFormatException ex) {
					Log.e(TAG, ex.getMessage());
					Toast.makeText(getApplicationContext(),
							"Invalid min or max values.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				TrainingParams params = new TrainingParams(
						TrainingActivities.Cycle);
				params.setActivity((Activity) v.getContext());
				params.setActive(active);
				params.setRest(rest);

				trainingTask = (TrainingTask) new TrainingTask()
						.execute(params);
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				chronometer.setBase(SystemClock.elapsedRealtime());
				chronometer.start();
			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				StopTask();
			}

		});
		
		refreshAdMob();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshAdMob();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_whistle, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		StopTask();
	}

	private void StopTask() {
		if (trainingTask != null) {
			chronometer.stop();
			trainingTask.cancel(true);
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
	}
	
	private void refreshAdMob() {
		AdView mAdView = (AdView) findViewById(R.id.ad);

		AdRequest adRequest = new AdRequest();
		adRequest.addKeyword("sporting goods");
		mAdView.loadAd(adRequest);
	}
}
