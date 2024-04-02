package com.quynhlm.dev.lab8.Service;

import com.quynhlm.dev.lab8.Model.District;
import com.quynhlm.dev.lab8.Model.DistrictRequest;
import com.quynhlm.dev.lab8.Model.Province;
import com.quynhlm.dev.lab8.Model.ResponseGHN;
import com.quynhlm.dev.lab8.Model.Ward;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/shiip/public-api/master-data/province")
    Call<ResponseGHN<ArrayList<Province>>> getListProvince();

    @POST("/shiip/public-api/master-data/district")
    Call<ResponseGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("/shiip/public-api/master-data/ward")
    Call<ResponseGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);

}
