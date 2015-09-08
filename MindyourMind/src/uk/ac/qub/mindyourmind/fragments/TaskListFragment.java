package uk.ac.qub.mindyourmind.fragments;


import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import uk.ac.qub.mindyourmind.activities.PreferencesActivity;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.adapters.TaskListAdapter;
import uk.ac.qub.mindyourmind.providers.TaskProvider;
import android.app.LoaderManager;
import uk.ac.qub.mindyourmind.interfaces.OnEditTask;



public class TaskListFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor>{

	RecyclerView recyclerView;
	TaskListAdapter adapter;
	
	public TaskListFragment(){
		//required empty constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new TaskListAdapter();
		
		getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v =  inflater.inflate(R.layout.fragment_task_list, container, false);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_add :
			((OnEditTask) getActivity()).editTask(0);
		return true;
		case R.id.menu_settings :
			startActivity(new Intent (getActivity(), PreferencesActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int ignored, Bundle args){
		return new CursorLoader(getActivity(), TaskProvider.CONTENT_URI, null, null, null, null);
	}
	
	@Override
	public void onLoadFinished (Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}
	
	@Override
	public void onLoaderReset (Loader<Cursor> loader){
		adapter.swapCursor(null);
	}
}
