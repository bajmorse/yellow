package com.example.serviceLayers;

import com.example.models.Listing;
import java.util.List;

/**
 * Created by WX009-PC on 2/20/14.
 */
public interface VolleyServiceLayerCallback {
    void callbackCall(List<Listing> listings);
}
