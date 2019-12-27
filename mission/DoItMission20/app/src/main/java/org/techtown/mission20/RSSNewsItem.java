package org.techtown.mission20;

import android.graphics.drawable.Drawable;

public class RSSNewsItem {

	private String title;
	private String link;
	private String description;
	private String pubDate;
	private String author;
	private String category;
	
	private Drawable mIcon;
	
	/**
	 * Initialize with icon and data array
	 */
	public RSSNewsItem() {
	}

	/**
	 * Initialize with icon and strings
	 */
	public RSSNewsItem(String title, String link, String description, String pubDate, String author, String category) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.author = author;
		this.category = category;
	}

	/**
	 * Set icon
	 * 
	 * @param icon
	 */
	public void setIcon(Drawable icon) {
		mIcon = icon;
	}

	/**
	 * Get icon
	 * 
	 * @return
	 */
	public Drawable getIcon() {
		return mIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Compare with the input object
	 * 
	 * @param other
	 * @return
	 */
	public int compareTo(RSSNewsItem other) {
		if (title.equals(other.getTitle())) {
			return -1;
		} else if (link.equals(other.getLink())) {
			return -1;
		} else if (description.equals(other.getDescription())) {
			return -1;
		} else if (pubDate.equals(other.getPubDate())) {
			return -1;
		} else if (author.equals(other.getAuthor())) {
			return -1;
		} else if (category.equals(other.getCategory())) {
			return -1;
		}
		
		return 0;
	}

}
