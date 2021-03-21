package com.codemayur.newsApi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.codemayur.newsApi.model.Root;

/**
 * <p>
 * <b>Problem Statement</b>
 * </p>
 * <p>
 * Create a program that calls News API(s) from http://newsapi.org and parses
 * the JSON.
 * </p>
 * <p>
 * 1. Displays the Headlines at any moment (Using the "Top headlines" API)
 * </p>
 * <p>
 * 2. Searches for a query and shows result (Using the everything API)
 * </p>
 * <p>
 * 3. Displays various sources of News, along with the description of the
 * channel (Using the Sources API)
 * </p>
 * <p>
 * <b>Everything</b><br>
 * http://localhost:8080/newsApi/searchQuery?q=bitcoin<br>
 * http://localhost:8080/newsApi/searchQuery?q=apple&from=2021-03-18&to=2021-03-18&sortBy=popularity<br>
 * http://localhost:8080/newsApi/searchQuery?domains=techcrunch.com,thenextweb.com<br>
 * </p>
 * <p>
 * <b>Top Headlines</b><br>
 * http://localhost:8080/newsApi/getHeadlines?country=us<br>
 * http://localhost:8080/newsApi/getHeadlines?sources=bbc-news<br>
 * http://localhost:8080/newsApi/getHeadlines?country=de&category=business<br>
 * http://localhost:8080/newsApi/getHeadlines?q=trump<br>
 * </p>
 * <p>
 * <b>Sources</b><br>
 * http://localhost:8080/newsApi/sourcesAndDescription<br>
 * http://localhost:8080/newsApi/sourcesAndDescription?category=business<br>
 * http://localhost:8080/newsApi/sourcesAndDescription?country=us<br>
 * </p>
 * 
 * @since 19/03/2021
 * @author mayur.somani
 */

@Controller
@RequestMapping("newsApi")
public class NewsApisController {

	private static final String API_KEY = "869a8838584141159a73b675fbcb3b71";
	private static final String URL = "http://newsapi.org/";

	private static final String TOP_HEADLINES = "v2/top-headlines?";
	private static final String EVERYTHING = "v2/everything?";
	private static final String SOURCES = "v2/sources?";

	private static final String COUNTRY_PARAM = "country";
	private static final String CATEGORY_PARAM = "category";
	private static final String SOURCES_PARAM = "sources";
	private static final String Q_PARAM = "q";
	private static final String Q_IN_TITLE_PARAM = "qInTitle";
	private static final String DOMAINS_PARAM = "domains";
	private static final String EXCLUDED_DOMAINS_PARAM = "excludeDomains";
	private static final String FROM_PARAM = "from";
	private static final String TO_PARAM = "to";
	private static final String LANGUAGE_PARAM = "language";
	private static final String SORT_BY_PARAM = "sortBy";
	private static final String PAGE_SIZE_PARAM = "pageSize";
	private static final String PAGE_PARAM = "page";
	private static final String API_KEY_PARAM = "apiKey";

	private static final String SUCCESS = "success";
	private static final String TOP_HEADLINES_KEY = "topHeadlines";
	private static final String EVERYTHING_KEY = "everything";
	private static final String SOURCES_AND_DESCRIPTION_KEY = "sourcesAndDescription";

