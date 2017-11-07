package com.etapps.trovenla.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.etapps.trovenla.R;
import com.etapps.trovenla.db.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by emanuele on 15/07/15.
 */
public class PicturesAdapter extends RealmAdapter<Picture, PicturesAdapter.PictureHolder> {

    private final Context mContext;
    OnItemClickListener mItemClickListener;
    private RealmResults<Picture> mLibraries;

    public PicturesAdapter(Context context, RealmResults<Picture> contacts) {
        super(context, contacts, true);
        this.mContext = context;
        this.mLibraries = contacts;
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_picture, parent, false);
        return new PictureHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(PictureHolder holder, int position) {
        Picture contact = this.mLibraries.get(position);
        holder.mTitle.setText(contact.getTitle());
        holder.mAuthor.setText(contact.getContributor());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(contact.getThumbnail())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mLibraries.size();
    }

    public void clear() {
        mLibraries.clear();
    }

    public Picture getItematPosition(int position) {
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
        void onItemClick(View view, int position);
    }

    public class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_item_picture)
        ImageView mImage;
        @BindView(R.id.list_item_title)
        TextView mTitle;
        @BindView(R.id.list_item_author)
        TextView mAuthor;

        PictureHolder(View itemView) {
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
