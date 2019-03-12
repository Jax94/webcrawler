package com.ge.webcrawler.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;
import com.ge.webcrawler.model.Page;
import com.ge.webcrawler.service.CrawlerService;
import com.ge.webcrawler.utils.CrawlerUtil;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	@Autowired
	CrawlerUtil util;

	@Override
	public CrawlerResponse crawlPages(Internet internet) {
		
		CrawlerResponse crawlerResponse  = new CrawlerResponse();
		List<Page> pages = internet.getPages();
		
		//finding the success, skipped and error pages and removing duplicates by converting to set
		Set<String> success = new HashSet<>(util.findSuccessPages(pages));
		Set<String> skipped = new HashSet<>(util.findSkippedPages(pages));
		Set<String> error = new HashSet<>(util.findErrorPages(pages));

		//setting the response
		crawlerResponse.setSuccess(success);
		crawlerResponse.setSkipped(skipped);
		crawlerResponse.setError(error);
		
		return crawlerResponse;
	}

}
