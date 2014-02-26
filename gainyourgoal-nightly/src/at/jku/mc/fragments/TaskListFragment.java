package at.jku.mc.fragments;

import java.util.LinkedList;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;



import at.jku.mc.database.AndroidDbHelper;
import at.jku.mc.gainyourgoal.R;
import at.jku.modularity.database.TaskProvider;
import at.jku.modularity.datamodel.Task;

/**
 * TODO: When show Checked is clicked, show checked Tasks too. Default behaviour
 * can be set in Preferences
 */
public class TaskListFragment extends ListFragment {
//	private SimpleCursorAdapter taskAdapter;
	
	long id;
	String attribute;
	
	private ArrayAdapter<Task> taskAdapter;
	
	private TaskProvider taskProvider;

	public TaskListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		AndroidDbHelper dbHelper = new AndroidDbHelper(getActivity());
		String attribute = "checkedTasks";
		dbHelper.request(id, attribute);
		taskProvider = new TaskProvider(dbHelper);
		taskProvider.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] mTaskListOptions = getResources().getStringArray(
				R.array.tasklist_nav_array);
		ListView mCustomDrawerList = (ListView) getActivity().findViewById(
				R.id.left_customdrawer);
		mCustomDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.drawer_list_item, mTaskListOptions));

		long listid = getArguments().getLong(
				"listid");
		
		LinkedList<Task> items = taskProvider.getTasksOfList(listid);

		String[] from = new String[] { "name" };
		int[] to = new int[] { android.R.id.text1 };
		
		
		taskAdapter = new ArrayAdapter<Task>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, items);
		
//		taskAdapter = new SimpleCursorAdapter(getActivity(),
//				android.R.layout.simple_list_item_multiple_choice, items, from,
//				to, 0);
		
		setListAdapter(taskAdapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		registerForContextMenu(getListView());
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.task_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		long listId = ((AdapterContextMenuInfo) item.getMenuInfo()).id;
		switch (item.getItemId()) {
		// TODO: do tasks
		case R.id.edit:
			Log.d("asdf", "edit");
			return true;
		case R.id.delete:
			Log.d("asdf",
					"delete taskid "
							+ ((AdapterContextMenuInfo) item.getMenuInfo()).id);
			taskProvider.deleteTask(((AdapterContextMenuInfo) item
					.getMenuInfo()).id);
//			taskAdapter.changeCursor(taskProvider.getTasksOfList(getArguments()
//					.getLong("listid")));
			return true;
		case R.id.copy:
			// TODO: open addtask fragment with predefined values
			// remove stuff here
			return true;
		case R.id.check:
			Log.d("asdf", "check");
			taskProvider.setChecked(listId);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void checkTask() {
		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		for (int i = 0; i < getListView().getCount(); i++) {
			if (checked.get(i)) {
				// checkTask(l.getItemIdAtPosition(i));
				taskProvider.setChecked(getListView().getItemIdAtPosition(i));
				// for (int count = 0; count <= CommonOperations.MAX_RETRY;
				// count++) {
				// if (taskProvider.setChecked(getListView()
				// .getItemIdAtPosition(i)) > 0) {
				// break;
				// }
				// Log.d("asdf", "retry " + count);
				// }
			} else {
				taskProvider.setUnChecked(getListView().getItemIdAtPosition(i));
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.list, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered
				taskAdapter.getFilter().filter(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				taskAdapter.getFilter().filter(query);
				return true;
			}
		};
		searchView.setOnQueryTextListener(queryTextListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_add:
			Log.d("asdf", "add Task");
			Fragment fragment = new AddTaskFragment();
			fragment.setArguments(getArguments());
			FragmentTransaction transaction = getActivity()
					.getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		checkTask();
	}
}