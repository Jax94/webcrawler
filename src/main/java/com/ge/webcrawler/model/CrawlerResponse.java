package com.ge.webcrawler.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jagadeesh
 *
 * Model class for response object, 
 * containing successfully reachable pages, 
 * skipped pages and error pages, which were not available.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Set<String> success;

	private Set<String> skipped;
	
	private Set<String> error;

	

}
