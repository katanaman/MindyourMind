package uk.ac.qub.mindyourmind.providers;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


public class TaskProvider extends ContentProvider {
	
	// DataBase Columns
	public static final String COLUMN_TASKID = "_id";
	public static final String COLUMN_DATE_TIME = "task_date_time";
	public static final String COLUMN_NOTES = "notes";
	public static final String COLUMN_TITLE = "title";
	
	// DataBase Related Constants
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "data";
	
	//TODO add more tables
	private static final String DATABASE_TABLE = "tasks";
	
	//content provider Uri and Authority
	public static final String AUTHORITY = "uk.ac.qub.mindyourmind.providers.TaskProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/task"); 
	
	//the database
	SQLiteDatabase db;

	
	//MIME types used for listing tasks or looking up a single task
	private static final String TASKS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.tasks";
	private static final String TASK_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.tasks.task";
	
	//UriMatcher stuff
	private static final int LIST_TASK = 0;
	private static final int ITEM_TASK = 1;
	private static final UriMatcher URI_MATCHER = buildUriMatcher();
	
	/**
	 * Builds up a UriMatcher for search suggestion and shortcut refresh queries
	 */
	private static UriMatcher buildUriMatcher(){
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, "task", LIST_TASK);
		matcher.addURI(AUTHORITY, "task/#", ITEM_TASK);
		return matcher;
	}
	
	/**
	 * method needed to query the supported types
	 */
	@Override
	public String getType(Uri uri){
		switch (URI_MATCHER.match(uri)) {
			case LIST_TASK: 
				return TASKS_MIME_TYPE;
			case ITEM_TASK:
				return TASK_MIME_TYPE;
			default : 
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

			
	@Override
	public boolean onCreate() {
		// get connection to database
		db = new DatabaseHelper(getContext()).getWritableDatabase();
		return true;
	}


	/**
	 * called to read from the database
	 */
	@Override
	public Cursor query(Uri uri, String[] ignored1, String selection,
			String[] selectionArgs, String sortOrder) {
		String[] customPojection = new String[]{COLUMN_TASKID, COLUMN_TITLE, COLUMN_NOTES, COLUMN_DATE_TIME};
		
		Cursor c;
		
		switch(URI_MATCHER.match(uri)){
		case LIST_TASK:
			c = db.query(DATABASE_TABLE, customPojection, selection, selectionArgs, null, null, sortOrder);
			break;
		case ITEM_TASK:
			c = db.query(DATABASE_TABLE, customPojection, COLUMN_TASKID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))}, null, null, null, null);
			if(c.getCount() >0){
				c.moveToFirst();
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
		
		// the observer in this pattern allowing refreshing of the UI
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}

	/**
	 * called to insert into db
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		
		//don't allow value to specify it's own row id
		if (values.containsKey(COLUMN_TASKID)){
			throw new UnsupportedOperationException();
		}
		
		//return the id of the newly inserted item
		long id = db.insertOrThrow(DATABASE_TABLE, null, values);
		//observer patten to notify the listners 
		getContext().getContentResolver().notifyChange(uri, null);
		//return the added uri with it's id 
		return ContentUris.withAppendedId(uri, id);
	}

	
	/**
	 * method called when deleting a task
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = db.delete(DATABASE_TABLE, COLUMN_TASKID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
		
		if (count>0){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	/**
	 * method called when updating a task
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		//don't allow the ability to change a task id
		if ( values.containsKey(COLUMN_TASKID)){
			throw new UnsupportedOperationException();
		}
		
		//updating the required values using '?' as a security feature to prevent sql injection
		int count = db.update(DATABASE_TABLE, values, COLUMN_TASKID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
		
		//notify any listeners 
		if (count > 0){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		//return number of things changed
		return count;
	}
	
	
	/**
	 *Helper Class that will create and update our database
	 */
	protected static class DatabaseHelper extends SQLiteOpenHelper {
		
		static final String DATABASE_CREATE =
				"create table " + DATABASE_TABLE + " ("+
				COLUMN_TASKID + " integer primary key autoincrement, "+
				COLUMN_TITLE + " text not null, "+
				COLUMN_NOTES + " text not null, "+
				COLUMN_DATE_TIME + " integer not null);";
		
		public DatabaseHelper(Context context) {
			super (context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			throw new UnsupportedOperationException();
		}
	}
}