	@GetMapping("getHeadlines")
	public ResponseEntity<Map<String, Object>> getHeadlines(
			@RequestParam(value = "country", required = false) String country,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "sources", required = false) String sources,
			@RequestParam(value = "q", required = false) String q) {

		Map<String, Object> responseMap = new HashMap<>();

		Root topHeadlines = null;
		try {

			StringBuilder url = new StringBuilder(URL);
			url.append(TOP_HEADLINES);

			if (country != null) {
				url.append(COUNTRY_PARAM);
				url.append("=");
				url.append(country);
				if (category != null) {
					url.append("&");
					url.append(CATEGORY_PARAM);
					url.append("=");
					url.append(category);
				}
			} else if (sources != null) {
				url.append(SOURCES_PARAM);
				url.append("=");
				url.append(sources);
			} else if (q != null) {
				url.append(Q_PARAM);
				url.append("=");
				url.append(q);
			} else {
				throw new IllegalStateException("Parameter Combinations not valid!");
			}
			url.append("&");
			url.append(API_KEY_PARAM);
			url.append("=");
			url.append(API_KEY);

			RestTemplate restTemplate = new RestTemplate();
			topHeadlines = restTemplate.getForObject(url.toString(),
					Root.class);

		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put(SUCCESS,
					false);
			responseMap.put(TOP_HEADLINES_KEY,
					null);
			return new ResponseEntity<>(responseMap,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put(SUCCESS,
				true);
		responseMap.put(TOP_HEADLINES_KEY,
				topHeadlines);
		return new ResponseEntity<>(responseMap,
				HttpStatus.OK);
	}

	@GetMapping("searchQuery")
	public ResponseEntity<Map<String, Object>> searchQuery(@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "qInTitle", required = false) String qInTitle,
			@RequestParam(value = "sources", required = false) String sources,
			@RequestParam(value = "domains", required = false) String domains,
			@RequestParam(value = "excludeDomains", required = false) String excludeDomains,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to,
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "page", required = false) String page) {

		Map<String, Object> responseMap = new HashMap<>();

		Root everything = null;
		try {

			StringBuilder url = new StringBuilder(URL);
			url.append(EVERYTHING);

			if (q != null) {
				url.append(Q_PARAM);
				url.append("=");
				url.append(q);
				if (from != null && to != null && sortBy != null) {
					url.append("&");
					url.append(FROM_PARAM);
					url.append("=");
					url.append(from);
					url.append("&");
					url.append(TO_PARAM);
					url.append("=");
					url.append(to);
					url.append("&");
					url.append(SORT_BY_PARAM);
					url.append("=");
					url.append(sortBy);
				}
			} else if (domains != null) {
				url.append(DOMAINS_PARAM);
				url.append("=");
				url.append(domains);
			} else {
				throw new IllegalStateException("Parameter Combinations not valid!");
			}

			url.append("&");
			url.append(API_KEY_PARAM);
			url.append("=");
			url.append(API_KEY);

			RestTemplate restTemplate = new RestTemplate();
			everything = restTemplate.getForObject(url.toString(),
					Root.class);

		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put(SUCCESS,
					false);
			responseMap.put(EVERYTHING_KEY,
					null);
			return new ResponseEntity<>(responseMap,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put(SUCCESS,
				true);
		responseMap.put(EVERYTHING_KEY,
				everything);
		return new ResponseEntity<>(responseMap,
				HttpStatus.OK);

	}

	@GetMapping("sourcesAndDescription")
	public ResponseEntity<Map<String, Object>> sourcesAndDescription(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "country", required = false) String country) {

		Map<String, Object> responseMap = new HashMap<>();

		Root sourcesAndDescription = null;
		try {

			StringBuilder url = new StringBuilder(URL);
			url.append(SOURCES);

			if (category != null) {
				url.append(CATEGORY_PARAM);
				url.append("=");
				url.append(category);
				url.append("&");
			} else if (country != null) {
				url.append(COUNTRY_PARAM);
				url.append("=");
				url.append(country);
				url.append("&");
			} else {
				// do nothing
			}

			url.append(API_KEY_PARAM);
			url.append("=");
			url.append(API_KEY);

			RestTemplate restTemplate = new RestTemplate();
			sourcesAndDescription = restTemplate.getForObject(url.toString(),
					Root.class);

		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put(SUCCESS,
					false);
			responseMap.put(SOURCES_AND_DESCRIPTION_KEY,
					null);
			return new ResponseEntity<>(responseMap,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put(SUCCESS,
				true);
		responseMap.put(SOURCES_AND_DESCRIPTION_KEY,
				sourcesAndDescription);
		return new ResponseEntity<>(responseMap,
				HttpStatus.OK);

	}

}
