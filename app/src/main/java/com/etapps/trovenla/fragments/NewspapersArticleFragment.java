package com.etapps.trovenla.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etapps.trovenla.R;
import com.etapps.trovenla.api.TroveApi;
import com.etapps.trovenla.api.TroveRest;
import com.etapps.trovenla.models.articles.Article;
import com.etapps.trovenla.models.articles.FullArticle;
import com.etapps.trovenla.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewspapersArticleFragment extends Fragment {
    private static final String SHARE_HASHTAG = " #Trove";

    private Context mContext;

    @BindView(R.id.loadingLayout)
    LinearLayout mLoadingLayout;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.newspaper)
    TextView mNewspaper;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.editText)
    TextView mText;

    private ShareActionProvider mShareActionProvider;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Article article;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewspapersArticleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        TroveApi api = TroveRest.getAdapter(TroveApi.class);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

        if (getArguments().containsKey(Constants.TROVE_KEY)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String mKeyStr = getArguments().getString(Constants.TROVE_KEY);

            Call<FullArticle> call = api.getArticle(mKeyStr, Constants.KEY, Constants.FORMAT, Constants.INCLUDE, Constants.RECLEVEL);
            call.enqueue(new Callback<FullArticle>() {
                @Override
                public void onResponse(Call<FullArticle> call, Response<FullArticle> response) {
                    if (response.isSuccessful()) {
                        article = response.body().getArticle();
                        initObjs();
                        populateView();
                    } else {
                        Timber.e(response.message());
                    }
                    mLoadingLayout.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<FullArticle> call, Throwable t) {
                    mLoadingLayout.setVisibility(View.GONE);
                    Timber.e(t);
                }
            });


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_news_article, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void initObjs() {
        mTitle.setText(article.getHeading());
        if (article.getArticleText() != null) {
            mText.setText(Html.fromHtml(article.getArticleText()));
        }
        mNewspaper.setText(article.getTitle().getValue());
        mDate.setText(article.getDate());
        mText.setMovementMethod(LinkMovementMethod.getInstance());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, article.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, article.getTitle().getValue());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    private void populateView() {
        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    private void goToUrl(String url) {
        Timber.d(url);
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(mContext, "The url is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(mContext, Uri.parse(url));
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, article.getHeading());
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "newspaper-article");
            bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, url);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.articlefragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_launch) {
            goToUrl(article.getTroveUrl());
        }
        if (id == R.id.action_pdf) {
            goToUrl(article.getPdf());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(article.getPdf());
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, article.getId());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, article.getPdf());
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "pdf");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent shareBookLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, article.getUrl() + SHARE_HASHTAG);
        return shareIntent;
    }
}
