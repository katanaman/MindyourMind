package uk.ac.qub.mindyourmind.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import uk.ac.qub.mindyourmind.database.DBHelper;
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.database.RatingsTable;
import uk.ac.qub.mindyourmind.database.UniversityContentRepoTable;
import uk.ac.qub.mindyourmind.database.UserTable;

public class MindYourMindProvider extends ContentProvider {


	//the database
	SQLiteDatabase db;

	//content provider Uri and Authority
	public static final String AUTHORITY = "uk.ac.qub.mindyourmind.providers.TaskProvider";

	public static final Uri DIARY_URI = Uri.parse("content://" + AUTHORITY + "/diaryentry");
	public static final Uri EDUCATION_URI = Uri.parse("content://" + AUTHORITY + "/education");
	public static final Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/user");
	public static final Uri RATINGS_URI = Uri.parse("content://" + AUTHORITY + "/ratings");

	//MIME types used for listing tasks or looking up a single task
	private static final String DIARY_ENTRIES_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.diaryentries";
	private static final String DIARY_ENTRY_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.diaryentries.entry";
	private static final String USERS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.users";
	private static final String USER_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.users.user";
	private static final String RATINGS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.ratings";
	private static final String RATING_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.ratings.rating";
	private static final String EDUCATIONS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.educations";
	private static final String EDUCATION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.uk.ac.qub.mindyourmind.educations.education";

	//UriMatcher codes
	private static final int ENTRIES_TASK = 0;
	private static final int ENTRY_TASK = 1;
	private static final int USERS_TASK = 2;
	private static final int USER_TASK = 3;
	private static final int RATINGS_TASK = 4;
	private static final int RATING_TASK = 5;
	private static final int EDUCATIONS_TASK = 6;
	private static final int EDUCATION_TASK = 7;
	//UriMatcher
	private static final UriMatcher URI_MATCHER = buildUriMatcher();

