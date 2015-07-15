package com.etapps.trovenla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.Book;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class BookAdapter extends RealmAdapter<Book, BookAdapter.ContsHolder> {

    private final Context mContext;
    OnItemClickListener mItemClickListener;
    private RealmResults<Book> mContacts;

    public BookAdapter(Context context, RealmResults<Book> contacts) {
        super(context, contacts, true);
        this.mContext = context;
        this.mContacts = contacts;
    }

    @Override
    public ContsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: switch for different view type
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_results, parent, false);
        return new ContsHolder(v);
    }

    @Override
    public void onBindViewHolder(ContsHolder holder, int position) {
        Book contact = this.mContacts.get(position);
        holder.setTitle(contact.getTitle());
        holder.setYear(contact.getIssued());
        holder.setAuthor(contact.getContributor());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void addAll(RealmResults<Book> contacts) {
        mContacts.addAll(contacts);
    }

    public void clear() {
        mContacts.clear();
    }

    public Book getItematPosition(int position) {
        return mContacts.get(position);
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

    public class ContsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.list_item_title_textview)
        TextView mTitle;
        @Bind(R.id.list_item_author_textview)
        TextView mAuthor;
        @Bind(R.id.list_item_year_textview)
        TextView mYear;

        public ContsHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setTitle(String name) {
            if (null == mTitle) return;
            mTitle.setText(name);
        }

        public void setAuthor(String name) {
            if (null == mAuthor) return;
            mAuthor.setText(name);
        }

        public void setYear(String name) {
            if (null == mYear) return;
            mYear.setText(name);
        }


        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
