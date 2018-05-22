
package com.example.sidrajawaid.demofirebase.RetrofitFiles;

import android.graphics.Bitmap;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article implements Serializable
{

    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    private final static long serialVersionUID = -1495579234405678029L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Article() {
    }

    /**
     * 
     * @param publishedAt
     * @param author
     * @param urlToImage
     * @param title
     * @param source
     * @param description
     * @param url
     */
    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        super();
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }
    public Article(Source source, String author, String title, String description, String url, String urlToImage) {
        super();
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Article withSource(Source source) {
        this.source = source;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Article withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Article withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Article withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Article withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Article withUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
        return this;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Article withPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

}
