package at.jku.modularity.datamodel;

public class TodoList {
	private long id;
	private String title;
	private String description;
	private long category;
	private boolean archieved;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isArchieved() {
		return archieved;
	}

	public void setArchieved(int archieved) {
		// Boolean values in SQLite are stored as integers 0 (false) and 1
		// (true).
		if (archieved == 0)
			this.archieved = false;
		else if (archieved == 1)
			this.archieved = true;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return this.title;
	}
}
