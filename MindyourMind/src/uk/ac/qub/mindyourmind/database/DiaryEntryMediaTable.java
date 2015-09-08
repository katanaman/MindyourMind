package uk.ac.qub.mindyourmind.database;

public class DiaryEntryMediaTable {

	// columns of the users table
	public static final String TABLE_DIARY_ENTRY_MEDIA = "diary_entry_media";
	public static final String COLUMN_DIARY_ENTRY_MEDIA_ID = "_id";
	public static final String COLUMN_DIARY_ENTRY_MEDIA_URI = "uri";
	public static final String COLUMN_DIARY_ENTRY_MEDIA_DIARY_ID = "diary_id";

	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_DIARY_ENTRY_MEDIA_ID,
			COLUMN_DIARY_ENTRY_MEDIA_URI,
			COLUMN_DIARY_ENTRY_MEDIA_DIARY_ID
	};

	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_DIARY_ENTRY_MEDIA + "(" 
			+ COLUMN_DIARY_ENTRY_MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_DIARY_ENTRY_MEDIA_URI + " TEXT NOT NULL, "
			+ COLUMN_DIARY_ENTRY_MEDIA_DIARY_ID + " INTEGER NOT NULL "
			+");";
}
