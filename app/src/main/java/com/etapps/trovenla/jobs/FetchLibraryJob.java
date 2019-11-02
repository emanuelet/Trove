package com.etapps.trovenla.jobs;

import androidx.annotation.NonNull;

import com.etapps.trovenla.api.TroveApi;
import com.etapps.trovenla.api.TroveRest;
import com.etapps.trovenla.models.library.Contributor;
import com.etapps.trovenla.models.library.Library;
import com.etapps.trovenla.utils.Constants;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.io.IOException;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by emanuele on 06/11/17.
 */

public class FetchLibraryJob extends Job {

    public static final String TAG = "fetch_library";

    private static final String EXTRA_ID = "EXTRA_ID";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        TroveApi api = TroveRest.getAdapter(TroveApi.class);
        Realm realm = Realm.getDefaultInstance();

        String nuc = params.getExtras().getString(EXTRA_ID, "");
        Timber.d("Downloading info for %s", nuc);
        Call<Library> call = api.getLibrary(nuc, Constants.KEY, Constants.FORMAT, Constants.RECLEVEL);
        try {
            Response<Library> result = call.execute();
            Contributor l = result.body().getContributor();

            com.etapps.trovenla.db.Library lib = new com.etapps.trovenla.db.Library();
            lib.setNuc(l.getId());
            lib.setName(l.getName());
            lib.setAlgentry(l.getAlgentry());
            lib.setTotalholdings(l.getTotalholdings());
            lib.setShortname(l.getShortname());
            lib.setAccesspolicy(l.getAccesspolicy());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(lib);
            realm.commitTransaction();
            return Result.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.FAILURE;
        }
    }

    public static void scheduleJob(String nuc) {
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString(EXTRA_ID, nuc);

        new JobRequest.Builder(FetchLibraryJob.TAG)
                .setExecutionWindow(5_000L, 40_000L)
                .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.EXPONENTIAL)
                .setExtras(extras)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }
}