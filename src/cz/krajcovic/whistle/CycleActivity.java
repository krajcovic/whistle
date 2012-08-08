package cz.krajcovic.whistle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CycleActivity extends Activity {
	private static final String TAG = "RandomActivity";

	private EditText activeText;
	private EditText restText;

	private Button startButton;
	private Button stopButton;

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

		activeText.setText(Integer.toString(30));
		restText.setText(Integer.toString(5));

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int active, rest;
				try {
					active = activeText.getText().toString().equals("") ? 0 : Integer
							.parseInt(activeText.getText().toString());
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
			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				StopTask();
			}

		});
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
		trainingTask.cancel(true);
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
	}
}
