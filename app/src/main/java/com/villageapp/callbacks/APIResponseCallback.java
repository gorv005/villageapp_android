package com.villageapp.callbacks;

import com.villageapp.network.RestResponse;



public interface APIResponseCallback<T> {

    void onResponse(RestResponse<T> response);
}
