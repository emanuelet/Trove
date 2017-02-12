package com.etapps.trovenla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class RealmAdapter<T extends RealmObject, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected LayoutInflater inflater;
    protected RealmResults<T> realmResults;
    protected Context context;

    private final RealmChangeListener listener;


    private Realm getRealm(RealmResults<T> realmResults) {
        // "getRealm" is not accessible outside package io.realm
        Realm realm = null;
        try {
            Method method = RealmResults.class.getDeclaredMethod("getRealm");
            method.setAccessible(true);
            realm = (Realm) method.invoke(realmResults);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return realm;
    }

    public RealmAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
        this.listener = (!automaticUpdate) ? null : new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                notifyDataSetChanged();
            }
        };

        if (listener != null && realmResults != null) {
            realmResults
                    .addChangeListener(listener);
        }
    }

    @Override
    public long getItemId(int i) {
        // TODO: find better solution once we have unique IDs
        return i;
    }

    public T getItem(int i) {
        if (realmResults == null) {
            return null;
        }
        return realmResults.get(i);
    }

    public void updateRealmResults(RealmResults<T> queryResults) {
        if (listener != null) {
            // Making sure that Adapter is refreshed correctly if new RealmResults come from another Realm
            if (this.realmResults != null) {
                this.realmResults.removeChangeListener(listener);
            }
            if (queryResults != null) {
                queryResults.addChangeListener(listener);
            }
        }

        this.realmResults = queryResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }
}
