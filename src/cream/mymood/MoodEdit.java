package cream.mymood;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MoodEdit extends Activity {

	private final String TAG = "MyMood";

	EditText moodText;
	EditText noteText;
	Button submit;
	Button cancel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mood_edit);

		Log.d(TAG, "Activity created");
		moodText = (EditText) findViewById(R.id.mood);
		noteText = (EditText) findViewById(R.id.note);
		submit = (Button) findViewById(R.id.submit);
		cancel = (Button) findViewById(R.id.cancel);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editable mood = moodText.getText();
				Editable note = noteText.getText();

				Log.i(TAG, "mood:" + mood + ", note:" + note);

			}

		});
	}
}