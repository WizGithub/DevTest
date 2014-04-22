package com.example.webnetworktest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		WebView webView1;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			webView1 = (WebView) getView().findViewById(R.id.webView1);
			webView1.requestFocus();
			webView1.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
			webView1.setHorizontalScrollBarEnabled(false);
			webView1.setWebChromeClient(new MyWebChromeClient());
			webView1.getSettings().setDefaultTextEncodingName("euc-kr");
			webView1.setWebViewClient(new MyWebClient());
			WebSettings set = webView1.getSettings();
			set.setJavaScriptEnabled(true);	
			
		}

		@Override
		public void onResume() {
			String loadurl = "http://api.bellstore.co.kr/bell/WIZ_Etiquette_index.asp?ctn="
					+ "lnfzb9SillYirka12Y3MbQ=="
					+ "&site="
					+ 50019
					+ "&version=" + "1.1.19";
					
			webView1.loadUrl(loadurl);
			super.onResume();
		}


		class MyWebChromeClient extends WebChromeClient {
			public void onProgressChanged(WebView view, int progress) {

			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {

				// TODO Auto-generated method stub
				new AlertDialog.Builder(view.getContext())
						.setTitle("확인")
						.setMessage(message)
						.setNegativeButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										result.confirm();
									}
								})
						.setPositiveButton(android.R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										result.cancel();
									}
								}).setCancelable(false).create().show();

				return true;
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				new AlertDialog.Builder(view.getContext())
						.setTitle("확인")
						.setMessage(message)
						.setPositiveButton(android.R.string.ok,
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										result.confirm();
									}
								}).setCancelable(false).create().show();

				return true;
			}
		}

		class MyWebClient extends WebViewClient {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.loadUrl("file:///android_asset/error.html");
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			public void onPageFinished(WebView view, String url) {

				super.onPageFinished(view, url);
			}

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		}

	}

}
