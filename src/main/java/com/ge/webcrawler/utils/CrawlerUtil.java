package com.ge.webcrawler.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ge.webcrawler.model.Page;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CrawlerUtil {

	/*
	 * method to find out links which are present and accessible in the internet /
	 * using parallel stream for concurrency and parallel excecution
	 */
	public List<String> findSuccessPages(List<Page> pages) {

		List<String> success = new ArrayList<>();
		try {
			success = pages.parallelStream().map(Page::getAddress).collect(Collectors.toList());
		} catch (NoSuchElementException e) {
			log.info("No Succesful pages found, returning empty list");
		}
		return success;
	}

	/*
	 * method to find out links which are skipped when crawling in the internet /
	 * using parallel stream for concurrency and parallel excecution
	 */

	public List<String> findSkippedPages(List<Page> pages) {
		List<String> skippedPages = new ArrayList<>();
		List<String> visitedPages = new ArrayList<>();
		for (Page page : pages) {
			visitedPages.add(page.getAddress());
			List<String> currentSkippedPages = new ArrayList<>();

			try {
				currentSkippedPages = page.getLinks().parallelStream().filter(a->visitedPages.contains(a)).collect(Collectors.toList());
			} catch (NoSuchElementException e) {
				log.info("No skipped links for page {}", page.getAddress());
			}
			visitedPages.addAll(page.getLinks());			
			skippedPages.addAll(currentSkippedPages);
		}
		return skippedPages;
	}

	/*
	 * method to find out links which are part of linked pages in the internet /
	 * using parallel stream for concurrency and parallel excecution
	 */
	private List<String> getLinkedPages(List<Page> pages) {

		List<String> linkedPages = new ArrayList<>();
		try {
			linkedPages = pages.parallelStream().filter(a -> a.getLinks() != null)
					.flatMap(a -> a.getLinks().parallelStream()).collect(Collectors.toList());
		} catch (NoSuchElementException e) {
			log.info("No linked pages found, returning empty list");
		}
		return linkedPages;
	}

	/*
	 * method to find out links which are part of linked pages but not present in
	 * the internet / using parallel stream for concurrency and parallel excecution
	 */
	public List<String> findErrorPages(List<Page> pages) {
		List<String> errorPages = new ArrayList<>();
		List<String> successPages = findSuccessPages(pages);
		List<String> linkedPages = getLinkedPages(pages);
		try {
			if (!linkedPages.isEmpty()) {
				errorPages = linkedPages.parallelStream().filter(a -> !successPages.contains(a))
						.collect(Collectors.toList());
			}
		} catch (NoSuchElementException e) {
			log.info("No error pages found, returning empty list");
		}
		return errorPages;
	}

}
