package uk.ac.qub.mindyourmind.database;

public class RatingsTable {

	// columns of the users table
	public static final String TABLE_RATINGS = "ratings";
	public static final String COLUMN_RATINGS_ID = "_id";
	public static final String COLUMN_RATINGS_TYPE = "type";
	public static final String COLUMN_RATINGS_VALUE = "value";
	public static final String COLUMN_RATINGS_TIMESTAMP = "timestamp";
	public static final String COLUMN_RATINGS_USER_ID = "user_id";

	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_RATINGS_ID,
			COLUMN_RATINGS_TYPE,
			COLUMN_RATINGS_VALUE,
			COLUMN_RATINGS_TIMESTAMP,
			COLUMN_RATINGS_USER_ID
	};

	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_RATINGS + "(" 
			+ COLUMN_RATINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_RATINGS_TYPE + " TEXT NOT NULL, "
			+ COLUMN_RATINGS_VALUE + " INTEGER NOT NULL, "
			+ COLUMN_RATINGS_TIMESTAMP + " INTEGER NOT NULL, "
			+ COLUMN_RATINGS_USER_ID + " INTEGER NOT NULL "
			+");";
}
