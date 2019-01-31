package com.sillylife.news.Utils;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.sillylife.news.Database.Modal.RssFeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.sillylife.news.Utils.RSSConstants.AUTHOR;
import static com.sillylife.news.Utils.RSSConstants.COPYRIGHT;
import static com.sillylife.news.Utils.RSSConstants.DESCRIPTION;
import static com.sillylife.news.Utils.RSSConstants.FULLIMAGE;
import static com.sillylife.news.Utils.RSSConstants.GUID;
import static com.sillylife.news.Utils.RSSConstants.ITEM;
import static com.sillylife.news.Utils.RSSConstants.LANGUAGE;
import static com.sillylife.news.Utils.RSSConstants.LINK;
import static com.sillylife.news.Utils.RSSConstants.PUB_DATE;
import static com.sillylife.news.Utils.RSSConstants.STORYIMAGE;
import static com.sillylife.news.Utils.RSSConstants.TITLE;

public class FetchFeedTask extends AsyncTask<URL, Boolean, List<RssFeedModel>> {

	public interface FetchFeedTaskListener {
		void onPreExcute();

		void onSuccess(List<RssFeedModel> rssFeedModels);
	}

	private List<RssFeedModel> mFeedModelList;
	private FetchFeedTaskListener fetchFeedTaskListener;

	public FetchFeedTask(FetchFeedTaskListener fetchFeedTaskListener) {
		this.fetchFeedTaskListener = fetchFeedTaskListener;
	}

	@Override
	protected List<RssFeedModel> doInBackground(URL... urls) {
		try {
			InputStream inputStream = urls[0].openConnection().getInputStream();
			mFeedModelList = parseFeed(inputStream);
			return mFeedModelList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<RssFeedModel> rssFeedModels) {
		super.onPostExecute(rssFeedModels);
		fetchFeedTaskListener.onSuccess(rssFeedModels);
	}

	@Override
	protected void onPreExecute() {
		fetchFeedTaskListener.onPreExcute();
	}

	public void cancel() {
		super.onCancelled();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	private List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
		String title = null;
		String link = null;
		String description = null;
		String pubDate = null;
		String StoryImage = null;
		String fullimage = null;
		boolean isItem = false;
		List<RssFeedModel> items = new ArrayList<>();

		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			xmlPullParser.setInput(inputStream, null);

			xmlPullParser.nextTag();
			while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
				int eventType = xmlPullParser.getEventType();

				String name = xmlPullParser.getName();
				if (name == null)
					continue;

				if (eventType == XmlPullParser.END_TAG) {
					if (name.equalsIgnoreCase(ITEM)) {
						isItem = false;
					}
					continue;
				}

				if (eventType == XmlPullParser.START_TAG) {
					if (name.equalsIgnoreCase(ITEM)) {
						isItem = true;
						continue;
					}
				}

				Log.d("MainActivity", "Parsing name ==> " + name);
				String result = "";
				if (xmlPullParser.next() == XmlPullParser.TEXT) {
					result = xmlPullParser.getText();
					xmlPullParser.nextTag();
				}
				switch (name) {
					case TITLE:
						title = result;
						break;
					case DESCRIPTION:
						description = result;
						break;
					case LINK:
						link = result;
						break;
					case GUID:

						break;
					case LANGUAGE:

						break;
					case AUTHOR:

						break;
					case PUB_DATE:
						pubDate = result;
						break;
					case COPYRIGHT:

						break;

					case STORYIMAGE:
						StoryImage = result;
						break;
					case FULLIMAGE:
						fullimage = result;
						break;
				}

				if (title != null && link != null && description != null) {
					if (isItem) {
						RssFeedModel item = new RssFeedModel(title, link, description, pubDate, StoryImage, fullimage);
						items.add(item);
					}

					title = null;
					link = null;
					description = null;
					isItem = false;
				}
			}

			return items;
		} finally {
			inputStream.close();
		}
	}
}