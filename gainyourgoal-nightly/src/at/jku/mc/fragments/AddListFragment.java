package at.jku.mc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import at.jku.mc.database.AndroidDbHelper;
import at.jku.mc.gainyourgoal.R;
import at.jku.mc.main.CommonOperations;
import at.jku.modularity.database.TaskProvider;
import at.jku.modularity.database.TodoListProvider;

public class AddListFragment extends Fragment {
	private TodoListProvider todoListProvider;

	public AddListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidDbHelper dbHelper = new AndroidDbHelper(getActivity());		
		todoListProvider = new TodoListProvider(dbHelper);
		todoListProvider.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_list, container,
				false);
		final Button mOkButton = (Button) view.findViewById(R.id.ok);
		Button mCancelButton = (Button) view.findViewById(R.id.cancel);
		final EditText mTitleText = (EditText) view
				.findViewById(R.id.editTitle);
		final EditText mDescriptionText = (EditText) view
				.findViewById(R.id.editDescription);
		final Spinner mCategorySpinner = (Spinner) view
				.findViewById(R.id.spinnerCategory);
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
				// send to Database
				todoListProvider.createTodoList(
						mTitleText.getText().toString(), mDescriptionText
								.getText().toString(), mCategorySpinner
								.getSelectedItemId());
				getActivity().getFragmentManager().popBackStack();
				// getActivity().getFragmentManager().beginTransaction().remove(this).commit();
			}
		});

		return view;
	}
}
