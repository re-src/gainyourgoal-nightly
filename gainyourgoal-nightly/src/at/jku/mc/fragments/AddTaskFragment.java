package at.jku.mc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import at.jku.mc.database.AndroidDbHelper;

import at.jku.mc.gainyourgoal.R;
import at.jku.mc.main.CommonOperations;
import at.jku.modularity.database.TaskProvider;

public class AddTaskFragment extends Fragment {
	private TaskProvider taskProvider;

	public AddTaskFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidDbHelper dbHelper = new AndroidDbHelper(getActivity());
		
		taskProvider = new TaskProvider(dbHelper);
		taskProvider.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_task, container,
				false);
		final Button mOkButton = (Button) view.findViewById(R.id.ok);
		Button mCancelButton = (Button) view.findViewById(R.id.cancel);
		final EditText mTitleText = (EditText) view
				.findViewById(R.id.editTitle);
		final EditText mDescriptionText = (EditText) view
				.findViewById(R.id.editDescription);
		final CheckBox mRedoCheckbox = (CheckBox) view
				.findViewById(R.id.checkboxRedo);
		final CalendarView mDeadlineCalendar = (CalendarView) view
				.findViewById(R.id.calendarDeadline);
		final Spinner mPrioritySpinner = (Spinner) view
				.findViewById(R.id.spinnerPriority);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				getActivity().getFragmentManager().popBackStackImmediate();
				// getActivity().getFragmentManager().beginTransaction().remove(this).commit();
			}
		});
		mTitleText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (CommonOperations.isEmpty(mTitleText)) {
					mOkButton.setEnabled(false);
				} else {
					mOkButton.setEnabled(true);
				}
			}
		});

		mOkButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Log.d("asdf","date: "+mDeadlineCalendar.getDate()+" desc "+mDescriptionText.getText().toString()+" title "+mTitleText.getText().toString()+" check "+mRedoCheckbox.isSelected()+" prio "+mPrioritySpinner.getSelectedItem().toString());
				//TODO: different methods for different input. (for example when no points are entered)
				// send to Database
				taskProvider.createTask(mTitleText.getText().toString(),
						mDescriptionText.getText().toString(),
						Long.parseLong(mPrioritySpinner.getSelectedItem().toString()),
						mRedoCheckbox.isChecked(), mDeadlineCalendar.getDate(),
						getArguments().getLong("listid"));
				getActivity().getFragmentManager().popBackStack();
				// getActivity().getFragmentManager().beginTransaction().remove(this).commit();
			}
		});

		return view;
	}
}