	/**
	 * Builds up a UriMatcher for search suggestion and shortcut refresh queries
	 */
	private static UriMatcher buildUriMatcher(){
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, "diaryentry", ENTRIES_TASK);
		matcher.addURI(AUTHORITY, "diaryentry/#", ENTRY_TASK); 
		matcher.addURI(AUTHORITY, "user", USERS_TASK);
		matcher.addURI(AUTHORITY, "user/#", USER_TASK); 
		matcher.addURI(AUTHORITY, "ratings", RATINGS_TASK);
		matcher.addURI(AUTHORITY, "ratings/#", RATING_TASK); 
		matcher.addURI(AUTHORITY, "education", EDUCATIONS_TASK);
		matcher.addURI(AUTHORITY, "education/#", EDUCATION_TASK);
		return matcher;
	}

	@Override
	public boolean onCreate() {
		db = DBHelper.getInstance(getContext()).getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		Cursor c;

		switch(URI_MATCHER.match(uri)){
		case ENTRIES_TASK:
			c = db.query(DiaryEntryTable.TABLE_DIARY_ENTRYS, DiaryEntryTable.PROJECTION, selection, selectionArgs, null, null, sortOrder);
			break;
		case ENTRY_TASK:
			c = db.query(DiaryEntryTable.TABLE_DIARY_ENTRYS, DiaryEntryTable.PROJECTION, DiaryEntryTable.COLUMN_DIARY_ENTRY_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))}, null, null, null, null);
			if(c.getCount() >0){
				c.moveToFirst();
			}
			break;

		case USERS_TASK :
			c = db.query(UserTable.TABLE_USERS, UserTable.PROJECTION, selection, selectionArgs, null, null, sortOrder);
			break;
		case USER_TASK :
			c = db.query(UserTable.TABLE_USERS, UserTable.PROJECTION, UserTable.COLUMN_USER_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))}, null, null, null, null);
			if(c.getCount() >0){
				c.moveToFirst();
			}
			break;

		case RATINGS_TASK :
			c = db.query(RatingsTable.TABLE_RATINGS, RatingsTable.PROJECTION, selection, selectionArgs, null, null, sortOrder);
			break;
		case RATING_TASK :
			c = db.query(RatingsTable.TABLE_RATINGS, RatingsTable.PROJECTION, RatingsTable.COLUMN_RATINGS_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))}, null, null, null, null);
			if(c.getCount() >0){
				c.moveToFirst();
			}
			break;

		case EDUCATIONS_TASK :
			c = db.query(UniversityContentRepoTable.TABLE_UNIVERSITY_CONTENT_REPO, UniversityContentRepoTable.PROJECTION, selection, selectionArgs, null, null, sortOrder);
			break;
		case EDUCATION_TASK :
			c = db.query(UniversityContentRepoTable.TABLE_UNIVERSITY_CONTENT_REPO, UniversityContentRepoTable.PROJECTION, UniversityContentRepoTable.COLUMN_UNIVERSITY_CONTENT_REPO_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))}, null, null, null, null);
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
	
	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case ENTRIES_TASK: 
			return DIARY_ENTRIES_MIME_TYPE;
		case ENTRY_TASK:
			return DIARY_ENTRY_MIME_TYPE;
		case USERS_TASK:
			return USERS_MIME_TYPE;
		case USER_TASK:
			return USER_MIME_TYPE;
		case RATINGS_TASK:
			return RATINGS_MIME_TYPE;
		case RATING_TASK:
			return RATING_MIME_TYPE;
		case EDUCATIONS_TASK:
			return EDUCATIONS_MIME_TYPE;
		case EDUCATION_TASK:
			return EDUCATION_MIME_TYPE;

		default : 
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		long id;
		
		switch (URI_MATCHER.match(uri)) {
		
		case ENTRIES_TASK: 
			//don't allow value to specify it's own row id
			if (values.containsKey(DiaryEntryTable.COLUMN_DIARY_ENTRY_ID)){
				throw new UnsupportedOperationException();
				}
			//return the id of the newly inserted item
			id = db.insertOrThrow(DiaryEntryTable.TABLE_DIARY_ENTRYS, null, values);
			//observer patten to notify the listners 
			getContext().getContentResolver().notifyChange(uri, null);
			//return the added uri with it's id 
			return ContentUris.withAppendedId(uri, id);

		case USERS_TASK:
			//return the id of the newly inserted item
			id = db.insertOrThrow(UserTable.TABLE_USERS, null, values);
			//observer patten to notify the listners 
			getContext().getContentResolver().notifyChange(uri, null);
			//return the added uri with it's id 
			return ContentUris.withAppendedId(uri, id);
			
		case RATINGS_TASK:
			//don't allow value to specify it's own row id
			if (values.containsKey(RatingsTable.COLUMN_RATINGS_ID)){
				throw new UnsupportedOperationException();
				}
			//return the id of the newly inserted item
			id = db.insertOrThrow(RatingsTable.TABLE_RATINGS, null, values);
			//observer patten to notify the listners 
			getContext().getContentResolver().notifyChange(uri, null);
			//return the added uri with it's id 
			return ContentUris.withAppendedId(uri, id);
		

		case EDUCATIONS_TASK:
			//don't allow value to specify it's own row id
			if (values.containsKey(UniversityContentRepoTable.COLUMN_UNIVERSITY_CONTENT_REPO_ID)){
				throw new UnsupportedOperationException();
				}
			//return the id of the newly inserted item
			id = db.insertOrThrow(UniversityContentRepoTable.TABLE_UNIVERSITY_CONTENT_REPO, null, values);
			//observer patten to notify the listners 
			getContext().getContentResolver().notifyChange(uri, null);
			//return the added uri with it's id 
			return ContentUris.withAppendedId(uri, id);
		

		default : 
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		int count;
		
		switch (URI_MATCHER.match(uri)) {
		case ENTRIES_TASK: 
			count = db.delete(DiaryEntryTable.TABLE_DIARY_ENTRYS, DiaryEntryTable.COLUMN_DIARY_ENTRY_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			if (count>0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			return count;
		case USERS_TASK:
			count = db.delete(UserTable.TABLE_USERS, UserTable.COLUMN_USER_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			if (count>0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			return count;

		case RATINGS_TASK:
			count = db.delete(RatingsTable.TABLE_RATINGS, RatingsTable.COLUMN_RATINGS_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			if (count>0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			return count;

		case EDUCATIONS_TASK:
			count = db.delete(UniversityContentRepoTable.TABLE_UNIVERSITY_CONTENT_REPO, UniversityContentRepoTable.COLUMN_UNIVERSITY_CONTENT_REPO_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			if (count>0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			return count;

		default : 
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
				
		int count;
		
		switch (URI_MATCHER.match(uri)) {
		case ENTRIES_TASK: 
			//don't allow the ability to change a task id
			if ( values.containsKey(DiaryEntryTable.COLUMN_DIARY_ENTRY_ID)){
				throw new UnsupportedOperationException();
			}
			
			//updating the required values using '?' as a security feature to prevent sql injection
			count = db.update(DiaryEntryTable.TABLE_DIARY_ENTRYS, values, DiaryEntryTable.COLUMN_DIARY_ENTRY_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			//notify any listeners 
			if (count > 0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			//return number of things changed
			return count;
		
		case USERS_TASK:
			//don't allow the ability to change a task id
			if ( values.containsKey(UserTable.COLUMN_USER_ID)){
				throw new UnsupportedOperationException();
			}
			
			//updating the required values using '?' as a security feature to prevent sql injection
			count = db.update(UserTable.TABLE_USERS, values, UserTable.COLUMN_USER_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			//notify any listeners 
			if (count > 0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			//return number of things changed
			return count;
		
		case RATINGS_TASK:
			//don't allow the ability to change a task id
			if ( values.containsKey(RatingsTable.COLUMN_RATINGS_ID)){
				throw new UnsupportedOperationException();
			}
			
			//updating the required values using '?' as a security feature to prevent sql injection
			count = db.update(RatingsTable.TABLE_RATINGS, values, RatingsTable.COLUMN_RATINGS_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			//notify any listeners 
			if (count > 0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			//return number of things changed
			return count;
	
		case EDUCATIONS_TASK:
			//don't allow the ability to change a task id
			if ( values.containsKey(UniversityContentRepoTable.COLUMN_UNIVERSITY_CONTENT_REPO_ID)){
				throw new UnsupportedOperationException();
			}
			
			//updating the required values using '?' as a security feature to prevent sql injection
			count = db.update(UniversityContentRepoTable.TABLE_UNIVERSITY_CONTENT_REPO, values, UniversityContentRepoTable.COLUMN_UNIVERSITY_CONTENT_REPO_ID + "=?", new String[]{Long.toString(ContentUris.parseId(uri))});
			
			//notify any listeners 
			if (count > 0){
				getContext().getContentResolver().notifyChange(uri, null);
			}
			//return number of things changed
			return count;
		

		default : 
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}	
	}
}
