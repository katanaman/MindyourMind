package uk.ac.qub.mindyourmind.adapters;

import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.interfaces.OnEditEntry;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
	
	Cursor cursor;
	int titleColumnIndex;
	int notesColumnIndex;
	int idColumnIndex;
	
	public void swapCursor(Cursor c) {
		cursor = c;
		if (cursor!=null){
			cursor.moveToFirst();
			titleColumnIndex = cursor.getColumnIndex(DiaryEntryTable.COLUMN_DIARY_ENTRY_TITLE);
			notesColumnIndex = cursor.getColumnIndex(DiaryEntryTable.COLUMN_DIARY_ENTRY_CONTENT);
			idColumnIndex = cursor.getColumnIndex(DiaryEntryTable.COLUMN_DIARY_ENTRY_ID);
		}
		notifyDataSetChanged();
	}
	
	
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
		cursor.moveToPosition(i);
		viewHolder.titleView.setText(cursor.getString(titleColumnIndex));
		viewHolder.notesView.setText(cursor.getString(notesColumnIndex));
		
		
		//set image thumbnail
		Picasso.with(context).load(getImageUrlForTask(id)).into(viewHolder.imageView);
		
		//set onClick action
		viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((OnEditEntry) context).editTask(id);
			}
		});
		
		//set on long Click action
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
		});
	}
	
	@Override
	public long getItemId(int position) {
		cursor.moveToPosition(position);
		return cursor.getLong(idColumnIndex);
	};
	
	@Override
	public int getItemCount() {
		return cursor!=null? cursor.getCount() : 0;
	}
	
	public static String getImageUrlForTask(long taskId){
		return "http://lorempixel.com/600/400/nature/?fakeId="+taskId;
	}
	
	void deleteTask(Context context, long id){
		Log.d("TaskListAdapter", "called deleteTask");
		context.getContentResolver().delete(ContentUris.withAppendedId(MindYourMindProvider.DIARY_URI, id), null, null);
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
