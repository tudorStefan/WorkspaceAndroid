package com.example.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "FVLmhB3kHmVAnSvqc1PcR6NQL1lIHYZReo1cfioB";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    String ndb_url = "";
    String example = "https://api.nal.usda.gov/ndb/search/?format=json&q=butter&sort=n&max=25&offset=0&api_key=DEMO_KEY";

    public void create_ndbno_request_url(View view){
        Log.d("DORIAN", "POPA");
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        String local_response;

        ndb_url = "https://api.nal.usda.gov/ndb/search/?format=json&q="+message+"&sort=n&max=5&offset=0&api_key="+API_KEY;
        Log.d("NDB url", ndb_url);
        getString(new VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result){
                try {
                    JSONArray matrice_json = result.getJSONObject("list").getJSONArray("item");
                    for (int i = 0; i < matrice_json.length(); i++){
                        Log.d("Dorian", matrice_json.getJSONObject(i).getString("ndbno"));
                    }
                    display_nbdno(matrice_json.getJSONObject(0).getString("ndbno"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        callback.onSuccess(response);
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

    public void display_nbdno(String ndb_no){
        Log.d("DORIAN_PPP", ndb_no);
        String url ="https://api.nal.usda.gov/ndb/reports/?ndbno="+ndb_no+"&type=b&format=json&api_key=DEMO_KEY";
        TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);
        TableRow tr = (TableRow) table_layout.findViewById(R.id.BigRow_0);
        final TextView name_View = (TextView) tr.findViewById(R.id.name_view);
        final TextView proteins_View = (TextView) findViewById(R.id.proteins_view);
        final TextView calories_View = (TextView) findViewById(R.id.calories_view);
        final TextView fats_View = (TextView) findViewById(R.id.fats_view);
        Log.d("URLK", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest  strReq = new JsonObjectRequest (url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        // (optionally) some manipulation of the response
                        try {
                            JSONObject matrice_json = response.getJSONObject("report").getJSONObject("food");
                            name_View.setText(matrice_json.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}

