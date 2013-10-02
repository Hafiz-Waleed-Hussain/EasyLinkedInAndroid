package com.easylinkedin.webservices;

import android.content.Context;

import com.interfaces.DownloadObserver;
import com.webservices.GetWebService;

public class GetRequestWebService extends GetWebService{

	public GetRequestWebService(Context context,
			DownloadObserver downloadObserver, String url) {
		super(context, downloadObserver, url);
	
	}

	
}
