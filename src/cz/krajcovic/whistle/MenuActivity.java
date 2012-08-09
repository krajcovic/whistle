package cz.krajcovic.whistle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends AdMobActivity {

	private static final String TAG = "MenuActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Button buttonRandom = (Button) findViewById(R.id.buttonRandom);
		buttonRandom.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						RandomActivity.class);
				startActivity(intent);
			}
		});

		Button buttonPeriod = (Button) findViewById(R.id.buttonPeriod);
		buttonPeriod.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						PeriodActivity.class);
				startActivity(intent);
			}
		});

		Button buttonCycle = (Button) findViewById(R.id.buttonCycle);
		buttonCycle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						CycleActivity.class);
				startActivity(intent);
			}
		});
		
		refreshAdMob();

	}
}
