package com.code.statuses;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.code.base.RestUtilities;
import com.code.constants.EndPoints;
import com.code.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TweetCreateRetrieveDeleteTest {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweet="The tweet is to be #posted, #read and @deleted";
	String tweetId="";
	
	@BeforeClass
	public void setup() {
		reqSpec=RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		
		resSpec=RestUtilities.getResponseSpecification();
	}
	
	@Test()
	public void postAPIResponse() {
	RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_POST);
	RestUtilities.createQueryParam(reqSpec,"status",tweet);
	Response res= RestUtilities.getResponse(reqSpec, "post");
		tweetId = res.path("id_str");
		System.out.println("tweet id "+tweetId);
	}
	
	@Test(dependsOnMethods = {"postAPIResponse"})
	public void getAPIResponsebyId() {
	Response res=
	given()
		.spec(reqSpec)
		.queryParam("id",tweetId)
	.when()
		.get(EndPoints.STATUSES_TWEET_READ_SINGLE)
	.then()
		.spec(resSpec)
		.extract().response();
	
	String retrievedtweet=res.path("text");
	System.out.println("Tweet was - "+retrievedtweet);	
	}
	
	@Test(dependsOnMethods = {"getAPIResponsebyId"})
	public void deleteAPIResponsebyId() {
	RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY);
	RestUtilities.createPathParam(reqSpec, "id", tweetId);
	Response res = RestUtilities.getResponse(reqSpec, "post");
	}

}
