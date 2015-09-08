package uk.ac.qub.mindyourmind.database;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper instance;
	private static SQLiteDatabase db;
	private final String TAG = this.getClass().getSimpleName();
	private static final String DATABASE_NAME = "DBName";
	private static final int DATABASE_VERSION = 1;

	
	public static synchronized DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}
		return instance;
	}

	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db == null || !db.isOpen()) {
			db = getWritableDatabase();
		}
		db.execSQL(UserTable.SQL_CREATE);
		db.execSQL(UserUniversityTable.SQL_CREATE);
		db.execSQL(UniversityContentRepoTable.SQL_CREATE);
		db.execSQL(DiaryEntryTable.SQL_CREATE);
		db.execSQL(DiaryEntryMediaTable.SQL_CREATE);
		db.execSQL(RatingsTable.SQL_CREATE);
		db.execSQL(LoginAuditTable.SQL_CREATE);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade old: " + oldVersion + " new: " + newVersion);
		throw new UnsupportedOperationException("Feature Not Supported Yet");
	}
	

	
}