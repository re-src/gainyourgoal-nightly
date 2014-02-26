package at.jku.modularity.database;

import java.util.LinkedList;

import at.jku.modularity.datamodel.TodoList;


public final class TodoListProvider {
		
	private IDatabaseHelper dbHelper;

	public TodoListProvider(IDatabaseHelper currentDbHelper) {
		dbHelper = currentDbHelper;
	}	

	public void open() {
		dbHelper.openDb();
	}

	public void close() {
		dbHelper.closeDb();
	}

	public void createTodoList(String title, String description, long category) {
//		ContentValues values = new ContentValues();
//		values.put(DatabaseHelper.TODOLIST_COLUMN_TITLE, title);
//		values.put(DatabaseHelper.TODOLIST_COLUMN_DESCRIPTION, description);
//		values.put(DatabaseHelper.TODOLIST_COLUMN_CATEGORY, category);
//		values.put(DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED, false);
//		long insertId = database.insert(DatabaseHelper.TABLE_TODOLIST, null,
//				values);
//		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
//				null, DatabaseHelper.TODOLIST_COLUMN_ID + " = "
//						+ insertId, null, null, null, null);
//		cursor.moveToFirst();
//		cursor.close();
		dbHelper.createTodoList(title, description, category);
	}

	public void deleteTodoList(long id) {
		
		dbHelper.deleteTodoList(id);
//		System.out.println("List deleted with id: " + id);
//		database.delete(DatabaseHelper.TABLE_TODOLIST,
//				DatabaseHelper.TODOLIST_COLUMN_ID + " = " + id, null);
	}

	public int archiveList(long id) {
//		System.out.println("List archived with id: " + id);
//		ContentValues args = new ContentValues();
//		args.put(DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED, TODOLIST_ARCHIVED);
//		return database.update(DatabaseHelper.TABLE_TODOLIST, args,
//				DatabaseHelper.TODOLIST_COLUMN_ID + " = " + id, null);
		return dbHelper.archiveList(id);
	}

	public LinkedList<TodoList> getNotArchivedTodoLists() {
//		// DESC -> hoechste id zuerst anzeigen
//		// TODO: nicht hoechste id, sondern Reihenfolge festlegbar
//		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
//				null, DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED + " = "
//						+ 0, null, null, null,
//				DatabaseHelper.TODOLIST_COLUMN_ID + " DESC");
//		return cursor;
		
		return dbHelper.getNotArchivedTodoLists();
	}

	public LinkedList<TodoList> getArchivedTodoLists() {
//		// DESC -> hoechste id zuerst anzeigen
//		// TODO: nicht hoechste id, sondern Reihenfolge festlegbar
//		Cursor cursor = database.query(DatabaseHelper.TABLE_TODOLIST,
//				null, DatabaseHelper.TODOLIST_COLUMN_ISARCHIVED + " = "
//						+ 1, null, null, null,
//				DatabaseHelper.TODOLIST_COLUMN_ID + " DESC");
//		return cursor;
		
		return dbHelper.getArchivedTodoLists();
	}

}
