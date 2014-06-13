package com.licenta.classmanager.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.licenta.classmanager.R;
import de.timroes.android.listview.EnhancedListView;

public class LessonsListAdapter extends BaseAdapter {

    private List<String> mItems = new ArrayList<String>();
    private Activity activity;
    private EnhancedListView elv;
    
    public LessonsListAdapter(Activity activity, EnhancedListView elv) {
    	this.activity = activity;
    	this.elv = elv;
    }

    public void resetItems() {
        mItems.clear();
        for(int i = 1; i <= 5; i++) {
            mItems.add("Item " + i);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    public void insert(int position, String item) {
        mItems.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
    * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.announcement_list_item, parent, false);
            // Clicking the delete icon, will read the position of the item stored in
            // the tag and delete it from the list. So we don't need to generate a new
            // onClickListener every time the content of this view changes.
//            final View origView = convertView;
//            convertView.findViewById(R.id.action_open).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    elv.delete(((ViewHolder)origView.getTag()).position);
//                }
//            });

            holder = new ViewHolder();
            assert convertView != null;
            holder.mTextView = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position = position;
        holder.mTextView.setText(mItems.get(position));

        return convertView;
    }

    private class ViewHolder {
        TextView mTextView;
        int position;
    }

}
