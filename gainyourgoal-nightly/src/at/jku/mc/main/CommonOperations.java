package at.jku.mc.main;

import android.widget.EditText;

public class CommonOperations {
	public final static int MAX_RETRY = 3; 
	
	public static boolean isEmpty(EditText etText) {
		return etText.getText().toString().trim().length() == 0;
	}
}
