package com.example.edreichua.myruns6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
	 * @param jArrayString
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */
	public static void post(String endpoint, String jArrayString)
			throws IOException {
		Log.d("Testing url", endpoint);
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}

		// Save parameters to map
		Map<String,String> params = new HashMap<>();
		params.put("result",jArrayString);
		params.put("regId",Globals.regID);

		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Map.Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}

		// Send message to server
		String body = bodyBuilder.toString();
		Log.d("Testing body", body);

		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			Log.d("TAGG",""+status);

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
