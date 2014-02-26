/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.jku.mc.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import at.jku.mc.fragments.BadgesFragment;
import at.jku.mc.fragments.ConfigurationFragment;
import at.jku.mc.fragments.MainListFragment;
import at.jku.mc.fragments.MainListFragment.OnListClickedListener;
import at.jku.mc.fragments.StatisticsFragment;
import at.jku.mc.fragments.TaskListFragment;
import at.jku.mc.gainyourgoal.R;

public class MainActivity extends Activity implements OnListClickedListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDefaultOptions;
	private LinearLayout mDrawerLayoutView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();
		mDefaultOptions = getResources().getStringArray(
				R.array.default_nav_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayoutView = (LinearLayout) findViewById(R.id.drawer_view);
		mDrawerList = (ListView) findViewById(R.id.left_defaultdrawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDefaultOptions));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	// Open, Close NavigationList with menu-button
	@Override
	public boolean onKeyDown(int keycode, KeyEvent e) {
		if (keycode == KeyEvent.KEYCODE_MENU) {
			if (!mDrawerLayout.isDrawerOpen(mDrawerLayoutView)) {
				mDrawerLayout.openDrawer(mDrawerLayoutView);
			} else {
				mDrawerLayout.closeDrawer(mDrawerLayoutView);
			}
		}
		return super.onKeyDown(keycode, e);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLayoutView);
		if (menu.findItem(R.id.action_search) != null) {
			menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		}
		if (menu.findItem(R.id.action_add) != null) {
			menu.findItem(R.id.action_add).setVisible(!drawerOpen);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d("asdf", "position: " + position);
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment;

		// TODO: is there a better way to change fragments?
		switch (position) {
		case 0:
			Log.d("asdf", "mainlistFragment");
			fragment = new MainListFragment();
			break;
		case 1:
			Log.d("asdf", "badgeFragment");
			fragment = new BadgesFragment();
			break;
		case 2:
			Log.d("asdf", "statisticsFragment");
			fragment = new StatisticsFragment();
			break;
		case 3:
			Log.d("asdf", "configurationFragment");
			fragment = new ConfigurationFragment();
			break;
		default:
			fragment = new MainListFragment();
			break;
		}
		Bundle args = new Bundle();
		fragment.setArguments(args);

		// http://developer.android.com/guide/components/fragments.html
		// Adding fragment to the activity, programmatically to existing
		// ViewGroup
		FragmentManager fragmentManager = getFragmentManager();
		// Replace whatever is in the fragment_container view with this
		// fragment
		FragmentTransaction transaction = fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment);
		// add fragment to BackStack
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mDefaultOptions[position]);
		mDrawerLayout.closeDrawer(mDrawerLayoutView);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void onListClicked(long id, String title) {
		Fragment fragment = new TaskListFragment();
		Bundle args = new Bundle();
		args.putLong("listid", id);
		fragment.setArguments(args);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction().replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();		
		setTitle(title);
	}

}