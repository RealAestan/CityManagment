package com.example.lp.cours1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lp.cours1.model.City;

import java.util.List;

public class CityListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<City> cityItems;

    public CityListAdapter(Activity activity, List<City> movieItems) {
        this.activity = activity;
        this.cityItems = movieItems;
    }

    @Override
    public int getCount() {
        return cityItems.size();
    }

    @Override
    public Object getItem(int location) {
        return cityItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        Button deleteBtn = (Button) convertView.findViewById(R.id.delete_btn);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView latitude = (TextView) convertView.findViewById(R.id.latitude);
        TextView longitude = (TextView) convertView.findViewById(R.id.longitude);

        // getting city data for the row
        final City c = cityItems.get(position);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                askOption(parent, c, position).show();
            }
        });

        name.setText(c.getNom_Ville());
        latitude.setText(c.getLatitude());
        longitude.setText(c.getLongitude());

        return convertView;
    }

    private AlertDialog askOption(final ViewGroup parent, final City c, final int position)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(parent.getContext())
                //set message, title, and icon
                .setTitle("Supprimer")
                .setMessage("Êtes-vous sûr de vouloir supprimer ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, MainActivity.DELETE_URL.concat(c.getCode_INSEE()),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    cityItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }
                        );
                        // Add the request to the RequestQueue.
                        SingletonVolley.getInstance(parent.getContext()).addToRequestQueue(stringRequest);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}
