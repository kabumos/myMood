/**
 * 
 */
package cream.mymood;

import java.util.Date;

import cream.mymood.util.StringUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Cream
 * 
 */
public class MyMoodDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_ICON = "icon";
	public static final String KEY_MOOD = "mood";
	public static final String KEY_TIME = "time";

	private static final String TAG = "MyMoodDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "mymood";
	private static final String DATABASE_TABLE = "mood";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + "(" + KEY_ROWID
			+ "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ICON + "TEXT, "
			+ KEY_MOOD + "TEXT NOT NULL, " + KEY_TIME + "INTEGER NOT NULL);";

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version" + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		}
	}

	public MyMoodDbAdapter(Context ctx) {
		mCtx = ctx;
	}

	public MyMoodDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Create a new mood record. 'mood' is required. System time will be instead
	 * when 'time' is missing.
	 * 
	 * @param icon
	 * @param mood
	 * @param time
	 * @return new record ID.
	 */
	public long createMood(String icon, String mood, Date time) {
		if (StringUtil.isEmpty(mood)) {
			throw new IllegalArgumentException("mood is missing");
		}
		Date createdTime = time != null ? time : new Date();

		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_ICON, icon);
		initialValues.put(KEY_MOOD, mood);
		initialValues.put(KEY_TIME, createdTime.getTime());

		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Delete the record match rowId.
	 * 
	 * @param rowId
	 * @return true - the record delete success.
	 */
	public boolean deleteMood(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Returns all moods record in database.
	 * 
	 * @return
	 */
	public Cursor fetchAllMoods() {

		return mDb.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_ICON,
				KEY_MOOD, KEY_TIME }, null, null, null, null, null);
	}

	/**
	 * Returns the record match rowId.
	 * 
	 * @param rowId
	 * @return <code>null</code> will be returned when no record matched.
	 * @throws SQLException
	 */
	public Cursor fetchMood(long rowId) throws SQLException {

		Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * Update mood record which match rowId. 'icon' could be empty. 'mood' is
	 * required. When 'time' is missing, it will set as current time.
	 * 
	 * @param rowId
	 * @param icon
	 * @param mood
	 * @param time
	 * @return true - update success.
	 */
	public boolean updateMood(long rowId, String icon, String mood, Date time) {
		if (StringUtil.isEmpty(mood)) {
			throw new IllegalArgumentException("mood is missing");
		}
		Date updateTime = time != null ? time : new Date();
		ContentValues args = new ContentValues();
		args.put(KEY_ICON, icon);
		args.put(KEY_MOOD, mood);
		args.put(KEY_TIME, updateTime.getTime());

		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
