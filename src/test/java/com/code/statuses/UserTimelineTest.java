package com.code.statuses;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import com.code.base.RestUtilities;
import com.code.constants.EndPoints;
import com.code.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserTimelineTest {
	
RequestSpecification reqSpec;
ResponseSpecification resSpec;
	
	@BeforeClass
	public void setup() {
		reqSpec=RestUtilities.getRequestSpecification();
		reqSpec.queryParam("user_id", "DevamSrivastav3");
		reqSpec.basePath(Path.STATUSES);
		
		resSpec=RestUtilities.getResponseSpecification();
}
	@Test
	public void readTweetsonTimeline() {
		given()
			.spec(reqSpec)
		.when()
			.get(EndPoints.STATUSES_USER_Timeline)
		.then()
			.log().all()
			.spec(resSpec)
			.body("user.screen_name",hasItem("DevamSrivastav3"));
		
	}
	@Test
	public void readTheLatestTweets() {
		given()
			.spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))
		.when()
			.get(EndPoints.STATUSES_USER_Timeline)
		.then()
			.log().all()
			.spec(resSpec)
			.body("user.screen_name", hasItem("DevamSrivastav3"));
	}
	@Test
	public void readLatestTweetsAlternateWay() {
		RestUtilities.setEndPoint(EndPoints.STATUSES_USER_Timeline);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "count", "2"), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println("Read Tweets 2 Method");
		Assert.assertTrue(screenNameList.contains("DevamSrivastav3"));
	}

}

