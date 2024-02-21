package com.vikas.android.news2.data;

import java.util.List;

public class ArticleRepository {
  private final DataStore dataStore;

  public ArticleRepository() {
    dataStore = DataStore.get();
  }

  public List<Article> getArticles() {
    return dataStore.getArticles();
  }

  public List<Article> queryArticles(String query) {
    return dataStore.queryArticles(query);
  }
}
