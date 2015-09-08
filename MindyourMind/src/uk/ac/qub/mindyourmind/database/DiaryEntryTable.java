package uk.ac.qub.mindyourmind.database;

public class DiaryEntryTable {

	// columns of the users table
	public static final String TABLE_DIARY_ENTRYS = "diary_entries";
	public static final String COLUMN_DIARY_ENTRY_ID = "_id";
	public static final String COLUMN_DIARY_ENTRY_TITLE = "title";
	public static final String COLUMN_DIARY_ENTRY_CONTENT = "content";
	public static final String COLUMN_DIARY_ENTRY_TIMESTAMP = "time_stamp";
	public static final String COLUMN_DIARY_ENTRY_USER_ID = "user_id";

	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_DIARY_ENTRY_ID,
			COLUMN_DIARY_ENTRY_TITLE,
			COLUMN_DIARY_ENTRY_CONTENT,
			COLUMN_DIARY_ENTRY_TIMESTAMP,
			COLUMN_DIARY_ENTRY_USER_ID
	};

	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_DIARY_ENTRYS + "(" 
			+ COLUMN_DIARY_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_DIARY_ENTRY_TITLE + " TEXT NOT NULL, "
			+ COLUMN_DIARY_ENTRY_CONTENT + " TEXT NOT NULL, "
			+ COLUMN_DIARY_ENTRY_TIMESTAMP + " INTEGER NOT NULL, "
			+ COLUMN_DIARY_ENTRY_USER_ID + " INTEGER NOT NULL "
			+");";
}
