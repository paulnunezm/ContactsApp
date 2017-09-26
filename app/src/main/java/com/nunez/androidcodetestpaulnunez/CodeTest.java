package com.nunez.androidcodetestpaulnunez;

import android.app.Application;

import io.realm.Realm;


public class CodeTest extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
  }

}
