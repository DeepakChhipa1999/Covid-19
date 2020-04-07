package com.covidvirus.app.data.network.services.location;

import com.covidvirus.app.data.network.model.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationApi {

    @GET("/json")
    Call<Location> getLocationData();


}
