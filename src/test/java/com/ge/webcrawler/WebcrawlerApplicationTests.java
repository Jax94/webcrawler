package com.ge.webcrawler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.webcrawler.model.CrawlerResponse;
import com.ge.webcrawler.model.Internet;
import com.ge.webcrawler.model.Page;
import com.ge.webcrawler.service.impl.CrawlerServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class WebcrawlerApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	CrawlerServiceImpl service;
	
	Internet internet;
	CrawlerResponse response;
	
    @Before
    public void setUp() {
    	Page page1 = new Page("http://foo.bar.com/p1",
				Arrays.asList("http://foo.bar.com/p2", "http://foo.bar.com/p3", "http://foo.bar.com/p4"));
		Page page2 = new Page("http://foo.bar.com/p2", Arrays.asList("http://foo.bar.com/p2", "http://foo.bar.com/p4"));
		Page page3 = new Page("http://foo.bar.com/p4",
				Arrays.asList("http://foo.bar.com/p5", "http://foo.bar.com/p1", "http://foo.bar.com/p6"));
		Page page4 = new Page("http://foo.bar.com/p5", new ArrayList<String>());
		Page page5 = new Page("http://foo.bar.com/p6",
				Arrays.asList("http://foo.bar.com/p7", "http://foo.bar.com/p4", "http://foo.bar.com/p5"));
		internet= new Internet(Arrays.asList(page1, page2, page3, page4, page5));
		
		response = new CrawlerResponse(
				new HashSet<String>(Arrays.asList("http://foo.bar.com/p4", "http://foo.bar.com/p2",
						"http://foo.bar.com/p1", "http://foo.bar.com/p6", "http://foo.bar.com/p5")),
				new HashSet<String>(Arrays.asList("http://foo.bar.com/p4", "http://foo.bar.com/p2",
						"http://foo.bar.com/p1", "http://foo.bar.com/p5")),
				new HashSet<String>(Arrays.asList("http://foo.bar.com/p3", "http://foo.bar.com/p7")));

    }

	@Test
	public void crawlerTest() throws Exception {
		
		when(service.crawlPages(internet))
		.thenCallRealMethod();

		
		
		mvc.perform(post("/crawler").contentType(MediaType.APPLICATION_JSON).content(toJson(internet)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	

	private static String toJson(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
