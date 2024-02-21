package com.vikas.android.news2.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataStore {
  private static final String TAG = "DataStore";
  private static final String JSON_FILE_NAME = "news.json";
  private static DataStore sInstance;
  private Context mContext;

  private DataStore(Context context) {
    mContext = context;
  }

  public static DataStore init(Context context) {
    if (sInstance == null) {
      sInstance = new DataStore(context);
    }
    return sInstance;
  }

  public static DataStore get() {
    if (sInstance == null) {
      throw new IllegalStateException("DataStore must be initialised");
    }
    return sInstance;
  }

  public List<Article> getArticles() {
    String jsonString = getJsonString();
    List<Article> parsedList = parseJsonData(jsonString);
    sortArticles(parsedList);
    return parsedList;
  }

  public List<Article> queryArticles(String query) {

    List<Article> articles = getArticles();

    List<Article> resultArticles = new ArrayList<>();

    String[] searchWords = query.trim().split(" ");
    List<List<Article>> buckets = new ArrayList<>();
    for (int i = 0; i < searchWords.length; i++) {
      buckets.add(i, new ArrayList<>());
    }

    for (Article article : articles) {
      int count = 0;
      for (String searchWord : searchWords) {

        if (Arrays.asList(article.getTitle().toLowerCase().split(" ")).contains(searchWord.toLowerCase())) {
          count++;
        }
      }
      if (count > 0) {
        buckets.get(searchWords.length - count).add(article);
      }
    }

    for (List<Article> bucket : buckets) {
      resultArticles.addAll(bucket);
    }

    return resultArticles;
  }

  public String getJsonString() {
    String json = "hello";
    try {
      InputStream inputStream = mContext.getAssets().open(JSON_FILE_NAME);
      int size = inputStream.available();
      byte[] buffer = new byte[size];
      inputStream.read(buffer);
      inputStream.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException e) {
      Log.w(TAG, "Error in loading asset file", e);
    }
    return json;
  }

  public List<Article> parseJsonData(String jsonString) {

    ArrayList<Article> articles = new ArrayList<>();

    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      JSONArray jsonArray = jsonObject.getJSONArray("articles");

      for (int i = 0; i < jsonArray.length(); i++) {
        try {
          Article article = new Article(jsonArray.getJSONObject(i));
          articles.add(article);
        } catch (Exception e) {
          //article skipped
          Log.w(TAG, "Error in parsing article at index " + i, e);
        }

      }

    } catch (Exception e) {
      //Error in reading/parsing json
      Log.e(TAG, "Error in parsing the json", e);
    }

    return articles;
  }

  public void sortArticles(List<Article> parsedList) {
    Collections.sort(parsedList);
    Collections.reverse(parsedList);
  }
}
