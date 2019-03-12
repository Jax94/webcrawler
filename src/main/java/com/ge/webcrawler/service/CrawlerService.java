package com.ge.webcrawler.service;

import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;

public interface CrawlerService {

	CrawlerResponse crawlPages(Internet internet);

}
