package com.code.list;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.code.base.RestUtilities;
import com.code.constants.EndPoints;
import com.code.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CreateListTest {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String listId="";
	
	@BeforeClass
	public void setup() {
		reqSpec=RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.LISTS);
		
		resSpec=RestUtilities.getResponseSpecification();
	}
	
	@Test()
	public void postAPIResponse() {
	RestUtilities.setEndPoint(EndPoints.LISTS_CREATE);
	RestUtilities.createQueryParam(reqSpec,"name","TestNameList");
	RestUtilities.createQueryParam(reqSpec,"mode","public");
	RestUtilities.createQueryParam(reqSpec,"description","This is a createdList");
	
	Response res= RestUtilities.getResponse(reqSpec, "post");
		listId = res.path("id_str");
		System.out.println("list id "+listId);
	}

}
