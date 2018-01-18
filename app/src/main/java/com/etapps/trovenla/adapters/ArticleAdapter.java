package com.etapps.trovenla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.ArticleDb;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class ArticleAdapter extends RealmAdapter<ArticleDb, ArticleAdapter.BookHolder> {

    private final Context mContext;
    OnItemClickListener mItemClickListener;
    private RealmResults<ArticleDb> mArticles;

    public ArticleAdapter(Context context, RealmResults<ArticleDb> contacts) {
        super(context, contacts, true);
        this.mContext = context;
        this.mArticles = contacts;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_article, parent, false);
        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        ArticleDb contact = this.mArticles.get(position);
        holder.mTitle.setText(contact.getHeading());
        holder.mYear.setText(contact.getDate());
        holder.mAuthor.setText(contact.getTitle());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void clear() {
        mArticles.clear();
    }

    public ArticleDb getItematPosition(int position) {
        return mArticles.get(position);
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

        BookHolder(View itemView) {
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
