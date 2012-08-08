package cz.krajcovic.whistle;

import android.app.Activity;

public class TrainingParams {

	private Activity activity;

	private TrainingActivities type;

	private int min;
	private int max;

	private int period;

	public TrainingParams(TrainingActivities type) {
		this.type = type;
	}

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

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public TrainingActivities getType() {
		return type;
	}

	public void setType(TrainingActivities type) {
		this.type = type;
	}
}
