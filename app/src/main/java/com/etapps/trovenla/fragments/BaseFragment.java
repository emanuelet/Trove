package com.etapps.trovenla.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public FirebaseAnalytics getAnalitycs() {
        return mFirebaseAnalytics;
    }

    /**
     * @return the FragmentManager for interacting with fragments associated
     * with this activity.
     */
    protected FragmentManager getActivitySupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    /**
     * Use this method if you need add a new fragment to a container
     *
     * @param containerViewId Identifier of the container whose fragment(s) are to be replaced
     * @param fragment        The new fragment to place in the container
     */
    protected void onAddTransaction(@IdRes int containerViewId, @NonNull Fragment fragment, Bundle bundle) {
        fragment.setRetainInstance(true);
        fragment.setArguments(bundle);
        getActivitySupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Use this method if you need replace an existing fragment that was added to a container
     *
     * @param containerViewId Identifier of the container whose fragment(s) are to be replaced
     * @param fragment        The new fragment to place in the container
     */
    protected void onReplaceTransaction(@IdRes int containerViewId, @NonNull Fragment fragment, Bundle bundle) {
        fragment.setRetainInstance(true);
        fragment.setArguments(bundle);
        getActivitySupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }


    /**
     * Every fragment has to do something once the view has been created and inflated. You have to implement
     * this method that will be called after the Butterknife injection is completed.
     */
    protected abstract void initialize();

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends InkFragment.
     */
    protected abstract int getFragmentLayout();


    /**
     * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
     * value.
     *
     * @param view to extract each widget injected in the fragment.
     */
    private void injectViews(final View view) {
        ButterKnife.bind(this, view);
    }
}
