package com.etapps.trovenla.utils;

import android.util.Log;
import android.widget.Toast;

import com.etapps.trovenla.activities.BookListActivity;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.db.Library;
import com.etapps.trovenla.models.libraries.Contributor;
import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.queries.Books;
import com.etapps.trovenla.models.queries.Holding;
import com.etapps.trovenla.models.queries.Work;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Ian Ryan on 11/9/2015.
 */
public class Results {

    private static Realm realm;

    public Results(Realm mrealm) {
        realm = mrealm;
    }

    public void addAll(Books books) {
        RealmList<Book> bkList = new RealmList<>();
        for (Work i : books.getResponse().getZone().get(0).getRecords().getWork()) {
            bkList.add(add(i));
        }
        Log.d("Results", "loaded " + bkList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bkList);
        realm.commitTransaction();
    }

    public static Book add(Work i) {
        Book bk = new Book();
        bk.setId(i.getId());
        bk.setTitle(i.getTitle());
        if (i.getContributor().size() != 0) {
            bk.setContributor(i.getContributor().get(0));
        }
        bk.setHoldingsCount(i.getHoldingsCount());
        bk.setScore(i.getRelevance().getScore());
        bk.setValue(i.getRelevance().getValue());
        bk.setTroveUrl(i.getTroveUrl());
        bk.setIssued(i.getIssued());
        bk.setHoldingsCount(i.getHoldingsCount());
        bk.setVersionCount(i.getVersionCount());
        if (i.getSnippet() != null) {
            bk.setSnippet(i.getSnippet());
        }
        RealmList<Library> llist = new RealmList<>();
        realm.beginTransaction();
        for (Holding s : i.getHolding()) {
            if (s.getNuc() != null) {
                Library stored = realm.where(Library.class)
                        .equalTo("nuc", s.getNuc())
                        .findFirst();
                Library l = new Library();
                if (stored != null) {
                    l = stored;
                } else {
                    l.setNuc(s.getNuc());
                    // TODO trigger fetch of info for the library
                }
                if (s.getUrl() != null) {
                    l.setUrlHolding(s.getUrl().getValue());
                }
                llist.add(l);
            }
        }
        realm.commitTransaction();
        bk.setLibraries(llist);
        return bk;
    }

    public void addLibraries(Libraries libraries) {
        RealmList<Library> libList = new RealmList<>();
        for (Contributor l : libraries.getResponse().getContributor()) {
            Library lib = new Library();
            lib.setNuc(l.getId());
            lib.setName(l.getName());
            lib.setAlgentry(l.getAlgentry());
            lib.setTotalholdings(l.getTotalholdings());
            lib.setShortname(l.getShortname());
            lib.setAccesspolicy(l.getAccesspolicy());
            libList.add(lib);
        }
        Log.d("Results", "loaded " + libList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(libList);
        realm.commitTransaction();
    }
}
