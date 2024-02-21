package com.vikas.android.news2.data;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article implements Comparable<Article> {
  private Date publishedAt;
  private String title;
  private String url;

  private String urlToImage;

  public Article(String title, Date publishedAt, String urlToImage, String url) {
    this.title = title;
    this.publishedAt = publishedAt;
    this.urlToImage = urlToImage;
    this.url = url;
  }

  public Article(JSONObject jsonObject) throws Exception {

    try {
      String dateString = jsonObject.has("publishedAt") ? jsonObject.getString("publishedAt") : null;

      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      try {
        publishedAt = format.parse(dateString);
      } catch (ParseException e) {
        throw new Exception("Wrong date format");
      }

      title = jsonObject.has("title") ? jsonObject.getString("title") : null;
      url = jsonObject.has("url") ? jsonObject.getString("url") : null;
      urlToImage = jsonObject.has("urlToImage") ? jsonObject.getString("urlToImage") : null;

    } catch (Exception e) {
      throw new Exception("Error in parsing article json");
    }
  }

  public Date getPublishedAt() {
    return publishedAt;
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public String getUrlToImage() {
    return urlToImage;
  }

  @Override
  public String toString() {
    return "Article{" +
        "publishedAt=" + publishedAt +
        ", title='" + title + '\'' +
        ", url='" + url + '\'' +
        ", urlToImage='" + urlToImage + '\'' +
        '}';
  }

  @Override
  public int compareTo(Article other) {
    return this.getPublishedAt().compareTo(other.getPublishedAt());
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (obj instanceof Article) {
      Article other = (Article) obj;
      return this.title.equals(other.title) && this.publishedAt.equals(other.publishedAt) && this.urlToImage.equals(other.urlToImage) && this.url.equals(other.url);
    } else {
      return false;
    }

  }
}
