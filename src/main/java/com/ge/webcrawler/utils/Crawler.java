package com.ge.webcrawler.utils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.webcrawler.model.Internet;
import com.ge.webcrawler.model.Page;

/**
 * @author jagadeesh
 *
 * A Runnable class, which takes start page link and Internet object to recursively crawl the links
 */


public class Crawler implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(Crawler.class);



	private String link;
	private Internet internet;

	// for calling recursive crawling threads
	private ExecutorService taskExecutor;

	// creating the objects to store the result as ConcurrentSkipListSet as they are thread safe
	private static final ConcurrentSkipListSet<String> crawledPages = new ConcurrentSkipListSet<>();
	private static final ConcurrentSkipListSet<String> skippedPages = new ConcurrentSkipListSet<>();
	private static final ConcurrentSkipListSet<String> errorPages = new ConcurrentSkipListSet<>();
	
	public Crawler() {
		
	}

	public Crawler(String link, Internet internet, ExecutorService taskExecutor) {
		super();
		this.link = link;
		this.internet = internet;
		this.taskExecutor = taskExecutor;
	}

	public static ConcurrentSkipListSet<String> getCrawledPages() {
		return crawledPages;
	}

	public static ConcurrentSkipListSet<String> getSkippedPages() {
		return skippedPages;
	}

	public static ConcurrentSkipListSet<String> getErrorPages() {
		return errorPages;
	}

	
	//clears all the results for this crawler request
	public static void destroy() {
		crawledPages.clear();
		skippedPages.clear();
		errorPages.clear();
	}


	public void run() {

		Optional<Page> pageEntity = internet.getPages().parallelStream().filter(a -> a.getAddress().equals(link))
				.findFirst();
		//checking if a page is present in the internet for that link
		if (pageEntity.isPresent()) {
			Page page = pageEntity.get();
			
			//Page is present, checking if already present in the crawledPages set, while adding the same
			if (crawledPages.add(page.getAddress())) {
				logger.info("Page marked as visited : {}", link);
				doCrawlPage(page);
			}
			
			//Page is present, but since it is already present in crawledPages, adding this link to skippedPages
			else {
				skippedPages.add(link);
				logger.info("Page marked as skipped : {}", link);
			}

		} 
		
		//since no page is present for this link in the internet object, adding it to errors
		else {
			errorPages.add(link);
			logger.info("Page marked as error : {}", link);

		}
	}

	// recursively calls crawling(this same thread) on links present in the page
	private void doCrawlPage(Page page) {
		List<String> links = page.getLinks();
		for (String link : links) {
			taskExecutor.submit(new Crawler(link, internet,taskExecutor));
		}
	}
}
