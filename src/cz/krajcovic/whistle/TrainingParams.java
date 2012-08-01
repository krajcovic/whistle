package cz.krajcovic.whistle;

import android.app.Activity;

public class TrainingParams {
	
	private Activity activity;
	
	private int min;
	private int max;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
