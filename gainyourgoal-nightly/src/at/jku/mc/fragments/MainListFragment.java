package at.jku.mc.fragments;

import java.util.LinkedList;

import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import at.jku.mc.database.AndroidDbHelper;

import at.jku.mc.gainyourgoal.R;
import at.jku.modularity.database.TodoListProvider;
import at.jku.modularity.datamodel.TodoList;

public class MainListFragment extends ListFragment {
	private ArrayAdapter<TodoList> todoListAdapter;
	private TodoListProvider todoListProvider;
	private LinkedList<TodoList> items;

	public MainListFragment() {
		// Empty constructor required for fragment subclasses
	}

	// Container Activity must implement this interface
	public interface OnListClickedListener {
		public void onListClicked(long id, String title);
	}

	OnListClickedListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnListClickedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnListSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		AndroidDbHelper dbHelper = new AndroidDbHelper(getActivity());				
		todoListProvider = new TodoListProvider(dbHelper);
		todoListProvider.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] mMainListOptions = getResources().getStringArray(
				R.array.mainlist_nav_array);
		ListView mCustomDrawerList = (ListView) getActivity().findViewById(
				R.id.left_customdrawer);
		mCustomDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.drawer_list_item, mMainListOptions));

		mCustomDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		 items = todoListProvider.getNotArchivedTodoLists();
		
		TodoList t = new TodoList();
		
		if(items.size() >0)
		{
			 t = items.get(0);
		}
		
		Long ss = t.getId();
		
		todoListAdapter = new ArrayAdapter<TodoList>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		
		setListAdapter(todoListAdapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	// TODO: outsource interface and implement in each fragment. we need a
	// ArchiveFragment to dearchive archived lists
	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d("asdf", "show archive " + position);
		
			///G	todoListAdapter.changeCursor(todoListProvider
			///G				.getArchivedTodoLists());
			//TODO: close Drawer
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.list_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// TODO: do tasks
		case R.id.edit:
			Log.d("asdf", "edit");
			return true;
		case R.id.delete:
			Log.d("asdf",
					"delete listid "
							+ ((AdapterContextMenuInfo) item.getMenuInfo()).id);
			todoListProvider.deleteTodoList(((AdapterContextMenuInfo) item
					.getMenuInfo()).id);
			//	todoListAdapter.changeCursor(todoListProvider
			//				.getNotArchivedTodoLists());
			return true;
		case R.id.set_color:
			// remove stuff here
			return true;
		case R.id.archive:
			Log.d("asdf",
					"archive listid "
							+ ((AdapterContextMenuInfo) item.getMenuInfo()).id);
			todoListProvider.archiveList(((AdapterContextMenuInfo) item
					.getMenuInfo()).id);
			///G		todoListAdapter.changeCursor(todoListProvider
			///G				.getNotArchivedTodoLists());
			return true;
		default:
			return super.onContextItemSelected(item);
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
				todoListAdapter.getFilter().filter(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				todoListAdapter.getFilter().filter(query);
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
			Log.d("asdf", "add List");
			Fragment fragment = new AddListFragment();
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
		String title = ((TextView) l.findViewById(android.R.id.text1))
				.getText().toString();
		//Gergö: diese id war bei mir immer 0... darum setze ich es einfach....
		id = items.get(position).getId();
		Log.d("asdf", "listid " + id);
		mListener.onListClicked(id, title);
	}
}