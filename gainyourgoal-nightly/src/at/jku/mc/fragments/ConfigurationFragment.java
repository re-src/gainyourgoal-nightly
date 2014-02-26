package at.jku.mc.fragments;

import at.jku.mc.gainyourgoal.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfigurationFragment extends PreferenceFragment {
	public ConfigurationFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	  
	// Load the preferences from an XML resource
	addPreferencesFromResource(R.xml.preferences);
	}
}
