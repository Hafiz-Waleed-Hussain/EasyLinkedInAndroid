EasyLinkedInAndroid
===================

Version 1.0 (Beta)
In this version i added these features.
. LinkedIn Authorization
. LinkedIn UserInfo
. LinkedIn Connections
. LinkedIn CutomAPI (In this feature you create the url on your own which are given on LinkedIn doc and send that url through my function and it give you the response. But at this time it only support GET Request)

First add this library in your Android project.
Add Internet Permission in your manifest
   <uses-permission android:name="android.permission.INTERNET" />
  
Add activity in manifest.

        <activity
            android:name="com.easylinkedin.EasyLinkedInAuthActivity"
            android:screenOrientation="portrait" >
            

Now in your Java class getInstance of EasyLinkedIn class;

  private EasyLinkedIn _EasyLinkedIn = null;
  
  _EasyLinkedIn = EasyLinkedIn.getInstance(this, "consumerkey",
				"consumersecretkey", "callbackurl", "", "");
  
  For Authorization simple call
  
    	_EasyLinkedIn.authorize(MainActivity.this, new Callback() {
				
				@Override
				public void onSucess(Object data) {
					
				}
				
				@Override
				public void onFailure() {
					
				}
			});
    
  For getting user info
 
      String fields = "id,first-name,headline";
      or 
      String fields = null;
   
    _EasyLinkedIn.getUserInfo(this,getUserInfoDownloadObserver, fields);
    
  // Here getUserInfoDownloadObserver is a simple interface which tell you about the call like request start, request complete with success or failure.
  
  DownloadObserver getUserInfoDownloadObserver = new DownloadObserver() {

		@Override
		public void onDownloadingStart() {

		}

		@Override
		public void onDownloadingComplete(Object data) {

		}

		@Override
		public void onDownloadFailure(Object errorData) {

		}
	};
    
    For getting any custom info which at this time is not added in the libray. For example i want to get user connection but at this time library not give this functionaliyt. Now i can do this in a very simple way.
    
      // The below url i get from linkedin doc. So you can easily get url from linkedin doc.
    	String url = "https://api.linkedin.com/v1/people/~/connections:(id)?format=json&oauth2_access_token="+EasyLinkedIn.getAccessToken();
			_EasyLinkedIn.createCustom(this, DownloadObserver, url);
