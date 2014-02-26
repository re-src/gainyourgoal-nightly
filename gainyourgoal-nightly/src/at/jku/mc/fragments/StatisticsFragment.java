package at.jku.mc.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import at.jku.mc.database.AndroidDbHelper;

import at.jku.mc.gainyourgoal.R;
import at.jku.modularity.database.UserProvider;

public class StatisticsFragment extends Fragment {
	private UserProvider userProvider;

	public StatisticsFragment() {
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
		View view = inflater.inflate(R.layout.fragment_statistics, container,
				false);
		TextView overallstatsview = (TextView) view
				.findViewById(R.id.overallStatsTextview);
		TextView weeklystatsview = (TextView) view
				.findViewById(R.id.overallStatsTextview);
		TextView monthlystatsview = (TextView) view
				.findViewById(R.id.overallStatsTextview);
		TextView points = (TextView) view.findViewById(R.id.pointsText);

		points.setText(""+userProvider.getPoints());

		return view;
	}
}
