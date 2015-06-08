package org.codeformiami.transittracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

class MyAsyncTask extends AsyncTask<String, String, Void> {

    private Activity activity;
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    String result = "";

    public MyAsyncTask(Activity activity, GoogleMap mMap) {
        super();
        this.activity = activity;
        this.mMap = mMap;
        progressDialog = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        /*progressDialog.setMessage("Downloading your data...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                MyAsyncTask.this.cancel(true);
            }
        });*/
    }

    @Override
    protected Void doInBackground(String... params) {

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        BufferedReader bReader = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpGet request = new HttpGet();
            URI website = new URI("http://miami-transit-api.herokuapp.com/api/Buses.json");
            request.setURI(website);
            HttpResponse response = httpclient.execute(request);
            bReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        } catch (UnsupportedEncodingException e1) {
            //Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (Exception e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            result = sBuilder.toString();

        } catch (Exception e) {
            //Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
      return null;
    } // protected Void doInBackground(String... params)

    protected void onPostExecute(Void v) {
        //parse JSON data
        try {
            JSONObject obj = new JSONObject(result);
            JSONObject recordSet = obj.getJSONObject("RecordSet");
            JSONArray jArray = recordSet.getJSONArray("Record");
            for(int i=0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                String tripHeadsign = jObject.getString("TripHeadsign");
                String routeId = jObject.getString("RouteID");
                String busId = jObject.getString("BusID");
                String serviceDirection = jObject.getString("ServiceDirection");
                String locationUpdated = jObject.getString("LocationUpdated");
                double latitude = jObject.getDouble("Latitude");
                double longitude = jObject.getDouble("Longitude");

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude,longitude))
                        .title(tripHeadsign)
                        .snippet("Route: "+routeId+" "+serviceDirection+" (Bus: "+busId+") "+locationUpdated)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            } // End Loop
            //this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)
    } // protected void onPostExecute(Void v)
} //class MyAsyncTask extends AsyncTask<String, String, Void>