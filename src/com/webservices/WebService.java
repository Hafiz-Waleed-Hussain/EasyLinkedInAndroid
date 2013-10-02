package com.webservices;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.interfaces.DownloadObserver;
import com.interfaces.Executor;

public abstract class WebService implements Executor {

	protected RequestQueue _RequestQueue = null;

	protected DownloadObserver _DownloadObserver = null;

	protected JsonObjectRequest _Request = null;


	public WebService(Context context, DownloadObserver _DownloadObserver) {

		this._RequestQueue = Volley.newRequestQueue(context);
		this._DownloadObserver = _DownloadObserver;
	}

	protected Response.Listener<JSONObject> reponseListener = new Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {

			_DownloadObserver.onDownloadingComplete(response);
		}
	};

	protected Response.ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {

			_DownloadObserver.onDownloadFailure(error);

		}

	};

	@Override
	public void execute() {

		_RequestQueue.add(_Request);
		_DownloadObserver.onDownloadingStart();

	}

}
