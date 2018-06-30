package com.etapps.trovenla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class BookAdapter extends RealmRecyclerViewAdapter<Book, BookAdapter.BookHolder> {

    private final Context mContext;
    private OnItemClickListener mItemClickListener;
    private RealmResults<Book> mContacts;

    public BookAdapter(Context context, RealmResults<Book> books) {
        super(books, true, true);
        this.mContext = context;
        this.mContacts = books;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_results, parent, false);
        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        Book book = this.mContacts.get(position);
        holder.mTitle.setText(book.getTitle());
        holder.mYear.setText(book.getIssued());
        holder.mAuthor.setText(book.getContributor());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void addAll(RealmResults<Book> books) {
        mContacts.addAll(books);
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
        void onItemClick(View view, int position);
    }

    public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_item_title_textview)
        TextView mTitle;
        @BindView(R.id.list_item_author_textview)
        TextView mAuthor;
        @BindView(R.id.list_item_year_textview)
        TextView mYear;

        public BookHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
