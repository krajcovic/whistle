package cz.krajcovic.whistle;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends AdMobActivity {

	private static final String TAG = "MenuActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_menu);
		this.setContentView(R.layout.activity_menu);

		Button buttonRandom = (Button) this.findViewById(R.id.buttonRandom);
		buttonRandom.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						RandomActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});

		Button buttonPeriod = (Button) this.findViewById(R.id.buttonPeriod);
		buttonPeriod.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						PeriodActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});

		Button buttonCycle = (Button) this.findViewById(R.id.buttonCycle);
		buttonCycle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						CycleActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});

//		this.refreshAdMob();

		KeyguardManager keyguardManager = (KeyguardManager) this
				.getSystemService(Activity.KEYGUARD_SERVICE);
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);

	}
}
