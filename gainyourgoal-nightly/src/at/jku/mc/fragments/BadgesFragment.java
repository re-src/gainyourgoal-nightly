package at.jku.mc.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import at.jku.mc.database.AndroidDbHelper;
import at.jku.modularity.database.*;
import at.jku.modularity.datamodel.*;

public class BadgesFragment extends ListFragment {
	private ArrayAdapter<Badge> badgesAdapter;
	private UserProvider userProvider;

	public BadgesFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidDbHelper dbHelper = new AndroidDbHelper(getActivity());
		userProvider = new UserProvider(dbHelper);
		userProvider.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<Badge> items = userProvider.getBadges();
		String[] from = new String[] { "badges" };
		int[] to = new int[] { android.R.id.text1 };
	
		badgesAdapter = new ArrayAdapter<Badge>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		
		
		setListAdapter(badgesAdapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
