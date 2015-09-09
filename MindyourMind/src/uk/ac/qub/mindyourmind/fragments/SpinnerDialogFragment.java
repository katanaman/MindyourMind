package uk.ac.qub.mindyourmind.fragments;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import uk.ac.qub.mindyourmind.R;

/*
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
}*/
public class SpinnerDialogFragment extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    private Spinner mSpinner;

   public interface DialogListener {
        public void ready(int n);
        public void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialogFragment(Context context, ArrayList<String> list, DialogListener listener) {
        super(context);
        mReadyListener = listener;
        mContext = context;
        mList = new ArrayList<String>();
        mList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_spinner_dialog);
        mSpinner = (Spinner) findViewById (R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);
        
        Button buttonOK = (Button) findViewById(R.id.dialogOK);
        Button buttonCancel = (Button) findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(n);
                SpinnerDialogFragment.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                SpinnerDialogFragment.this.dismiss();
            }
        });
    }
}
