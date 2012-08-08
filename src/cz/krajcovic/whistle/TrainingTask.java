package cz.krajcovic.whistle;

import java.util.Random;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.util.Log;

public class TrainingTask extends AsyncTask<TrainingParams, Integer, Integer> {

	private static final String TAG = "TrainingTask";

	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;

	Random random = new Random(System.currentTimeMillis());

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(TrainingParams... params) {

		Integer counter = 0;

		synchronized (this) {

			// Set the hardware buttons to control the music
			params[0].getActivity().setVolumeControlStream(
					AudioManager.STREAM_MUSIC);
			// Load the sound
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
			soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
				public void onLoadComplete(SoundPool soundPool, int sampleId,
						int status) {
					loaded = true;
				}
			});

			soundID = soundPool.load(params[0].getActivity(), R.raw.whistle01,
					1);
			
			playPool(params[0]);

			try {
				while (true) {
					switch(params[0].getType())
					{
					case Random:
						wait(getRandom(params[0]) * 1000);
						break;
					case Period:
						wait(params[0].getPeriod() * 1000);
						break;
					case Cycle:
						wait(params[0].getActive() * 1000);
						playPool(params[0]);
						wait(params[0].getRest() * 1000);
						break;
					}

					counter++;

					if (isCancelled()) {
						return counter;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}

		return counter;
	}

	public int getRandom(TrainingParams params) {
		return random.nextInt(params.getMax() - params.getMin())
				+ params.getMin();
	}

	public void playPool(TrainingParams params) {
		params.getActivity();
		// Getting the user sound settings
		AudioManager audioManager = (AudioManager) params.getActivity()
				.getSystemService(Context.AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		// Is the sound loaded already?
		if (loaded) {
			soundPool.play(soundID, volume, volume, 1, 0, 1f);
			// Log.e("Test", "Played sound");
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
