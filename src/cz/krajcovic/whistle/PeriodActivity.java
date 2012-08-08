package cz.krajcovic.whistle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PeriodActivity extends Activity {

	private static final String TAG = "RandomActivity";

	private EditText periodText;

	private Button startButton;
	private Button stopButton;

	TrainingTask trainingTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_period);

		periodText = (EditText) findViewById(R.id.editTextPeriod);

		startButton = (Button) findViewById(R.id.buttonStart);
		stopButton = (Button) findViewById(R.id.buttonStop);
		stopButton.setEnabled(false);

		periodText.setText(Integer.toString(20));

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int period;
				try {
					period = periodText.getText().toString().equals("") ? 0
							: Integer.parseInt(periodText.getText().toString());

				}

				catch (NumberFormatException ex) {
					Log.e(TAG, ex.getMessage());
					Toast.makeText(getApplicationContext(),
							"Invalid min or max values.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				TrainingParams params = new TrainingParams(
						TrainingActivities.Period);
				params.setActivity((Activity) v.getContext());
				params.setPeriod(period);

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
