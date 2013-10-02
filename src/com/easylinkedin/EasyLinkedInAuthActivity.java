package com.easylinkedin;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easylinkedin.interfaces.EasyLinkedInConstants;
import com.easylinkedin.webservices.AccessTokenWebService;
import com.interfaces.DownloadObserver;
import com.utils.DialogBuilder;

public class EasyLinkedInAuthActivity extends Activity {

	private WebView _WebView = null;

	private Dialog _Dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.linkedin_auth_activity);
		initViews();
		setUpViews();

	}

	private void initViews() {

		_WebView = (WebView) findViewById(R.id.LinkedInAuthActivity_web_view);

	}

	private void setUpViews() {

		String url = getOauthUrl(EasyLinkedIn.get_ConsumerKey(),
				EasyLinkedIn.get_CallBackUrl(), EasyLinkedIn.get_State(),
				EasyLinkedIn.get_Scope());
		configureWebView(_WebView.getSettings());
		_WebView.setWebViewClient(new MyClient());
		_WebView.loadUrl(url);

	}

	class MyClient extends WebViewClient {

		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);

		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d("Check", url);

			Uri uri = Uri.parse(url);
			Uri callBackUri = Uri.parse(EasyLinkedIn.get_CallBackUrl());

			String host = uri.getHost();
			String callBackHost = callBackUri.getHost();

			if (host.equals(callBackHost)) {

				view.stopLoading();
				String code = uri.getQueryParameter("code");

				AccessTokenWebService webservice = new AccessTokenWebService(
						EasyLinkedInAuthActivity.this,
						accessTokenDownloadObserver, getAccessTokenUrl(code,
								EasyLinkedIn.get_CallBackUrl(),
								EasyLinkedIn.get_ConsumerKey(),
								EasyLinkedIn.get_ConsumerSecretKey()),
						new JSONObject());
				webservice.execute();
				Log.d("Check", code);

			}

			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);
			goneWebView();
			showDialog();

		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
			showWebView();
			dismissDialog();

		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void configureWebView(WebSettings webSettings) {

		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginsEnabled(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);

	}

	private void showDialog() {

		_Dialog = DialogBuilder.BuildDialog(this);
		_Dialog.show();
	}

	private void dismissDialog() {

		_Dialog.dismiss();
	}

	private void showWebView() {

		_WebView.setVisibility(WebView.VISIBLE);

	}

	private void goneWebView() {

		_WebView.setVisibility(WebView.GONE);

	}

	public String getOauthUrl(String consumerKey, String redirectUri,
			String state, String scope) {

		return String
				.format(EasyLinkedInConstants.SERVER_NAME
						+ "uas/oauth2/authorization?response_type=code&client_id=%s&state=%s&redirect_uri=%s&scope=%s",
						consumerKey, state, redirectUri, scope);
	}

	public String getAccessTokenUrl(String authorizeCode, String redirectUri,
			String consumerKey, String consumerSecretKey) {

		return String
				.format(EasyLinkedInConstants.SERVER_NAME
						+ "uas/oauth2/accessToken?grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s",
						authorizeCode, redirectUri, consumerKey,
						consumerSecretKey);
	}

	private DownloadObserver accessTokenDownloadObserver = new DownloadObserver() {

		@Override
		public void onDownloadingStart() {

			showDialog();

		}

		@Override
		public void onDownloadingComplete(Object data) {

			Log.d("Check", data.toString());
			try {
				JSONObject jsonData = new JSONObject(data.toString());
				EasyLinkedIn
						.getSharedPreferenceEditor()
						.putString(EasyLinkedIn.EASY_LINKED_IN_ACCESS_TOKEN,
								jsonData.getString("access_token")).commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dismissDialog();
			finish();
		}

		@Override
		public void onDownloadFailure(Object errorData) {

			dismissDialog();
			finish();

		}
	};
}
