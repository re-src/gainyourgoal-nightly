package at.jku.mc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
@Deprecated
/**
 * set up Database inclusive relations between lists and tasks. 1 list can have
 * many tasks
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "to-do.db";
	// which version is currently the best?
	private static final int DATABASE_VERSION = 1;

	//TODO: table user need to be reviewed and changed
	public static final String TABLE_USER = "user";
	public static final String USER_COLUMN_ID = "_id";
	public static final String USER_COLUMN_NAME = "name";
	public static final String USER_COLUMN_BADGES = "badges";
	public static final String USER_COLUMN_POINTS = "points";

	public static final String TABLE_TODOLIST = "todolist";
	public static final String TODOLIST_COLUMN_ID = "_id";
	public static final String TODOLIST_COLUMN_TITLE = "title";
	public static final String TODOLIST_COLUMN_DESCRIPTION = "description";
	public static final String TODOLIST_COLUMN_CATEGORY = "category";
	public static final String TODOLIST_COLUMN_ISARCHIVED = "archived";

	public static final String TABLE_TASK = "task";
	public static final String TASK_COLUMN_ID = "_id";
	public static final String TASK_COLUMN_LIST = "list";
	public static final String TASK_COLUMN_NAME = "name";
	public static final String TASK_COLUMN_DESCRIPTION = "description";
	public static final String TASK_COLUMN_PRIORITY = "priority";
	public static final String TASK_COLUMN_REDO = "redo";
	public static final String TASK_COLUMN_CREATED = "created";
	public static final String TASK_COLUMN_DEADLINE = "deadline";
	public static final String TASK_COLUMN_CHECKED = "checked";

	public static final String TABLE_BADGE = "badge";
	public static final String BADGE_COLUMN_ID = "_id";
	public static final String BADGE_COLUMN_NAME = "name";
	public static final String BADGE_COLUMN_CATEGORY = "category";

	/*
	 * public static final String TABLE_STATISTICS = "statistics"; public static
	 * final String STATISTICS_COLUMN_ID = "_id";
	 */

	public static final String TABLE_PREFERENCES = "preferences";
	public static final String PREFERENCES_COLUMN_ID = "_id";
	public static final String PREFERENCES_COLUMN_NAME = "name";
	public static final String PREFERENCES_COLUMN_VALUE = "value";

	// Table creation sql statement
	private static final String USER_CREATE = "create table " + TABLE_USER
			+ "(" + USER_COLUMN_ID + " integer primary key autoincrement, "
			+ USER_COLUMN_NAME + " text not null, " + USER_COLUMN_BADGES
			+ " string, " + USER_COLUMN_POINTS + " integer);";

	private static final String TODOLIST_CREATE = "create table "
			+ TABLE_TODOLIST + "(" + TODOLIST_COLUMN_ID
			+ " integer primary key autoincrement, " + TODOLIST_COLUMN_TITLE
			+ " text not null, " + TODOLIST_COLUMN_DESCRIPTION + " text, "
			+ TODOLIST_COLUMN_CATEGORY + " integer, "
			+ TODOLIST_COLUMN_ISARCHIVED + " boolean);";

	// store dates as integer -> as Unix Time, the number of seconds since
	// The ON DELETE CASCADE bit causes the Task entry to be deleted in the
	// event that the todolist, it refers to is deleted.
	// 1970-01-01 00:00:00 UTC.
	private static final String TASK_CREATE = "create table " + TABLE_TASK
			+ "(" + TASK_COLUMN_ID + " integer primary key autoincrement, "
			+ TASK_COLUMN_LIST + " integer, " + TASK_COLUMN_NAME
			+ " text not null, " + TASK_COLUMN_DESCRIPTION + " text not null, "
			+ TASK_COLUMN_PRIORITY + " integer, " + TASK_COLUMN_REDO
			+ " integer, " + TASK_COLUMN_CREATED + " integer, "
			+ TASK_COLUMN_DEADLINE + " integer, " + TASK_COLUMN_CHECKED
			+ " integer, foreign key(" + TASK_COLUMN_LIST + ") references "
			+ TABLE_TODOLIST + " (" + TODOLIST_COLUMN_ID
			+ ") on delete cascade);";

	private static final String BADGE_CREATE = "create table " + TABLE_BADGE
			+ "(" + BADGE_COLUMN_ID + " integer primary key autoincrement, "
			+ BADGE_COLUMN_NAME + " text not null);";

	private static final String PREFERENCES_CREATE = "create table "
			+ TABLE_PREFERENCES + "(" + PREFERENCES_COLUMN_ID
			+ " integer primary key autoincrement, " + PREFERENCES_COLUMN_NAME
			+ " String, " + PREFERENCES_COLUMN_VALUE + " String);";

	// Table drop sql statement
	private static final String USER_DROP = "drop table if exists "
			+ TABLE_USER;
	private static final String TODOLIST_DROP = "drop table if exists "
			+ TABLE_TODOLIST;
	private static final String TASK_DROP = "drop table if exists "
			+ TABLE_TASK;
	private static final String BADGE_DROP = "drop table if exists "
			+ TABLE_BADGE;
	private static final String PREFERENCES_DROP = "drop table if exists "
			+ TABLE_PREFERENCES;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// add all CREATE TABLE statements
		database.execSQL(USER_CREATE);
		database.execSQL(TODOLIST_CREATE);
		database.execSQL(TASK_CREATE);
		database.execSQL(BADGE_CREATE);
		database.execSQL(PREFERENCES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		// add DROP TABLE statements
		db.execSQL(USER_DROP);
		db.execSQL(TODOLIST_DROP);
		db.execSQL(TASK_DROP);
		db.execSQL(BADGE_DROP);
		db.execSQL(PREFERENCES_DROP);
		onCreate(db);
	}

}
