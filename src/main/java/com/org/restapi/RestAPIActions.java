package com.org.restapi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.org.common.APIWebMobileUtility;

public class RestAPIActions extends APIWebMobileUtility {

	private static final String dataSheetPath = "/src/test/resource/com/org/json";
	protected static final Logger log = Logger.getLogger(RestAPIActions.class);

	/**
	 * Used to send HTTP_POST request
	 * 
	 * @param uri
	 * @param request
	 * @return String
	 */
	public String sendPOSTRequest(String uri, String request) {	
		String responseData = null;
		try {
			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				Response response = RestAssured.given().and().contentType("application/json").body(request).when()
						.post(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_PUT request
	 * 
	 * @param uri
	 * @param request
	 * @return String
	 */
	public String sendPUTRequest(String uri, String request) {
		String responseData = null;
		try {

			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request body is : " + request);

				Response response = RestAssured.given().and().contentType("application/json").body(request).when()
						.put(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to get HTTP_GET response
	 * 
	 * @param uri
	 * @return String
	 */
	public String getGETResponse(String uri) {
		String responseData = null;
		try {
			if (uri != null) {
				log.info("Requested URI : " + uri);
				Response response = RestAssured.given().contentType("application/json").when().get(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to delete resource By HTTP_Delete
	 * 
	 * @param uri
	 * @return String
	 */
	public String doDELETEResource(String uri) {
		String responseData = null;
		try {
			if (uri != null) {
				log.info("Requested URI : " + uri);
				Response response = RestAssured.given().and().contentType("application/json").when().delete(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_POST request
	 * 
	 * @param uri
	 * @param request
	 * @param headers
	 * @return String
	 */
	public String sendPOSTRequest(String uri, String request, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(request).when().post(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_PUT request
	 * 
	 * @param uri
	 * @param request
	 * @param headers
	 * @return String
	 */
	public String sendPUTRequest(String uri, String request, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request body is : " + request);

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(request).when().put(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_PATCH request
	 * 
	 * @param uri
	 * @param request
	 * @return String
	 */
	public String sendPATCHRequest(String uri, String request) {
		String responseData = null;
		try {

			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				Response response = RestAssured.given().and().contentType("application/json").body(request).when()
						.patch(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to get HTTP_PATCH response
	 * 
	 * @param uri
	 * @param headers
	 * @return Response
	 */
	public Response sendPATCHRequest(String uri, String request, LinkedHashMap<String, String> headers) {
		Response response = null;
		try {

			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				response = RestAssured.given().headers(headers).and().contentType("application/json").body(request)
						.when().patch(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to get HTTP_GET response
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String getGETResponse(String uri, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null) {
				log.info("Requested URI : " + uri);
				Response response = RestAssured.given().headers(headers).and().contentType("application/json").when()
						.get(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to delete resource By HTTP_Delete
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String doDELETEResource(String uri, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null) {
				log.info("Requested URI : " + uri);
				Response response = RestAssured.given().headers(headers).and().contentType("application/json").when()
						.delete(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_POST JSON request From File
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String sendHTTPPOSTRequestFromJSONFile(String uri, String fileName, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && fileName != null) {
				log.info("Requested URI : " + uri);
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".json"));

				String jsonRequest = object.toJSONString();
				log.info("Request body is : " + jsonRequest);

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(jsonRequest).when().post(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and fileName Can not be null");
				log.error("URI and fileName Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_PUT JSON request From File
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String sendHTTPPUTRequestFromJSONFile(String uri, String fileName, LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && fileName != null) {
				log.info("Requested URI : " + uri);
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".json"));

				String jsonRequest = object.toJSONString();
				log.info("Request body is : " + jsonRequest);

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(jsonRequest).when().put(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and fileName Can not be null");
				log.error("URI and fileName Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_POST Array JSON request From File
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String sendHTTPPOSTArrayRequestFromJSONFile(String uri, String fileName,
			LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && fileName != null) {
				log.info("Requested URI : " + uri);
				JSONParser parser = new JSONParser();
				JSONArray object = (JSONArray) parser.parse(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".json"));

				String jsonRequest = object.toString();
				log.info("Request body is : " + jsonRequest);

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(jsonRequest).when().post(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and fileName Can not be null");
				log.error("URI and fileName Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_PUT Array JSON request From File
	 * 
	 * @param uri
	 * @param headers
	 * @return String
	 */
	public String sendHTTPPUTArrayRequestFromJSONFile(String uri, String fileName,
			LinkedHashMap<String, String> headers) {
		String responseData = null;
		try {
			if (uri != null && fileName != null) {
				log.info("Requested URI : " + uri);
				JSONParser parser = new JSONParser();
				JSONArray object = (JSONArray) parser.parse(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".json"));

				String jsonRequest = object.toString();
				log.info("Request body is : " + jsonRequest);

				Response response = RestAssured.given().headers(headers).and().contentType("application/json")
						.body(jsonRequest).when().put(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and fileName Can not be null");
				log.error("URI and fileName Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}

	/**
	 * Used to send HTTP_POST request
	 * 
	 * @param uri
	 * @param request
	 * @return Response
	 */
	public Response sendHTTPPOSTRequest(String uri, String request) {
		Response response = null;
		try {
			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				response = RestAssured.given().and().contentType("application/json").body(request).when().post(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to send HTTP_PUT request
	 * 
	 * @param uri
	 * @param request
	 * @return Response
	 */
	public Response sendHTTPPUTRequest(String uri, String request) {
		Response response = null;
		try {

			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request body is : " + request);

				response = RestAssured.given().and().contentType("application/json").body(request).when().put(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to get HTTP_GET response
	 * 
	 * @param uri
	 * @return Response
	 */
	public Response getHTTPGETResponse(String uri) {
		Response response = null;
		try {

			if (uri != null) {
				log.info("Requested URI : " + uri);
				response = RestAssured.given().and().contentType("application/json").when().get(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to delete resource By HTTP_Delete
	 * 
	 * @param uri
	 * @return Response
	 */
	public Response doHTTPDELETEResource(String uri) {
		Response response = null;
		try {

			if (uri != null) {
				log.info("Requested URI : " + uri);
				response = RestAssured.given().and().contentType("application/json").when().delete(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to send HTTP_POST request
	 * 
	 * @param uri
	 * @param request
	 * @param headers
	 * @return Response
	 */
	public Response sendHTTPPOSTRequest(String uri, String request, LinkedHashMap<String, String> headers) {
		Response response = null;
		try {

			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request is : " + request + "\n");

				response = RestAssured.given().headers(headers).and().contentType("application/json").body(request)
						.when().post(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to send HTTP_PUT request
	 * 
	 * @param uri
	 * @param request
	 * @param headers
	 * @return Response
	 */
	public Response sendHTTPPUTRequest(String uri, String request, LinkedHashMap<String, String> headers) {
		Response response = null;
		try {
			if (uri != null && request != null) {
				log.info("Requested URI : " + uri);
				log.info("Request body is : " + request);

				response = RestAssured.given().headers(headers).and().contentType("application/json").body(request)
						.when().put(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI and Request Can not be null");
				log.error("URI and Request Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to get HTTP_GET response
	 * 
	 * @param uri
	 * @param headers
	 * @return Response
	 */
	public Response getHTTPGETResponse(String uri, LinkedHashMap<String, String> headers) {
		Response response = null;
		try {

			if (uri != null) {
				log.info("Requested URI : " + uri);
				response = RestAssured.given().headers(headers).and().contentType("application/json").when().get(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * Used to delete resource By HTTP_Delete
	 * 
	 * @param uri
	 * @param headers
	 * @return Response
	 */
	public Response doHTTPDELETEResource(String uri, LinkedHashMap<String, String> headers) {
		Response response = null;
		try {

			if (uri != null) {
				log.info("Requested URI : " + uri);
				response = RestAssured.given().headers(headers).and().contentType("application/json").when()
						.delete(uri);
				String responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n" + responseData + "\n");
			} else {
				stepFail("URI Can not be null");
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			stepFail("Not getting any Response from Server");
			Assert.fail("Not getting any Response from Server", e);
		}
		return response;
	}

	/**
	 * used to get JSON String from JSON file.
	 * 
	 * @param filePath
	 * @return String
	 */
	public String getJSONStringFromJSONFile(String filePath) {

		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) parser.parse(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String jsonRequest = object.toJSONString();
		return jsonRequest;
	}

	/**
	 * @param index
	 * @return String
	 * @Description getTestCaseName based on index
	 */
	protected String getTestCaseName(int index) {

		StackTraceElement e[] = Thread.currentThread().getStackTrace();
		String methodName = e[index].getMethodName();

		return methodName;
	}

	/**
	 * @param folderName
	 * @return String
	 * @Description get request file path
	 */
	protected String getRequestFilePath(String folderName) {

		String filePath = System.getProperty("baseDir") + folderName + "/" + getTestCaseName(3) + ".json";
		return filePath;
	}

	protected String todayDate() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String tdate = sdf.format(date);
		System.out.println(tdate);
		return tdate;
	}

}
