
package com.example.sidrajawaid.demofirebase.RetrofitFiles;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    private final static long serialVersionUID = 5073157184435508850L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Example() {
    }

    /**
     * 
     * @param articles
     * @param totalResults
     * @param status
     */
    public Example(String status, int totalResults, List<Article> articles) {
        super();
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Example withStatus(String status) {
        this.status = status;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public Example withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Example withArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

}
