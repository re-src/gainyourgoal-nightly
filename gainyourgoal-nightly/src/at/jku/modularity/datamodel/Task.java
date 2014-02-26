package at.jku.modularity.datamodel;

import java.util.Calendar;

import at.jku.mc.database.AndroidDbHelper;

/**
 * TODO: when creating a task, one can enter name, description, priority,
 * deadline and redo. if redo is checked, new Task will be generated with
 * deadline=now+(deadline-created)
 */

public class Task {
	private long id;
	private String attribute;
	
	private String name;
	private String description;
	private int priority;
	private Calendar created;
	private Calendar deadline;
	private boolean redo;
	private int listId;
	private boolean checked;

	public Task() {
	};

	public Task(long id, String name, String description, Priority priority,
			Calendar deadline, boolean redo) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = 0;
		this.created = Calendar.getInstance();
		this.deadline = deadline;
		this.redo = redo;
	}

	
	public Task(long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = 0;
		this.created = Calendar.getInstance();
		this.deadline = deadline; // TODO: we do need a standard deadline!
		this.redo = false;
	}
	
	
	public Task(long id, String name, String description, int listId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = 0;
		this.created = Calendar.getInstance();
		this.deadline = deadline; // TODO: we do need a standard deadline!
		this.redo = false;
		this.listId = listId;
	}
	
	public void getCheckedTasks(){
		attribute = "checkedTasks";
		AndroidDbHelper dbHelper = new AndroidDbHelper(null);
		dbHelper.request(id, attribute);	
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Calendar getDeadline() {
		return deadline;
	}

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	public boolean isRedo() {
		return redo;
	}

	public void setRedo(boolean redo) {
		this.redo = redo;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
