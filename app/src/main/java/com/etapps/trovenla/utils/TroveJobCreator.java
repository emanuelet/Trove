package com.etapps.trovenla.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etapps.trovenla.jobs.FetchLibraryJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by emanuele on 06/11/17.
 */

public class TroveJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case FetchLibraryJob.TAG:
                return new FetchLibraryJob();
            default:
                return null;
        }
    }
}