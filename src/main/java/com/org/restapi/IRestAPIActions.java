package com.org.restapi;

import java.util.Map;

import com.jayway.restassured.response.Response;

public interface IRestAPIActions {

	public String http_post(String uri, String request, Map<String, String> headers);

	public String http_put(String uri, String request, Map<String, String> headers);

	public String http_get(String uri, Map<String, String> headers);

	public String http_delete(String uri, Map<String, String> headers);

	public Response httpPOST(String uri, String request, Map<String, String> headers);

	public Response httpPUT(String uri, String request, Map<String, String> headers);

	public Response httpGET(String uri, Map<String, String> headers);

	public Response httpDELETE(String uri, Map<String, String> headers);

	public Response httpPATCH(String uri, String request, Map<String, String> headers);

	public String sendHTTPPOSTRequestFromJSONFile(String uri, String fileName, Map<String, String> headers);

	public String sendHTTPPUTRequestFromJSONFile(String uri, String fileName, Map<String, String> headers);

	public String sendHTTPPOSTArrayRequestFromJSONFile(String uri, String fileName, Map<String, String> headers);

	public String sendHTTPPUTArrayRequestFromJSONFile(String uri, String fileName, Map<String, String> headers);

}
