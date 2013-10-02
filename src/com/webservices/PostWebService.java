package com.webservices;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.interfaces.DownloadObserver;

public abstract class PostWebService extends WebService {

	private JSONObject _Data = null;

	public PostWebService(Context context, DownloadObserver downloadObserver,
			String url, JSONObject data) {

		super(context, downloadObserver);

		_Data = data;

		_Request = new JsonObjectRequest(Request.Method.POST, url, _Data,
				reponseListener, errorListener);

	}

	public PostWebService(Context context, DownloadObserver downloadObserver,
			String url, JSONObject data, final Map<String, String> header) {

		super(context, downloadObserver);

		_Data = data;

		_Request = new JsonObjectRequest(Request.Method.POST, url, _Data,
				reponseListener, errorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return header;
			}
		};

	}

}
