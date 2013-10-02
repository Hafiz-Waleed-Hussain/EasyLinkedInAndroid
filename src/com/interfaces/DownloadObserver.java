package com.interfaces;

public interface DownloadObserver {

	void onDownloadingStart();
	
	void onDownloadingComplete(Object data);
	
	void onDownloadFailure(Object errorData);
}
