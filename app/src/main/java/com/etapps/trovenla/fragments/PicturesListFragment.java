package com.etapps.trovenla.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.PicturesAdapter;
import com.etapps.trovenla.db.Picture;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A list fragment representing a list of DbTranslator. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link BookDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class PicturesListFragment extends Fragment {
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = id -> {
    };
    @BindView(R.id.books)
    RecyclerView mArticles;
    @BindView(R.id.empty_view)
    TextView emptyView;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Activity mContext;
    private Realm realm;
    private PicturesAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PicturesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        initList();

        return rootView;
    }

    private void initList() {
        mArticles.setLayoutManager(new GridLayoutManager(mContext, 2));
        RealmResults<Picture> pictures = realm.where(Picture.class).findAll();
        pictures.addChangeListener(this::checkResults);
        checkResults(pictures);
    }

    private void checkResults(RealmResults<Picture> pictureDbs) {
        if (pictureDbs.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            mArticles.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            mArticles.setVisibility(View.VISIBLE);
            adapter = new PicturesAdapter(mContext, pictureDbs);
            mArticles.setAdapter(adapter);
            mArticles.setHasFixedSize(true);
            adapter.SetOnItemClickListener((view, position) -> {
                Picture item = adapter.getItematPosition(position);

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mContext, Uri.parse(item.getTroveUrl()));

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, item.getTitle());
                bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "picture");
                bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, item.getTroveUrl());
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onPictureSelected(String id);
    }
}
