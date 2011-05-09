package cream.mymood;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MoodEdit extends Activity {

	private final String TAG = "MyMood";

	private MyMoodDbAdapter dbHelper;

	EditText moodText;
	TextView timeView;
	Button submit;
	Button cancel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mood_edit);

		Log.d(TAG, "Activity created");

		dbHelper = new MyMoodDbAdapter(this).open();

		moodText = (EditText) findViewById(R.id.mood);
		timeView = (TextView) findViewById(R.id.time);
		submit = (Button) findViewById(R.id.submit);
		cancel = (Button) findViewById(R.id.cancel);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editable mood = moodText.getText();
				CharSequence time = timeView.getText();

				Log.i(TAG, "mood:" + mood + ", time:" + time);

				dbHelper.createMood(null, mood.toString(), null);

				setResult(RESULT_OK);
				dbHelper.close();
				finish();
			}

		});
	}
}