package com.sillylife.news.Database.Modal;

public class RssFeedModel {

	public String title;
	public String link;
	public String description;
	public String pubDate;
	public String StoryImage;
	public String fullimage;

	public RssFeedModel(String title, String link, String description, String pubDate, String storyImage, String fullimage) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		StoryImage = storyImage;
		this.fullimage = fullimage;
	}
}
