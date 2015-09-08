package uk.ac.qub.mindyourmind.database;

public class UniversityContentRepoTable {

	// columns of the users table
	public static final String TABLE_UNIVERSITY_CONTENT_REPO = "university_content_repo";
	public static final String COLUMN_UNIVERSITY_CONTENT_REPO_ID = "_id";
	public static final String COLUMN_UNIVERSITY_CONTENT_REPO_TITLE = "title";
	public static final String COLUMN_UNIVERSITY_CONTENT_REPO_CONTENT_STRING = "content_string";
	public static final String COLUMN_UNIVERSITY_CONTENT_REPO_USER_INSTITUTION_ID = "user_university_id";

	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_UNIVERSITY_CONTENT_REPO_ID,
			COLUMN_UNIVERSITY_CONTENT_REPO_TITLE,
			COLUMN_UNIVERSITY_CONTENT_REPO_CONTENT_STRING,
			COLUMN_UNIVERSITY_CONTENT_REPO_USER_INSTITUTION_ID
	};

	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_UNIVERSITY_CONTENT_REPO + "(" 
			+ COLUMN_UNIVERSITY_CONTENT_REPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_UNIVERSITY_CONTENT_REPO_TITLE + " TEXT NOT NULL, "
			+ COLUMN_UNIVERSITY_CONTENT_REPO_CONTENT_STRING + " TEXT NOT NULL, "
			+ COLUMN_UNIVERSITY_CONTENT_REPO_USER_INSTITUTION_ID + " INTEGER NOT NULL "
			+");";
}
