
package com.etapps.trovenla.models.ol;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Identifiers {

    @SerializedName("google")
    @Expose
    private List<String> google = null;
    @SerializedName("lccn")
    @Expose
    private List<String> lccn = null;
    @SerializedName("openlibrary")
    @Expose
    private List<String> openlibrary = null;
    @SerializedName("isbn_13")
    @Expose
    private List<String> isbn13 = null;
    @SerializedName("amazon")
    @Expose
    private List<String> amazon = null;
    @SerializedName("isbn_10")
    @Expose
    private List<String> isbn10 = null;
    @SerializedName("oclc")
    @Expose
    private List<String> oclc = null;
    @SerializedName("librarything")
    @Expose
    private List<String> librarything = null;
    @SerializedName("goodreads")
    @Expose
    private List<String> goodreads = null;

    public List<String> getGoogle() {
        return google;
    }

    public void setGoogle(List<String> google) {
        this.google = google;
    }

    public Identifiers withGoogle(List<String> google) {
        this.google = google;
        return this;
    }

    public List<String> getLccn() {
        return lccn;
    }

    public void setLccn(List<String> lccn) {
        this.lccn = lccn;
    }

    public Identifiers withLccn(List<String> lccn) {
        this.lccn = lccn;
        return this;
    }

    public List<String> getOpenlibrary() {
        return openlibrary;
    }

    public void setOpenlibrary(List<String> openlibrary) {
        this.openlibrary = openlibrary;
    }

    public Identifiers withOpenlibrary(List<String> openlibrary) {
        this.openlibrary = openlibrary;
        return this;
    }

    public List<String> getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(List<String> isbn13) {
        this.isbn13 = isbn13;
    }

    public Identifiers withIsbn13(List<String> isbn13) {
        this.isbn13 = isbn13;
        return this;
    }

    public List<String> getAmazon() {
        return amazon;
    }

    public void setAmazon(List<String> amazon) {
        this.amazon = amazon;
    }

    public Identifiers withAmazon(List<String> amazon) {
        this.amazon = amazon;
        return this;
    }

    public List<String> getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(List<String> isbn10) {
        this.isbn10 = isbn10;
    }

    public Identifiers withIsbn10(List<String> isbn10) {
        this.isbn10 = isbn10;
        return this;
    }

    public List<String> getOclc() {
        return oclc;
    }

    public void setOclc(List<String> oclc) {
        this.oclc = oclc;
    }

    public Identifiers withOclc(List<String> oclc) {
        this.oclc = oclc;
        return this;
    }

    public List<String> getLibrarything() {
        return librarything;
    }

    public void setLibrarything(List<String> librarything) {
        this.librarything = librarything;
    }

    public Identifiers withLibrarything(List<String> librarything) {
        this.librarything = librarything;
        return this;
    }

    public List<String> getGoodreads() {
        return goodreads;
    }

    public void setGoodreads(List<String> goodreads) {
        this.goodreads = goodreads;
    }

    public Identifiers withGoodreads(List<String> goodreads) {
        this.goodreads = goodreads;
        return this;
    }

}
