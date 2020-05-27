package com.code.base;

import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.code.constants.Auth;
import com.code.constants.Path;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {
	
	public static String ENDPOINT;
	public static RequestSpecBuilder requestBuilder;
	public static RequestSpecification requestSpec;
	public static ResponseSpecBuilder responseBuilder;
	public static ResponseSpecification responseSpec;
	
	public static void setEndPoint(String epoint) {
		ENDPOINT=epoint;
	}
	
	public static RequestSpecification getRequestSpecification() {		
		AuthenticationScheme authScheme = 
		RestAssured.oauth(Auth.api_Key, Auth.api_Secret, Auth.access_Token, Auth.access_Secret);
		requestBuilder = new RequestSpecBuilder();
		requestBuilder.setBaseUri(Path.BASE_URI);
		requestBuilder.setAuth(authScheme);
		requestSpec = requestBuilder.build();
		return requestSpec;
	}
	public static ResponseSpecification getResponseSpecification() {
		responseBuilder = new ResponseSpecBuilder();
		responseBuilder.expectStatusCode(200);
		responseBuilder.expectResponseTime(lessThan(7L), TimeUnit.SECONDS);
		responseSpec=responseBuilder.build();
		return responseSpec;
	}
	public static RequestSpecification createQueryParam(RequestSpecification rspec,String param,String value) {
		RequestSpecification rspecWithQparam = rspec.queryParam(param,value);
		return rspecWithQparam;
	}
	public static RequestSpecification createQueryParam(RequestSpecification rspec, Map<String,String> mp ) {
		RequestSpecification rspecWithQparam = rspec.queryParams(mp);
		return rspecWithQparam;
	}
	public static RequestSpecification createPathParam(RequestSpecification rspec,String param,String value) {
		RequestSpecification rspecWithPparam = rspec.pathParam(param,value);
		return rspecWithPparam;
	}
	public static RequestSpecification createPathParam(RequestSpecification rspec, Map<String,String> mp ) {
		RequestSpecification rspecWithPparam = rspec.pathParams(mp);
		return rspecWithPparam;
	}
	public static Response getResponse() {
		return given().get(ENDPOINT);
	}
	public static Response getResponse(RequestSpecification reqspec,String methodType) {
		requestSpec.spec(reqspec);
		Response response=null;
		if(methodType.equalsIgnoreCase("get")) {
			response=given().spec(requestSpec).get(ENDPOINT);
		}
		else if(methodType.equalsIgnoreCase("post")) {
			response=given().spec(requestSpec).post(ENDPOINT);
		}
		else if(methodType.equalsIgnoreCase("put")) {
			response=given().spec(requestSpec).put(ENDPOINT);
		}
		else if(methodType.equalsIgnoreCase("delete")) {
			response=given().spec(requestSpec).delete(ENDPOINT);
		}
		else {
			System.out.println("HTTPS MethodType is not supported");
		}
		response.then().log().all();
		response.then().spec(responseSpec);
		return response;
	}
	public static JsonPath getJSONPath(Response res) {
		String jsonpath = res.asString();
		return new JsonPath(jsonpath);	
	}
	public static XmlPath getXMLPath(Response res) {
		String xmlpath = res.asString();
		return new XmlPath(xmlpath);	
	}
	
	public static void resetBasePath() {
		RestAssured.basePath=null;
	}
	public static void setContentType(ContentType ct) {
		given().contentType(ct);
	}
	
}
