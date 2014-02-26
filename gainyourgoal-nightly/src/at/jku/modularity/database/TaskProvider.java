package at.jku.modularity.database;

import java.util.Calendar;
import java.util.LinkedList;

import at.jku.modularity.datamodel.Task;


public final class TaskProvider {

	// Database fields
//	private SQLiteDatabase database;
	private IDatabaseHelper dbHelper;
//	private UserProvider userProvider;

	public TaskProvider(IDatabaseHelper currentDbHelper) {
		dbHelper = currentDbHelper;
	//	userProvider = currentUserProvider;
	}

	public void open()  {
		dbHelper.openDb();
	}

	public void close() {
		dbHelper.closeDb();
	}

	public void createTask(String name, String description, long priority,
			boolean redo, long date, long list) {
	
		Long l = (long) 3;
		dbHelper.createTask(new Task(l ,name,  description, (int)list));
	}

	public int setChecked(long id) {
		return dbHelper.setChecked(id);
	}

	// TODO: Rename to task done
	private boolean fiveTasksChecked() {
		return dbHelper.fiveTasksChecked();		
	}

	public int setUnChecked(long id) {
		
		System.out.println("Task unchecked with id: " + id);	
		
	//	userProvider.removePoints(dbHelper.getPriority(id));		
		return dbHelper.setChecked(id);
	}

	private int getPriority(long id) {
		return dbHelper.getPriority(id);
	}

	 //TODO restore checked tasks on fragment opening
	 public LinkedList<Task> getChecked() {
		 return dbHelper.getChecked();
	 }

	public int getCheckedCount() {
		return getChecked().size();
	}

	public void deleteTask(long id) {
		dbHelper.deleteTask(id);
	}

	public LinkedList<Task> getTasksOfList(long id) {
		// DESC -> hoechste id zuerst anzeigen
		// TODO: nicht hoechste id, sondern Reihenfolge festlegbar
		return dbHelper.getTasksOfListO(id);
	}
}
