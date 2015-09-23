package uk.ac.qub.mindyourmind.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import uk.ac.qub.mindyourmind.activities.MenuComponent;

public class GridViewImageAdapter extends BaseAdapter {
    private Context mContext;
    private MenuComponent[] menu;
    private static final int MENU_ICON_SIZE = 150;
    
    public GridViewImageAdapter(Context c) {
        mContext = c;
    }
    
    public GridViewImageAdapter(Context c, MenuComponent[] m) {
        mContext = c;
        menu = m;
    }

    public int getCount() {
        return menu.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight()/3));
            imageView.setLayoutParams(new GridView.LayoutParams(MENU_ICON_SIZE, MENU_ICON_SIZE));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mContext.getResources().getIdentifier(menu[position].getImage(), "drawable", mContext.getPackageName()));
        return imageView;
    }
}