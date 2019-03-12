package com.ge.webcrawler.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;
import com.ge.webcrawler.model.Page;
import com.ge.webcrawler.service.CrawlerService;
import com.ge.webcrawler.utils.Crawler;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrawlerServiceImpl implements CrawlerService {

	@Autowired
	private ExecutorService taskExecutor;

	@Override
	public CrawlerResponse crawlPages(Internet internet) {

		CrawlerResponse crawlerResponse = new CrawlerResponse();

		// getting the first page
		List<Page> pages = internet.getPages();
		Page startPage = pages.get(0);

		startCrawling(startPage,internet);
	
		
		/* setting the response, creating a new HashSet here from the results as 
		they are final variables and need to be cleared for next request*/
		crawlerResponse.setSuccess(new HashSet<>(Crawler.getCrawledPages()));
		crawlerResponse.setSkipped(new HashSet<>(Crawler.getSkippedPages()));
		crawlerResponse.setError(new HashSet<>(Crawler.getErrorPages()));
		Crawler.destroy();

		return crawlerResponse;
	}

	/* calling ExecutorService to start crawling from the start page 
	 * and wait till all the threads complete
	 */
	private void startCrawling(Page startPage, Internet internet) {
		Crawler crawler = new Crawler(startPage.getAddress(), internet, taskExecutor);
		taskExecutor.execute(crawler);
		Boolean completed = false;
		while (completed == false) {
			try {
				while (taskExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
					continue;
				}
				completed = true;

			} catch (InterruptedException e) {
				log.info("Thread interrupted : {} ", e.getMessage());
				taskExecutor.shutdownNow();
				completed = true;
			}
		}		
	}

}
