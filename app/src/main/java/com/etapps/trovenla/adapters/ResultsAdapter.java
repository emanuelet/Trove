package com.etapps.trovenla.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.fragments.ResultsFragment;

/**
 * {@link ResultsAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class ResultsAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    public ResultsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int layoutId = R.layout.list_item_results;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String title = cursor.getString(ResultsFragment.COL_BOOK_TITLE);
        // Find TextView and set formatted date on it
        viewHolder.titleView.setText(title);
        // Read weather forecast from cursor
        String author = cursor.getString(ResultsFragment.COL_BOOK_AUTHOR);
        if (author.length() >= 40) {
            author = author.substring(0, 38) + "..";
        }
        // Find TextView and set weather forecast on it
        viewHolder.authorView.setText(author);
        String year = cursor.getString(ResultsFragment.COL_BOOK_YEAR);
        // Find TextView and set the year on it
        viewHolder.yearView.setText(year);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final TextView titleView;
        public final TextView authorView;
        public final TextView yearView;

        public ViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.list_item_title_textview);
            authorView = (TextView) view.findViewById(R.id.list_item_author_textview);
            yearView = (TextView) view.findViewById(R.id.list_item_year_textview);
        }
    }
}