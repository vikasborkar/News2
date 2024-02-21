package com.vikas.android.news2;

import android.app.Application;
import android.content.Context;

import com.vikas.android.news2.data.DataStore;

public class NewsApplication extends Application {

  private static Context applicationContext;

  @Override
  public void onCreate() {
    super.onCreate();
    DataStore.init(this);
    applicationContext = this.getApplicationContext();
  }

  public static Context getContext() {
    return applicationContext;
  }
}
