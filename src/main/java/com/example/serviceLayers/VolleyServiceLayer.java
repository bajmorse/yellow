package com.example.serviceLayers;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.models.YellowResponse;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by WX009-PC on 2/20/14.
 */
public class VolleyServiceLayer {
    private RequestQueue mRequestQueue;
    public VolleyServiceLayerCallback volleyServiceLayerCallback;

    public VolleyServiceLayer(Context context){
        mRequestQueue =  Volley.newRequestQueue(context);
    }

    public void GetListings(String url) {
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                YellowResponse yellowResponse = gson.fromJson(response.toString(), YellowResponse.class);
                volleyServiceLayerCallback.callbackCall(yellowResponse.getListings());
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGGING:", error.getMessage());
            }
        });
        mRequestQueue.add(jr);
    }
}

