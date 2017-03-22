
package com.etapps.trovenla.models.ol;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("publishers")
    @Expose
    private List<Publisher> publishers = null;
    @SerializedName("pagination")
    @Expose
    private String pagination;
    @SerializedName("identifiers")
    @Expose
    private Identifiers identifiers;
    @SerializedName("table_of_contents")
    @Expose
    private List<TableOfContent> tableOfContents = null;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("classifications")
    @Expose
    private Classifications classifications;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("number_of_pages")
    @Expose
    private long numberOfPages;
    @SerializedName("cover")
    @Expose
    private Cover cover;
    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("authors")
    @Expose
    private List<Author> authors = null;
    @SerializedName("by_statement")
    @Expose
    private String byStatement;
    @SerializedName("publish_places")
    @Expose
    private List<PublishPlace> publishPlaces = null;
    @SerializedName("ebooks")
    @Expose
    private List<Ebook> ebooks = null;

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Book withPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
        return this;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public Book withPagination(String pagination) {
        this.pagination = pagination;
        return this;
    }

    public Identifiers getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Identifiers identifiers) {
        this.identifiers = identifiers;
    }

    public Book withIdentifiers(Identifiers identifiers) {
        this.identifiers = identifiers;
        return this;
    }

    public List<TableOfContent> getTableOfContents() {
        return tableOfContents;
    }

    public void setTableOfContents(List<TableOfContent> tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    public Book withTableOfContents(List<TableOfContent> tableOfContents) {
        this.tableOfContents = tableOfContents;
        return this;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Book withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Book withWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Book withUrl(String url) {
        this.url = url;
        return this;
    }

    public Classifications getClassifications() {
        return classifications;
    }

    public void setClassifications(Classifications classifications) {
        this.classifications = classifications;
    }

    public Book withClassifications(Classifications classifications) {
        this.classifications = classifications;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Book withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(long numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Book withNumberOfPages(long numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Book withCover(Cover cover) {
        this.cover = cover;
        return this;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Book withSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Book withPublishDate(String publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Book withKey(String key) {
        this.key = key;
        return this;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Book withAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public String getByStatement() {
        return byStatement;
    }

    public void setByStatement(String byStatement) {
        this.byStatement = byStatement;
    }

    public Book withByStatement(String byStatement) {
        this.byStatement = byStatement;
        return this;
    }

    public List<PublishPlace> getPublishPlaces() {
        return publishPlaces;
    }

    public void setPublishPlaces(List<PublishPlace> publishPlaces) {
        this.publishPlaces = publishPlaces;
    }

    public Book withPublishPlaces(List<PublishPlace> publishPlaces) {
        this.publishPlaces = publishPlaces;
        return this;
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }

    public void setEbooks(List<Ebook> ebooks) {
        this.ebooks = ebooks;
    }

    public Book withEbooks(List<Ebook> ebooks) {
        this.ebooks = ebooks;
        return this;
    }

}
