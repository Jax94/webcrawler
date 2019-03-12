package com.ge.webcrawler.resources;

import org.springframework.http.ResponseEntity;

import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "crawler")
public interface CrawlerApi {

    @ApiOperation(value = "Get the succesful,skipped and error pages from an internet object", notes = "", response = CrawlerResponse.class, tags = { "web-crawler" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = CrawlerResponse.class),
            @ApiResponse(code = 417, message = "Expectations Failed", response = Void.class)})

    ResponseEntity<CrawlerResponse> crawlPages(@ApiParam(value = "Details of internet object") Internet internet);

}
