package com.etapps.trovenla.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.BookAdapter;
import com.etapps.trovenla.db.Book;

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
public class BookListFragment extends Fragment {
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = id -> {
    };
    @BindView(R.id.books)
    RecyclerView mBooks;
    @BindView(R.id.empty_view)
    TextView emptyView;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    private Activity mContext;
    private Realm realm;
    private BookAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
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
        mBooks.setLayoutManager(new LinearLayoutManager(mContext));
        RealmResults<Book> books = realm.where(Book.class).findAll();
        books.addChangeListener(bookDbs -> {
            if (bookDbs.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                mBooks.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                mBooks.setVisibility(View.VISIBLE);
                adapter = new BookAdapter(mContext, bookDbs);
                mBooks.setAdapter(adapter);
                mBooks.setHasFixedSize(true);
                adapter.SetOnItemClickListener((view, position) -> {
                    Book item = adapter.getItematPosition(position);
                    mCallbacks.onItemSelected(item.getId());
                });
            }
        });
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
    }
}
