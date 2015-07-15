package com.etapps.trovenla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.Library;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class LibrariesAdapter extends RealmAdapter<Library, LibrariesAdapter.LibraryHolder> {

    private final Context mContext;
    OnItemClickListener mItemClickListener;
    private RealmResults<Library> mLibraries;

    public LibrariesAdapter(Context context, RealmResults<Library> contacts) {
        super(context, contacts, true);
        this.mContext = context;
        this.mLibraries = contacts;
    }

    @Override
    public LibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_libraries, parent, false);
        return new LibraryHolder(v);
    }

    @Override
    public void onBindViewHolder(LibraryHolder holder, int position) {
        Library contact = this.mLibraries.get(position);
        holder.setTitle(contact.getName());
    }

    @Override
    public int getItemCount() {
        return mLibraries.size();
    }

    public void addAll(RealmResults<Library> contacts) {
        mLibraries.addAll(contacts);
    }

    public void clear() {
        mLibraries.clear();
    }

    public Library getItematPosition(int position) {
        return mLibraries.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class LibraryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.list_item_library_name)
        TextView mTitle;

        public LibraryHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setTitle(String name) {
            if (null == mTitle) return;
            mTitle.setText(name);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
