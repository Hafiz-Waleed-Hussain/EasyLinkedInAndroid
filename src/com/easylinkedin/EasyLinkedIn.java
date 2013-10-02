package com.easylinkedin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.easylinkedin.interfaces.Callback;
import com.easylinkedin.webservices.GetRequestWebService;
import com.interfaces.DownloadObserver;
import com.utils.UrlMaker;

public class EasyLinkedIn {

	private static EasyLinkedIn _EasyLinkedInReference = null;

	private UrlMaker _UrlMaker = null;

	private static String _ConsumerKey;

	private static String _ConsumerSecretKey;

	private static String _CallBackUrl;

	private static String _Scope;

	private static String _State;

	private static SharedPreferences _SharedPreference = null;
	

	public static final String EASY_LINKED_IN_ACCESS_TOKEN = "easy_linked_in_access_token";

	private GetRequestWebService _GetWebservice = null;
	
	private EasyLinkedIn(String consumerKey, String consumerSecretKey,
			String callBackUrl, String scope, String state) {

		_ConsumerKey = consumerKey;
		_ConsumerSecretKey = consumerSecretKey;
		_CallBackUrl = callBackUrl;
		_Scope = ((scope == null) ? "" : scope);
		_State = ((state == null || state.trim() == "") ? "STATE" : state);
		_UrlMaker = UrlMaker.getInstance();
	}

	/**
	 * 
	 * @param consumerKey
	 * @param consumerSecretKey
	 * @param callBackUrl
	 * @param scope
	 *            optional
	 * @param state
	 *            optional
	 */
	public static final EasyLinkedIn getInstance(Context context,
			String consumerKey, String consumerSecretKey, String callBackUrl,
			String scope, String state) {

		_SharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);

		if (_EasyLinkedInReference == null)
			_EasyLinkedInReference = new EasyLinkedIn(consumerKey,
					consumerSecretKey, callBackUrl, scope, state);
		return _EasyLinkedInReference;

	}

	static Callback authCallback;
	public void authorize(Context context, Callback callback) {

		authCallback = callback;
		Intent intent = new Intent(context, EasyLinkedInAuthActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 
	 * @param context
	 * @param downloadObserver
	 * @param fields
	 *            (optional) send null or for field names consult this link
	 *            http://developer.linkedin.com/documents/profile-fields
	 */
	public void getUserInfo(Context context, DownloadObserver downloadObserver,
			String fields) {

		String url = _UrlMaker.getUrl(UrlMaker.USER_INFO, fields);
		Log.d("Check", url);
		_GetWebservice = new GetRequestWebService(context,
				downloadObserver, url);
		_GetWebservice.execute();
	}

	/**
	 * 
	 * @param context
	 * @param downloadObserver
	 * @param fields
	 *            (optional) send null or for field names consult linkedin doc
	 *           
	 */
	public void getConnections(Context context,
			DownloadObserver downloadObserver, String fields) {

		String url = _UrlMaker.getUrl(UrlMaker.GET_CONNECTIONS, fields);
		Log.d("Check", url);
		_GetWebservice = new GetRequestWebService(context,
				downloadObserver, url);
		_GetWebservice.execute();

	}

	public void createCustom(Context context,
			DownloadObserver downloadObserver, String url) {


		Log.d("Check", url);		
		_GetWebservice = new GetRequestWebService(context, downloadObserver, url);
		_GetWebservice.execute();
		
	}

	static String get_ConsumerKey() {
		return _ConsumerKey;
	}

	static String get_ConsumerSecretKey() {
		return _ConsumerSecretKey;
	}

	static String get_CallBackUrl() {
		return _CallBackUrl;
	}

	static String get_Scope() {
		return _Scope;
	}

	static String get_State() {
		return _State;
	}

	public static SharedPreferences.Editor getSharedPreferenceEditor() {

		return _SharedPreference.edit();
	}

	public static boolean hasAccessToken() {

		return _SharedPreference.contains(EASY_LINKED_IN_ACCESS_TOKEN);
	}

	public static String getAccessToken() {
		return _SharedPreference.getString(EASY_LINKED_IN_ACCESS_TOKEN, null);
	}
}
