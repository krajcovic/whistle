package cz.krajcovic.whistle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdMobActivity extends Activity {

	private AdView adView;

	/* Your ad unit id. Replace with your actual ad unit id. */
	private static final String AD_UNIT_ID = "ca-app-pub-4197154738167514/8913637788";

	protected void onCreate(Bundle savedInstanceState, int activity) {
		super.onCreate(savedInstanceState);
		this.setContentView(activity);

		// Create an ad.
		this.adView = new AdView(this);
		this.adView.setAdSize(AdSize.BANNER);
		this.adView.setAdUnitId(AD_UNIT_ID);

		// Add the AdView to the view hierarchy. The view will have no size
		// until the ad is loaded.
		LinearLayout layout = (LinearLayout) this
				.findViewById(R.id.linearLayout);
		layout.addView(this.adView);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("AAA780CD3E74B3969124CE8589CC2C28").build();

		this.adView.loadAd(adRequest);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// this.refreshAdMob();
		if (this.adView != null) {
			this.adView.resume();
		}
	}

	@Override
	protected void onPause() {
		if (this.adView != null) {
			this.adView.pause();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// Destroy the AdView.
		if (this.adView != null) {
			this.adView.destroy();
		}
		super.onDestroy();
	}
}
