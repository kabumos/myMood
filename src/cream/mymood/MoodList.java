/**
 * 
 */
package cream.mymood;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Show a list of mood.
 * 
 * @author Cream
 * @since 2011-05-01
 */
public class MoodList extends ListActivity {

	private static final String TAG = "MoodList";

	private static final int CONTEXT_MENU_GROUP = 0x01;
	private static final int OPTIONS_MENU_GROUP = 0X02;

	private static final int EDIT_ID = 0x01;
	private static final int DELETE_ID = 0x02;
	private static final int CREATE_ID = 0x03;
	private static final int ABOUT_ID = 0x04;

	private static final int FIRST_ORDER = ContextMenu.FIRST;

	private MyMoodDbAdapter dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mood_list);

		Log.d(TAG, "Creating database");

		dbHelper = new MyMoodDbAdapter(this);
		dbHelper.open();

		fillData();
		registerForContextMenu(getListView());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void setListAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setListAdapter(adapter);
	}

	/**
	 * Call Database to fetch all data. Then fill it to view.
	 */
	private void fillData() {
		Cursor moodsCursor = dbHelper.fetchAllMoods();
		startManagingCursor(moodsCursor);

		String[] from = new String[] { MyMoodDbAdapter.KEY_ICON,
				MyMoodDbAdapter.KEY_MOOD, MyMoodDbAdapter.KEY_TIME };

		int[] to = new int[] { R.id.icon, R.id.mood, R.id.time };

		SimpleCursorAdapter moods = new SimpleCursorAdapter(this,
				R.layout.mood_row, moodsCursor, from, to);
		setListAdapter(moods);
	}

	// Menu operation

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Log.d(TAG, "creating context menu");
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(CONTEXT_MENU_GROUP, EDIT_ID, FIRST_ORDER,
				R.string.menu_edit_mood);
		menu.add(CONTEXT_MENU_GROUP, DELETE_ID, FIRST_ORDER + 1,
				R.string.menu_delete_mood);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case EDIT_ID:
			// Call MoodEdit.
			Intent i = new Intent();
			//
			// i.set
			// startActivityForResult(intent, requestCode);
			break;
		case DELETE_ID:
			// Call DBAdapter to delete this item.

			break;
		}

		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(OPTIONS_MENU_GROUP, CREATE_ID, FIRST_ORDER,
				R.string.menu_write_mood);
		menu.add(OPTIONS_MENU_GROUP, ABOUT_ID, FIRST_ORDER + 1,
				R.string.menu_about);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getGroupId() == OPTIONS_MENU_GROUP) {
			switch (item.getItemId()) {
			case CREATE_ID:
				Intent i = new Intent();
				i.setClass(MoodList.this, MoodEdit.class);
				startActivityForResult(i, 0);
				break;
			case ABOUT_ID:
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	// Activity result operation.

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}

}
