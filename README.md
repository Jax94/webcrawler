# webcrawler
This document describes the webcrawler appication, which takes the details of pages as Internet object and Gives the Succesfully traversed links, skipped links and links which are part of other pages but are not present in the internet as error links as output.

#### Deployment Steps:-
In order to build the program, the following is required

1.Java 8 JDK

2.Maven 3.5.2

To Start the application:-
*  Clone this repository
*  Build the application entering ./build in terminal
*  Start the application by entering ./start in terminal  

Access Swagger-UI using the following link after starting the application : http://localhost:8000/swagger-ui.html
#### Web Crawler endpoint
This endpoint takes the details of pages as Internet object and Gives the Succesfully traversed links, skipped links and links which are part of other pages but are not present in the internet as error links as output.

Request:
```
POST /web/crawler
```
Sample Request Body:
```
{
 "pages": [
		{
		 "address":"http://foo.bar.com/p1",
		 "links": ["http://foo.bar.com/p2","http://foo.bar.com/p3", "http://foo.bar.com/p4"]
		},
		{
			"address":"http://foo.bar.com/p2",
			"links": ["http://foo.bar.com/p2","http://foo.bar.com/p4"]
		 },
		{
		"address":"http://foo.bar.com/p4",
		"links": ["http://foo.bar.com/p5","http://foo.bar.com/p1", "http://foo.bar.com/p6"]
		 },
		 {
		"address":"http://foo.bar.com/p5",
		"links": []
		},
	 {
		"address":"http://foo.bar.com/p6",
		"links": ["http://foo.bar.com/p7","http://foo.bar.com/p4", "http://foo.bar.com/p5"]
	 }
 ]
}
```

Sample Response:


```
{
    "success": [
        "http://foo.bar.com/p4",
        "http://foo.bar.com/p2",
        "http://foo.bar.com/p1",
        "http://foo.bar.com/p6",
        "http://foo.bar.com/p5"
    ],
    "skipped": [
        "http://foo.bar.com/p4",
        "http://foo.bar.com/p2",
        "http://foo.bar.com/p1",
        "http://foo.bar.com/p5"
    ],
    "error": [
        "http://foo.bar.com/p3",
        "http://foo.bar.com/p7"
    ]
}
```
