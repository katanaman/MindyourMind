package uk.ac.qub.mindyourmind.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import uk.ac.qub.mindyourmind.R;

public class SpinnerDialogFragment extends DialogFragment {

	static final String CONTENT = "content";
	Context context;
	
	public static SpinnerDialogFragment newInstance(Bundle bundle) {
		
		SpinnerDialogFragment fragment = new SpinnerDialogFragment();

	        fragment.setArguments(bundle);

	        return fragment;
	        }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity.getApplicationContext();
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		 super.onCreateDialog(savedInstanceState);
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 LayoutInflater inflater = getActivity().getLayoutInflater();
		 
		 View view = inflater.inflate(R.layout.fragment_spinner_dialog, null);
		 
		 Spinner spin = (Spinner) view.findViewById(R.id.spinner);
		 return null;
	}
}
	
/*
	    // Use the Builder class for convenient dialog construction
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	
	    View view = inflater.inflate(R.layout.dialog, null);
	
	    Spinner spin;
	    spin = (Spinner)view.findViewById(R.id.spinner1);
	
	    List<String> list = new ArrayList<String>();
	    list.add("Material 1");
	    list.add("Material 2");
	    list.add("Material 3");
	    list.add("Material 4");
	    list.add("Material 5");
	    list.add("Material 6");
	
	    //Second List
	    List<String> list2 = new ArrayList<String>();
	    list2.add("Mat 7");
	    list2.add("Mat 8");
	    list2.add("Mat 9");
	
	    //Combined List
	    List<String> listCombine = new ArrayList<String>();
	    listCombine.addAll(list);
	    listCombine.addAll(list2);
	
	    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_item, listCombine); 
	
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	    spin.setAdapter(dataAdapter);
	
	    builder
	    .setTitle("Title")
	    .setView(inflater.inflate(R.layout.dialog, null))
	           .setPositiveButton("Add", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   // FIRE ZE MISSILES!
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   // User cancelled the dialog
	               }
	           });
	    // Create the AlertDialog object and return it
	    return builder.create();
}*/