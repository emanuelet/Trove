package com.etapps.trovenla.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.ArticleAdapter;
import com.etapps.trovenla.db.ArticleDb;

import butterknife.BindView;
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
public class NewspapersListFragment extends BaseFragment {
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

    private Activity mContext;
    private Realm realm;
    private ArticleAdapter adapter;
    private RealmResults<ArticleDb> articles;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewspapersListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void initialize() {
        initList();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_main;
    }


    private void initList() {
        mArticles.setLayoutManager(new LinearLayoutManager(mContext));
        articles = realm.where(ArticleDb.class).findAll();
        articles.addChangeListener(this::checkResults);
        checkResults(articles);
    }

    private void checkResults(RealmResults<ArticleDb> articleDbs) {
        if (articleDbs.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            mArticles.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            mArticles.setVisibility(View.VISIBLE);
            adapter = new ArticleAdapter(mContext, articleDbs);
            mArticles.setAdapter(adapter);
            mArticles.setHasFixedSize(true);
            adapter.SetOnItemClickListener((view, position) -> {
                ArticleDb item = adapter.getItematPosition(position);
                mCallbacks.onArticleSelected(item.getId());
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
        void onArticleSelected(String id);
    }
}
