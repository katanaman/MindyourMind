package uk.ac.qub.mindyourmind.database;

public class LoginAuditTable {

	// columns of the users table
	public static final String TABLE_LOGIN_AUDIT = "login_audit";
	public static final String COLUMN_LOGIN_AUDIT_ID = "_id";
	public static final String COLUMN_LOGIN_AUDIT_LAST_LOGIN = "last_login";
	public static final String COLUMN_LOGIN_AUDIT_LAST_ONLINE_REFRESH = "last_online_refresh";
	public static final String COLUMN_LOGIN_AUDIT_LOGIN_COUNT = "login_count";
	public static final String COLUMN_LOGIN_AUDIT_USER_ID = "user_id";


	// projection of all columns
	public static final String[] PROJECTION = {
			COLUMN_LOGIN_AUDIT_ID,
			COLUMN_LOGIN_AUDIT_LAST_LOGIN,
			COLUMN_LOGIN_AUDIT_LAST_ONLINE_REFRESH,
			COLUMN_LOGIN_AUDIT_LOGIN_COUNT,
			COLUMN_LOGIN_AUDIT_USER_ID
	};


	// SQL statement of the employees table creation
	static final String SQL_CREATE = "CREATE TABLE " + TABLE_LOGIN_AUDIT + "(" 
			+ COLUMN_LOGIN_AUDIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_LOGIN_AUDIT_LAST_LOGIN + " INTEGER NOT NULL, "
			+ COLUMN_LOGIN_AUDIT_LAST_ONLINE_REFRESH + " INTEGER NOT NULL, "
			+ COLUMN_LOGIN_AUDIT_LOGIN_COUNT + " INTEGER NOT NULL, "
			+ COLUMN_LOGIN_AUDIT_USER_ID + " INTEGER NOT NULL "
			+");";
}
