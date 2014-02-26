package at.jku.mc.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import at.jku.modularity.database.IDatabaseHelper;

import at.jku.modularity.datamodel.*;


public class AndroidDbHelper implements IDatabaseHelper {

	private static final String FIVETASKSDONE_BADGE = "Five Tasks Done";

	
	DatabaseHelper dbHelper;
	private SQLiteDatabase database;
	
	public AndroidDbHelper(Context context)
	{
		dbHelper = new DatabaseHelper(context);
	}
	
	@Override
	public void closeDb() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void createTask(Task newTask) {
			long created = Calendar.getInstance().getTimeInMillis();
			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.TASK_COLUMN_NAME, newTask.getName());
			values.put(DatabaseHelper.TASK_COLUMN_DESCRIPTION, newTask.getDescription());
			//values.put(DatabaseHelper.TASK_COLUMN_PRIORITY, newTask.getPriority().toString());
			values.put(DatabaseHelper.TASK_COLUMN_REDO,  0);
			values.put(DatabaseHelper.TASK_COLUMN_CREATED, created);
			values.put(DatabaseHelper.TASK_COLUMN_DEADLINE, 0);
			values.put(DatabaseHelper.TASK_COLUMN_LIST, newTask.getListId());
			long insertId = database
					.insert(DatabaseHelper.TABLE_TASK, null, values);
			Cursor cursor = database.query(DatabaseHelper.TABLE_TASK, null,
					DatabaseHelper.TASK_COLUMN_ID + " = " + insertId, null, null,
					null, null);
			cursor.moveToFirst();
			cursor.close();	
	}

	@Override
	public void deleteTask(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fiveTasksChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Task> getChecked() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPriority(long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LinkedList<Task> getTasksOfListO(long id) {
		Cursor cursor = database.query(DatabaseHelper.TABLE_TASK, null,
				DatabaseHelper.TASK_COLUMN_LIST + " = " + id, null, null, null,
				DatabaseHelper.TASK_COLUMN_ID + " DESC");
		
		LinkedList<Task> retVal = new LinkedList<Task>();
		
		cursor.moveToFirst();
		
		while (cursor.isAfterLast() == false) {			 
			Task t = new Task();			
		    t.setDescription(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TASK_COLUMN_DESCRIPTION)));			
			t.setName(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TASK_COLUMN_NAME)));		 
			t.setId(cursor.getLong(cursor.getColumnIndex(IDatabaseHelper.TASK_COLUMN_ID)));
		//	t.setDeadline(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TASK_COLUMN_DEADLINE)));
			
			retVal.push(t);
			
			cursor.moveToNext();           
        }
		
		return retVal;
	}

	@Override
	public void openDb() {
		database = dbHelper.getWritableDatabase();
		if (!database.isReadOnly()) {
			// Enable foreign key constraints
			database.execSQL("PRAGMA foreign_keys=ON;");
		}
		
	}

	@Override
	public int setChecked(long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int archiveList(long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteTodoList(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LinkedList<TodoList> getArchivedTodoLists() {
//		// DESC -> hoechste id zuerst anzeigen
//		// TODO: nicht hoechste id, sondern Reihenfolge festlegbar
		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
				null, DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED + " = "
						+ 1, null, null, null,
				DatabaseHelper.TODOLIST_COLUMN_ID + " DESC");
		
		LinkedList<TodoList> retVal = new LinkedList<TodoList>();
		
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {		
			TodoList tList = new TodoList();
			
			tList.setTitle(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_TITLE)));
			tList.setId(cursor.getInt(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_ID)));
			
			retVal.add(tList);
			cursor.moveToNext();           
		}
		
		return retVal;
		
	}

	@Override
	public LinkedList<TodoList> getNotArchivedTodoLists() {

		// DESC -> hoechste id zuerst anzeigen
		// TODO: nicht hoechste id, sondern Reihenfolge festlegbar
		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
				null, DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED + " = "
						+ 0, null, null, null,
				DatabaseHelper.TODOLIST_COLUMN_ID + " DESC");
		
		LinkedList<TodoList> retVal = new LinkedList<TodoList>();
		
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {		
			TodoList tList = new TodoList();
			
			tList.setTitle(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_TITLE)));
			tList.setId(cursor.getInt(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_ID)));
			
			//tList.setCategory(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_CATEGORY)))
			
			retVal.add(tList);
			cursor.moveToNext();           
		}
		
		return retVal;
	}


	public void createTodoList(String title, String description, long category)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.TODOLIST_COLUMN_TITLE, title);
		values.put(DatabaseHelper.TODOLIST_COLUMN_DESCRIPTION, description);
		values.put(DatabaseHelper.TODOLIST_COLUMN_CATEGORY, category);
		values.put(DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED, false);
		long insertId = database.insert(DatabaseHelper.TABLE_TODOLIST, null,
				values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
				null, DatabaseHelper.TODOLIST_COLUMN_ID + " = "
						+ insertId, null, null, null, null);
		cursor.moveToFirst();
		cursor.close();
	}	
	
	
	public void createUser()
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.USER_COLUMN_NAME, "Test");
		values.put(DatabaseHelper.USER_COLUMN_BADGES, 0);
		values.put(DatabaseHelper.USER_COLUMN_POINTS, 0);
		long insertId = database
				.insert(DatabaseHelper.TABLE_USER, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null,
				DatabaseHelper.USER_COLUMN_ID + " = " + insertId, null, null,
				null, null);
		cursor.moveToFirst();
		cursor.close();
	}
	
	public void addPoints(int newPoints)
	{
		int userpoints = 0;
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
				new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null, null,
				null, null, null);
		Log.d("asdf", "usercursor " + cursor);
		if (cursor.getCount() == 0) {
			createUser();
			cursor = database.query(DatabaseHelper.TABLE_USER,
					new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null,
					null, null, null, null);
		}
		if (cursor.moveToFirst())
			userpoints = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
		ContentValues args = new ContentValues();
		args.put(DatabaseHelper.USER_COLUMN_POINTS, userpoints + newPoints);
		database.update(DatabaseHelper.TABLE_USER, args,
				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}
	
	public void removePoints(int newPoints)
	{
		int userpoints = 0;
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
				new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null, null,
				null, null, null);
		if (cursor.getCount() == 0) {
			createUser();
			cursor = database.query(DatabaseHelper.TABLE_USER,
					new String[] { DatabaseHelper.USER_COLUMN_POINTS }, null,
					null, null, null, null);
		}
		if (cursor.moveToFirst())
			userpoints = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
		ContentValues args = new ContentValues();
		args.put(DatabaseHelper.USER_COLUMN_POINTS, userpoints - newPoints);
		database.update(DatabaseHelper.TABLE_USER, args,
				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}
	
	public ArrayList<Badge> getBadges()
	{
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null,
				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null, null, null,
				null);
		
		ArrayList<Badge> retVal = new ArrayList<Badge>();
		
		
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {		
			Badge badge = new Badge();
			
			badge.setId(cursor.getInt(cursor.getColumnIndex(IDatabaseHelper.BADGE_COLUMN_ID)));
			badge.setName(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.BADGE_COLUMN_NAME)));			
			//tList.setCategory(cursor.getString(cursor.getColumnIndex(IDatabaseHelper.TODOLIST_COLUMN_CATEGORY)))
			
			retVal.add(badge);
			cursor.moveToNext();           
		}
		
		return retVal;	
		
	}
	
	public void addFiveTasksDoneBadge()
	{
		ContentValues args = new ContentValues();
		args.put(DatabaseHelper.USER_COLUMN_BADGES, FIVETASKSDONE_BADGE);
		database.update(DatabaseHelper.TABLE_USER, args,
				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null);
	}
	
	public int getPoints()
	{
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null,
				DatabaseHelper.USER_COLUMN_ID + " = " + 1, null, null, null,
				null);
		if (cursor.moveToFirst())
			return cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.USER_COLUMN_POINTS));
		return 0;
	}

	public void request(long id, String attribute) {
		// TODO Auto-generated method stub
		if (attribute == "checkedTasks") {
		}
		else{}
	}

}
