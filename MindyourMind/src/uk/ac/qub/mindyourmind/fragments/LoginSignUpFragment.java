package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;

public class LoginSignUpFragment extends Fragment{

	public static final String DEFAULT_FRAGMNET_TAG = "loginSignUpFragment";
	
	Button login, signUp;
	private View view;
	
	public static LoginSignUpFragment newInstance() {
		return new LoginSignUpFragment();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view =  inflater.inflate(R.layout.fragment_login_or_sign_up, container, false);
		
		login = (Button) view.findViewById(R.id.button_login);
		signUp = (Button) view.findViewById(R.id.button_sign_up);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				((OnLoginClicked) getActivity()).openMainMenuFragment(view);
			}
		});
		
		signUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OnSignUpClicked) getActivity()).openSignUpActivity(view);
			}
		});
		
		return view;
	}
	
/*
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button_login:
			((OnLoginClicked) getActivity()).openLoginFragment(view);
			Toast.makeText(getActivity(), "clicked Login", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button_sign_up:
			((OnSignUpClicked) getActivity()).openAuthenticateFragment(view);
			Toast.makeText(getActivity(), "clicked signup", Toast.LENGTH_SHORT).show();
			break;
		}
	}*/
	
}
