package com.sillylife.news;

import android.app.Application;

import com.sillylife.news.Utils.ImageHelper;

public class News extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ImageHelper.init(this);
	}
}
