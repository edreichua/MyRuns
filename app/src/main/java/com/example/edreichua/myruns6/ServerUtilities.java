package com.example.edreichua.myruns6;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Helper class used to communicate with the AppEngine server.
 */
public final class ServerUtilities {
	
	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();


	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            POST address.
	 * @param jArray
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */
	public static void post(String endpoint, JSONArray jArray)
			throws IOException {
		Log.d("Testing url", endpoint);
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(endpoint);


		try {
			StringEntity se = new StringEntity(jArray.toString());
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// post the request
			HttpResponse response = httpClient.execute(httpPost, httpContext);

			// handle the response
			int status = response.getStatusLine().getStatusCode();
			Log.d("TAGG",""+status);
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}

			// Get Response
			HttpEntity entity = response.getEntity();

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
