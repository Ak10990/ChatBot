package com.hiq.freedomvision.apis;

import com.hiq.freedomvision.models.DestinationResult;
import com.hiq.freedomvision.models.LeadFormResult;
import com.hiq.freedomvision.models.MainObjectResult;
import com.hiq.freedomvision.models.WeatherResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @FormUrlEncoded
    @POST("/results")
    Call<MainObjectResult> listMainObjectResponse(@Field("query") String hotelTypeQuery);

    @FormUrlEncoded
    @POST("/results")
    Call<WeatherResult> listWeatherResponse(@Field("query") String destinationName);

    @FormUrlEncoded
    @POST("/results")
    Call<DestinationResult> listDestinationResponse(@Field("query") String destinationName);

    @FormUrlEncoded
    @POST("/appleads")
    Call<LeadFormResult> listLeadFormResponse(@Field("name") String name, @Field("email") String email,
                                              @Field("mobile") String mobile, @Field("source_city") String sourceCity,
                                              @Field("no_of_people") String no_of_people, @Field("nights") String nights,
                                              @Field("price_category") String price_category, @Field("destination_city") String destinationCity);

}