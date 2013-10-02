package com.utils;

import com.easylinkedin.EasyLinkedIn;

public class UrlMaker {

	private static UrlMaker _UrlMakerReference = null;

	private static final String SERVER_NAME = "https://api.linkedin.com/v1/";

	public static final int USER_INFO = 100;
	public static final int GET_CONNECTIONS = 101;

	private UrlMaker() {

	}

	public static UrlMaker getInstance() {

		if (_UrlMakerReference == null)
			_UrlMakerReference = new UrlMaker();
		return _UrlMakerReference;
	}

	public String getUrl(int code, String fields) {

		switch (code) {
		case USER_INFO:
			return getUserInfoUrl(fields);

		case GET_CONNECTIONS:
			return getConnections(fields);

		default:
			return null;
		}
	}


	private String getUserInfoUrl(String fields) {

		String fieldsContainer = getFieldContainer(fields);
		return SERVER_NAME + "people/~" + fieldsContainer
				+ "?format=json&oauth2_access_token="
				+ EasyLinkedIn.getAccessToken();

	}

	private String getConnections(String fields) {

		String fieldsContainer = getFieldContainer(fields);
		return SERVER_NAME+"people/~/connections"+fieldsContainer+"?format=json&oauth2_access_token="+EasyLinkedIn.getAccessToken();
	
	}
	
	
	private String getFieldContainer(String fields) {
		String fieldsContainer = null;

		if (fields == null || fields.trim().equals(""))
			fieldsContainer = "";
		else
			fieldsContainer = ":(" + fields + ")";
		return fieldsContainer;
	}
}
