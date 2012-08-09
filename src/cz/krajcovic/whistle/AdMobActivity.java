package cz.krajcovic.whistle;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;

public class AdMobActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshAdMob();
	}

	public void refreshAdMob() {
		AdView mAdView = (AdView) findViewById(R.id.ad);

		AdRequest adRequest = new AdRequest();
		adRequest.addKeyword("sporting goods");
		mAdView.loadAd(adRequest);
	}
}
