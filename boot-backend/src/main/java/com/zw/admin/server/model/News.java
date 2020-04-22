package com.zw.admin.server.model;

import java.util.Date;

public class News extends BaseEntity<Long> {

	private String title;
	private String content;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
