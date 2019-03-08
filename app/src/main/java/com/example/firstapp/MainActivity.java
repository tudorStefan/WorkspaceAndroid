package com.example.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "FVLmhB3kHmVAnSvqc1PcR6NQL1lIHYZReo1cfioB";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    String url ="https://api.nal.usda.gov/ndb/reports/?ndbno=01009&type=b&format=json&api_key=DEMO_KEY";
    String ndb_url = "";
    String example = "https://api.nal.usda.gov/ndb/search/?format=json&q=butter&sort=n&max=25&offset=0&api_key=DEMO_KEY";

    public void create_ndbno_request_url(View view){
        Log.d("DORIAN", "POPA");
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        String local_response;

        ndb_url = "https://api.nal.usda.gov/ndb/search/?format=json&q="+message+"&sort=n&max=25&offset=0&api_key="+API_KEY;

        getString(new VolleyCallback(){
            @Override
            public void onSuccess(String result){
                Log.d("DORIAN", result);
            }
        }, ndb_url);
        Log.d("DORIAN", "POPA_4");
    }

    public void getString(final VolleyCallback callback, String url) {
        Log.d("DORIAN", "POPA_0");
        Log.d("DORIAN", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest  strReq = new JsonObjectRequest (url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        // (optionally) some manipulation of the response
                        Log.d("DORIAN", "POPA_2");

                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        Log.d("DORIAN", "POPA_3");
        queue.add(strReq);
    }


    public void send_request (View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView textView = (TextView) findViewById(R.id.text);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Send button */
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

}

