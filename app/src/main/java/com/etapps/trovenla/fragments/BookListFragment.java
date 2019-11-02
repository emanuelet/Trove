package com.etapps.trovenla.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.BookAdapter;
import com.etapps.trovenla.db.Book;

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
public class BookListFragment extends BaseFragment {
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks;
    @BindView(R.id.books)
    RecyclerView mBooks;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    private Activity mContext;
    private Realm realm;
    private BookAdapter adapter;
    private boolean isLoading = false;
    private LinearLayoutManager layoutManager;
    public static final int PAGE_SIZE = 10;
    private String query;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFragment() {
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

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
        layoutManager = new LinearLayoutManager(mContext);
        mBooks.setLayoutManager(layoutManager);
        RealmResults<Book> books = realm.where(Book.class).findAll();
        books.addChangeListener(this::checkResults);
        checkResults(books);
        mBooks.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private void checkResults(RealmResults<Book> bookDbs) {
        if (bookDbs.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            mBooks.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            mBooks.setVisibility(View.VISIBLE);
          query=  bookDbs.first().getQuery();
            adapter = new BookAdapter(mContext, bookDbs);
            mBooks.setAdapter(adapter);
            mBooks.setHasFixedSize(true);
            adapter.SetOnItemClickListener((view, position) -> {
                Book item = adapter.getItematPosition(position);
                mCallbacks.onItemSelected(item.getId());
            });
        }
        isLoading = false;
        progressBar.setVisibility(View.GONE);
    }

    private void loadMoreItems() {
        isLoading = true;

        progressBar.setVisibility(View.VISIBLE);
        mCallbacks.loadMoreBooks(query);
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
        void onItemSelected(String id);

        void loadMoreBooks(String query);
    }
}
