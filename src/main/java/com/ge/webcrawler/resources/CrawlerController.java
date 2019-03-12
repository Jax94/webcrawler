package com.ge.webcrawler.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;
import com.ge.webcrawler.service.CrawlerService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jagadeesh
 *
 * Controller class for webcrawler
 */

@Slf4j
@RestController
@RequestMapping("/web")
@Api(tags = {"web-crawler"})
public class CrawlerController implements CrawlerApi{
	
	@Autowired CrawlerService service;

	
	/* Finds the succesfully visited, skipped and 
	 * error pages when crawling the internet
	 */
	@CrossOrigin
	@PostMapping(value="/crawler")
	public ResponseEntity<CrawlerResponse> crawlPages( @RequestBody Internet internet) {
		CrawlerResponse crawlerResponse= null;
		try {
			if(!internet.getPages().isEmpty()) {
				crawlerResponse = service.crawlPages(internet);
		}
		} catch (Exception e) {
			log.error("Error occured : {}",e.getMessage());
			return new ResponseEntity<>(crawlerResponse, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(crawlerResponse, HttpStatus.OK);
	}
	

}
