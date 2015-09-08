package uk.ac.qub.mindyourmind.database;

public class UserTable {

	// columns of the users table
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_USER_ID = "_id";
	public static final String COLUMN_USER_NAME = "name";
	public static final String COLUMN_USER_EMAIL = "email_address";
	public static final String COLUMN_USER_DATE_OF_BIRTH = "date_of_birth";
	public static final String COLUMN_USER_PASSWORD = "password";
	public static final String COLUMN_USER_CODE = "code";
	public static final String COLUMN_USER_GENDER = "gender";

	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_USER_ID,
			COLUMN_USER_NAME,
			COLUMN_USER_EMAIL,
			COLUMN_USER_DATE_OF_BIRTH,
			COLUMN_USER_PASSWORD,
			COLUMN_USER_CODE,
			COLUMN_USER_GENDER
	};


	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_USERS + "(" 
			+ COLUMN_USER_ID + " INTEGER PRIMARY KEY, " 
			+ COLUMN_USER_NAME + " TEXT NOT NULL, "
			+ COLUMN_USER_EMAIL + " TEXT NOT NULL, "
			+ COLUMN_USER_DATE_OF_BIRTH + " INTEGER NOT NULL, "
			+ COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
			+ COLUMN_USER_CODE + " TEXT NOT NULL, "
			+ COLUMN_USER_GENDER + " TEXT NOT NULL "
			+");";
}
