package at.jku.modularity.database;

import java.util.ArrayList;
import java.util.LinkedList;

import at.jku.mc.database.AndroidDbHelper;
import at.jku.modularity.datamodel.Badge;
import at.jku.modularity.datamodel.Task;
import at.jku.modularity.datamodel.TodoList;



public interface IDatabaseHelper {
	
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
	
	
	public void createTask(Task newTask);
	public void openDb();
	public void closeDb();
	public int setChecked(long id);
	public boolean fiveTasksChecked();
	public int getPriority(long id);
	public LinkedList<Task> getChecked();
	public void deleteTask(long id);
	public LinkedList<Task> getTasksOfListO(long id);
	public LinkedList<TodoList> getNotArchivedTodoLists();
	public LinkedList<TodoList> getArchivedTodoLists();
	public int archiveList(long id);
	public void deleteTodoList(long id);
	public void createTodoList(String title, String description, long category);
	public void createUser();
	public void addPoints(int newPoints);
	public void removePoints(int newPoints);
	public ArrayList<Badge> getBadges();
	public void addFiveTasksDoneBadge();
	public int getPoints();
	public void request(long id, String attribute);
}
