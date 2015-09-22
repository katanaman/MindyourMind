package uk.ac.qub.mindyourmind.adapters;

import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnEditEntry;
import uk.ac.qub.mindyourmind.interfaces.OnPlayVideo;

public class MeditationZoneAdapter extends RecyclerView.Adapter<MeditationZoneAdapter.ViewHolder> {

	
	static String[] fakeData = new String[] {
		"A Guided Meditation on Letting Go"
	};
	
	Cursor cursor;
	int titleColumnIndex;
	int notesColumnIndex;
	int idColumnIndex;
	
	
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int i){
		//create a new view
		CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task, parent, false);
		
		//wrap in viewholder
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder (final ViewHolder viewHolder, final int i){
		final Context context = viewHolder.titleView.getContext();
		final long id = getItemId(i);
		//set the text
		/*cursor.moveToPosition(i);
		viewHolder.titleView.setText(cursor.getString(titleColumnIndex));
		viewHolder.notesView.setText(cursor.getString(notesColumnIndex));
		*/
		viewHolder.titleView.setText(fakeData[0]);
		
		//set image thumbnail
		Picasso.with(context).load(getImageUrlForTask(id)).into(viewHolder.imageView);
		
		//set onClick action
		viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				((OnPlayVideo) context).playVideo("rSrSemQUeSI");
			}
		});
		
		/*set on long Click action
		viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				
				new AlertDialog.Builder(context).setTitle(R.string.delete_q)
				.setMessage(viewHolder.titleView.getText())
				.setCancelable(true)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(R.string.delete, 
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								deleteTask(context, id);
							}
						})
						.show();
					return true;
			}
		});*/
	}
	
	@Override
	public long getItemId(int position) {
		return 1L;
		
		//cursor.moveToPosition(position);
		//return cursor.getLong(idColumnIndex);
	};
	
	@Override
	public int getItemCount() {
		// return cursor!=null? cursor.getCount() : 0;
		return fakeData.length;
	}
	
	public static String getImageUrlForTask(long taskId){
		return "http://i1.ytimg.com/vi/rSrSemQUeSI/1.jpg";
		//return "http://lorempixel.com/600/400/nature/?fakeId="+taskId;
	}
	
	void deleteTask(Context context, long id){
		Log.d("TaskListAdapter", "called deleteTask");
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		CardView cardView;
		TextView titleView;
		TextView notesView;
		ImageView imageView;
		
		public ViewHolder(CardView card){
			super(card);
			cardView = card;
			titleView = (TextView) card.findViewById(R.id.text1);
			notesView = (TextView) card.findViewById(R.id.text2);
			imageView = (ImageView) card.findViewById(R.id.image);
		}
	}
}

