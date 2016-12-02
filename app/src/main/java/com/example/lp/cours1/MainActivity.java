package com.example.lp.cours1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lp.cours1.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
//    private ListView mListView;
    private List<City> cityList = new ArrayList<>();
    private CityListAdapter adapter;

    public static final String URL = "http://192.168.240.22/webservice/villes";
    public static final String DELETE_URL = "http://192.168.240.22/webservice/ville/";

    private ProgressDialog pDialog;
    private static final String KEY_NAME = "Nom_Ville";
    private static final String KEY_CAPS_NAME = "MAJ";
    private static final String KEY_ZIPCODE = "Code_Postal";
    private static final String KEY_INSEE = "Code_INSEE";
    private static final String KEY_REGION = "Code_Region";
    private static final String KEY_LATITUDE = "Latitude";
    private static final String KEY_LONGITUDE = "Longitude";
    private static final String KEY_REMOTENESS = "Eloignement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView mListView = (ListView) findViewById(R.id.list_view);
        adapter = new CityListAdapter(this, cityList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(MainActivity.this, SecondaryActivity.class);
                appInfo.putExtra("INSEE", cityList.get(position).getCode_INSEE());
                startActivity(appInfo);
            }
        });

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request

        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Vous n'avez pas de connexion internet !",Toast.LENGTH_LONG).show();
        } else {
            pDialog.setMessage("Chargement...");
            pDialog.show();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    City city = new City();
                                    city.setNom_Ville(obj.getString(KEY_NAME));
                                    city.setLatitude(obj.getString(KEY_LATITUDE));
                                    city.setLongitude(obj.getString(KEY_LONGITUDE));
                                    city.setCode_INSEE(obj.getString(KEY_INSEE));
                                    // adding movie to cities array
                                    cityList.add(city);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hidePDialog();
                        }
                    });
            SingletonVolley.getInstance(this).addToRequestQueue(jsonArrayRequest);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
