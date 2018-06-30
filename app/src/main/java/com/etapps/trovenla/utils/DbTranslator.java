package com.etapps.trovenla.utils;

import android.text.TextUtils;

import com.etapps.trovenla.db.ArticleDb;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.db.FullArticle;
import com.etapps.trovenla.db.Library;
import com.etapps.trovenla.db.Picture;
import com.etapps.trovenla.models.libraries.Contributor;
import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.newspapers.Article;
import com.etapps.trovenla.models.newspapers.Newspaper;
import com.etapps.trovenla.models.queries.Books;
import com.etapps.trovenla.models.queries.Holding;
import com.etapps.trovenla.models.queries.Identifier;
import com.etapps.trovenla.models.queries.Work;

import io.realm.Realm;
import io.realm.RealmList;
import timber.log.Timber;

/**
 * Created by Ian Ryan on 11/9/2015.
 */
public class DbTranslator {

    private static Realm realm;

    public DbTranslator(Realm mrealm) {
        realm = mrealm;
    }

    public void addBooks(Books books) {
        realm.beginTransaction();
        realm.delete(Book.class);
        realm.commitTransaction();
        RealmList<Book> bkList = new RealmList<>();
        for (Work i : books.getResponse().getZone().get(0).getRecords().getWork()) {
            bkList.add(addBook(i));
        }
        Timber.d("loaded %s", bkList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bkList);
        realm.commitTransaction();
    }

    public void addNewspapers(Newspaper books) {
        realm.beginTransaction();
        realm.delete(ArticleDb.class);
        realm.commitTransaction();
        RealmList<ArticleDb> bkList = new RealmList<>();
        for (Article i : books.getResponse().getZone().get(0).getRecords().getArticle()) {
            bkList.add(addArticle(i));
        }
        Timber.d("loaded %s", bkList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bkList);
        realm.commitTransaction();
    }

    public void addPictures(Books books) {
        realm.beginTransaction();
        realm.delete(Picture.class);
        realm.commitTransaction();
        RealmList<Picture> bkList = new RealmList<>();
        for (Work i : books.getResponse().getZone().get(0).getRecords().getWork()) {
            bkList.add(addPicture(i));
        }
        Timber.d("loaded %s", bkList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bkList);
        realm.commitTransaction();
    }

    private static Book addBook(Work i) {
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
            String nuc = s.getNuc();
            if (!TextUtils.isEmpty(nuc)) {
                Library stored = realm.where(Library.class)
                        .equalTo("nuc", nuc)
                        .findFirst();
                Library l = new Library();
                if (stored != null) {
                    l = stored;
                } else {
                    l.setNuc(nuc);
                    Timber.d("to download " + nuc);
//                    FetchLibraryJob.scheduleJob(nuc);
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

    private static Picture addPicture(Work i) {
        Picture bk = new Picture();
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
        for (Identifier s : i.getIdentifier()) {
            if (s.getLinktype().equals("thumbnail")) {
                bk.setThumbnail(s.getValue());
            }
            if (s.getLinktype().equals("fulltext")) {
                bk.setOriginalLink(s.getValue());
            }
        }
        return bk;
    }

    private static ArticleDb addArticle(Article i) {
        ArticleDb bk = new ArticleDb();
        bk.setId(i.getId());
        bk.setUrl(i.getUrl());
        bk.setHeading(i.getHeading());
        bk.setCategory(i.getCategory());
        bk.setDate(i.getDate());
        bk.setPage(i.getPage());
        bk.setTitle(i.getTitle().getValue());
        bk.setScore(i.getRelevance().getScore());
        bk.setValue(i.getRelevance().getValue());
        bk.setSnippet(i.getSnippet());
        bk.setEdition(i.getEdition());
        bk.setTroveUrl(i.getTroveUrl());
        return bk;
    }

    private static FullArticle addFullArticle(Article i) {
        FullArticle bk = new FullArticle();
        bk.setId(i.getId());
        bk.setUrl(i.getUrl());
        bk.setHeading(i.getHeading());
        bk.setCategory(i.getCategory());
        bk.setDate(i.getDate());
        bk.setPage(i.getPage());
        bk.setTitle(i.getTitle().getValue());
        bk.setTroveUrl(i.getTroveUrl());
        return bk;
    }

    public void addLibraries(Libraries libraries) {
        RealmList<Library> libList = new RealmList<>();
        for (Contributor l : libraries.getResponse().getContributor()) {
            addLibrary(libList, l);
            if (l.getChildren() != null && l.getChildren().getContributor().size() != 0) {
                for (Contributor c : l.getChildren().getContributor()) {
                    addLibrary(libList, c);
                    if (c.getChildren() != null && c.getChildren().getContributor().size() != 0) {
                        for (Contributor sc : l.getChildren().getContributor()) {
                            addLibrary(libList, sc);
                        }
                    }
                }
            }
        }
        Timber.d("loaded %s", libList.size());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(libList);
        realm.commitTransaction();
    }

    private void addLibrary(RealmList<Library> libList, Contributor l) {
        Library lib = new Library();
        lib.setNuc(l.getId());
        lib.setName(l.getName());
        lib.setAlgentry(l.getAlgentry());
        lib.setTotalholdings(l.getTotalholdings());
        lib.setShortname(l.getShortname());
        lib.setAccesspolicy(l.getAccesspolicy());
        libList.add(lib);
    }
}
