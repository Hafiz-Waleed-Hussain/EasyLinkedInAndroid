package com.webservices;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.interfaces.DownloadObserver;

public abstract class GetWebService extends WebService {

	public GetWebService(Context context, DownloadObserver downloadObserver,
			String url) {

		super(context, downloadObserver);
		_Request = new JsonObjectRequest(Request.Method.GET, url, null,
				reponseListener, errorListener);
	}

	public GetWebService(Context context, DownloadObserver downloadObserver,
			String url, final Map<String, String> header) {

		super(context, downloadObserver);
		_Request = new JsonObjectRequest(Request.Method.GET, url, null,
				reponseListener, errorListener) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return header;
			}
		};
	}

}
