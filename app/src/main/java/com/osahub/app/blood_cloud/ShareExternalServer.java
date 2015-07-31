package com.osahub.app.blood_cloud;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by anant on 31/07/15.
 */
public class ShareExternalServer {
    public String shareRegIdWithAppServer(final Context context,
                                          final String regId) {

        String result = "";

        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL("http://blood-cloud.appspot.com/GCMNotification?shareRegId="+regId);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.APP_SERVER_URL, e);
                result = "Invalid URL: " + Config.APP_SERVER_URL;
            }




            HttpURLConnection urlConnection = null;
            try {
                assert serverUrl != null;
                urlConnection = (HttpURLConnection) serverUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int status = urlConnection.getResponseCode();
                if (status == 200) {
                    result = "RegId shared with Application Server. RegId: "
                            + regId;
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }
}
