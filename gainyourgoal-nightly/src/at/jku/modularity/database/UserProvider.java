package at.jku.modularity.database;

import java.util.ArrayList;

import at.jku.modularity.datamodel.Badge;


//TODO does work, but count-mechanism is wrong
public final class UserProvider {
	
	private IDatabaseHelper dbHelper;

	public UserProvider(IDatabaseHelper currentDbHelper) {
		dbHelper = currentDbHelper;
	}	
	
	public void open(){
		dbHelper.openDb();
	}


	public void close() {
		dbHelper.closeDb();
	}

	public void addPoints(int newPoints) {
		
		dbHelper.addPoints(newPoints);
//		int userpoints = 0;
//		Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
//				new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null, null,
//				null, null, null);
//		Log.d("asdf", "usercursor " + cursor);
//		if (cursor.getCount() == 0) {
//			createUser();
//			cursor = database.query(DatabaseHelper.TABLE_USER,
//					new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null,
//					null, null, null, null);
//		}
//		if (cursor.moveToFirst())
//			userpoints = cursor.getInt(cursor
//					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
//		ContentValues args = new ContentValues();
//		args.put(DatabaseHelper.USER_COLUMN_POINTS, userpoints + int userpoints = 0;
//		Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
//		new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null, null,
//		null, null, null);
//Log.d("asdf", "usercursor " + cursor);
//if (cursor.getCount() == 0) {
//	createUser();
//	cursor = database.query(DatabaseHelper.TABLE_USER,
//			new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null,
//			null, null, null, null);
//}
//if (cursor.moveToFirst())
//	userpoints = cursor.getInt(cursor
//			.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
//ContentValues args = new ContentValues();
//args.put(DatabaseHelper.USER_COLUMN_POINTS, userpoints + newPoints);
//database.update(DatabaseHelper.TABLE_USER, args,
//		DatabaseHelper.USER_COLUMN_ID + " = " + 1, null););
//		database.update(DatabaseHelper.TABLE_USER, args,
//				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}

	public void removePoints(int newPoints) {
		
		dbHelper.removePoints(newPoints);
//		int userpoints = 0;
//		Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
//				new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null, null,
//				null, null, null);
//		if (cursor.getCount() == 0) {
//			createUser();
//			cursor = database.query(DatabaseHelper.TABLE_USER,
//					new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null,
//					null, null, null, null);
//		}
//		if (cursor.moveToFirst())
//			userpoints = cursor.getInt(cursor
//					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
//		ContentValues args = new ContentValues();
//		args.put(DatabaseHelper.USER_COLUMN_POINTS, userpoints - newPoints);
//		database.update(DatabaseHelper.TABLE_USER, args,
//				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}

	public void addFiveTasksDoneBadge() {
		dbHelper.addFiveTasksDoneBadge();
//		ContentValues args = new ContentValues();
//		args.put(DatabaseHelper.USER_COLUMN_BADGES, FIVETASKSDONE_BADGE);
//		database.update(DatabaseHelper.TABLE_USER, args,
//				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}

	public ArrayList<Badge> getBadges() {
		
		return dbHelper.getBadges();
		
//		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null,
//				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null, null, null,
//				null);
//		return cursor;
	}

	public int getPoints() {
//		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null,
//				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null, null, null,
//				null);
//		if (cursor.moveToFirst())
//			return cursor.getInt(cursor
//					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
//		return 0;
		return dbHelper.getPoints();
	}
}
