package com.etapps.trovenla.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.ArticleDb;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class ArticleAdapter extends RealmRecyclerViewAdapter<ArticleDb, ArticleAdapter.BookHolder> {

    private final Context mContext;
    private OnItemClickListener mItemClickListener;
    private RealmResults<ArticleDb> mArticles;

    public ArticleAdapter(Context context, RealmResults<ArticleDb> articles) {
        super(articles, true, true);
        this.mContext = context;
        this.mArticles = articles;
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
        ArticleDb article = this.mArticles.get(position);
        holder.mTitle.setText(article.getHeading());
        holder.mYear.setText(article.getDate());
        if (!article.getCategory().equals("Article")) {
            holder.mCategory.setVisibility(View.VISIBLE);
            holder.mCategory.setText(article.getCategory());
        } else {
            holder.mCategory.setVisibility(View.GONE);
        }
        holder.mAuthor.setText(article.getTitle());
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
        @BindView(R.id.list_item_category_textview)
        TextView mCategory;
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
