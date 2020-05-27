package com.code.list;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.code.base.RestUtilities;
import com.code.constants.EndPoints;
import com.code.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CreateReadDeleteListTest {
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String lid="";
	String screenName="";
	
	@BeforeClass
	public void setup() {
		reqSpec=RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.LISTS);
		
		resSpec=RestUtilities.getResponseSpecification();
	}
	@Test()
	public void createList() {
	RestUtilities.setEndPoint(EndPoints.LISTS_CREATE);
	RestUtilities.createQueryParam(reqSpec,"name","TestNameList");
	RestUtilities.createQueryParam(reqSpec,"mode","public");
	RestUtilities.createQueryParam(reqSpec,"description","This is a createdList");
	Response res= RestUtilities.getResponse(reqSpec, "post");
	lid = res.path("id_str");
	System.out.println("list id "+lid);
	screenName = res.path("user.screen_name");
	System.out.println("Scrren Name : "+screenName);
	}
	
	@Test(dependsOnMethods = {"createList"})
	public void readListByScreenName() {
	RestUtilities.setEndPoint(EndPoints.LISTS_RETRIEVE);
	RestUtilities.createQueryParam(reqSpec,"screen_name",screenName);
	Response res= RestUtilities.getResponse(reqSpec, "get");
	}
	
	@Test(dependsOnMethods = {"readListByScreenName"})
	public void deleteListResponsebyId() {
	RestUtilities.setEndPoint(EndPoints.LISTS_DELETE);
	RestUtilities.createQueryParam(reqSpec, "list_id", lid);
	Response res= RestUtilities.getResponse(reqSpec, "post");
	lid = res.path("id_str");
	System.out.println("list id removed "+lid);
	}
	

}
