package uk.ac.qub.mindyourmind.database;

public class UserUniversityTable {

	// columns of the users table
	public static final String TABLE_USER_UNIVERSITY = "user_university";
	public static final String COLUMN_USER_UNIVERSITY_ID = "_id";
	public static final String COLUMN_USER_UNIVERSITY_NAME = "institution_name";
	public static final String COLUMN_USER_UNIVERSITY_FACULTY = "faculty";
	public static final String COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY = "year_of_study";
	public static final String COLUMN_USER_UNIVERSITY_USER_ID = "user_id";


	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_USER_UNIVERSITY_ID,
			COLUMN_USER_UNIVERSITY_NAME,
			COLUMN_USER_UNIVERSITY_FACULTY,
			COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY,
			COLUMN_USER_UNIVERSITY_USER_ID
	};

	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_USER_UNIVERSITY + "(" 
			+ COLUMN_USER_UNIVERSITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_USER_UNIVERSITY_NAME + " TEXT NOT NULL, "
			+ COLUMN_USER_UNIVERSITY_FACULTY + " TEXT NOT NULL, "
			+ COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY + " INTEGER NOT NULL, "
			+ COLUMN_USER_UNIVERSITY_USER_ID + " INTEGER NOT NULL "
			+");";
}
