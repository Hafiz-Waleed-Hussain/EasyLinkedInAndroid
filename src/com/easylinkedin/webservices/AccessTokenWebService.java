package com.easylinkedin.webservices;

import org.json.JSONObject;

import android.content.Context;

import com.interfaces.DownloadObserver;
import com.webservices.PostWebService;

public class AccessTokenWebService extends PostWebService{

	public AccessTokenWebService(Context context,
			DownloadObserver downloadObserver, String url, JSONObject data) {
		super(context, downloadObserver, url, data);
	}

}
