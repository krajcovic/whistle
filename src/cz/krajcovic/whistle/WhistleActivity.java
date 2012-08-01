package cz.krajcovic.whistle;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WhistleActivity extends Activity {

	private static final String TAG = "WhistleActivity";

	private EditText minText;
	private EditText maxText;

	private Button startButton;
	private Button stopButton;
	
	TrainingTask trainingTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whistle);

		minText = (EditText) findViewById(R.id.editTextMin);
		maxText = (EditText) findViewById(R.id.editTextMax);
		startButton = (Button) findViewById(R.id.buttonStart);
		stopButton = (Button) findViewById(R.id.buttonStop);
		stopButton.setEnabled(false);
		
		minText.setText(1);
		maxText.setText(10);

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int min, max;
				try {
					min = minText.getText().toString().equals("") ? 0 : Integer
						.parseInt(minText.getText().toString());
					max = maxText.getText().equals("") ? 0 : Integer
						.parseInt(maxText.getText().toString());
					
					if(min >= max)
					{
						Toast.makeText(getApplicationContext(), "Max have to bigger than min.", Toast.LENGTH_SHORT).show();
						return;
					}
						
				}
				
				catch (NumberFormatException ex)
				{
					Log.e(TAG, ex.getMessage());
					Toast.makeText(getApplicationContext(), "Invalid min or max values.", Toast.LENGTH_SHORT).show();
					return;
				}
				
				TrainingParams params = new TrainingParams();
				params.setActivity((Activity) v.getContext());
				params.setMin(min);
				params.setMax(max);				
				
				trainingTask = (TrainingTask) new TrainingTask().execute(params);
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				trainingTask.cancel(true);
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_whistle, menu);
		return true;
	}
}
