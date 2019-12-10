package com.example.appmenu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("suggest")
    Call<API_get> getPlats(@Body API_send arrays);
}
