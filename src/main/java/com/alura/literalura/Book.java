package com.alura.literalura;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonAlias("title")
    private String title;
    @JsonAlias("authors")
    private String author;
    private String[] languages;
    private int downloadCount;

    // Getters y setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String[] getLanguages() { return languages; }
    public void setLanguages(String[] languages) { this.languages = languages; }

    public int getDownloadCount() { return downloadCount; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Languages: " + Arrays.toString(languages) + ", Downloads: " + downloadCount;
    }
}