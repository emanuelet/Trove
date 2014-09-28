package com.etapps.trove;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by emanuele on 16/09/14.
 */
public class LibrariesAdapter extends CursorAdapter {

    public LibrariesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int layoutId = R.layout.list_item_libraries;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(DetailFragment.COL_LIB_NAME);
        /*if (name.length()>=25) {
            name= name.substring(0, 25) + "...";
        }*/
        // Find TextView and set formatted date on it
        viewHolder.libNameView .setText(name);
        // Read weather forecast from cursor
        /*String city = cursor.getString(DetailFragment.COL_LIB_CITY);
        // Find TextView and set weather forecast on it
        viewHolder.libCityView.setText(city);*/
        //String url = cursor.getString(DetailFragment.COL_LIB_URL);

    }

    private class ViewHolder {
        public final TextView libNameView;
        //public final TextView libCityView;

        public ViewHolder(View view) {
            libNameView = (TextView) view.findViewById(R.id.list_item_library_name_textview);
//            libCityView = (TextView) view.findViewById(R.id.list_item_city_textview);
        }
    }
}
