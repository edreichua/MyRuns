package com.example.edreichua.myruns6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Helper class used to communicate with the AppEngine server.
 *
 */
public final class ServerUtilities {

	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            POST address.
	 * @param params
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */
	public static void post(String endpoint, Map<String,String> params)
			throws IOException {

		// Test for malformed URL
		Log.d("Testing url", endpoint);
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}

		// Build the string and construct the post parameters
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

		// Use the post structure for construction
		while (iterator.hasNext()) {
			Map.Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}

		// Convert post message to bytes
		String body = bodyBuilder.toString();
		Log.d("Testing body", body);
		byte[] bytes = body.getBytes();

		// Send message to server
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			// Post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();

			// Handle the response
			int status = conn.getResponseCode();
			Log.d("HTTP response status",""+status);

			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}

			// Get Response
			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();

			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			Log.d("Test response",response.toString());
			rd.close();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
