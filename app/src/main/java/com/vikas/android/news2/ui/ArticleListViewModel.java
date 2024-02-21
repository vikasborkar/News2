package com.vikas.android.news2.ui;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vikas.android.news2.data.Article;
import com.vikas.android.news2.data.ArticleRepository;

import java.util.List;

public class ArticleListViewModel extends ViewModel {
  private ArticleRepository articleRepository;

  private MutableLiveData<List<Article>> articles;

  public ArticleListViewModel() {
    articleRepository = new ArticleRepository();

    articles = new MutableLiveData<>();
    articles.setValue(articleRepository.getArticles());
  }

  public void setQuery(@Nullable String query) {
    if (TextUtils.isEmpty(query)) {
      articles.setValue(articleRepository.getArticles());
    } else {
      articles.setValue(articleRepository.queryArticles(query));
    }
  }

  public MutableLiveData<List<Article>> getArticles() {
    return articles;
  }

}
