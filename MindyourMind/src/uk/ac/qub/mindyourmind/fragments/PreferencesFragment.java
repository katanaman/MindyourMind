package uk.ac.qub.mindyourmind.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.text.method.DigitsKeyListener;
import uk.ac.qub.mindyourmind.R;

public class PreferencesFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//create screen from prefs xml
		addPreferencesFromResource(R.xml.mind_your_mind_preferences);
		
		//set the keyboard to be used
		EditTextPreference timeDefault = (EditTextPreference) findPreference(getString(R.string.pref_default_time_from_now_key));
		timeDefault.getEditText().setKeyListener(DigitsKeyListener.getInstance());
		
	}
}
