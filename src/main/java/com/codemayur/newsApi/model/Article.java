package com.codemayur.newsApi.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
	
	private Source source;
	private String author;
	private String title;
	private String description;
	private String url;
	private String urlToImage;
	private Date publishedAt;
	private String content;

}
